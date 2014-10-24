package ampa.sa.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.math.BigDecimal;
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
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import ampa.sa.activity.Activity;
import ampa.sa.booking.Booking;
import ampa.sa.persistence.Persistence;
import ampa.sa.student.FamilyService;
import ampa.sa.student.Student;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainWindow extends JFrame {

	private JPanel contentPane;
	private JTable studentsTable;
	private FamilyService familyService;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
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
					+ (date.get(Calendar.MONTH) + 1) + date.get(Calendar.YEAR);
			Object[] data = { student.getName(), student.getLastname(), dateS };
			dtm.addRow(data);
		}
		studentsTable.updateUI();// DUDA Es necesario?
	}
	
	private void fillConceptTable(Student student, JTable table){
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
		for (Booking b : bookings) {
			Object[] data = { b.getDiningHall().getSchedule(), b.getDiningHall().getPrice() };
			dtm.addRow(data);
			//FIXME Juntar 
		}
		
		table.updateUI();// DUDA Es necesario?
	}

	private JPanel createExplainPanel(Student student){
		JLabel lblName = new JLabel("Nombre:"+student.getLastname()+", "+student.getName());
		lblName.setHorizontalTextPosition(SwingConstants.CENTER);
		lblName.setHorizontalAlignment(SwingConstants.LEFT);

		JLabel lblTotalStudent = new JLabel("Total alumno:"+familyService.getStudentExpenses(student)+"€");
		lblTotalStudent.setHorizontalTextPosition(SwingConstants.CENTER);
		lblTotalStudent.setHorizontalAlignment(SwingConstants.LEFT);
		JPanel explainPanel = new JPanel();
		JScrollPane scrollPane_1 = new JScrollPane();
		GroupLayout gl_explainPanel = new GroupLayout(explainPanel);
		gl_explainPanel.setHorizontalGroup(
			gl_explainPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_explainPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_explainPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
						.addComponent(lblName, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
						.addComponent(lblTotalStudent, GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_explainPanel.setVerticalGroup(
			gl_explainPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_explainPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblName)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblTotalStudent, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
					.addContainerGap())
		);

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
		if(studentsTable.getRowCount()==0){
			return;
		}
		// FIXME Ordenar students
		JTabbedPane tabPanel=null;
		List<Student> students = familyService.getStudents();
		Student studentSelected = students.get(studentsTable.getSelectedRow());
		Component[] componentsPanel = panel.getComponents();
		for (Component component : componentsPanel) {
			if (component.getClass() == JTabbedPane.class) {
				tabPanel = (JTabbedPane) component;
			} else {
				if (component.getName().compareTo("lblHouseHold") == 0) {
					((JLabel) component).setText((((JLabel) component)
							.getText())
							+ studentSelected.getHouseHold().getBanckAccount());
				} else {
					BigDecimal amount = familyService.getHouseholdExpenses(studentSelected.getHouseHold());
					((JLabel) component).setText((((JLabel) component)
							.getText())
							+ amount + " €");
				}
			}
		}
		List<Student> studentsOfHouseHold = familyService.findStudents(studentSelected.getHouseHold());
		for (Student student : studentsOfHouseHold) {
			tabPanel.addTab(student.getName(), createExplainPanel(student));
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

		JPanel rigthPanel = new JPanel();
		contentPane.add(rigthPanel);

		JLabel lblHouseHold = new JLabel("Núcleo familiar: ");
		lblHouseHold.setHorizontalTextPosition(SwingConstants.CENTER);
		lblHouseHold.setHorizontalAlignment(SwingConstants.CENTER);
		lblHouseHold.setAlignmentX(Component.CENTER_ALIGNMENT);

		JTabbedPane tabAlumno = new JTabbedPane(JTabbedPane.TOP);

		JLabel lblTotalHouseHold = new JLabel("Total núcleo familiar:");
		lblTotalHouseHold.setHorizontalTextPosition(SwingConstants.CENTER);
		lblTotalHouseHold.setHorizontalAlignment(SwingConstants.CENTER);
		
				JButton btnMatricularEnActividad = new JButton(
						"Matricular en actividad");
		
				JButton btnReservasComedor = new JButton("Reservas comedor");
				btnReservasComedor.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//TODO Aqui se lanza la ventana nueva
					}
				});
		GroupLayout gl_rigthPanel = new GroupLayout(rigthPanel);
		gl_rigthPanel.setHorizontalGroup(
			gl_rigthPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_rigthPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_rigthPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_rigthPanel.createSequentialGroup()
							.addComponent(tabAlumno, GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(gl_rigthPanel.createSequentialGroup()
							.addGroup(gl_rigthPanel.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblTotalHouseHold, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
								.addComponent(lblHouseHold, GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE))
							.addGap(25))
						.addGroup(gl_rigthPanel.createSequentialGroup()
							.addComponent(btnMatricularEnActividad)
							.addContainerGap(115, Short.MAX_VALUE))
						.addGroup(gl_rigthPanel.createSequentialGroup()
							.addComponent(btnReservasComedor)
							.addContainerGap(147, Short.MAX_VALUE))))
		);
		gl_rigthPanel.setVerticalGroup(
			gl_rigthPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_rigthPanel.createSequentialGroup()
					.addGap(18)
					.addComponent(lblHouseHold, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(lblTotalHouseHold)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tabAlumno, GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(btnMatricularEnActividad)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnReservasComedor)
					.addGap(46))
		);

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
		gl_explainPanel.setHorizontalGroup(
			gl_explainPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_explainPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_explainPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
						.addComponent(lblName, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
						.addComponent(lblTotalStudent, GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_explainPanel.setVerticalGroup(
			gl_explainPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_explainPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblName)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblTotalStudent, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
					.addContainerGap())
		);

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

		Persistence.getInstance();
		fillStudentsTable();
		fillRightPanel(rigthPanel);
	}
}