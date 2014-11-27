package ampa.sa.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;

import java.awt.Panel;

import javax.swing.SwingConstants;

import ampa.sa.activity.Activity;
import ampa.sa.booking.Booking;
import ampa.sa.booking.BookingService;
import ampa.sa.diningHall.DiningHall;
import ampa.sa.receipt.Bill;
import ampa.sa.student.FamilyService;
import ampa.sa.student.Household;
import ampa.sa.student.Student;
import ampa.sa.util.exceptions.DuplicateInstanceException;
import ampa.sa.util.exceptions.InstanceNotFoundException;

import javax.swing.JComboBox;

import com.toedter.calendar.JDateChooser;

import javax.swing.border.MatteBorder;

import java.awt.Color;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.lang.NullPointerException;

import javax.swing.border.EtchedBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class StudentManagementWindow extends JFrame {

	private JPanel contentPane;
	private JTextField txtAcc1;
	private JTextField txtAcc2;
	private JTextField txtAcc4;
	private JTextField txtAcc3;
	private JTextField txtTutor1;
	private JTextField txtTutor2;
	private JTextField txtName;
	private JTextField txtLastName;
	private JDateChooser dateChooser;
	private JComboBox<Student.Category> combo;
	private Boolean checkNull = true;
	
	private static FamilyService familyService = FamilyService.getInstance();

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentManagementWindow frame = new StudentManagementWindow(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public StudentManagementWindow(Household houseHold) {
		

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 417, 386);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		
		JLabel lblNmeroDeCuenta = new JLabel("N\u00FAmero de cuenta : ");
		
		txtAcc1 = new JTextField();
		txtAcc1.setColumns(3);
		
		JLabel label = new JLabel("-");
		
		txtAcc2 = new JTextField();
		txtAcc2.setColumns(3);
		
		JLabel label_1 = new JLabel("-");
		
		txtAcc4 = new JTextField();
		txtAcc4.setColumns(3);
		
		txtAcc3 = new JTextField();
		txtAcc3.setColumns(3);
		
		JLabel label_2 = new JLabel("-");
		
		JLabel lblTutor = new JLabel("Tutor 1 : ");
		
		JLabel lblTutor2 = new JLabel("Tutor 2 : ");
		
		txtTutor1 = new JTextField();
		txtTutor1.setColumns(10);
		
		txtTutor2 = new JTextField();
		txtTutor2.setColumns(10);
		
		if(houseHold != null){
			setHH(houseHold);
		}
		
		JButton btnNew = new JButton("Crear alumno");
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if((txtName.getText().length()!=0) && (txtLastName.getText().length()!=0) && (dateChooser.getDate() != null))
				{
					Calendar cal = Calendar.getInstance();
					cal.setTime(dateChooser.getDate());
					if(!checkNull){
						
						Student student = new Student(houseHold, txtName.getText(),txtLastName.getText(),
								(Student.Category) combo.getSelectedItem(), cal, new HashSet<Activity>(),
								new HashSet<Booking>());
						Set<Student> mentored = houseHold.getMentored();
						mentored.add(student);
						houseHold.setMentored(mentored);
						
						try {
							familyService.updateHousehold(houseHold);
							familyService.createStudent(student);
							JOptionPane.showMessageDialog(null,"Estudiante creado correctamente");
							StudentManagementWindow thisWindow = (StudentManagementWindow) ((JButton) arg0
									.getSource()).getTopLevelAncestor();
							thisWindow.setVisible(false);
							thisWindow.dispose();
						} catch (DuplicateInstanceException e) {
							JOptionPane.showMessageDialog(null,"ERROR: No se ha podido crear el usuario");
						} catch (InstanceNotFoundException e) {
							JOptionPane.showMessageDialog(null,"ERROR: El n�cleo familiar no se ha podido actualizar correctamente");
						}
					}else{
						if((txtAcc1.getText().length()==3)&&(txtAcc2.getText().length()==3)
								&&(txtAcc3.getText().length()==3)&&(txtAcc4.getText().length()==3)
								&&(txtTutor1.getText().length()!=0))
						{
							
							String account = txtAcc1.getText() + "-" + txtAcc2.getText() + "-" + txtAcc3.getText() + "-" + txtAcc4.getText(); 
							Household h1;
							if(txtTutor2.getText().length()==0){
								h1 = new Household(account,
										new HashSet<Student>(), new HashSet<Bill>(),txtTutor1.getText());
							}else{
								h1 = new Household(account,
										new HashSet<Student>(), new HashSet<Bill>(),
										txtTutor1.getText(),txtTutor2.getText());
							}
							
							Student student = new Student(h1, txtName.getText(),
									txtLastName.getText(),(Student.Category) combo.getSelectedItem(), 
									cal, new HashSet<Activity>(),
									new HashSet<Booking>());
							System.out.println("New Student -> " + student.getCategory().toString());
							
							Set<Student> hs = new HashSet<Student>();
							hs.add(student);
							h1.setMentored(hs);
							try {
								familyService.createStudent(student);
								familyService.createHousehold(h1);
								JOptionPane.showMessageDialog(null,"Estudiante y n�cleo familiar creados correctamente");
								StudentManagementWindow thisWindow = (StudentManagementWindow) ((JButton) arg0
										.getSource()).getTopLevelAncestor();
								thisWindow.setVisible(false);
								thisWindow.dispose();
							} catch (DuplicateInstanceException e) {
								JOptionPane.showMessageDialog(null,"ERROR: Datos duplicados en BD");
							}
						}else
						{
							JOptionPane.showMessageDialog(null,"ERROR: Los campos del n�mero de cuenta son obligatorios");
						}
					}
				}else{
					JOptionPane.showMessageDialog(null,"ERROR: Por favor, rellena todos los campos");
				}
			}
			
		});
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		JLabel lblDatosDelNucleo = new JLabel("Datos del nucleo familiar :");
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StudentManagementWindow thisWindow = (StudentManagementWindow) ((JButton) e
						.getSource()).getTopLevelAncestor();
				thisWindow.setVisible(false);
				thisWindow.dispose();
			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_1, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)
						.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblTutor2)
								.addComponent(lblTutor)
								.addComponent(lblNmeroDeCuenta))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(txtAcc1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(4)
									.addComponent(label)
									.addGap(4)
									.addComponent(txtAcc2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(4)
									.addComponent(label_1)
									.addGap(4)
									.addComponent(txtAcc3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(4)
									.addComponent(label_2)
									.addGap(4)
									.addComponent(txtAcc4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addComponent(txtTutor1, GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
								.addComponent(txtTutor2)))
						.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
							.addComponent(btnVolver)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnNew))
						.addComponent(lblDatosDelNucleo))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblDatosDelNucleo)
					.addGap(19)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNmeroDeCuenta)
						.addComponent(txtAcc1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(3)
							.addComponent(label))
						.addComponent(txtAcc2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(3)
							.addComponent(label_1))
						.addComponent(txtAcc3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(3)
							.addComponent(label_2))
						.addComponent(txtAcc4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtTutor1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblTutor))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtTutor2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblTutor2))
					.addGap(45)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNew)
						.addComponent(btnVolver))
					.addContainerGap())
		);
		
		JLabel lblDatosDelAlumno = new JLabel("Datos del alumno :");
		
		JLabel lblNombre = new JLabel("Nombre : ");
		
		JLabel lblApellidos = new JLabel("Apellidos : ");
		lblApellidos.setHorizontalAlignment(SwingConstants.RIGHT);
		
		txtName = new JTextField();
		txtName.setColumns(10);
		
		txtLastName = new JTextField();
		txtLastName.setColumns(10);
		
		JLabel lblCourse = new JLabel("Curso : ");
		lblCourse.setHorizontalAlignment(SwingConstants.RIGHT);
		
		combo = new JComboBox<Student.Category>();

		combo.addItem(Student.Category.INFANTIL);
		combo.addItem(Student.Category.PRIMARIA);
		
		dateChooser = new JDateChooser();
		
		JLabel lblFechaNac = new JLabel("Fecha nac : ");
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(lblDatosDelAlumno)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblNombre)
								.addComponent(lblApellidos, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblCourse))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_1.createSequentialGroup()
									.addComponent(combo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
									.addComponent(lblFechaNac)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(dateChooser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addComponent(txtLastName, GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
								.addComponent(txtName, GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE))))
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblDatosDelAlumno)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNombre)
						.addComponent(txtName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblApellidos)
						.addComponent(txtLastName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblCourse)
							.addComponent(combo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblFechaNac))
						.addComponent(dateChooser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(19, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		panel.setLayout(gl_panel);
	}
	
	private void setHH(Household houseHold){
		//txtAcc1.setEnabled(false);
		txtAcc1.setEditable(false);
		//txtAcc2.setEnabled(false);
		txtAcc2.setEditable(false);
		//txtAcc3.setEnabled(false);
		txtAcc3.setEditable(false);
		//txtAcc4.setEnabled(false);
		txtAcc4.setEditable(false);
		
		//txtTutor1.setEnabled(false);
		txtTutor1.setEditable(false);
		//txtTutor2.setEnabled(false);
		txtTutor2.setEditable(false);
		
		String account = houseHold.getBanckAccount();
		if(account.length() != 15)
			System.out.println("bad size");
		String[] parts = account.split("-");
		txtAcc1.setText(parts[0]);
		txtAcc2.setText(parts[1]);
		txtAcc3.setText(parts[2]);
		txtAcc4.setText(parts[3]);
		
		txtTutor1.setText(houseHold.getRepresentative());
		if(houseHold.getRepresentative1() != null)
			txtTutor2.setText(houseHold.getRepresentative1());
	}
}
