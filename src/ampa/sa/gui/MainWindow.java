package ampa.sa.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import ampa.sa.activity.Activity;
import ampa.sa.booking.Booking;
import ampa.sa.booking.BookingService;
import ampa.sa.diningHall.DiningHall;
import ampa.sa.persistence.Persistence;
import ampa.sa.student.FamilyService;
import ampa.sa.student.Student;
import ampa.sa.util.exceptions.InstanceNotFoundException;
import ampa.sa.util.exceptions.ReceiptsNotFoundException;

public class MainWindow extends JFrame {

	private JPanel contentPane;
	private JTable studentsTable;
	private FamilyService familyService;
	private BookingService bookingService;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Persistence.getInstance();
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void fillStudentsTable() {
		familyService = FamilyService.getInstance();
		List<Student> students = familyService.getStudents();

		DefaultTableModel dtm = (DefaultTableModel) studentsTable.getModel();
		for (int i = 0; i < dtm.getRowCount(); i++) {
			dtm.removeRow(i);
		}

		// FIXME Ordenar students
		for (Student student : students) {
			Calendar date = student.getDateBorn();
			String dateS = date.get(Calendar.DAY_OF_MONTH) + "/"
					+ (date.get(Calendar.MONTH) + 1) + "/"
					+ date.get(Calendar.YEAR);
			Object[] data = { student.getName(), student.getLastname(), dateS };
			dtm.addRow(data);
		}
		studentsTable.updateUI();// DUDA Es necesario?
	}

	private void fillConceptTable(Student student, JTable table) {
		bookingService = BookingService.getInstance();
		DefaultTableModel dtm = (DefaultTableModel) table.getModel();
		for (int i = 0; i < dtm.getRowCount(); i++) {
			dtm.removeRow(i);
		}

		Set<Activity> activities = student.getActivities();
		for (Activity a : activities) {
			Object[] data = { a.getName(), a.getPrize() };
			dtm.addRow(data);
		}
		Set<Booking> bookings = student.getBookings();
		List<DiningHall> dhs = bookingService.getDiningHall();
		int count;
		for (DiningHall dh : dhs) {
			count = 0;
			for (Booking b : bookings) {
				if (b.getDiningHall().equals(dh)) {
					count++;
				}
			}
			if (count != 0) {
				Object[] data = {
						count + "x Comedor " + dh.getSchedule(),
						dh.getPrice().multiply(new BigDecimal(count)) + " ("
								+ dh.getPrice() + " ud )" };
				dtm.addRow(data);
			}
		}

		table.updateUI();// DUDA Es necesario?
	}

