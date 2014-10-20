package ampa.sa.test;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.HashSet;

import org.junit.Test;

import ampa.sa.activity.Activity;
import ampa.sa.activity.ActivityService;
import ampa.sa.student.Student;
import ampa.sa.util.exceptions.DuplicateInstanceException;
import ampa.sa.util.exceptions.InstanceNotFoundException;


public class TestActividades {

	private ActivityService activityService = ActivityService.getInstance();
	
	@Test
	public void addActivity() {

		ActivityService activityService = ActivityService.getInstance();
		Activity activity = new Activity(4, "Baloncesto", 10, new BigDecimal(
				20.0), new HashSet<Student>());

		try {
			activityService.create(activity);
		} catch (DuplicateInstanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			assertEquals(activityService.find(4), activity);
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test(expected = DuplicateInstanceException.class)
	public void addDuplicateActivity() {

		ActivityService activityService = ActivityService.getInstance();
		Activity activity = new Activity(4, "Baloncesto", 10, new BigDecimal(
				20.0), new HashSet<Student>());

		try {
			activityService.create(activity);
			activityService.create(activity);
		} catch (DuplicateInstanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test(expected = InstanceNotFoundException.class)
	public void removeActivity() {

		ActivityService activityService = ActivityService.getInstance();
		Activity activity = new Activity(4, "Baloncesto", 10, new BigDecimal(
				20.0), new HashSet<Student>());

		try {
			activityService.create(activity);
		} catch (DuplicateInstanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			assertEquals(activityService.find(4), activity);
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			activityService.remove(activity);
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			assertEquals(activityService.find(4), activity);
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test(expected = InstanceNotFoundException.class)
	public void removeInexistentActivity() {

		ActivityService activityService = ActivityService.getInstance();
		Activity activity = new Activity(4, "Baloncesto", 10, new BigDecimal(
				20.0), new HashSet<Student>());

		try {
			activityService.remove(activity);
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void updateActivity() {

		ActivityService activityService = ActivityService.getInstance();
		Activity activity = new Activity(4, "Baloncesto", 10, new BigDecimal(
				20.0), new HashSet<Student>());

		try {
			activityService.create(activity);
		} catch (DuplicateInstanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		activity.setName("Baloncesto Primaria");

		try {
			activityService.update(activity);
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void findInexistantActivity() {

		ActivityService activityService = ActivityService.getInstance();
		Activity activity = new Activity(4, "Baloncesto", 10, new BigDecimal(
				20.0), new HashSet<Student>());

		try {
			assertEquals(activityService.find(4), activity);
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
