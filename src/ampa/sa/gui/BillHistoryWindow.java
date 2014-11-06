package ampa.sa.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

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

import ampa.sa.receipt.Receipt;
import ampa.sa.receipt.ReceiptLine;
import ampa.sa.receipt.ReceiptService;
import ampa.sa.student.Household;
import ampa.sa.util.exceptions.ReceiptsNotFoundException;

public class BillHistoryWindow extends JFrame {

	private JPanel contentPane;
	private JTable receiptTable;
	private Household household;
	private ReceiptService receiptService;
	private JTable tableLinesBill;

	/**
	 * Launch the application.
	 */
	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { BillHistoryWindow frame = new
	 * BillHistoryWindow(); frame.setVisible(true); } catch (Exception e) {
	 * e.printStackTrace(); } } }); } �
	 */
	/**
	 * Create the frame.
	 * 
	 * @throws ReceiptsNotFoundException
	 */
	public BillHistoryWindow(Household h) throws ReceiptsNotFoundException {

		this.household = h;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JPanel leftPanel = new JPanel();
		contentPane.add(leftPanel);
		leftPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel rigthPanel = new JPanel();
		contentPane.add(rigthPanel);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(
				Alignment.TRAILING).addGroup(
				Alignment.LEADING,
				gl_contentPane
						.createSequentialGroup()
						.addComponent(leftPanel, GroupLayout.PREFERRED_SIZE,
								212, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED,
								GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(rigthPanel, GroupLayout.PREFERRED_SIZE,
								218, GroupLayout.PREFERRED_SIZE)));
		gl_contentPane.setVerticalGroup(gl_contentPane
				.createParallelGroup(Alignment.LEADING)
				.addComponent(leftPanel, Alignment.TRAILING,
						GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
				.addComponent(rigthPanel, GroupLayout.DEFAULT_SIZE, 252,
						Short.MAX_VALUE));

		JScrollPane scrollPane = new JScrollPane();
		leftPanel.add(scrollPane);
		
		GroupLayout gl_leftPanel = new GroupLayout(leftPanel);
		gl_leftPanel.setHorizontalGroup(gl_leftPanel.createParallelGroup(
				Alignment.LEADING).addComponent(scrollPane,
				GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE));
		gl_leftPanel.setVerticalGroup(gl_leftPanel.createParallelGroup(
				Alignment.LEADING).addComponent(scrollPane,
				GroupLayout.PREFERRED_SIZE, 252, GroupLayout.PREFERRED_SIZE));

		receiptTable = new JTable();
		receiptTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		receiptTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		receiptTable.setModel(new DefaultTableModel(new Object[][] { { null,
				null }, }, new String[] { "Fecha", "Total" }) {
			Class[] columnTypes = new Class[] { Calendar.class,
					BigDecimal.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane.setViewportView(receiptTable);
		leftPanel.setLayout(gl_leftPanel);
		contentPane.setLayout(gl_contentPane);

		fillBillsTable();
		receiptTable.setRowSelectionInterval(0, 0);
		fillRightPanel(rigthPanel);
		
		JLabel lblHouseHold = new JLabel("Núcleo familiar: ");
		lblHouseHold.setName("lblHouseHold");
		lblHouseHold.setText("Núcleo familiar: "
				+ household.getBanckAccount());
		lblHouseHold.setHorizontalTextPosition(SwingConstants.CENTER);
		lblHouseHold.setHorizontalAlignment(SwingConstants.CENTER);
		lblHouseHold.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JPanel explainPanel = new JPanel();
		GroupLayout gl_rigthPanel = new GroupLayout(rigthPanel);
		gl_rigthPanel.setHorizontalGroup(
			gl_rigthPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_rigthPanel.createSequentialGroup()
					.addGroup(gl_rigthPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_rigthPanel.createSequentialGroup()
							.addGap(10)
							.addComponent(lblHouseHold))
						.addComponent(explainPanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_rigthPanel.setVerticalGroup(
			gl_rigthPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_rigthPanel.createSequentialGroup()
					.addGap(11)
					.addComponent(lblHouseHold, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(explainPanel, GroupLayout.PREFERRED_SIZE, 207, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GroupLayout gl_explainPanel = new GroupLayout(explainPanel);
		gl_explainPanel.setHorizontalGroup(
			gl_explainPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 208, GroupLayout.PREFERRED_SIZE)
		);
		gl_explainPanel.setVerticalGroup(
			gl_explainPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 207, GroupLayout.PREFERRED_SIZE)
		);
		
		tableLinesBill = new JTable();
		tableLinesBill.setModel(new DefaultTableModel(new Object[][] { { null,
			null }, }, new String[] { "Concepto", "Precio" }) {
			Class[] columnTypes = new Class[] { String.class, BigDecimal.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane_1.setViewportView(tableLinesBill);
		explainPanel.setLayout(gl_explainPanel);
		rigthPanel.setLayout(gl_rigthPanel);
		fillRightPanel(rigthPanel);

		receiptTable.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent event) {
						try {
							fillRightPanel(rigthPanel);
						} catch (ReceiptsNotFoundException e) {
							e.printStackTrace();
						}
					}
				});
	}
	
	public void fillBillsTable() throws ReceiptsNotFoundException {
		receiptService = ReceiptService.getInstance();
		List<Receipt> receipts = receiptService
				.findReceiptsByHousehold(household);

		DefaultTableModel dtm = (DefaultTableModel) receiptTable.getModel();
		for (int i = 0; i < dtm.getRowCount(); i++) {
			dtm.removeRow(i);
		}

		// FIXME Ordenar receipts
		for (Receipt receipt : receipts) {
			Calendar date = receipt.getDate();
			String dateS = date.get(Calendar.DAY_OF_MONTH) + "/"
					+ (date.get(Calendar.MONTH) + 1) + "/"
					+ date.get(Calendar.YEAR);
			Object[] data = { dateS, receipt.getTotal() };
			dtm.addRow(data);
		}
		receiptTable.updateUI();// DUDA Es necesario?
	}

	private void fillRightPanel(JPanel panel) throws ReceiptsNotFoundException {
		
		JScrollPane scrollPane_1 = new JScrollPane();
		tableLinesBill = new JTable();
		tableLinesBill.setModel(new DefaultTableModel(new Object[][] { { null,
			null }, }, new String[] { "Concepto", "Precio" }) {
			Class[] columnTypes = new Class[] { String.class, BigDecimal.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane_1.setViewportView(tableLinesBill);
		
		if (receiptTable.getRowCount() == 0) {
			return;
		}
		List<Receipt> receipts = receiptService
				.findReceiptsByHousehold(household);
		final Receipt receiptSelected = receipts.get(receiptTable
				.getSelectedRow());
		Set<ReceiptLine> receiptLines = receiptSelected.getReceiptLines();
		DefaultTableModel dtm = (DefaultTableModel) tableLinesBill.getModel();
		for (int i = 0; i < dtm.getRowCount(); i++) {
			dtm.removeRow(i);
		}
		
		for (ReceiptLine receiptLine : receiptLines) {
			Object[] data = {receiptLine.getConcept(), receiptLine.getPrice()};
			dtm.addRow(data);
		}
		
		tableLinesBill.updateUI();// DUDA Es necesario?
		
	}
	
}
