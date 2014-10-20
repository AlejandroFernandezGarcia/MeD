package ampa.sa.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import ampa.sa.booking.Booking;
import ampa.sa.persistence.Persistence;
import ampa.sa.student.Student;
import ampa.sa.util.exceptions.DuplicateInstanceException;
import ampa.sa.util.exceptions.InstanceNotFoundException;

@SuppressWarnings("serial")
public class ActivityService implements Serializable {

	List<Activity> activities = new ArrayList<Activity>();

	private static ActivityService instance = null;

	private ActivityService() {
	}

	private synchronized static void createInstance() {
		if (instance == null) {
			instance = new ActivityService();
		}
	}

	public static ActivityService getInstance() {
		createInstance();
		return instance;
	}

	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

	public void create(Activity activity) throws DuplicateInstanceException {
		if (!activities.contains(activity)) {
			activities.add(activity);
			Persistence.getInstance().save();
		} else {
			throw new DuplicateInstanceException(activity, "Activity");
		}
	}

	public void remove(Activity activity) throws InstanceNotFoundException {
		boolean found = false;
		Iterator<Activity> iter;
		iter = activities.iterator();
		while (iter.hasNext() && !found) {
			if (iter.next().getId() == activity.getId()) {
				found = true;
				activities.remove(iter.next());
				Persistence.getInstance().save();
			}
		}
		if (!found) {
			throw new InstanceNotFoundException(activity.getId(), "Activity");
		}
	}

	public void update(Activity activity) {
		boolean found = false;
		Iterator<Activity> iter;
		iter = activities.iterator();
		while (iter.hasNext() && !found) {
			Activity a = iter.next();
			if (a.getId() == activity.getId()) {
				found = true;
				a.setName(activity.getName());
				a.setPlaces(activity.getPlaces());
				a.setPrize(activity.getPrize());
				a.setStudents(activity.getStudents());
				Persistence.getInstance().save();
			}
		}
	}

	public List<Student> findStudents(Activity activity) {
		List<Student> students = new ArrayList<Student>();
		Set<Student> studentsActivity = activity.getStudents();
		for (Student s : studentsActivity) {
			students.add(s);
		}
		return students;
	}

	public List<Activity> findActivities() {
		return activities;
	}

	public Activity find(long id) throws InstanceNotFoundException {
		for (Activity a : activities) {
			if (a.getId() == id) {
				return a;
			}
		}
		throw new InstanceNotFoundException(id, "Activity");
	}
}
