package ampa.sa.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import ampa.sa.activity.Activity;
import ampa.sa.bill.BillService;
import ampa.sa.booking.BookingService;
import ampa.sa.persistence.Persistence;
import ampa.sa.student.FamilyService;
import ampa.sa.student.Student;
import ampa.sa.util.MouseListenerOnlyClick;
import ampa.sa.util.exceptions.InstanceNotFoundException;

public class MainWindow extends JFrame {

	private JPanel contentPane;
	private JTable studentsTable;
	private FamilyService familyService;
	private BookingService bookingService;
	private BillService billService;
	private JPanel rigthPanel;
	private MainWindow now;
	StudentManagementWindow smw = null;

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

	public JPanel getRigthPanel() {
		return rigthPanel;
	}

	public void fillStudentsTable() {
		familyService = FamilyService.getInstance();
		List<Student> students = familyService.getStudents();

		DefaultTableModel dtm = (DefaultTableModel) studentsTable.getModel();
		for (int i = 0; i < dtm.getRowCount(); i++) {
			dtm.removeRow(i);
		}
		for (Student student : students) {
			if (student.isDeleted())
				continue;
			Calendar date = student.getDateBorn();
			String dateS = date.get(Calendar.DAY_OF_MONTH) + "/"
					+ (date.get(Calendar.MONTH) + 1) + "/"
					+ date.get(Calendar.YEAR);
			Icon icon = new ImageIcon("src/ampa/sa/util/remove-Student.png");
			DefaultTableCellRenderer renderer = new javax.swing.table.DefaultTableCellRenderer();
			renderer.setIcon(icon);
			renderer.setHorizontalAlignment(SwingConstants.CENTER);
			studentsTable.getColumn("").setCellRenderer(renderer);
			Object[] data = { null, student.getName(), student.getLastname(),
					dateS };
			dtm.addRow(data);
		}
		if (dtm.getRowCount() != 0) {
			studentsTable.setRowSelectionInterval(0, 0);
		}
	}

	private void fillConceptList(Student student, JList<String> list) {
		bookingService = BookingService.getInstance();
		DefaultListModel<String> dlm = new DefaultListModel<String>();
		list.setModel(dlm);
		dlm.removeAllElements();

		Set<Activity> activities = student.getActivities();
		for (Activity a : activities) {
			dlm.addElement(a.getName());
		}

		String[] bookings = bookingService
				.isBookingAllDayOfWeekInMonth(student);
		for (String string : bookings) {
			if (string.compareTo("") != 0) {
				dlm.addElement(string);
			}
		}
	}

