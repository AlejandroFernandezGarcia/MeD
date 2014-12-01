package ampa.sa.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ampa.sa.booking.Booking;
import ampa.sa.booking.BookingService;
import ampa.sa.diningHall.DiningHall;
import ampa.sa.student.FamilyService;
import ampa.sa.student.Student;
import ampa.sa.util.exceptions.DuplicateInstanceException;
import ampa.sa.util.exceptions.InstanceNotFoundException;
import ampa.sa.util.exceptions.MaxCapacityException;
import ampa.sa.util.exceptions.NotValidDateException;

public class BookingTest {

	private BookingService bookingService = BookingService.getInstance();
	private FamilyService familyService = FamilyService.getInstance();
	public static final String DATE_FORMAT = "dd/MM/yyyy";

	@Before
	public void before() throws ParseException {
		new DatosMock();
	}

	@Test
	public void addBookingTest() {

		Student s = null;
		try {
			s = familyService.findStudent(3);
		} catch (InstanceNotFoundException e) {
			assertTrue("Student not found", false);
		}
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Calendar cb = Calendar.getInstance();
		try {
			cb.setTime(sdf.parse("27/10/2014"));
		} catch (ParseException e) {
			assertTrue("Bad format to calendar", false);
		}
		try {
			bookingService.create(new Booking(cb, s, bookingService
					.getDiningHall().get(0)));
		} catch (DuplicateInstanceException | MaxCapacityException e) {
			assertTrue("Booking has already been created", false);
		} catch (NotValidDateException e) {
			e.printStackTrace();
		}
		Student ss = null;
		try {
			ss = bookingService.find(2).getStudent();
			assertEquals(ss, s);
			assertEquals(bookingService.find(2).getDate(), cb);
			assertEquals(bookingService.find(2).getDiningHall(), bookingService
					.getDiningHall().get(0));
		} catch (InstanceNotFoundException e) {
			assertTrue("Booking not found", false);
		}

	}

	@Test(expected = DuplicateInstanceException.class)
	public void addDuplicateBookingTest() throws DuplicateInstanceException {

		Student s = null;
		Booking b = null;
		try {
			s = familyService.findStudent(3);
		} catch (InstanceNotFoundException e) {
			assertTrue("Student not found", false);
		}
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Calendar cb = Calendar.getInstance();
		try {
			cb.setTime(sdf.parse("27/10/2014"));
		} catch (ParseException e) {
			assertTrue("Bad format to calendar", false);
		}
		b = new Booking(cb, s, bookingService.getDiningHall().get(0));

		try {
			bookingService.create(b);
			bookingService.create(b);
		} catch (MaxCapacityException e1) {
			assertTrue("Full dining hall ", false);
		} catch (NotValidDateException e) {
			assertTrue("Invalid date", false);
		}
	}

	@Test
	public void checkPlacesByDateTest() {

		Student s = null;
		try {
			s = familyService.findStudent(3);
		} catch (InstanceNotFoundException e) {
			assertTrue("Student not found", false);
		}
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Calendar cb = Calendar.getInstance();
		try {
			cb.setTime(sdf.parse("27/10/2014"));
		} catch (ParseException e) {
			assertTrue("Bad format to calendar", false);
		}
		try {
			bookingService.create(new Booking(cb, s, bookingService
					.getDiningHall().get(0)));
		} catch (DuplicateInstanceException | MaxCapacityException e) {
			assertTrue("Duplicate booking or full dining hall", false);
		} catch (NotValidDateException e) {
			assertTrue("Invalid date", false);
		}

		assertEquals(bookingService.getPlacesForDiningSchedule(cb,
				bookingService.getDiningHall().get(0)), 22);
	}

	@Test
	public void removeBookingTest() throws InstanceNotFoundException {

		Booking b = null;

		b = bookingService.find(0);
		bookingService.remove(b);
		Booking b2 = bookingService.find(0);
		assertTrue(!b.equals(b2));

	}

	@Test(expected = InstanceNotFoundException.class)
	public void removeInexistantBookingTest() throws InstanceNotFoundException {

		Booking b = null;

		b = bookingService.find(0);
		bookingService.remove(b);

		bookingService.remove(b);

	}

