package ampa.sa.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import ampa.sa.booking.Booking;
import ampa.sa.booking.BookingService;
import ampa.sa.diningHall.DiningHall;
import ampa.sa.student.FamilyService;
import ampa.sa.student.Student;
import ampa.sa.util.exceptions.DuplicateInstanceException;
import ampa.sa.util.exceptions.InstanceNotFoundException;
import ampa.sa.util.exceptions.MaxCapacityException;
import ampa.sa.util.exceptions.NotValidDateException;

import com.toedter.calendar.JCalendar;

public class BookingWindow extends JFrame {

	private JPanel contentPane;
	private JTextField fldAlumno;
	private JTextField fldCurso;
	private JTextField fldPlaces;
	private JCalendar calendar;
	private JComboBox comboBox;
	private JButton btnDeleteBooking;

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
	private JTable bookingsTable;

	/**
	 * Launch the application.
	 */
	// public static void main(String[] args) {
	// EventQueue.invokeLater(new Runnable() {
	// public void run() {
	// try {
	//
	// DatosMock dtm = new DatosMock();
	// BookingWindow frame = new BookingWindow(familyService.findStudent(1));
	//
	//
	//
	// frame.setVisible(true);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// });
	// }

	/**
	 * Create the frame.
	 * 
	 * @throws ParseException
	 * @throws InstanceNotFoundException
	 */
	public BookingWindow(MainWindow mainWindow, Student stu)
			throws ParseException, InstanceNotFoundException {
		sdf = new SimpleDateFormat(DATE_FORMAT);
		this.student = stu;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 657, 454);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 0, 0, 0));

		JPanel panel = new JPanel();
		contentPane.add(panel);

		calendar = new JCalendar();
		calendar.getYearChooser().setEnabled(false);
		calendar.getDayChooser().addPropertyChangeListener(
				new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent evt) {
						if (evt.getPropertyName().equals("day"))
							fldPlaces.setText(String.valueOf(bookingService
									.getPlacesForDiningSchedule(calendar
											.getCalendar(),
											(DiningHall) comboBox
													.getSelectedItem())));
						fillBookings();
					}
				});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel.createSequentialGroup()
						.addComponent(calendar, GroupLayout.PREFERRED_SIZE,
								315, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(
				Alignment.LEADING).addComponent(calendar,
				GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE));
		panel.setLayout(gl_panel);

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

		JLabel lblHorario = new JLabel("Horario : ");

		comboBox = new JComboBox<DiningHall>();

		List<DiningHall> diningHall = bookingService.getDiningHall();

		for (int i = 0; i < diningHall.size(); i++) {
			comboBox.addItem(diningHall.get(i));
		}

		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fldPlaces.setText(String.valueOf(bookingService
						.getPlacesForDiningSchedule(calendar.getCalendar(),
								(DiningHall) comboBox.getSelectedItem())));
			}
		});

		JLabel lblMonitors = new JLabel("Monitores : ");

		fldPlaces = new JTextField();
		fldPlaces.setEditable(false);
		fldPlaces.setColumns(10);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(Color.LIGHT_GRAY));

		btnDeleteBooking = new JButton("Cancelar reserva");
		btnDeleteBooking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int select = JOptionPane.showConfirmDialog(null,
						"�Realmente desea cancelar la reserva?",
						"Cancelar reserva", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if (select == 0) {
					Booking selected = (Booking) bookingsTable.getModel()
							.getValueAt(bookingsTable.getSelectedRow(), 2);

					// FIXME
					System.out.println(selected);
					try {
						bookingService.remove(selected);
					} catch (InstanceNotFoundException e) {
						e.printStackTrace();
					}
					fillBookings();
				}
			}
		});

		JButton btnReservar = new JButton("Realizar reserva");
		btnReservar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO ids unicas. De momento no hacen falta

				try {
					if (!daysAreChecked()) {
						Calendar cal = Calendar.getInstance();
						cal.setTime(calendar.getCalendar().getTime());
						Booking b = new Booking(cal, student,
								(DiningHall) comboBox.getSelectedItem());
						try {
							bookingService.create(b);
						} catch (NotValidDateException e) {
							JOptionPane.showMessageDialog(null,
									"El dia " + sdf.format(cal.getTime())
											+ " es festivo");
						}
						fldPlaces.setText(String.valueOf(bookingService.getPlacesForDiningSchedule(
								calendar.getCalendar(),
								(DiningHall) comboBox.getSelectedItem())));
					} else {
						createBookingsPerMonth(calendar.getCalendar().get(
								Calendar.MONTH));
					}
				} catch (DuplicateInstanceException e) {
					JOptionPane
							.showMessageDialog(null,
									"La reserva para el alumno y el horario establecido ya existe");
				} catch (MaxCapacityException e) {
					JOptionPane
							.showMessageDialog(null,
									"No quedan plazas disponibles para el horario seleccionado");
				}
				fillBookings();
			}
		});

		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BookingWindow thisWindow = (BookingWindow) ((JButton) e
						.getSource()).getTopLevelAncestor();
				mainWindow.fillRightPanel(mainWindow.getRigthPanel());
				thisWindow.setVisible(false);
				thisWindow.dispose();
			}
		});
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1
				.setHorizontalGroup(gl_panel_1
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panel_1
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_panel_1
														.createParallelGroup(
																Alignment.TRAILING)
														.addComponent(
																scrollPane,
																GroupLayout.DEFAULT_SIZE,
																295,
																Short.MAX_VALUE)
														.addComponent(
																separator,
																GroupLayout.DEFAULT_SIZE,
																295,
																Short.MAX_VALUE)
														.addGroup(
																gl_panel_1
																		.createSequentialGroup()
																		.addGroup(
																				gl_panel_1
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								lblAlumno)
																						.addComponent(
																								lblCurso))
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addGroup(
																				gl_panel_1
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								fldCurso,
																								GroupLayout.DEFAULT_SIZE,
																								246,
																								Short.MAX_VALUE)
																						.addComponent(
																								fldAlumno,
																								GroupLayout.DEFAULT_SIZE,
																								246,
																								Short.MAX_VALUE)))
														.addGroup(
																gl_panel_1
																		.createSequentialGroup()
																		.addGroup(
																				gl_panel_1
																						.createParallelGroup(
																								Alignment.TRAILING)
																						.addComponent(
																								lblMonitors)
																						.addComponent(
																								lblHorario))
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addGroup(
																				gl_panel_1
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								comboBox,
																								0,
																								234,
																								Short.MAX_VALUE)
																						.addComponent(
																								fldPlaces,
																								GroupLayout.PREFERRED_SIZE,
																								58,
																								GroupLayout.PREFERRED_SIZE)))
														.addComponent(
																btnDeleteBooking)
														.addComponent(
																panel_2,
																GroupLayout.DEFAULT_SIZE,
																295,
																Short.MAX_VALUE)
														.addGroup(
																gl_panel_1
																		.createSequentialGroup()
																		.addComponent(
																				btnReservar)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				btnVolver)))
										.addContainerGap()));
		gl_panel_1
				.setVerticalGroup(gl_panel_1
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panel_1
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_panel_1
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(lblAlumno)
														.addComponent(
																fldAlumno,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_panel_1
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(lblCurso)
														.addComponent(
																fldCurso,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addComponent(scrollPane,
												GroupLayout.PREFERRED_SIZE,
												107, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addComponent(btnDeleteBooking)
										.addGap(5)
										.addComponent(separator,
												GroupLayout.PREFERRED_SIZE, 10,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_panel_1
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																lblHorario)
														.addComponent(
																comboBox,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addGroup(
												gl_panel_1
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																lblMonitors)
														.addComponent(
																fldPlaces,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addComponent(panel_2,
												GroupLayout.PREFERRED_SIZE, 59,
												Short.MAX_VALUE)
										.addGap(18)
										.addGroup(
												gl_panel_1
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(btnVolver)
														.addComponent(
																btnReservar))
										.addContainerGap()));

		bookingsTable = new JTable();
		bookingsTable.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Fecha", "Tipo horario", "Objeto" }));
		bookingsTable.getColumnModel().getColumn(2).setMinWidth(0);
		bookingsTable.getColumnModel().getColumn(2).setMaxWidth(0);
		bookingsTable.getColumnModel().getColumn(2).setWidth(0);
		scrollPane.setViewportView(bookingsTable);

		JLabel lblReservaSemanal = new JLabel("Reserva : ");

		chckbxLun = new JCheckBox("lun");
		chckbxLun.setEnabled(false);

		chckbxMar = new JCheckBox("mar");
		chckbxMar.setEnabled(false);

		chckbxMie = new JCheckBox("mie");
		chckbxMie.setEnabled(false);

		chckbxJue = new JCheckBox("jue");
		chckbxJue.setEnabled(false);

		chckbxVie = new JCheckBox("vie");
		chckbxVie.setEnabled(false);

		JRadioButton rdbtnDay = new JRadioButton("diaria");
		rdbtnDay.setSelected(true);
		rdbtnDay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				chckbxLun.setEnabled(false);
				chckbxMar.setEnabled(false);
				chckbxMie.setEnabled(false);
				chckbxJue.setEnabled(false);
				chckbxVie.setEnabled(false);
			}
		});

		JRadioButton rdbtnMonth = new JRadioButton("mensual");
		rdbtnMonth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				chckbxLun.setEnabled(true);
				chckbxMar.setEnabled(true);
				chckbxMie.setEnabled(true);
				chckbxJue.setEnabled(true);
				chckbxVie.setEnabled(true);
			}
		});
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnDay);
		group.add(rdbtnMonth);

		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2
				.setHorizontalGroup(gl_panel_2
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panel_2
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_panel_2
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_panel_2
																		.createSequentialGroup()
																		.addComponent(
																				chckbxLun)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				chckbxMar)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				chckbxMie)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				chckbxJue)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				chckbxVie))
														.addGroup(
																gl_panel_2
																		.createSequentialGroup()
																		.addComponent(
																				lblReservaSemanal)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				rdbtnDay)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				rdbtnMonth)))
										.addContainerGap(84, Short.MAX_VALUE)));
		gl_panel_2
				.setVerticalGroup(gl_panel_2
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panel_2
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_panel_2
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																lblReservaSemanal)
														.addComponent(rdbtnDay)
														.addComponent(
																rdbtnMonth))
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addGroup(
												gl_panel_2
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(chckbxLun)
														.addComponent(chckbxMar)
														.addComponent(chckbxMie)
														.addComponent(chckbxJue)
														.addComponent(chckbxVie))
										.addContainerGap(23, Short.MAX_VALUE)));
		panel_2.setLayout(gl_panel_2);
		panel_1.setLayout(gl_panel_1);

	}

	public void fillBookings() {
		List<Booking> bookings = bookingService.getStudentBookingsByDate(
				student, this.calendar.getCalendar());
		DefaultTableModel modelTB = (DefaultTableModel) bookingsTable
				.getModel();

		if (modelTB.getRowCount() > 0) {
			for (int i = modelTB.getRowCount() - 1; i > -1; i--) {
				modelTB.removeRow(i);
			}
		}

		for (Booking booking : bookings) {
			Calendar date = booking.getDate();
			String dateS = sdf.format(date.getTime());
			Object[] data = { dateS, booking.getDiningHall().toString(),
					booking };
			modelTB.addRow(data);
		}
		if (bookings.size() == 0)
			btnDeleteBooking.setEnabled(false);
		else
			btnDeleteBooking.setEnabled(true);
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
		fldAlumno.setText(student.getLastname() + "," + student.getName());
	}

	public boolean daysAreChecked() {
		return (chckbxLun.isSelected() || chckbxMar.isSelected()
				|| chckbxMie.isSelected() || chckbxJue.isSelected() || chckbxVie
					.isSelected());
	}

	public void createBookingsPerMonth(Integer month) {
		List<Integer> days = new ArrayList<Integer>();
		List<Booking> transaction = new ArrayList<Booking>();
		if (chckbxLun.isSelected())
			days.add(Calendar.MONDAY);
		if (chckbxMar.isSelected())
			days.add(Calendar.TUESDAY);
		if (chckbxMie.isSelected())
			days.add(Calendar.WEDNESDAY);
		if (chckbxJue.isSelected())
			days.add(Calendar.THURSDAY);
		if (chckbxVie.isSelected())
			days.add(Calendar.FRIDAY);

		Calendar cal1 = Calendar.getInstance();
		while (cal1.get(Calendar.MONTH) == month) {
			if (days.contains(cal1.get(Calendar.DAY_OF_WEEK))) {
				Calendar c = Calendar.getInstance();
				c.setTime(cal1.getTime());
				DiningHall dh = (DiningHall) comboBox.getSelectedItem();
				Booking b = new Booking(c, student, dh);
				try {
					bookingService.create(b);
					transaction.add(b);
				} catch (DuplicateInstanceException | MaxCapacityException e) {
					JOptionPane.showMessageDialog(
							null,
							"Error creando reserva para " + dh.toString()
									+ " (D�a "
									+ cal1.get(Calendar.DAY_OF_MONTH)
									+ "). Operaci�n de reservas canceladas");
					for (int i = 0; i < transaction.size(); i++) {
						try {
							bookingService.remove(transaction.get(i));
						} catch (InstanceNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				} catch (NotValidDateException e) {
					JOptionPane.showMessageDialog(null,
							"AVISO: Alguno de los d�as es festivo");
				}
			}
			cal1.add(Calendar.DATE, 1);
		}
		fillBookings();
	}

}
