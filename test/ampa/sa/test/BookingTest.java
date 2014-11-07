package ampa.sa.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

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
import ampa.sa.util.Schedule;
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
			bookingService.create(new Booking(3, cb, s, bookingService
					.getDiningHall().get(0)));
		} catch (DuplicateInstanceException | MaxCapacityException e) {
			assertTrue("Booking has already been created", false);
		} catch (NotValidDateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Student ss = null;
		try {
			ss = bookingService.find(3).getStudent();
			assertEquals(ss, s);
			assertEquals(bookingService.find(3).getDate(), cb);
			assertEquals(bookingService.find(3).getDiningHall(), bookingService
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
		b = new Booking(3, cb, s, bookingService.getDiningHall().get(0));

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
			s = familyService.findStudent(4);
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
			bookingService.create(new Booking(3, cb, s, bookingService
					.getDiningHall().get(0)));
		} catch (DuplicateInstanceException | MaxCapacityException e) {
			assertTrue("Duplicate booking or full dining hall", false);
		} catch (NotValidDateException e) {
			assertTrue("Invalid date", false);
		}

		assertEquals(bookingService.getPlacesForDiningSchedule(cb,
				bookingService.getDiningHall().get(0)), 22);
	}

	@Test(expected = InstanceNotFoundException.class)
	public void removeBookingTest() throws InstanceNotFoundException {

		Booking b = null;

		b = bookingService.find(1);
		bookingService.remove(b);

		bookingService.find(1);

	}

	@Test(expected = InstanceNotFoundException.class)
	public void removeInexistantBookingTest() throws InstanceNotFoundException {

		Booking b = null;

		b = bookingService.find(1);
		bookingService.remove(b);

		bookingService.remove(b);

	}

	@Test
	public void updateBookingTest() {

		Booking b = null;
		Calendar d = null;
		try {
			b = bookingService.find(1);
			d = b.getDate();
		} catch (InstanceNotFoundException e) {
			assertTrue("Booking not found", false);
		}

		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Calendar cb = Calendar.getInstance();
		try {
			cb.setTime(sdf.parse("28/10/2014"));
		} catch (ParseException e) {
			assertTrue("Bad format to calendar", false);
		}

		b.setDate(cb);

		try {
			bookingService.update(b);
		} catch (InstanceNotFoundException e1) {
			assertTrue("Booking not exists", false);
		}

		try {
			bookingService.find(1);
		} catch (InstanceNotFoundException e) {
			assertTrue("Booking not exsists", false);
		}

		assertNotEquals(b.getDate(), d);
	}

	@Test(expected = InstanceNotFoundException.class)
	public void updateInexistentBookingTest() throws InstanceNotFoundException {

		Booking b = null;

		b = bookingService.find(1);
		bookingService.remove(b);

		bookingService.update(b);
	}

	@Test
	public void getStudentBookingByDateTest() {

		Booking b = null;
		Student s = null;
		try {
			b = bookingService.find(1);
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
			bookingService.create(new Booking(3, cb1, s, bookingService
					.findDiningHall(2)));
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
			b = bookingService.find(1);
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
			bookingService.create(new Booking(3, cb1, s, bookingService
					.findDiningHall(2)));
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
			Student testSt1 = familyService.findStudent(1);
			Student testSt2 = familyService.findStudent(2);

			Schedule sch = new Schedule();
			sch.setStartTime("12:30pm");
			sch.setEndTime("14:00pm");
			DiningHall d1 = new DiningHall(1, sch, 1, BigDecimal.valueOf(4.00));

			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
			Calendar testDate = Calendar.getInstance();
			testDate.setTime(sdf.parse("21/11/2014"));
			Booking b1 = new Booking(1, testDate, testSt1, d1);
			Booking b2 = new Booking(1, testDate, testSt2, d1);
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
			Schedule sch = new Schedule();
			sch.setStartTime("12:30pm");
			sch.setEndTime("14:00pm");
			DiningHall d1 = new DiningHall(1, sch, 2, BigDecimal.valueOf(4.00));

			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
			Calendar testDate = Calendar.getInstance();
			testDate.setTime(sdf.parse("25/12/2014"));
			Booking b1 = new Booking(1, testDate, testSt1, d1);
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

			Schedule sc1 = new Schedule();
			sc1.setStartTime("8:00am");
			sc1.setEndTime("9:30am");

			DiningHall d1 = new DiningHall(1, sc1, 24, BigDecimal.valueOf(4.00));

			Schedule sc2 = new Schedule();
			sc2.setStartTime("12:30pm");
			sc2.setEndTime("14:00pm");

			DiningHall d2 = new DiningHall(2, sc2, 20, BigDecimal.valueOf(5.00));

			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(sdf.parse("27/10/2014"));
			Booking b1 = new Booking(3, cal1, familyService.findStudent(3), d1);

			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(sdf.parse("22/10/2014"));
			Booking b2 = new Booking(4, cal2, familyService.findStudent(3), d1);

			Calendar cal3 = Calendar.getInstance();
			cal3.setTime(sdf.parse("01/08/2014"));
			Booking b3 = new Booking(5, cal3, familyService.findStudent(3), d1);

			Calendar cal4 = Calendar.getInstance();
			cal4.setTime(sdf.parse("03/10/2014"));
			Booking b4 = new Booking(6, cal4, familyService.findStudent(3), d2);

			Calendar cal5 = Calendar.getInstance();
			cal5.setTime(sdf.parse("03/02/2015"));
			Booking b5 = new Booking(7, cal5, familyService.findStudent(3), d2);

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

			Schedule sc1 = new Schedule();
			sc1.setStartTime("8:00am");
			sc1.setEndTime("9:30am");

			DiningHall d1 = new DiningHall(1, sc1, 24, BigDecimal.valueOf(4.00));

			Schedule sc2 = new Schedule();
			sc1.setStartTime("8:00am");
			sc1.setEndTime("9:30am");

			DiningHall d2 = new DiningHall(2, sc2, 24, BigDecimal.valueOf(4.00));

			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(sdf.parse("27/10/2016"));
			Booking b1 = new Booking(8, cal1, familyService.findStudent(1), d1);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(sdf.parse("27/10/2016"));
			Booking b2 = new Booking(9, cal2, familyService.findStudent(2), d1);
			Calendar cal3 = Calendar.getInstance();
			cal3.setTime(sdf.parse("27/10/2016"));
			Booking b3 = new Booking(10, cal1, familyService.findStudent(3), d1);
			Calendar cal4 = Calendar.getInstance();
			cal4.setTime(sdf.parse("27/10/2016"));
			Booking b4 = new Booking(11, cal4, familyService.findStudent(4), d1);

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
			Booking b5 = new Booking(8, cal4, familyService.findStudent(4), d2);

			assertEquals(0, bookingService.getPlacesBooked(b5));

		} catch (ParseException | InstanceNotFoundException
				| DuplicateInstanceException | MaxCapacityException
				| NotValidDateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