	@Test
	public void getStudentBookingByDateTest() {

		Booking b = null;
		Student s = null;
		try {
			b = bookingService.find(0);
			s = b.getStudent();
		} catch (InstanceNotFoundException e) {
			assertTrue("Booking not found", false);
		}

		List<Booking> lb = bookingService.getStudentBookingsByDate(s,
				b.getDate());
		assertEquals(lb.size(), 1);

		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Calendar cb1 = Calendar.getInstance();
		try {
			cb1.setTime(sdf.parse("27/10/2014"));
		} catch (ParseException e1) {
			assertTrue("Bad format to calendar", false);
		}

		try {
			bookingService.create(new Booking(cb1, s, bookingService
					.findDiningHall(1)));
		} catch (DuplicateInstanceException | MaxCapacityException
				| NotValidDateException | InstanceNotFoundException e) {
			assertTrue("Error in getStudentBookingByDateTest", false);
		}

		// esto solo busca si un estudiante tiene reservas ese dia, de ma�ana o
		// de tarde
		lb = bookingService.getStudentBookingsByDate(s, b.getDate());
		assertEquals(lb.size(), 2);
	}

	@Test
	public void getBookingPrizeTest() {

		Booking b = null;
		Student s = null;
		try {
			b = bookingService.find(0);
			s = b.getStudent();
		} catch (InstanceNotFoundException e) {
			assertTrue("Booking not found", false);
		}

		List<Booking> lb = bookingService.getStudentBookingsByDate(s,
				b.getDate());
		assertEquals(bookingService.getBookingsPrize(lb),
				new BigDecimal("4.00"));

		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Calendar cb1 = Calendar.getInstance();
		try {
			cb1.setTime(sdf.parse("27/10/2014"));
		} catch (ParseException e1) {
			assertTrue("Bad format to calendar", false);
		}

		try {
			bookingService.create(new Booking(cb1, s, bookingService
					.findDiningHall(1)));
		} catch (DuplicateInstanceException | MaxCapacityException
				| NotValidDateException | InstanceNotFoundException e) {
			assertTrue("Error in getStudentBookingByDateTest", false);
		}

		lb = bookingService.getStudentBookingsByDate(s, b.getDate());
		assertEquals(bookingService.getBookingsPrize(lb),
				new BigDecimal("9.00"));
	}

	@Test(expected = MaxCapacityException.class)
	public void maxCapacityTest() throws MaxCapacityException {
		try {
			Student testSt1 = familyService.findStudent(0);
			Student testSt2 = familyService.findStudent(1);

			DiningHall d1 = new DiningHall(1, BigDecimal.valueOf(4.00),
					DiningHall.Type.MADRUGADORES);

			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
			Calendar testDate = Calendar.getInstance();
			testDate.setTime(sdf.parse("21/11/2014"));
			Booking b1 = new Booking(testDate, testSt1, d1);
			Booking b2 = new Booking(testDate, testSt2, d1);
			bookingService.create(b1);
			bookingService.create(b2);
		} catch (InstanceNotFoundException | DuplicateInstanceException
				| NotValidDateException | ParseException e) {
			assertTrue("Error in maxCapacityTest", false);
		}
	}

	@Test(expected = NotValidDateException.class)
	public void forbiddenBookingTest() throws NotValidDateException {
		try {
			Student testSt1 = familyService.findStudent(1);
			DiningHall d1 = new DiningHall(2, BigDecimal.valueOf(4.00),
					DiningHall.Type.MADRUGADORES);

			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
			Calendar testDate = Calendar.getInstance();
			testDate.setTime(sdf.parse("25/12/2014"));
			Booking b1 = new Booking(testDate, testSt1, d1);
			bookingService.create(b1);
		} catch (MaxCapacityException | InstanceNotFoundException
				| ParseException | DuplicateInstanceException e) {
			assertTrue("Fallo en inicializaci�n de test", false);
		}
	}

	@Test
	public void findStudentBookingsByMonthAndYearTest() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

			DiningHall d1 = new DiningHall(24, BigDecimal.valueOf(4.00),
					DiningHall.Type.MADRUGADORES);
			DiningHall d2 = new DiningHall(20, BigDecimal.valueOf(5.00),
					DiningHall.Type.COMEDOR);

			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(sdf.parse("27/10/2014"));
			Booking b1 = new Booking(cal1, familyService.findStudent(3), d1);

			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(sdf.parse("22/10/2014"));
			Booking b2 = new Booking(cal2, familyService.findStudent(3), d1);

			Calendar cal3 = Calendar.getInstance();
			cal3.setTime(sdf.parse("01/08/2014"));
			Booking b3 = new Booking(cal3, familyService.findStudent(3), d1);

