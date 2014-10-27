package ampa.sa.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ampa.sa.activity.ActivityService;
import ampa.sa.booking.Booking;
import ampa.sa.booking.BookingService;
import ampa.sa.student.FamilyService;
import ampa.sa.student.Student;
import ampa.sa.util.exceptions.DuplicateInstanceException;
import ampa.sa.util.exceptions.InstanceNotFoundException;
import ampa.sa.util.exceptions.MaxCapacityException;

public class TestBooking {

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
			s = familyService.findStudent(4);
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Calendar cb = Calendar.getInstance();
		try {
			cb.setTime(sdf.parse("27/10/2014"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			bookingService.create(new Booking(3, cb, s, bookingService
					.getDiningHall().get(0)));
		} catch (DuplicateInstanceException | MaxCapacityException e) {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test(expected = DuplicateInstanceException.class)
	public void addDuplicateBookingTest() throws DuplicateInstanceException {

		Student s = null;
		Booking b = null;
		try {
			s = familyService.findStudent(4);
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Calendar cb = Calendar.getInstance();
		try {
			cb.setTime(sdf.parse("27/10/2014"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		b = new Booking(3, cb, s, bookingService.getDiningHall().get(0));

		try {
			try {
				bookingService.create(b);
			} catch (DuplicateInstanceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (MaxCapacityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			bookingService.create(b);
		} catch (MaxCapacityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void checkPlacesByDateTest() {

		Student s = null;
		try {
			s = familyService.findStudent(4);
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Calendar cb = Calendar.getInstance();
		try {
			cb.setTime(sdf.parse("27/10/2014"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			bookingService.create(new Booking(3, cb, s, bookingService
					.getDiningHall().get(0)));
		} catch (DuplicateInstanceException | MaxCapacityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertEquals(bookingService.getPlacesForDiningSchedule(cb,
				bookingService.getDiningHall().get(0)), 22);
	}

	@Test(expected = InstanceNotFoundException.class)
	public void removeBookingTest() throws InstanceNotFoundException {

		Booking b = null;
		try {
			b = bookingService.find(1);
			bookingService.remove(b);
		} catch (InstanceNotFoundException e) {

		}

		bookingService.find(1);

	}

	@Test(expected = InstanceNotFoundException.class)
	public void removeInexistantBookingTest() throws InstanceNotFoundException {

		Booking b = null;
		try {
			b = bookingService.find(1);
			bookingService.remove(b);
		} catch (InstanceNotFoundException e) {

		}
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Calendar cb = Calendar.getInstance();
		try {
			cb.setTime(sdf.parse("28/10/2014"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		b.setDate(cb);

		try {
			bookingService.update(b);
		} catch (InstanceNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			bookingService.find(1);
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertNotEquals(b.getDate(), d);
	}

	@Test(expected = InstanceNotFoundException.class)
	public void updateInexistentBookingTest() throws InstanceNotFoundException {

		Booking b = null;
		try {
			b = bookingService.find(1);
			bookingService.remove(b);
		} catch (InstanceNotFoundException e) {

		}
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

		}

		List<Booking> lb = bookingService.getStudentBookingsByDate(s,
				b.getDate());
		assertEquals(lb.size(), 1);

		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Calendar cb1 = Calendar.getInstance();
		try {
			cb1.setTime(sdf.parse("27/10/2014"));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			bookingService.create(new Booking(3, cb1, s, bookingService
					.findDiningHall(2)));
		} catch (DuplicateInstanceException | MaxCapacityException
				| InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// esto solo busca si un estudiante tiene reservas ese dia, de mañana o
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

		}

		List<Booking> lb = bookingService.getStudentBookingsByDate(s,
				b.getDate());
		assertEquals(bookingService.getBookingsPrize(lb),
				BigDecimal.valueOf(4.00));

		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Calendar cb1 = Calendar.getInstance();
		try {
			cb1.setTime(sdf.parse("27/10/2014"));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			bookingService.create(new Booking(3, cb1, s, bookingService
					.findDiningHall(2)));
		} catch (DuplicateInstanceException | MaxCapacityException
				| InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		lb = bookingService.getStudentBookingsByDate(s, b.getDate());
		assertEquals(bookingService.getBookingsPrize(lb),
				BigDecimal.valueOf(9.00));
	}

}