	private Student getStudentFromTable() {
		DefaultTableModel dtm = (DefaultTableModel) studentsTable.getModel();
		Vector data = dtm.getDataVector();
		int rowSelected = studentsTable.getSelectedRow();
		String name = ((Vector) data.elementAt(rowSelected)).elementAt(1)
				.toString();
		String lastName = ((Vector) data.elementAt(rowSelected)).elementAt(2)
				.toString();
		String date = ((Vector) data.elementAt(rowSelected)).elementAt(3)
				.toString();
		String[] dateS = date.split("/");
		Calendar dateBorn = Calendar.getInstance();
		dateBorn.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateS[0]));
		dateBorn.set(Calendar.MONTH, Integer.parseInt(dateS[1]) - 1);
		dateBorn.set(Calendar.YEAR, Integer.parseInt(dateS[2]));
		try {
			Student student = familyService.findStudent(new Student(name,
					lastName, dateBorn));
			return student;
		} catch (InstanceNotFoundException e1) {
			return null;
		}
	}

	private JPanel createExplainPanel(Student student) {
		JLabel lblName = new JLabel("Nombre: " + student.getLastname() + ", "
				+ student.getName());
		lblName.setHorizontalTextPosition(SwingConstants.CENTER);
		lblName.setHorizontalAlignment(SwingConstants.LEFT);

		JPanel explainPanel = new JPanel();

		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_explainPanel = new GroupLayout(explainPanel);
		gl_explainPanel
				.setHorizontalGroup(gl_explainPanel
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(
								gl_explainPanel
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_explainPanel
														.createParallelGroup(
																Alignment.TRAILING)
														.addComponent(
																scrollPane,
																Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE,
																269,
																Short.MAX_VALUE)
														.addComponent(
																lblName,
																Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE,
																269,
																Short.MAX_VALUE))
										.addContainerGap()));
		gl_explainPanel.setVerticalGroup(gl_explainPanel.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_explainPanel
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(lblName)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE,
								138, Short.MAX_VALUE).addContainerGap()));

		JList list = new JList();
		scrollPane.setViewportView(list);
		explainPanel.setLayout(gl_explainPanel);

		fillConceptList(student, list);

		return explainPanel;
	}

	public void fillRightPanel(JPanel panel) {
		billService = BillService.getInstance();
		if (studentsTable.getRowCount() == 0) {
			return;
		}
		JTabbedPane tabPanel = null;
		List<Student> students = familyService.getStudents();
		Student studentSelected = getStudentFromTable();
		Component[] componentsPanel = panel.getComponents();
		for (Component component : componentsPanel) {
			if (component.getClass() == JTabbedPane.class) {
				tabPanel = (JTabbedPane) component;
			} else if (component.getName() != null) {
				if (component.getName().compareTo("lblHouseHold") == 0) {
					((JLabel) component).setText("Núcleo familiar: "
							+ studentSelected.getHouseHold().getBanckAccount());
				} else if (component.getName().compareTo("lblRepresentative") == 0) {
					String rep1 = studentSelected.getHouseHold()
							.getRepresentative();
					String rep2 = studentSelected.getHouseHold()
							.getRepresentative1();
					((JLabel) component).setText("Representante/s: "
							+ (rep1 != null ? rep1 + ", " : "")
							+ (rep2 != null ? rep2 : ""));
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
												now, studentSelected);
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
									billService.createBill(studentSelected
											.getHouseHold());
									bhw = new BillHistoryWindow(studentSelected
											.getHouseHold());
									bhw.setVisible(true);
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
											now, studentSelected);
									amw.setVisible(true);
								}
							});

				}
			}
		}
		List<Student> studentsOfHouseHold = familyService
				.findStudents(studentSelected.getHouseHold());
		tabPanel.removeAll();
		int i = 0;
		for (Student student : studentsOfHouseHold) {
			if (student.isDeleted())
				continue;
			tabPanel.addTab(student.getName(), createExplainPanel(student));
			if (student.equals(studentSelected)) {
				tabPanel.setSelectedIndex(i);
			}
			i++;
		}
		Icon icon = new ImageIcon("src/ampa/sa/util/add-Student.png");
		tabPanel.addTab("", icon, null,
				"Añadir estudiante a esta unidad familiar");

		tabPanel.addMouseListener(new MouseListenerOnlyClick() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if ((smw == null) || (!smw.isVisible())) {
					JTabbedPane sourceTabbedPane = (JTabbedPane) e.getSource();
					int index = sourceTabbedPane.getSelectedIndex();
					if (index == (sourceTabbedPane.getTabCount() - 1)
							&& (index != 0)) {
						Student studentSelected = getStudentFromTable();
						smw = new StudentManagementWindow(now, studentSelected
								.getHouseHold());
						smw.setVisible(true);
					}
				}
			}
		});

	}

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setTitle("AMPA S.A.");
		this.now = this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 657, 462);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnAlumno = new JMenu("Alumno");
		menuBar.add(mnAlumno);

		JMenuItem mntmAadirAlumno = new JMenuItem("Añadir alumno");
		mnAlumno.add(mntmAadirAlumno);
		mntmAadirAlumno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StudentManagementWindow smw = new StudentManagementWindow(now,
						null);
				smw.setVisible(true);
			}
		});

		JMenu mnActividad = new JMenu("Actividad");
		menuBar.add(mnActividad);

		JMenuItem mntmListarActividades = new JMenuItem("Listar actividades");
		mnActividad.add(mntmListarActividades);
		mntmListarActividades.setName("List Activities");
		mntmListarActividades.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListActivitiesWindow law;
				law = new ListActivitiesWindow();
				law.setVisible(true);
			}
		});

		JMenuItem mntmAadirActividad = new JMenuItem("Añadir actividad");
		mnActividad.add(mntmAadirActividad);
		mntmAadirActividad.setName("Create Activity");
		mntmAadirActividad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateActivityWindow caw;
				caw = new CreateActivityWindow();
				caw.setVisible(true);
			}
		});

		JMenuItem mntmModificarActividad = new JMenuItem("Modificar actividad");
		mnActividad.add(mntmModificarActividad);
		mntmModificarActividad.setName("Update Activity");
		mntmModificarActividad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UpdateActivityWindow uaw;
				uaw = new UpdateActivityWindow();
				uaw.setVisible(true);
			}
		});

		JMenuItem mntmBorrarActividad = new JMenuItem("Borrar actividad");
		mnActividad.add(mntmBorrarActividad);
		mntmBorrarActividad.setName("Delete Activity");
		mntmBorrarActividad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeleteActivityWindow daw;
				daw = new DeleteActivityWindow();
				daw.setVisible(true);
			}
		});

		JMenu mnAyuda = new JMenu("Ayuda");
		menuBar.add(mnAyuda);

		JMenuItem mntmAcercaDe = new JMenuItem("Acerca de");
		mnAyuda.add(mntmAcercaDe);
		mntmAcercaDe.setName("About us");
		mntmAcercaDe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AboutUsWindow auw;
				auw = new AboutUsWindow();
				auw.setVisible(true);
			}
		});

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
				null, null, null }, }, new String[] { "", "Nombre",
				"Apellidos", "Fecha nacimiento" }) {
			Class[] columnTypes = new Class[] { Object.class, String.class,
					String.class, String.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false,
					false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		studentsTable.getColumnModel().getColumn(0).setPreferredWidth(15);
		studentsTable.getColumnModel().getColumn(0).setMaxWidth(15);
		studentsTable.getColumnModel().getColumn(3).setPreferredWidth(120);
		scrollStudents.setViewportView(studentsTable);

		JButton btnAddStudent = new JButton("Añadir estudiante");
		leftPanel.add(btnAddStudent, BorderLayout.SOUTH);
		btnAddStudent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				StudentManagementWindow smw = new StudentManagementWindow(now,
						null);
				smw.setVisible(true);
			}
		});

		rigthPanel = new JPanel();
		contentPane.add(rigthPanel);

		JLabel lblHouseHold = new JLabel("Núcleo familiar: ");
		lblHouseHold.setName("lblHouseHold");
		lblHouseHold.setHorizontalTextPosition(SwingConstants.CENTER);
		lblHouseHold.setHorizontalAlignment(SwingConstants.CENTER);
		lblHouseHold.setAlignmentX(Component.CENTER_ALIGNMENT);

		JTabbedPane tabAlumno = new JTabbedPane(JTabbedPane.TOP);

		JButton btnSignUpActivity = new JButton("Apuntarse a actividades");
		btnSignUpActivity.setName("btnSignUpActivity");

		JButton btnReservasComedor = new JButton("Reservas comedor");

		btnReservasComedor.setName("btnCreateBooking");

		JButton btnShowBills = new JButton("Mostrar recibos");
		btnShowBills.setName("btnShowBills");

		JLabel lblLblrepresentative = new JLabel("Representante/s:");
		lblLblrepresentative.setName("lblRepresentative");

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
																				lblHouseHold,
																				GroupLayout.DEFAULT_SIZE,
																				286,
																				Short.MAX_VALUE)
																		.addGap(25))
														.addGroup(
																Alignment.TRAILING,
																gl_rigthPanel
																		.createSequentialGroup()
																		.addGroup(
																				gl_rigthPanel
																						.createParallelGroup(
																								Alignment.TRAILING)
																						.addComponent(
																								btnSignUpActivity,
																								Alignment.LEADING,
																								GroupLayout.DEFAULT_SIZE,
																								299,
																								Short.MAX_VALUE)
																						.addComponent(
																								btnReservasComedor,
																								Alignment.LEADING,
																								GroupLayout.DEFAULT_SIZE,
																								299,
																								Short.MAX_VALUE)
																						.addComponent(
																								btnShowBills,
																								Alignment.LEADING,
																								GroupLayout.DEFAULT_SIZE,
																								299,
																								Short.MAX_VALUE))
																		.addContainerGap())
														.addGroup(
																Alignment.TRAILING,
																gl_rigthPanel
																		.createSequentialGroup()
																		.addComponent(
																				tabAlumno,
																				GroupLayout.DEFAULT_SIZE,
																				299,
																				Short.MAX_VALUE)
																		.addContainerGap())
														.addGroup(
																gl_rigthPanel
																		.createSequentialGroup()
																		.addComponent(
																				lblLblrepresentative)
																		.addContainerGap(
																				240,
																				Short.MAX_VALUE)))));
		gl_rigthPanel.setVerticalGroup(gl_rigthPanel.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_rigthPanel
						.createSequentialGroup()
						.addGap(18)
						.addComponent(lblHouseHold, GroupLayout.PREFERRED_SIZE,
								23, GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(lblLblrepresentative)
						.addGap(13)
						.addComponent(tabAlumno, GroupLayout.DEFAULT_SIZE, 239,
								Short.MAX_VALUE).addGap(18)
						.addComponent(btnSignUpActivity)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btnReservasComedor)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btnShowBills)));

		JPanel explainPanel = new JPanel();
		tabAlumno.addTab("New tab", null, explainPanel, null);
		tabAlumno.setEnabledAt(0, true);

		JLabel lblName = new JLabel("Nombre:");
		lblName.setHorizontalTextPosition(SwingConstants.CENTER);
		lblName.setHorizontalAlignment(SwingConstants.LEFT);

		rigthPanel.setLayout(gl_rigthPanel);

		// Editado

		fillStudentsTable();
		fillRightPanel(rigthPanel);

		studentsTable.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent event) {
						fillRightPanel(rigthPanel);

					}
				});
		studentsTable.addMouseListener(new MouseListenerOnlyClick() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (studentsTable.getSelectedColumn() == 0) {
					int selected = JOptionPane
							.showConfirmDialog(
									null,
									"¿Realmente desea eliminar al estudiante seleccionado?",
									"Borrar estudiante",
									JOptionPane.YES_NO_OPTION,
									JOptionPane.QUESTION_MESSAGE);
					if (selected == 0) {
						Student student = getStudentFromTable();
						student.setDeleted(true);
						try {
							familyService.updateStudent(student);
						} catch (InstanceNotFoundException e1) {
							e1.printStackTrace();
						}
						MainWindow newWindow = new MainWindow();
						newWindow.setVisible(true);
						newWindow.setBounds(now.getBounds());
						now.setVisible(false);
						now.dispose();
					}

				}
			}
		});
	}
}
