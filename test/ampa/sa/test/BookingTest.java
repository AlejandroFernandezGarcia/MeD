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
			assertTrue("Student not found",false);
		}
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Calendar cb = Calendar.getInstance();
		try {
			cb.setTime(sdf.parse("27/10/2014"));
		} catch (ParseException e) {
			assertTrue("Bad format to calendar",false);
		}
		try {
			bookingService.create(new Booking(3, cb, s, bookingService
					.getDiningHall().get(0)));
		} catch (DuplicateInstanceException | MaxCapacityException e) {
			assertTrue("Booking has already been created",false);
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
			assertTrue("Booking not found",false);
		}

	}

	@Test(expected = DuplicateInstanceException.class)
	public void addDuplicateBookingTest() throws DuplicateInstanceException {

		Student s = null;
		Booking b = null;
		try {
			s = familyService.findStudent(3);
		} catch (InstanceNotFoundException e) {
			assertTrue("Student not found",false);
		}
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Calendar cb = Calendar.getInstance();
		try {
			cb.setTime(sdf.parse("27/10/2014"));
		} catch (ParseException e) {
			assertTrue("Bad format to calendar",false);
		}
		b = new Booking(3, cb, s, bookingService.getDiningHall().get(0));

		try {
			bookingService.create(b);
			bookingService.create(b);
		} catch (MaxCapacityException e1) {
			assertTrue("Full dining hall ",false);
		} catch (NotValidDateException e) {
			assertTrue("Invalid date",false);
		}
	}

	@Test
	public void checkPlacesByDateTest() {

		Student s = null;
		try {
			s = familyService.findStudent(4);
		} catch (InstanceNotFoundException e) {
			assertTrue("Student not found",false);
		}
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Calendar cb = Calendar.getInstance();
		try {
			cb.setTime(sdf.parse("27/10/2014"));
		} catch (ParseException e) {
			assertTrue("Bad format to calendar",false);
		}
		try {
			bookingService.create(new Booking(3, cb, s, bookingService
					.getDiningHall().get(0)));
		} catch (DuplicateInstanceException | MaxCapacityException e) {
			assertTrue("Duplicate booking or full dining hall",false);
		} catch (NotValidDateException e) {
			assertTrue("Invalid date",false);
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
			assertTrue("Booking not found",false);
		}

		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Calendar cb = Calendar.getInstance();
		try {
			cb.setTime(sdf.parse("28/10/2014"));
		} catch (ParseException e) {
			assertTrue("Bad format to calendar",false);
		}

		b.setDate(cb);

		try {
			bookingService.update(b);
		} catch (InstanceNotFoundException e1) {
			assertTrue("Booking not exists",false);
		}

		try {
			bookingService.find(1);
		} catch (InstanceNotFoundException e) {
			assertTrue("Booking not exsists",false);
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
			assertTrue("Booking not found",false);
		}

		List<Booking> lb = bookingService.getStudentBookingsByDate(s,
				b.getDate());
		assertEquals(lb.size(), 1);

		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Calendar cb1 = Calendar.getInstance();
		try {
			cb1.setTime(sdf.parse("27/10/2014"));
		} catch (ParseException e1) {
			assertTrue("Bad format to calendar",false);
		}

		try {
			bookingService.create(new Booking(3, cb1, s, bookingService
					.findDiningHall(2)));
		} catch (DuplicateInstanceException | MaxCapacityException
				| NotValidDateException | InstanceNotFoundException e) {
			assertTrue("Error in getStudentBookingByDateTest",false);
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
			assertTrue("Booking not found",false);
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
			assertTrue("Bad format to calendar",false);
		}

		try {
			bookingService.create(new Booking(3, cb1, s, bookingService
					.findDiningHall(2)));
		} catch (DuplicateInstanceException | MaxCapacityException
				| NotValidDateException | InstanceNotFoundException e) {
			assertTrue("Error in getStudentBookingByDateTest",false);
		}

		lb = bookingService.getStudentBookingsByDate(s, b.getDate());
		assertEquals(bookingService.getBookingsPrize(lb),
				new BigDecimal("9.00"));
	}

}
