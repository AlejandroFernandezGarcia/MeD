package ampa.sa.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import ampa.sa.activity.Activity;
import ampa.sa.activity.ActivityService;
import ampa.sa.booking.Booking;
import ampa.sa.booking.BookingService;
import ampa.sa.student.FamilyService;
import ampa.sa.student.Household;
import ampa.sa.student.Student;
import ampa.sa.util.exceptions.DuplicateInstanceException;
import ampa.sa.util.exceptions.InstanceNotFoundException;

public class TestStudents {

	private FamilyService familyService = FamilyService.getInstance();
	private ActivityService activityService = ActivityService.getInstance();
	private BookingService bookingService = BookingService.getInstance();

	public static final String DATE_FORMAT = "dd/MM/yyyy";

	@Before
	public void before() throws ParseException {
		new DatosMock();
	}
	
	@Test
	public void TestGetStudentExpenses() throws InstanceNotFoundException {

		Student student = familyService.findStudent(1);

		Set<Activity> activities = new HashSet<Activity>();
		Activity activity = activityService.find(1);
		activities.add(activity);
		activity = activityService.find(2);
		activities.add(activity);
		activity = activityService.find(3);
		activities.add(activity);
		student.setActivities(activities); // TOTAL: 64.0

		Set<Booking> bookings = new HashSet<Booking>();
		Booking booking = bookingService.find(1);
		bookings.add(booking);
		student.setBookings(bookings); // TOTAL; 4.0

		familyService.updateStudent(student);

		assertEquals(familyService.getStudentExpenses(student), new BigDecimal("68.0"));

	}
	
	@Test
	public void TestGetExpensesHousehold() throws InstanceNotFoundException, DuplicateInstanceException {

		Student student = familyService.findStudent(1);

		Set<Activity> activities = new HashSet<Activity>();
		Activity activity = activityService.find(1);
		activities.add(activity);
		activity = activityService.find(2);
		activities.add(activity);
		activity = activityService.find(3);
		activities.add(activity);
		student.setActivities(activities); // TOTAL: 64.0

		Set<Booking> bookings = new HashSet<Booking>();
		Booking booking = bookingService.find(1);
		bookings.add(booking);
		student.setBookings(bookings); // TOTAL; 4.0
		
		familyService.updateStudent(student);
		
		Set<Student> students = new HashSet<Student>();
		students.add(student);
		
		Household household = new Household("111-222-111-222", students);
		familyService.createHousehold(household);
		
		assertEquals(familyService.getHouseholdExpenses(familyService
				.findHousehold("111-222-111-222")), new BigDecimal("68.0"));
	}
	
	@Test
	public void TestGetStudentType() throws InstanceNotFoundException,
			ParseException, DuplicateInstanceException {
		Student student1 = familyService.findStudent(1);
		String type = familyService.getStudentType(student1);
		assertEquals(type, "Primary (11 years)");
		
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(sdf.parse("20/05/2001"));		
		student1.setDateBorn(cal1);
		familyService.updateStudent(student1);
		
		type = familyService.getStudentType(student1);
		assertEquals(type, "Secundary (13 years)");

	}
	
	@Test
	public void TestGetStudentTypeNull() throws InstanceNotFoundException,
			ParseException, DuplicateInstanceException {
		Student student1 = familyService.findStudent(1);
		String type = familyService.getStudentType(student1);
		assertEquals(type, "Primary (11 years)");
		
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(sdf.parse("20/05/2999"));		
		student1.setDateBorn(cal1);
		familyService.updateStudent(student1);
		
		type = familyService.getStudentType(student1);
		assertNull(type);

	}
	
	@Test
	public void TestCreateStudent() throws InstanceNotFoundException, ParseException {

		Household household = familyService.findHousehold("666-777-888-999");
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Calendar cal3 = Calendar.getInstance();
		cal3.setTime(sdf.parse("12/03/2006"));
		Student student = new Student(5, household, "Alejandro", "Fortes",
				cal3, new HashSet<Activity>(), new HashSet<Booking>());

		try {
			familyService.createStudent(student);
		} catch (DuplicateInstanceException e) {
			e.printStackTrace();
		}

		assertEquals(familyService.findStudent(5), student);

	}
	
	@Test(expected = DuplicateInstanceException.class)
	public void TestCreateDuplicateStudent() throws DuplicateInstanceException {

		Student student = null;
		try {
			student = familyService.findStudent(4);
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		}

		familyService.createStudent(student);

	}

	@Test
	public void TestCreateHousehold() throws InstanceNotFoundException {

		Household household = new Household("111-111-333-333",
				new HashSet<Student>());
		
		try {
			familyService.createHousehold(household);
		} catch (DuplicateInstanceException e) {
			e.printStackTrace();
		}
		
		assertEquals(familyService.findHousehold("111-111-333-333"), household);

	}

	@Test(expected = InstanceNotFoundException.class)
	public void TestNotFoundStudent() throws InstanceNotFoundException {

		familyService.findStudent(6);

	}

	@Test
	public void TestRemoveStudent() throws InstanceNotFoundException {

		try {
			familyService.removeStudent(1);
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		}

		assertEquals(familyService.getStudents().size(), 3);

	}
	
	@Test(expected = InstanceNotFoundException.class)
	public void TestRemoveInexistentStudent() throws InstanceNotFoundException {
		
		familyService.removeStudent(6);

	}

	@Test
	public void TestUpdateStudent() throws InstanceNotFoundException {

		Student student = familyService.findStudent(1);
		student.setName("Berto");
		familyService.updateStudent(student);
		student = familyService.findStudent(1);
		assertEquals(familyService.findStudent(1).getName(), "Berto");

	}

	
	@Test
	public void TestFindStudents() throws InstanceNotFoundException {

		List<Student> students = familyService.findStudents();
		Student student = familyService.findStudent(1);

		assertEquals(students.size(), 4);
		assertTrue(students.contains(student));

	}

	@Test
	public void TestFindHouseholds() throws InstanceNotFoundException {

		List<Household> households = familyService.findHouseholds();
		Household household = familyService.findHousehold("111-222-333-444");

		assertEquals(households.size(), 4);
		assertTrue(households.contains(household));

	}

	/*
	@Test
	public void TestFindStudentsByHousehold() throws InstanceNotFoundException {

		Household household = familyService.findHousehold("111-222-333-444");
		List<Student> students = familyService.findStudents(household);
		Student student = familyService.findStudent(1);
		
		assertEquals(students.size(), 1);
		assertTrue(students.contains(student));

	}
	*/
	
}
