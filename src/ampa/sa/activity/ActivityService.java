package ampa.sa.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
		Activity aux;
		iter = activities.iterator();
		while (iter.hasNext() && !found) {
			if ((aux = iter.next()).equals(activity)) {
				found = true;
				activities.remove(aux);
				Persistence.getInstance().save();
			}
		}
		if (!found) {
			throw new InstanceNotFoundException(activity, "Activity");
		}
	}

	public void update(Activity activity) throws InstanceNotFoundException {
		boolean found = false;
		Iterator<Activity> iter;
		iter = activities.iterator();
		while (iter.hasNext() && !found) {
			Activity a = iter.next();
			if (a.equals(activity)) {
				found = true;
				a.setPlaces(activity.getPlaces());
				a.setPrize(activity.getPrize());
				a.setStudents(activity.getStudents());
				Persistence.getInstance().save();
			}
		}
		if (!found) {
			throw new InstanceNotFoundException(activity, "Activity");
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

	public Activity find(long id) throws InstanceNotFoundException {
		Activity a = null;
		try {
			a = activities.get((int) id);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new InstanceNotFoundException(id, "Activity");
		}
		return a;
	}

	public Activity findByName(String name) throws InstanceNotFoundException {
		for (Activity a : activities) {
			if (a.getName().equals(name)) {
				return a;
			}
		}
		throw new InstanceNotFoundException(name, "Activity");
	}

	public void enrollmentStudentInActivity(Student student, Activity activity) {
		student.getActivities().add(activity);
		activity.getStudents().add(student);
		Persistence.getInstance().save();
	}

	public void unEnrollsStudentToActivity(Student student, Activity activity) {
		student.getActivities().remove(activity);
		activity.getStudents().remove(student);
		Persistence.getInstance().save();
	}

}