	private JPanel createExplainPanel(Student student) {
		JLabel lblName = new JLabel("Nombre: " + student.getLastname() + ", "
				+ student.getName());
		lblName.setHorizontalTextPosition(SwingConstants.CENTER);
		lblName.setHorizontalAlignment(SwingConstants.LEFT);

		JLabel lblTotalStudent = new JLabel("Total alumno: "
				+ familyService.getStudentExpenses(student) + "€");
		lblTotalStudent.setHorizontalTextPosition(SwingConstants.CENTER);
		lblTotalStudent.setHorizontalAlignment(SwingConstants.LEFT);
		JPanel explainPanel = new JPanel();
		JScrollPane scrollPane_1 = new JScrollPane();
		GroupLayout gl_explainPanel = new GroupLayout(explainPanel);
		gl_explainPanel
				.setHorizontalGroup(gl_explainPanel
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								Alignment.TRAILING,
								gl_explainPanel
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_explainPanel
														.createParallelGroup(
																Alignment.TRAILING)
														.addComponent(
																scrollPane_1,
																Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE,
																270,
																Short.MAX_VALUE)
														.addComponent(
																lblName,
																Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE,
																270,
																Short.MAX_VALUE)
														.addComponent(
																lblTotalStudent,
																GroupLayout.DEFAULT_SIZE,
																270,
																Short.MAX_VALUE))
										.addContainerGap()));
		gl_explainPanel.setVerticalGroup(gl_explainPanel.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_explainPanel
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(lblName)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(lblTotalStudent,
								GroupLayout.PREFERRED_SIZE, 23,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE,
								138, Short.MAX_VALUE).addContainerGap()));

		JTable tablaConcepto = new JTable();
		tablaConcepto.setModel(new DefaultTableModel(new Object[][] { { null,
				null }, }, new String[] { "Concepto", "Precio" }) {
			Class[] columnTypes = new Class[] { String.class, Object.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane_1.setViewportView(tablaConcepto);
		explainPanel.setLayout(gl_explainPanel);

		fillConceptTable(student, tablaConcepto);

		return explainPanel;
	}

	private void fillRightPanel(JPanel panel) {
		if (studentsTable.getRowCount() == 0) {
			return;
		}
		// FIXME Ordenar students
		JTabbedPane tabPanel = null;
		List<Student> students = familyService.getStudents();
		final Student studentSelected = students.get(studentsTable
				.getSelectedRow());
		Component[] componentsPanel = panel.getComponents();
		for (Component component : componentsPanel) {
			if (component.getClass() == JTabbedPane.class) {
				tabPanel = (JTabbedPane) component;
			} else if (component.getName() != null) {
				if (component.getName().compareTo("lblHouseHold") == 0) {
					((JLabel) component).setText("Núcleo familiar: "
							+ studentSelected.getHouseHold().getBanckAccount());
				} else if (component.getName().compareTo("lblTotalHouseHold") == 0) {
					BigDecimal amount = familyService
							.getHouseholdExpenses(studentSelected
									.getHouseHold());
					((JLabel) component).setText("Total núcleo familiar: "
							+ amount + " €");
				} else if (component.getName().compareTo("btnCreateBooking") == 0) {
					ActionListener[] al = ((JButton) component)
							.getListeners(ActionListener.class);
					if (al.length != 0) {
						((JButton) component).removeActionListener(al[0]);
					}
					((JButton) component)
							.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									try {
										BookingWindow bW = new BookingWindow(
												studentSelected);
										bW.setVisible(true);
									} catch (InstanceNotFoundException
											| ParseException e1) {
										e1.printStackTrace();
									}
								}
							});

				} else if (component.getName().compareTo("btnShowBills") == 0) {
					ActionListener[] al = ((JButton) component)
							.getListeners(ActionListener.class);
					if (al.length != 0) {
						((JButton) component).removeActionListener(al[0]);
					}
					((JButton) component)
							.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									BillHistoryWindow bhw;
									try {
										bhw = new BillHistoryWindow(
												studentSelected.getHouseHold());
												bhw.setVisible(true);
									} catch (ReceiptsNotFoundException e1) {
										e1.printStackTrace();
									}
								}
							});
				} else if (component.getName().compareTo("btnSignUpActivity") == 0) {
					ActionListener[] al = ((JButton) component)
							.getListeners(ActionListener.class);
					if (al.length != 0) {
						((JButton) component).removeActionListener(al[0]);
					}
					((JButton) component)
							.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									ActivityManagementWindow amw = new ActivityManagementWindow(
											studentSelected);
									amw.setVisible(true);
								}
							});

				}// TODO Añadir funcionalidad demás botones
			}
		}
		List<Student> studentsOfHouseHold = familyService
				.findStudents(studentSelected.getHouseHold());
		tabPanel.removeAll();
		int i = 0;
		for (Student student : studentsOfHouseHold) {
			tabPanel.addTab(student.getName(), createExplainPanel(student));
			if (student.equals(studentSelected)) {
				tabPanel.setSelectedIndex(i);
			}
			i++;
		}

	}

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 657, 462);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnAlumno = new JMenu("Alumno");
		menuBar.add(mnAlumno);

		JMenuItem mntmAadirAlumno = new JMenuItem("Añadir alumno");
		mnAlumno.add(mntmAadirAlumno);

		JMenu mnActividad = new JMenu("Actividad");
		menuBar.add(mnActividad);

		JMenuItem mntmAadirActividad = new JMenuItem("Añadir actividad");
		mnActividad.add(mntmAadirActividad);

		JMenuItem mntmModificarActividad = new JMenuItem("Modificar actividad");
		mnActividad.add(mntmModificarActividad);

		JMenuItem mntmBorrarActividad = new JMenuItem("Borrar actividad");
		mnActividad.add(mntmBorrarActividad);

		JMenu mnComedor = new JMenu("Comedor");
		menuBar.add(mnComedor);

		JMenuItem mntmModificar = new JMenuItem("Modificar");
		mnComedor.add(mntmModificar);

		JMenuItem mntmAadirNuevoHorario = new JMenuItem("Añadir nuevo horario");
		mnComedor.add(mntmAadirNuevoHorario);

		JMenu mnAyuda = new JMenu("Ayuda");
		menuBar.add(mnAyuda);

		JMenuItem mntmAcercaDe = new JMenuItem("Acerca de");
		mnAyuda.add(mntmAcercaDe);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 0, 0, 0));

		JPanel leftPanel = new JPanel();
		contentPane.add(leftPanel);
		leftPanel.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollStudents = new JScrollPane();
		leftPanel.add(scrollStudents);

		studentsTable = new JTable();
		studentsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		studentsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		studentsTable.setModel(new DefaultTableModel(new Object[][] { { null,
				null, null }, }, new String[] { "Nombre", "Apellidos",
				"Fecha nacimiento" }) {
			Class[] columnTypes = new Class[] { String.class, String.class,
					String.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, true, true };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollStudents.setViewportView(studentsTable);

		final JPanel rigthPanel = new JPanel();
		contentPane.add(rigthPanel);

		JLabel lblHouseHold = new JLabel("Núcleo familiar: ");
		lblHouseHold.setName("lblHouseHold");
		lblHouseHold.setHorizontalTextPosition(SwingConstants.CENTER);
		lblHouseHold.setHorizontalAlignment(SwingConstants.CENTER);
		lblHouseHold.setAlignmentX(Component.CENTER_ALIGNMENT);

		JTabbedPane tabAlumno = new JTabbedPane(JTabbedPane.TOP);

		JLabel lblTotalHouseHold = new JLabel("Total núcleo familiar:");
		lblTotalHouseHold.setName("lblTotalHouseHold");
		lblTotalHouseHold.setHorizontalTextPosition(SwingConstants.CENTER);
		lblTotalHouseHold.setHorizontalAlignment(SwingConstants.CENTER);

		JButton btnSignUpActivity = new JButton("Apuntarse a actividades");
		btnSignUpActivity.setName("btnSignUpActivity");

		JButton btnReservasComedor = new JButton("Reservas comedor");

		btnReservasComedor.setName("btnCreateBooking");

		JButton btnShowBills = new JButton("Mostrar recibos");
		btnShowBills.setName("btnShowBills");

		GroupLayout gl_rigthPanel = new GroupLayout(rigthPanel);
		gl_rigthPanel
				.setHorizontalGroup(gl_rigthPanel
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_rigthPanel
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_rigthPanel
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_rigthPanel
																		.createSequentialGroup()
																		.addComponent(
																				tabAlumno,
																				GroupLayout.DEFAULT_SIZE,
																				298,
																				Short.MAX_VALUE)
																		.addContainerGap())
														.addGroup(
																gl_rigthPanel
																		.createSequentialGroup()
																		.addGroup(
																				gl_rigthPanel
																						.createParallelGroup(
																								Alignment.TRAILING)
																						.addComponent(
																								lblTotalHouseHold,
																								Alignment.LEADING,
																								GroupLayout.DEFAULT_SIZE,
																								285,
																								Short.MAX_VALUE)
																						.addComponent(
																								lblHouseHold,
																								GroupLayout.DEFAULT_SIZE,
																								285,
																								Short.MAX_VALUE))
																		.addGap(25))
														.addGroup(
																gl_rigthPanel
																		.createSequentialGroup()
																		.addComponent(
																				btnSignUpActivity)
																		.addContainerGap(
																				114,
																				Short.MAX_VALUE))
														.addGroup(
																gl_rigthPanel
																		.createSequentialGroup()
																		.addComponent(
																				btnReservasComedor)
																		.addContainerGap(
																				146,
																				Short.MAX_VALUE))
														.addGroup(
																gl_rigthPanel
																		.createSequentialGroup()
																		.addComponent(
																				btnShowBills)
																		.addContainerGap(
																				193,
																				Short.MAX_VALUE)))));
		gl_rigthPanel.setVerticalGroup(gl_rigthPanel.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_rigthPanel
						.createSequentialGroup()
						.addGap(18)
						.addComponent(lblHouseHold, GroupLayout.PREFERRED_SIZE,
								23, GroupLayout.PREFERRED_SIZE)
						.addGap(10)
						.addComponent(lblTotalHouseHold)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(tabAlumno, GroupLayout.DEFAULT_SIZE, 210,
								Short.MAX_VALUE).addGap(18)
						.addComponent(btnSignUpActivity)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btnReservasComedor)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btnShowBills).addGap(15)));

		JPanel explainPanel = new JPanel();
		tabAlumno.addTab("New tab", null, explainPanel, null);
		tabAlumno.setEnabledAt(0, true);

		JLabel lblName = new JLabel("Nombre:");
		lblName.setHorizontalTextPosition(SwingConstants.CENTER);
		lblName.setHorizontalAlignment(SwingConstants.LEFT);

		JLabel lblTotalStudent = new JLabel("Total alumno:");
		lblTotalStudent.setHorizontalTextPosition(SwingConstants.CENTER);
		lblTotalStudent.setHorizontalAlignment(SwingConstants.LEFT);

		JScrollPane scrollPane_1 = new JScrollPane();
		GroupLayout gl_explainPanel = new GroupLayout(explainPanel);
		gl_explainPanel
				.setHorizontalGroup(gl_explainPanel
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								Alignment.TRAILING,
								gl_explainPanel
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_explainPanel
														.createParallelGroup(
																Alignment.TRAILING)
														.addComponent(
																scrollPane_1,
																Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE,
																270,
																Short.MAX_VALUE)
														.addComponent(
																lblName,
																Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE,
																270,
																Short.MAX_VALUE)
														.addComponent(
																lblTotalStudent,
																GroupLayout.DEFAULT_SIZE,
																270,
																Short.MAX_VALUE))
										.addContainerGap()));
		gl_explainPanel.setVerticalGroup(gl_explainPanel.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_explainPanel
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(lblName)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(lblTotalStudent,
								GroupLayout.PREFERRED_SIZE, 23,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE,
								138, Short.MAX_VALUE).addContainerGap()));

		JTable tablaConcepto = new JTable();
		tablaConcepto.setModel(new DefaultTableModel(new Object[][] { { null,
				null }, }, new String[] { "Concepto", "Precio" }) {
			Class[] columnTypes = new Class[] { String.class, Object.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane_1.setViewportView(tablaConcepto);
		explainPanel.setLayout(gl_explainPanel);
		rigthPanel.setLayout(gl_rigthPanel);

		// Editado

		fillStudentsTable();
		studentsTable.setRowSelectionInterval(0, 0);
		fillRightPanel(rigthPanel);

		studentsTable.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent event) {
						fillRightPanel(rigthPanel);
					}
				});
	}
}
