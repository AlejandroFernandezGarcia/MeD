package ampa.sa.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import ampa.sa.activity.Activity;
import ampa.sa.activity.ActivityService;
import ampa.sa.persistence.Persistence;
import ampa.sa.student.FamilyService;
import ampa.sa.student.Student;
import ampa.sa.util.exceptions.DuplicateInstanceException;
import ampa.sa.util.exceptions.InstanceNotFoundException;

public class ActivityTest {

	private ActivityService activityService = ActivityService.getInstance();
	private FamilyService familyService = FamilyService.getInstance();

	@Before
	public void before() throws ParseException {
		new DatosMock();
	}

	@Test
	public void addActivityTest() {

		Activity activity = new Activity("Baloncesto Primaria", 10,
				new BigDecimal(20.0), new HashSet<Student>());

		try {
			activityService.create(activity);
		} catch (DuplicateInstanceException e) {
			fail("Duplicated activity");
		}
		
		assertTrue(activityService.getActivities().contains(activity));

	}

	@Test(expected = DuplicateInstanceException.class)
	public void addDuplicateActivityTest() throws DuplicateInstanceException {

		Activity activity = null;
		try {
			activity = activityService.find(0);
		} catch (InstanceNotFoundException e) {
			fail("Activity not exist");
		}

		activityService.create(activity);

	}

	@Test
	public void removeActivityTest() throws InstanceNotFoundException {

		Activity activity = null;
		try {
			activity = activityService.find(0);
		} catch (InstanceNotFoundException e1) {
			fail("Activity not exist");
		}
		activityService.remove(activity);
		
		assertFalse("Activity not delete",activityService.getActivities().contains(activity));

	}

	@Test(expected = InstanceNotFoundException.class)
	public void removeInexistentActivityTest() throws InstanceNotFoundException {
		
		Activity activity = null;
		try {
			activity = activityService.find(0);
		} catch (InstanceNotFoundException e1) {
			fail("Activity not exist");
		}
		activityService.remove(activity);
		activityService.remove(activity);

	}

	@Test
	public void updateActivityTest() throws InstanceNotFoundException {
		Activity a = activityService.find(0);
		
		a.setPlaces(80);
		a.setPrize(new BigDecimal(100.2));
		
		activityService.update(a);
		
		assertTrue("Activity not modified",activityService.getActivities().contains(a));
		
		
	}
	
	@Test(expected = InstanceNotFoundException.class)
	public void updateInexistentActivityTest() throws InstanceNotFoundException{
		Activity a = activityService.find(0);
		activityService.remove(a);
		
		a.setPlaces(80);
		a.setPrize(new BigDecimal(100.2));
		
		activityService.update(a);
	}

	@Test(expected = InstanceNotFoundException.class)
	public void findInexistentActivityTest() throws InstanceNotFoundException {
		activityService.find(-1);
		
	}
	
	@Test(expected = InstanceNotFoundException.class)
	public void findByNameInexistentActivityTest() throws InstanceNotFoundException {
		Activity a = activityService.find(0);
		activityService.remove(a);
		
		activityService.findByName(a.getName());
		
	}

	@Test
	public void findByNameTest() throws InstanceNotFoundException {
		Activity a = activityService.find(0);
		
		Activity b = activityService.findByName(a.getName());
		
		assertEquals("Find by name not work",a,b);
	}
	
	@Test
	public void findEstudentInActiviteTest() throws InstanceNotFoundException{
		Activity a = activityService.find(0);
		for (Student s: a.getStudents()) {
			activityService.unEnrollsStudentToActivity(s, a);
		}
		Student s = familyService.findStudent(0);
		activityService.enrollmentStudentInActivity(s, a);
		
		assertTrue("Too many students in this activity",activityService.findStudents(a).size()==1);
	}
	
	@Test
	public void enrollementStudentInActivityTest() {
		Student s = null;
		Activity a = null;
		try {
			s = familyService.findStudent(1);
			a = activityService.find(1);
		} catch (InstanceNotFoundException e) {
			fail("Activity not exists");
		}
		activityService.enrollmentStudentInActivity(s, a);
		assertTrue("Student isn't enrolled in the activity", s.getActivities()
				.contains(a));
		assertTrue("Activity hasn't the student enrolled", a.getStudents()
				.contains(s));

	}

	@Test
	public void unEnrollsStudentToActivity() {
		Student s = null;
		Activity a = null;
		try {
			s = familyService.findStudent(1);
			a = activityService.find(1);
		} catch (InstanceNotFoundException e) {
			fail("Activity not exists");
		}
		activityService.enrollmentStudentInActivity(s, a);

		activityService.unEnrollsStudentToActivity(s, a);

		assertFalse("Student is enrolled in the activity yet", s
				.getActivities().contains(a));
		assertFalse("Activity has the student enrolled yet", a.getStudents()
				.contains(s));

	}
}
