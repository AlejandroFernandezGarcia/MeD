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
import ampa.sa.receipt.Receipt;
import ampa.sa.receipt.ReceiptLine;
import ampa.sa.receipt.ReceiptService;
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
	private Set<Receipt> receiptList = new HashSet<Receipt>();
	ActivityService activityService = ActivityService.getInstance();
	BookingService bookingService = BookingService.getInstance();
	FamilyService familyService = FamilyService.getInstance();

	public static final String DATE_FORMAT = "dd/MM/yyyy";

	public MockForGUI() throws ParseException {
		Persistence.getInstance();

		// Actividades
		activities.add(new Activity("Ajedrez", 14, new BigDecimal(15.5),
				new HashSet<Student>()));
		activities.add(new Activity("Tiro con arco", 8, new BigDecimal(30.0),
				new HashSet<Student>()));
		activities.add(new Activity("Kárate", 15, new BigDecimal(18.5),
				new HashSet<Student>()));
		activities.add(new Activity("Baloncesto", 10, new BigDecimal(20.0),
				new HashSet<Student>()));
		activities.add(new Activity("Fútbol", 20, new BigDecimal(20.0),
				new HashSet<Student>()));
		activities.add(new Activity("Tenis de mesa", 6, new BigDecimal(17.25),
				new HashSet<Student>()));
		activities.add(new Activity("Tenis", 8, new BigDecimal(25.0),
				new HashSet<Student>()));

		// Familias
		Household house1 = new Household("111-222-333-444",
				new HashSet<Student>(),new HashSet<Receipt>());
		Household house2 = new Household("222-333-444-555",
				new HashSet<Student>(),new HashSet<Receipt>());
		Household house3 = new Household("666-777-888-999",
				new HashSet<Student>(),new HashSet<Receipt>());
		Household house4 = new Household("000-999-888-777",
				new HashSet<Student>(),new HashSet<Receipt>());

		// Estudiantes
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(sdf.parse("20/05/2009"));
		students.add(new Student(house1, "Alberto", "Pérez",
				Student.Category.INFANTIL, cal1, new HashSet<Activity>(),
				new HashSet<Booking>()));
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(sdf.parse("29/05/2006"));
		students.add(new Student(house1, "Miguel", "Vinha",
				Student.Category.PRIMARIA, cal1, new HashSet<Activity>(),
				new HashSet<Booking>()));
		Calendar cal3 = Calendar.getInstance();
		cal3.setTime(sdf.parse("12/03/2005"));
		students.add(new Student(house3, "Alejandro", "Fortes",
				Student.Category.PRIMARIA, cal3, new HashSet<Activity>(),
				new HashSet<Booking>()));
		cal3.setTime(sdf.parse("12/03/2010"));
		students.add(new Student(house4, "Alejandro", "Fernandez",
				Student.Category.INFANTIL, cal3, new HashSet<Activity>(),
				new HashSet<Booking>()));

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

		diningHall.add(new DiningHall(new Schedule("07:00", "08:00"), 10,
				new BigDecimal(6.2)));
		diningHall.add(new DiningHall(new Schedule("14:00", "15:00"), 10,
				new BigDecimal(5.2)));

		Calendar cb1 = Calendar.getInstance();
		cb1.setTime(sdf.parse("27/10/2014"));
		Booking b1 = new Booking(cb1, students.get(0), diningHall.get(0));
		Calendar cb2 = Calendar.getInstance();
		cb2.setTime(sdf.parse("31/10/2014"));
		Booking b2 = new Booking(cb2, students.get(1), diningHall.get(0));
		Calendar cb3 = Calendar.getInstance();
		cb3.setTime(sdf.parse("31/10/2014"));
		Booking b3 = new Booking(cb3, students.get(0), diningHall.get(0));
		Calendar cb4 = Calendar.getInstance();
		cb4.setTime(sdf.parse("29/10/2014"));
		Booking b4 = new Booking(cb4, students.get(0), diningHall.get(1));
		Calendar cb5 = Calendar.getInstance();
		cb5.setTime(sdf.parse("31/10/2014"));
		Booking b5 = new Booking(cb5, students.get(2), diningHall.get(0));
		Calendar cb6 = Calendar.getInstance();
		cb6.setTime(sdf.parse("31/10/2014"));
		Booking b6 = new Booking(cb6, students.get(2), diningHall.get(1));
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

		// Bills

		ReceiptLine rl1 = new ReceiptLine(
				"Prueba para primer recibo, primera linea", new BigDecimal(7),
				1, new BigDecimal(7), students.get(0));
		ReceiptLine rl2 = new ReceiptLine(
				"Prueba para primer recibo, segunda linea", new BigDecimal(8),
				1, new BigDecimal(8), students.get(0));
		ReceiptLine rl3 = new ReceiptLine(
				"Prueba para primer recibo, tercera linea", new BigDecimal(8),
				1, new BigDecimal(8), students.get(0));

		Set<ReceiptLine> rllist = new HashSet<ReceiptLine>();
		rllist.add(rl1);
		rllist.add(rl2);

		Set<ReceiptLine> rllist2 = new HashSet<ReceiptLine>();
		rllist2.add(rl3);

		Calendar calReceipt1 = Calendar.getInstance();
		calReceipt1.setTime(sdf.parse("01/05/2009"));

		Calendar calReceipt2 = Calendar.getInstance();
		calReceipt2.setTime(sdf.parse("01/06/2009"));

		Receipt r1 = new Receipt(house1, new BigDecimal(15), rllist,
				calReceipt1);
		Receipt r2 = new Receipt(house1, new BigDecimal(8), rllist2,
				calReceipt2);

		receiptList.add(r1);
		receiptList.add(r2);

		house1.setReceipts(receiptList);

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