			Calendar cal4 = Calendar.getInstance();
			cal4.setTime(sdf.parse("03/10/2014"));
			Booking b4 = new Booking(cal4, familyService.findStudent(3), d2);

			Calendar cal5 = Calendar.getInstance();
			cal5.setTime(sdf.parse("03/02/2015"));
			Booking b5 = new Booking(cal5, familyService.findStudent(3), d2);

			bookingService.create(b1);
			bookingService.create(b2);
			bookingService.create(b3);
			bookingService.create(b4);
			bookingService.create(b5);

			assertEquals(
					3,
					bookingService.findStudentBookingsByMonthAndYear(
							familyService.findStudent(3), cal1).size());
			assertEquals(
					1,
					bookingService.findStudentBookingsByMonthAndYear(
							familyService.findStudent(3), cal3).size());
			assertEquals(
					1,
					bookingService.findStudentBookingsByMonthAndYear(
							familyService.findStudent(3), cal5).size());

			bookingService.remove(b1);
			bookingService.remove(b2);
			bookingService.remove(b3);
			bookingService.remove(b4);
			bookingService.remove(b5);
		} catch (InstanceNotFoundException | ParseException
				| DuplicateInstanceException | MaxCapacityException
				| NotValidDateException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getPlacesBookedTest() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

			DiningHall d1 = new DiningHall(24, BigDecimal.valueOf(4.00),
					DiningHall.Type.MADRUGADORES);
			DiningHall d2 = new DiningHall(24, BigDecimal.valueOf(4.00),
					DiningHall.Type.MADRUGADORES);

			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(sdf.parse("27/10/2016"));
			Booking b1 = new Booking(cal1, familyService.findStudent(0), d1);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(sdf.parse("27/10/2016"));
			Booking b2 = new Booking(cal2, familyService.findStudent(1), d1);
			Calendar cal3 = Calendar.getInstance();
			cal3.setTime(sdf.parse("27/10/2016"));
			Booking b3 = new Booking(cal1, familyService.findStudent(2), d1);
			Calendar cal4 = Calendar.getInstance();
			cal4.setTime(sdf.parse("27/10/2016"));
			Booking b4 = new Booking(cal4, familyService.findStudent(3), d1);

			bookingService.create(b1);
			bookingService.create(b2);
			bookingService.create(b3);
			assertEquals(3, bookingService.getPlacesBooked(b4));

			bookingService.remove(b2);
			assertEquals(2, bookingService.getPlacesBooked(b4));

			bookingService.create(b4);
			assertEquals(3, bookingService.getPlacesBooked(b2));

			Calendar cal5 = Calendar.getInstance();
			cal5.setTime(sdf.parse("27/10/2016"));
			Booking b5 = new Booking(cal4, familyService.findStudent(3), d2);

			assertEquals(0, bookingService.getPlacesBooked(b5));

		} catch (ParseException | InstanceNotFoundException
				| DuplicateInstanceException | MaxCapacityException
				| NotValidDateException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void isBookingAllDayOfWeekInMonthTest()
			throws InstanceNotFoundException, ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Student student = familyService.findStudent(0);
		DiningHall dh = bookingService.getDiningHall().get(0);
		Calendar calendar = Calendar.getInstance();
		Calendar cal = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH);
		while (calendar.get(Calendar.MONTH) == month) {
			Calendar cal2 = (Calendar) calendar.clone();
			cal.setTime(sdf.parse(calendar.get(Calendar.DAY_OF_MONTH) + "/"
					+ (calendar.get(Calendar.MONTH) + 1) + "/"
					+ calendar.get(Calendar.YEAR)));
			try {
				bookingService.create(new Booking(cal2, student, dh));
			} catch (DuplicateInstanceException | MaxCapacityException
					| NotValidDateException e) {
			}
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		student = familyService.findStudent(0);
		String [] result = bookingService.isBookingAllDayOfWeekInMonth(student);
		assertTrue(("Madrugadores: LMXJV").compareTo(result[0]) == 0);
	}

	@Test(expected = InstanceNotFoundException.class)
	public void findInexistentStudent() throws InstanceNotFoundException {
		bookingService.find(-1);
	}

	@Test(expected = InstanceNotFoundException.class)
	public void findInexistentDiningHall() throws InstanceNotFoundException {
		bookingService.findDiningHall(-1);
	}
}
