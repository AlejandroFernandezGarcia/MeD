package ampa.sa.test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

import ampa.sa.activity.ActivityService;
import ampa.sa.activity.Activity;
import ampa.sa.booking.Booking;
import ampa.sa.booking.BookingService;
import ampa.sa.diningHall.DiningHall;
import ampa.sa.persistence.Persistence;
import ampa.sa.student.FamilyService;
import ampa.sa.student.Household;
import ampa.sa.student.Student;

public class DatosMock {
	private List<Household> houseHold = new ArrayList<Household>();
	private List<Student> students = new ArrayList<Student>();
	private List<Activity> activities = new ArrayList<Activity>();
	private List<Booking> bookings = new ArrayList<Booking>();
	private List<DiningHall> diningHall = new ArrayList<DiningHall>();
	ActivityService activityService = ActivityService.getInstance();
	BookingService bookingService = BookingService.getInstance();
	FamilyService familyService = FamilyService.getInstance();

	public static final String DATE_FORMAT = "dd/MM/yyyy";

	private DatosMock() throws ParseException {
		Persistence.getInstanceTest();
		
		// Actividades
		activities.add(new Activity(1, "Ajedrez", 14, new BigDecimal(15.5),
				new HashSet<Student>()));
		activities.add(new Activity(2, "Tiro con arco", 8, new BigDecimal(30.0),
				new HashSet<Student>()));
		activities.add(new Activity(3, "Kárate", 15, new BigDecimal(18.5),
				new HashSet<Student>()));
		activities.add(new Activity(4, "Baloncesto", 10, new BigDecimal(20.0),
				new HashSet<Student>()));
		activities
				.add(new Activity(5, "Fútbol", 20, new BigDecimal(20.0), new HashSet<Student>()));
		activities.add(new Activity(6, "Tenis de mesa", 6, new BigDecimal(17.25),
				new HashSet<Student>()));
		activities.add(new Activity(7, "Tenis", 8, new BigDecimal(25.0),
				new HashSet<Student>()));

		// Familias
		Household house1 = new Household("111-222-333-444",
				new HashSet<Student>());
		Household house2 = new Household("222-333-444-555",
				new HashSet<Student>());
		Household house3 = new Household("666-777-888-999",
				new HashSet<Student>());
		Household house4 = new Household("000-999-888-777",
				new HashSet<Student>());

		// Estudiantes
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(sdf.parse("20/05/1992"));
		students.add(new Student(1, house1, "Alberto", "Pérez", cal1,
				new HashSet<Activity>(), new HashSet<Booking>()));
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(sdf.parse("29/05/1987"));
		students.add(new Student(2, house2, "Miguel", "Vinha", cal1,
				new HashSet<Activity>(), new HashSet<Booking>()));
		Calendar cal3 = Calendar.getInstance();
		cal3.setTime(sdf.parse("12/03/1989"));
		students.add(new Student(3, house3, "Alejandro", "Fortes", cal3,
				new HashSet<Activity>(), new HashSet<Booking>()));
		cal3.setTime(sdf.parse("12/03/1989"));
		students.add(new Student(3, house4, "Alejandro", "Apellido", cal3,
				new HashSet<Activity>(), new HashSet<Booking>()));

		activityService.setActivities(activities);
		bookingService.setBookings(bookings);
		familyService.setStudents(students);
		
	}

}