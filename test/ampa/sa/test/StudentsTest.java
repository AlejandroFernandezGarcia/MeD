package ampa.sa.test;

import static org.junit.Assert.assertEquals;
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
import ampa.sa.bill.Bill;
import ampa.sa.booking.Booking;
import ampa.sa.booking.BookingService;
import ampa.sa.student.FamilyService;
import ampa.sa.student.Household;
import ampa.sa.student.Student;
import ampa.sa.util.exceptions.DuplicateInstanceException;
import ampa.sa.util.exceptions.InstanceNotFoundException;

public class StudentsTest {

	private FamilyService familyService = FamilyService.getInstance();
	private ActivityService activityService = ActivityService.getInstance();
	private BookingService bookingService = BookingService.getInstance();

	public static final String DATE_FORMAT = "dd/MM/yyyy";

	@Before
	public void before() throws ParseException {
		new DatosMock();
	}

	@Test
	public void TestCreateStudent() throws InstanceNotFoundException,
			ParseException, DuplicateInstanceException {

		Household household = familyService.findHousehold("666-777-888-999");
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Calendar cal3 = Calendar.getInstance();
		cal3.setTime(sdf.parse("12/03/2006"));
		Student student = new Student(household, "Alejandro", "Fortes",
				Student.Category.PRIMARIA, cal3, new HashSet<Activity>(),
				new HashSet<Booking>());

		familyService.createStudent(student);

		assertTrue("Student not created",
				familyService.getStudents().contains(student));

		assertTrue(familyService.findStudents(household).contains(student));

	}

	@Test(expected = DuplicateInstanceException.class)
	public void TestCreateDuplicateStudent() throws DuplicateInstanceException {

		Student student = null;
		try {
			student = familyService.findStudent(0);
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		}

		familyService.createStudent(student);

	}

	@Test
	public void TestCreateHousehold() throws InstanceNotFoundException {

		Household household = new Household("111-111-333-333",
				new HashSet<Student>(), new HashSet<Bill>(),"Francisca");

		try {
			familyService.createHousehold(household);
		} catch (DuplicateInstanceException e) {
			e.printStackTrace();
		}

		assertEquals(familyService.findHousehold("111-111-333-333"), household);

	}

	@Test(expected = DuplicateInstanceException.class)
	public void TestCreateDuplicateHousehold()
			throws InstanceNotFoundException, DuplicateInstanceException {
		Household hh = null;

		hh = familyService.getHousehold().get(0);

		familyService.createHousehold(hh);

	}

	@Test(expected = InstanceNotFoundException.class)
	public void TestNotFoundStudent() throws InstanceNotFoundException {

		familyService.findStudent(-1);

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

		familyService.removeStudent(-1);

	}

	@Test
	public void TestUpdateStudent() throws InstanceNotFoundException {
		Student s = familyService.findStudent(0);
		s.setCategory(Student.Category.PRIMARIA);
		Household hh = s.getHouseHold();
		hh.setBanckAccount("xxx-yyy");
		s.setHouseHold(hh);
		familyService.updateStudent(s);
	}

	@Test(expected = InstanceNotFoundException.class)
	public void TestUpdateStudentInexistent() throws InstanceNotFoundException {
		Student s = familyService.findStudent(0);
		s.setCategory(Student.Category.PRIMARIA);
		Household hh = s.getHouseHold();
		hh.setBanckAccount("xxx-yyy");
		s.setHouseHold(hh);

		familyService.removeStudent(0);
		familyService.updateStudent(s);
	}

	@Test
	public void TestFindStudents() throws InstanceNotFoundException {

		List<Student> students = familyService.findStudents();
		Student student = familyService.findStudent(0);

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

		assertEquals(familyService.getStudentExpenses(student), new BigDecimal(
				"72.50"));

	}

	@Test
	public void TestGetExpensesHousehold() throws InstanceNotFoundException,
			DuplicateInstanceException {

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

		Household household = new Household("111-222-111-222", students,
				new HashSet<Bill>(),"Manuela");
		familyService.createHousehold(household);

		assertEquals(familyService.getHouseholdExpenses(familyService
				.findHousehold("111-222-111-222")), new BigDecimal("72.50"));
	}

	@Test
	public void TestGetStudentType() throws InstanceNotFoundException,
			ParseException, DuplicateInstanceException {
		Student student1 = familyService.findStudent(0);
		String type = familyService.getStudentType(student1);
		assertEquals(type, "INFANTIL");

		student1.setCategory(Student.Category.PRIMARIA);
		;
		familyService.updateStudent(student1);

		type = familyService.getStudentType(student1);
		assertEquals(type, "PRIMARIA");

	}

	@Test
	public void findStudentByHousehold() throws InstanceNotFoundException {
		Household hh = familyService.findHousehold(0);
		assertEquals(hh.getMentored().size(), familyService.findStudents(hh)
				.size());
	}

	@Test(expected = InstanceNotFoundException.class)
	public void findInexistentHousehold() throws InstanceNotFoundException {
		Household hh = familyService.findHousehold(-1);
	}

	@Test(expected = InstanceNotFoundException.class)
	public void findInexistentHouseholdByBankAccount()
			throws InstanceNotFoundException {
		Household hh = familyService.findHousehold("");
	}

	@Test
	public void removeHousehold() throws InstanceNotFoundException,
			ParseException, DuplicateInstanceException {
		Household hh = familyService.findHousehold(0);
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Calendar cal3 = Calendar.getInstance();
		cal3.setTime(sdf.parse("12/03/2006"));
		Student student = new Student(hh, "Alejandro", "Fortes",
				Student.Category.PRIMARIA, cal3, new HashSet<Activity>(),
				new HashSet<Booking>());

		familyService.createStudent(student);

		familyService.removeHousehold(hh.getBanckAccount());
		try {
			familyService.findHousehold(hh.getBanckAccount());
		} catch (InstanceNotFoundException e) {
			assertTrue(true);
		}
	}

	@Test(expected = InstanceNotFoundException.class)
	public void removeInexistenHousehold() throws InstanceNotFoundException {
		familyService.removeHousehold("a");
	}

}
