package ampa.sa.test;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import ampa.sa.activity.Activity;
import ampa.sa.activity.ActivityService;
import ampa.sa.persistence.Persistence;
import ampa.sa.student.Student;
import ampa.sa.test.DatosMock;
import ampa.sa.util.exceptions.DuplicateInstanceException;
import ampa.sa.util.exceptions.InstanceNotFoundException;

public class TestActividades {

	private ActivityService activityService = ActivityService.getInstance();

	@Before
	public void before() throws ParseException {
		new DatosMock();
	}

	@Test
	public void addActivity() {

		Activity activity = new Activity(8, "Baloncesto Primaria", 10,
				new BigDecimal(20.0), new HashSet<Student>());

		try {
			activityService.create(activity);
		} catch (DuplicateInstanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			assertEquals(activityService.find(8), activity);
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test(expected = DuplicateInstanceException.class)
	public void addDuplicateActivity() throws DuplicateInstanceException {

		Activity activity = null;
		try {
			activity = activityService.find(4);
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		activityService.create(activity);

	}

	@Test(expected = InstanceNotFoundException.class)
	public void removeActivity() throws InstanceNotFoundException {

		Activity activity = null;
		try {
			activity = activityService.find(4);
		} catch (InstanceNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		assertEquals(activityService.getActivities().size(), 7);

		try {
			activityService.remove(activity);
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertEquals(activityService.getActivities().size(), 6);
		activityService.find(4);

	}

	@Test(expected = InstanceNotFoundException.class)
	public void removeInexistentActivity() throws InstanceNotFoundException {

		Activity activity = new Activity(8, "Baloncesto de mentira", 10,
				new BigDecimal(20.0), new HashSet<Student>());

		activityService.remove(activity);
	}

	@Test
	public void updateActivity() {

		Activity activity = null;
		try {
			activity = activityService.find(4);
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		activity.setName("Baloncesto Primaria");

		activityService.update(activity);

		try {
			assertEquals(activityService.find(4), activity);
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test(expected = InstanceNotFoundException.class)
	public void findInexistantActivity() throws InstanceNotFoundException {
		activityService.find(8);

	}

}
