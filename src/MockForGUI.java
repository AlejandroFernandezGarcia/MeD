import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ampa.sa.activity.Activity;
import ampa.sa.activity.ActivityService;
import ampa.sa.booking.Booking;
import ampa.sa.booking.BookingService;
import ampa.sa.diningHall.DiningHall;
import ampa.sa.persistence.Persistence;
import ampa.sa.student.FamilyService;
import ampa.sa.student.Household;
import ampa.sa.student.Student;
import ampa.sa.util.Schedule;


public class MockForGUI {
	private List<Household> households = new ArrayList<Household>();
	private List<Student> students = new ArrayList<Student>();
	private List<Activity> activities = new ArrayList<Activity>();
	private List<Booking> bookings = new ArrayList<Booking>();
	private List<DiningHall> diningHall = new ArrayList<DiningHall>();
	ActivityService activityService = ActivityService.getInstance();
	BookingService bookingService = BookingService.getInstance();
	FamilyService familyService = FamilyService.getInstance();
	
	public static final String DATE_FORMAT = "dd/MM/yyyy";


		public MockForGUI() throws ParseException  {
			Persistence.getInstance();
			
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
			students.add(new Student(2, house1, "Miguel", "Vinha", cal1,
					new HashSet<Activity>(), new HashSet<Booking>()));
			Calendar cal3 = Calendar.getInstance();
			cal3.setTime(sdf.parse("12/03/1989"));
			students.add(new Student(3, house3, "Alejandro", "Fortes", cal3,
					new HashSet<Activity>(), new HashSet<Booking>()));
			cal3.setTime(sdf.parse("12/03/1989"));
			students.add(new Student(3, house4, "Alejandro", "Apellido", cal3,
					new HashSet<Activity>(), new HashSet<Booking>()));
			
			Set<Activity> sa = new HashSet<Activity>();
			sa.add(activities.get(0));
			sa.add(activities.get(1));
			students.get(0).setActivities(sa);
			students.get(2).setActivities(sa);
			
			Set<Student> hs = new HashSet<Student>();
			hs.add(students.get(0));
			hs.add(students.get(1));
			house1.setMentored(hs);
			
			Set<Student> hs2 = new HashSet<Student>();
			hs2.add(students.get(1));
			house2.setMentored(hs2);
			
			households.add(house1);
			households.add(house2);
			
			
			diningHall.add(new DiningHall(1, new Schedule("07:00","08:00"),10, new BigDecimal(6.2)));
			diningHall.add(new DiningHall(2, new Schedule("14:00","15:00"),10, new BigDecimal(5.2)));
			
			Calendar cb1 = Calendar.getInstance();
			cb1.setTime(sdf.parse("27/10/2014"));
			Booking b1 = new Booking(1, cb1, students.get(0),
					diningHall.get(0));
			Calendar cb2 = Calendar.getInstance();
			cb2.setTime(sdf.parse("31/10/2014"));
			Booking b2 = new Booking(2, cb2, students.get(1),
					diningHall.get(0));
			Calendar cb3 = Calendar.getInstance();
			cb3.setTime(sdf.parse("31/10/2014"));
			Booking b3 = new Booking(1, cb3, students.get(0),
					diningHall.get(0));
			Calendar cb4 = Calendar.getInstance();
			cb4.setTime(sdf.parse("29/10/2014"));
			Booking b4 = new Booking(2, cb4, students.get(0),
					diningHall.get(1));
			Calendar cb5 = Calendar.getInstance();
			cb5.setTime(sdf.parse("31/10/2014"));
			Booking b5 = new Booking(2, cb5, students.get(2),
					diningHall.get(0));
			Calendar cb6 = Calendar.getInstance();
			cb6.setTime(sdf.parse("31/10/2014"));
			Booking b6 = new Booking(2, cb6, students.get(2),
					diningHall.get(1));	
			bookings.add(b1);
			bookings.add(b2);
			bookings.add(b3);
			bookings.add(b4);
			bookings.add(b5);
			bookings.add(b6);
			students.get(0).getBookings().add(b1);
			students.get(0).getBookings().add(b3);
			students.get(0).getBookings().add(b4);
			students.get(1).getBookings().add(b2);
			students.get(2).getBookings().add(b5);
			students.get(2).getBookings().add(b6);
			
			
			activityService.setActivities(activities);
			bookingService.setBookings(bookings);
			familyService.setHousehold(households);
			familyService.setStudents(students);
			bookingService.setDiningHall(diningHall);
			
		}
	public static void main(String[] args) throws ParseException {
		new MockForGUI();
		Persistence.getInstance().save();
		System.out.println("Finalizada ejecucion del mock");
	}

}
