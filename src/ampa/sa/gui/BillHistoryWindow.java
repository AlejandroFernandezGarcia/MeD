package ampa.sa.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import ampa.sa.bill.Bill;
import ampa.sa.bill.BillLine;
import ampa.sa.bill.BillService;
import ampa.sa.booking.BookingService;
import ampa.sa.student.FamilyService;
import ampa.sa.student.Household;
import ampa.sa.student.Student;
import ampa.sa.util.exceptions.BillNotFoundException;

public class BillHistoryWindow extends JFrame {

	private JPanel contentPane;
	private JTable billTable;
	private Household household;
	private BillService billService;
	private JTable tableLinesBill;
	private JTable table;
	private FamilyService familyService;
	private JTabbedPane tabbedPane;
	private BookingService bookingService;
	public static final String DATE_FORMAT = "dd/MM/yyyy";

	/**
	 * Create the frame.
	 * 
	 * @throws BillNotFoundException
	 */
	public BillHistoryWindow(Household h) {
		this.household = h;

		this.familyService = FamilyService.getInstance();
		this.billService = BillService.getInstance();

		String[] yearString = new String[20];
		String[] monthString = new String[12];
		monthString[0] = "ENERO";
		monthString[1] = "FEBRERO";
		monthString[2] = "MARZO";
		monthString[3] = "ABRIL";
		monthString[4] = "MAYO";
		monthString[5] = "JUNIO";
		monthString[6] = "JULIO";
		monthString[7] = "AGOSTO";
		monthString[8] = "SEPTIEMBRE";
		monthString[9] = "OCTUBRE";
		monthString[10] = "NOVIEMBRE";
		monthString[11] = "DICIEMBRE";

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 464, 315);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JComboBox<Bill> comboBox = new JComboBox<Bill>();

		Set<Bill> bills = household.getBills();

		if (bills.size() == 0) {
			comboBox.setEnabled(false);
		} else {
			Iterator<Bill> iter = bills.iterator();
			while (iter.hasNext()) {
				comboBox.addItem(iter.next());
			}
			comboBox.setSelectedIndex(0);
		}

		JButton btnNewButton = new JButton("Buscar");

		JPanel panel = new JPanel();

		JLabel lblNewLabel = new JLabel("N\u00FAcleo Familiar: "
				+ household.getBanckAccount());

		JLabel lblNewLabel_1 = new JLabel("Coste Total: ");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane
				.createParallelGroup(Alignment.LEADING)
				.addGroup(
						gl_contentPane
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(comboBox,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE).addGap(145)
								.addComponent(btnNewButton)
								.addContainerGap(186, Short.MAX_VALUE))
				.addComponent(panel, Alignment.TRAILING,
						GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)
				.addGroup(
						gl_contentPane
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(lblNewLabel)
								.addPreferredGap(ComponentPlacement.RELATED,
										118, Short.MAX_VALUE)
								.addComponent(lblNewLabel_1).addGap(111)));
		gl_contentPane
				.setVerticalGroup(gl_contentPane
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_contentPane
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																comboBox,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																btnNewButton))
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																lblNewLabel)
														.addComponent(
																lblNewLabel_1))
										.addPreferredGap(
												ComponentPlacement.RELATED, 26,
												Short.MAX_VALUE)
										.addComponent(panel,
												GroupLayout.PREFERRED_SIZE,
												177, GroupLayout.PREFERRED_SIZE)));

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(
				Alignment.LEADING).addComponent(tabbedPane,
				GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(
				Alignment.LEADING).addGroup(
				Alignment.TRAILING,
				gl_panel.createSequentialGroup()
						.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE,
								176, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				tabbedPane.removeAll();

				Bill r = (Bill) comboBox.getSelectedItem();
				lblNewLabel_1.setText("Coste Total: " + r.getTotal());

				int i = 0;

				List<Student> students = familyService.findStudents(household);
				for (Student student : students) {
					Calendar now = Calendar.getInstance();
					if ((student.getDeleteDate().get(Calendar.MONTH) < now
							.get(Calendar.MONTH) && (student.getDeleteDate()
							.get(Calendar.YEAR) == now.get(Calendar.YEAR)))
							|| (student.getDeleteDate().get(Calendar.YEAR) < now
									.get(Calendar.YEAR))) {
						continue;
					}
					tabbedPane.addTab(student.getName(),
							createPanelBills(r, student));
					tabbedPane.setSelectedIndex(i);
					i++;
				}

			}
		});

		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);

	}

	private JPanel createPanelBills(Bill r, Student s) {

		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_1, null);

		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(gl_panel_1.createParallelGroup(
				Alignment.LEADING).addComponent(scrollPane,
				GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel_1
						.createSequentialGroup()
						.addGap(26)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE,
								81, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(24, Short.MAX_VALUE)));

		table = new JTable();

		table.setModel(new DefaultTableModel(
				new Object[][] { { null, null }, }, new String[] { "Concepto",
						"Precio Unidad", "Cantidad" }) {
			Class[] columnTypes = new Class[] { String.class, Object.class,
					Object.class };

		});
		scrollPane.setViewportView(table);
		panel_1.setLayout(gl_panel_1);

		fillConceptTable(r, s, table);

		return panel_1;
	}

	private void fillConceptTable(Bill r, Student s, JTable table) {
		bookingService = BookingService.getInstance();
		DefaultTableModel dtm = (DefaultTableModel) table.getModel();
		for (int i = 0; i < dtm.getRowCount(); i++) {
			dtm.removeRow(i);
		}

		Set<BillLine> billLines = billService.getBillLinesByStudent(r, s);
		for (BillLine rl : billLines) {
			Object[] data = { rl.getConcept(), rl.getUnitPrice(), rl.getUnits() };
			dtm.addRow(data);
		}

		table.updateUI();
	}
}
