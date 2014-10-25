package ampa.sa.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

import java.awt.GridLayout;
import java.awt.Component;

import javax.swing.JTabbedPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.JMenuBar;
import javax.swing.JButton;
import javax.swing.Box;
import javax.swing.JMenuItem;
import javax.swing.JMenu;

import ampa.sa.activity.Activity;
import ampa.sa.booking.Booking;
import ampa.sa.booking.BookingService;
import ampa.sa.diningHall.DiningHall;
import ampa.sa.student.FamilyService;
import ampa.sa.student.Household;
import ampa.sa.student.Student;
import ampa.sa.test.DatosMock;
import ampa.sa.util.Schedule;
import ampa.sa.util.exceptions.DuplicateInstanceException;
import ampa.sa.util.exceptions.InstanceNotFoundException;

import com.toedter.calendar.JCalendar;

import java.awt.FlowLayout;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JTextField;
import javax.swing.JSeparator;

import java.awt.Dimension;
import java.awt.ScrollPane;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BookingWindow extends JFrame {

	private JPanel contentPane;
	private JTextField fldAlumno;
	private JTextField fldCurso;
	private JTable bookingsTable;
	private JTextField fldPlaces;
	private JCalendar calendar;
	private JComboBox comboBox;
	
	private JCheckBox chckbxLun;
	private JCheckBox chckbxMar;
	private JCheckBox chckbxMie;
	private JCheckBox chckbxJue;
	private JCheckBox chckbxVie;
	
	private Student student;
	private static BookingService bookingService = BookingService.getInstance();
	private static FamilyService familyService = FamilyService.getInstance();
	
	public static final String DATE_FORMAT = "dd/MM/yyyy";
	
	SimpleDateFormat sdf;	
	private JTextField fldPruebas;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					DatosMock dtm = new DatosMock();			
					BookingWindow frame = new BookingWindow(familyService.findStudent(1));
					

					
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws ParseException 
	 * @throws InstanceNotFoundException 
	 */
	public BookingWindow(Student stu) throws ParseException, InstanceNotFoundException {
		sdf = new SimpleDateFormat(DATE_FORMAT);
        this.student = stu;
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
		
		JPanel panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		

		calendar = new JCalendar();
		calendar.getYearChooser().setEnabled(false);
		calendar.getDayChooser().addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName().equals("day"))
						fldPruebas.setText(sdf.format(calendar.getDate()));
						fldPlaces.setText(String.valueOf(bookingService.getPlacesForDiningSchedule(calendar.getCalendar(), (DiningHall) comboBox.getSelectedItem())));
						fillBookings();
			}
		});
		
		panel.add(calendar, BorderLayout.CENTER);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1);
		
		JLabel lblAlumno = new JLabel("Alumno : ");
		
		JLabel lblCurso = new JLabel("Curso :");
		
		fldAlumno = new JTextField();
		lblAlumno.setLabelFor(fldAlumno);
		fldAlumno.setEditable(false);
		fldAlumno.setColumns(10);
		fldAlumno.setText(student.getLastname() + " , " + student.getName());
		
		fldCurso = new JTextField();
		lblCurso.setLabelFor(fldCurso);
		fldCurso.setText("Curso");
		fldCurso.setEditable(false);
		fldCurso.setColumns(10);
		fldCurso.setText(familyService.getStudentType(this.student));
		
		JSeparator separator = new JSeparator();
		separator.setSize(new Dimension(200, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		
		JButton btnReservar = new JButton("Realizar reserva");
		btnReservar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//TODO ids unicas. De momento no hacen falta

				try {
					if(!daysAreChecked()){
						Calendar cal = Calendar.getInstance();
						cal.setTime(calendar.getCalendar().getTime());
						Booking b = new Booking(1, cal, student,
								(DiningHall) comboBox.getSelectedItem());						
						bookingService.create(b);
						fldPlaces.setText(String.valueOf(bookingService.getPlacesForDiningSchedule(calendar.getCalendar(), (DiningHall) comboBox.getSelectedItem())));
					}
					else
					{
						createBookingsPerMonth();
						//fldPruebas.setText("TUESDAY " + String.valueOf(Calendar.TUESDAY) + "," + String.valueOf(calendar.getCalendar().get(Calendar.DAY_OF_WEEK)));
					}
				} catch (InstanceNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				fillBookings();
			}
		});
		
		JLabel lblHorario = new JLabel("Horario : ");
		

		
		comboBox = new JComboBox<DiningHall>();

		
		List<DiningHall> diningHall = bookingService.getDiningHall();
		
		for (int i = 0; i < diningHall.size(); i++) 
		    comboBox.addItem(diningHall.get(i));
		
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fldPlaces.setText(String.valueOf(bookingService.getPlacesForDiningSchedule(calendar.getCalendar(), (DiningHall) comboBox.getSelectedItem())));
			}
		});		
		
		JLabel lblMonitors = new JLabel("Monitores : ");
		
		fldPlaces = new JTextField();
		fldPlaces.setEditable(false);
		fldPlaces.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(Color.LIGHT_GRAY));
		
		fldPruebas = new JTextField();
		fldPruebas.setColumns(10);
		fldPruebas.setText(calendar.getDate().toString());
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(separator, GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addComponent(lblAlumno)
								.addComponent(lblCurso))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addComponent(fldCurso, GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
								.addComponent(fldAlumno, GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)))
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
						.addComponent(panel_2, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblMonitors)
								.addComponent(lblHorario))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addComponent(comboBox, 0, 246, Short.MAX_VALUE)
								.addGroup(gl_panel_1.createSequentialGroup()
									.addComponent(fldPlaces, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(fldPruebas, GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE))))
						.addComponent(btnReservar))
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAlumno)
						.addComponent(fldAlumno, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCurso)
						.addComponent(fldCurso, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblHorario)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblMonitors)
						.addComponent(fldPlaces, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(fldPruebas, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
					.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnReservar)
					.addContainerGap())
		);
		
		JLabel lblReservaSemanal = new JLabel("Reserva mensual : ");
		
		chckbxLun = new JCheckBox("lun");
		
		chckbxMar = new JCheckBox("mar");
		
		chckbxMie = new JCheckBox("mie");
		
		chckbxJue = new JCheckBox("jue");
		
		chckbxVie = new JCheckBox("vie");
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(lblReservaSemanal)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(chckbxLun)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(chckbxMar)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(chckbxMie)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(chckbxJue)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(chckbxVie)))
					.addContainerGap(20, Short.MAX_VALUE))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblReservaSemanal)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(chckbxLun)
						.addComponent(chckbxMar)
						.addComponent(chckbxMie)
						.addComponent(chckbxJue)
						.addComponent(chckbxVie))
					.addContainerGap(45, Short.MAX_VALUE))
		);
		panel_2.setLayout(gl_panel_2);
		
		bookingsTable = new JTable();
		bookingsTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Reserva", "Tipo de horario"
			}
		));
		scrollPane.setViewportView(bookingsTable);
		panel_1.setLayout(gl_panel_1);

	}
	
	
	public void fillBookings(){
		List<Booking> bookings = bookingService.getStudentBookingsByDate(student, this.calendar.getCalendar());
		DefaultTableModel modelTB = (DefaultTableModel) bookingsTable.getModel();
		//FIX ME print
		System.out.println(bookingService.getBookings().toString());
		
		if (modelTB.getRowCount() > 0) {
		    for (int i = modelTB.getRowCount() - 1; i > -1; i--) {
		        modelTB.removeRow(i);
		    }
		}

		for (Booking booking : bookings) {
			Calendar date = booking.getDate();
			String dateS = sdf.format(date.getTime());
			Object[] data = { dateS , booking.getDiningHall().getSchedule().toString() };
			modelTB.addRow(data);
		}
	}
	
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
		fldAlumno.setText(student.getLastname()+","+student.getName());
	}
	
	public boolean daysAreChecked(){
		return (chckbxLun.isSelected()||chckbxMar.isSelected()||chckbxMie.isSelected()
				||chckbxJue.isSelected()||chckbxVie.isSelected());
	}
	
	public void createBookingsPerMonth() throws InstanceNotFoundException{
		List<Integer> days = new ArrayList<Integer>();
		if(chckbxLun.isSelected())
			days.add(Calendar.MONDAY);
		if(chckbxMar.isSelected())
			days.add(Calendar.TUESDAY);
		if(chckbxMie.isSelected())
			days.add(Calendar.THURSDAY);
		if(chckbxJue.isSelected())
			days.add(Calendar.WEDNESDAY);
		if(chckbxVie.isSelected())
			days.add(Calendar.FRIDAY);
		
		fldPruebas.setText(days.toString());
		bookingService.createByDayOfWeek(days,student,(DiningHall) comboBox.getSelectedItem());
		fillBookings();
	}
}
