package ampa.sa.gui;

import static org.junit.Assert.assertTrue;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import ampa.sa.booking.Booking;
import ampa.sa.booking.BookingService;
import ampa.sa.diningHall.DiningHall;
import ampa.sa.receipt.Receipt;
import ampa.sa.receipt.ReceiptLine;
import ampa.sa.receipt.ReceiptService;
import ampa.sa.student.FamilyService;
import ampa.sa.student.Household;
import ampa.sa.student.Student;
import ampa.sa.util.exceptions.InstanceNotFoundException;
import ampa.sa.util.exceptions.ReceiptsNotFoundException;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTabbedPane;

public class BillHistoryWindow extends JFrame {

	private JPanel contentPane;
	private JTable receiptTable;
	private Household household;
	private ReceiptService receiptService;
	private JTable tableLinesBill;
	private JTable table;
	private FamilyService familyService;
	private JTabbedPane tabbedPane;
	private BookingService bookingService;
	public static final String DATE_FORMAT = "dd/MM/yyyy";

	/**
	 * Create the frame.
	 * 
	 * @throws ReceiptsNotFoundException
	 */
	public BillHistoryWindow(Household h) throws ReceiptsNotFoundException{
		setResizable(false);

		this.household = h;

		this.familyService = FamilyService.getInstance();
		this.receiptService = ReceiptService.getInstance();

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
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JComboBox comboBox = new JComboBox(monthString);

		comboBox.setSelectedIndex(0);

		for (int i = 0; i < yearString.length; i++) {
			yearString[i] = String.valueOf(2000 + i);
		}

		JComboBox comboBox_1 = new JComboBox(yearString);
		comboBox_1.setSelectedIndex(14);

		JButton btnNewButton = new JButton("Buscar");

		JPanel panel = new JPanel();

		JLabel lblNewLabel = new JLabel("N\u00FAcleo Familiar: "
				+ household.getBanckAccount());

		JLabel lblNewLabel_1 = new JLabel("Coste Total: ");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(58)
					.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(59)
					.addComponent(btnNewButton)
					.addContainerGap(186, Short.MAX_VALUE))
				.addComponent(panel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.RELATED, 118, Short.MAX_VALUE)
					.addComponent(lblNewLabel_1)
					.addGap(111))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(lblNewLabel_1))
					.addPreferredGap(ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 177, GroupLayout.PREFERRED_SIZE))
		);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				tabbedPane.removeAll();

				SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
				Calendar cb = Calendar.getInstance();
				String s = new String("01/");
				if (comboBox.getSelectedIndex() < 10) {
					s += ("0");
					s += ((comboBox.getSelectedIndex() + 1));
					s += ("/");
				} else {
					s += (Integer.toString(comboBox.getSelectedIndex()));
					s += ("/");
				}
				s += (Integer.toString(2000 + comboBox_1.getSelectedIndex()));

				try {
					cb.setTime(sdf.parse(s));
				} catch (ParseException t) {
					assertTrue("Bad format to calendar", false);
				}

				Receipt r = null;
				try {
					r = receiptService.findReceiptByDate(h, cb);
					lblNewLabel_1.setText("Coste Total: " + r.getTotal());

					int i = 0;

					List<Student> students = familyService
							.findStudents(household);
					for (Student student : students) {
						tabbedPane.addTab(student.getName(),
								createPanelBills(r, student));
						tabbedPane.setSelectedIndex(i);
						i++;
					}

				} catch (InstanceNotFoundException e1) {
				//	e1.printStackTrace();
					lblNewLabel_1.setText("Coste Total: NaN");
				}

			}
		});

		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);

	}

	private JPanel createPanelBills(Receipt r, Student s) {

		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_1, null);

		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(gl_panel_1.createParallelGroup(
				Alignment.LEADING).addComponent(scrollPane,
				GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE));
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

	private void fillConceptTable(Receipt r, Student s, JTable table) {
		bookingService = BookingService.getInstance();
		DefaultTableModel dtm = (DefaultTableModel) table.getModel();
		for (int i = 0; i < dtm.getRowCount(); i++) {
			dtm.removeRow(i);
		}

		Set<ReceiptLine> billLines = receiptService.getReceiptLinesByStudent(r,
				s);
		for (ReceiptLine rl : billLines) {
			Object[] data = { rl.getConcept(), rl.getUnitPrice(), rl.getUnits() };
			dtm.addRow(data);
		}

		table.updateUI();// DUDA Es necesario?
	}
}
