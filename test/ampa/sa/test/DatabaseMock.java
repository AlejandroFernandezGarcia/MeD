package ampa.sa.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import ampa.sa.activity.Activity;
import ampa.sa.booking.Booking;
import ampa.sa.diningHall.DiningHall;
import ampa.sa.student.Household;
import ampa.sa.student.Student;

public class DatabaseMock {
	private List<Household> houseHold = new ArrayList<Household>();
	private List<Student> students = new ArrayList<Student>();
	private List<Activity> activities = new ArrayList<Activity>();
	private List<Booking> bookings = new ArrayList<Booking>();
	private List<DiningHall> diningHall = new ArrayList<DiningHall>();
	private static DatabaseMock singleton = null;

	public static final String DATE_FORMAT = "dd/MM/yyyy";
	
	private DatabaseMock() throws ParseException {
		
		
		//Actividades
		activities.add(new Activity(1, "Ajedrez", 14, 12.57,new HashSet<Student>()));
		activities.add(new Activity(2, "Tiro con arco", 8, 24,new HashSet<Student>()));
		activities.add(new Activity(3, "Kárate", 15, 14.50,new HashSet<Student>()));
		activities.add(new Activity(4, "Baloncesto", 10, 8.75,new HashSet<Student>()));
		activities.add(new Activity(5, "Fútbol", 20, 8,new HashSet<Student>()));
		activities.add(new Activity(6, "Tenis de mesa", 6, 6.25,new HashSet<Student>()));
		activities.add(new Activity(7, "Tenis", 8, 12.25,new HashSet<Student>()));
		
		//Familias
		Household house1 = new Household("111-222-333-444",new HashSet<Student>());
		Household house2 = new Household("222-333-444-555",new HashSet<Student>());
		Household house3 = new Household("666-777-888-999",new HashSet<Student>());
		Household house4 = new Household("000-999-888-777",new HashSet<Student>());		
		
		//Estudiantes
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(sdf.parse("20/05/1992"));
		students.add(new Student(1,house1, "Alberto", "Pérez",cal1, new HashSet<Activity>(), new HashSet<Booking>()));
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(sdf.parse("29/05/1987"));
		students.add(new Student(2,house2, "Miguel", "Vinha",cal1, new HashSet<Activity>(), new HashSet<Booking>()));
		Calendar cal3 = Calendar.getInstance();
		cal3.setTime(sdf.parse("12/03/1989"));		
		students.add(new Student(3,house3, "Alejandro", "Fortes",cal3, new HashSet<Activity>(), new HashSet<Booking>()));
		cal3.setTime(sdf.parse("12/03/1989"));		
		students.add(new Student(3,house4, "Alejandro", "Apellido",cal3, new HashSet<Activity>(), new HashSet<Booking>()));		
		
		
		
		//crear actividades
		//crear reservas
		//crear comedores
		
		//poner dependencias
		//meter en los arraylist
	}

	public static DatabaseMock getInstance() {
		if (singleton == null) {
			try {
				singleton = new DatabaseMock();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return singleton;
	}
}
