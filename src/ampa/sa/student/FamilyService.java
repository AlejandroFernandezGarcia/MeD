package ampa.sa.student;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import ampa.sa.activity.Activity;
import ampa.sa.booking.Booking;
import ampa.sa.persistence.Persistence;
import ampa.sa.util.exceptions.DuplicateInstanceException;
import ampa.sa.util.exceptions.InstanceNotFoundException;

@SuppressWarnings("serial")
public class FamilyService implements Serializable {

	List<Student> students = new ArrayList<Student>();

	private static FamilyService instance = null;

	private FamilyService() {
	}

	private synchronized static void createInstance() {
		if (instance == null) {
			instance = new FamilyService();
		}
	}

	public static FamilyService getInstance() {
		createInstance();
		return instance;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> bookings) {
		this.students = bookings;
	}

	public void createStudent(Student student)
			throws DuplicateInstanceException {
		if (!students.contains(student)) {
			students.add(student);
			Persistence.getInstance().save();
		} else {
			throw new DuplicateInstanceException(student, "Student");
		}
	}

	public void createHousehold(Household household) {

	}

	public void removeStudent(long id) throws InstanceNotFoundException {
		boolean found = false;
		Iterator<Student> iter;
		iter = students.iterator();
		while (iter.hasNext() && !found) {
			if (iter.next().getId() == id) {
				found = true;
				students.remove(iter.next());
				Persistence.getInstance().save();
			}
		}
		if (!found) {
			throw new InstanceNotFoundException(id, "Student");
		}
	}

	public void removeHousehold(long id) {

	}

	public List<Student> findStudents() {
		return students;
	}

	public List<Student> findStudents(Household household) {
		List<Student> s = new ArrayList<Student>();
		Set<Student> mentored = household.getMentored();
		for (Student m : mentored) {
			s.add(m);
		}
		return s;
	}

	public BigDecimal getHouseholdExpenses(Household household) {
		Set<Student> mentored = household.getMentored();
		BigDecimal amount = new BigDecimal(0.0);
		for (Student s : mentored) {
			// 1º Get activities prize
			Set<Activity> activities = s.getActivities();
			for (Activity a : activities) {
				amount = amount.add(a.getPrize());
			}
			// 2º Get bookings prize
			Set<Booking> bookings = s.getBookings();
			for (Booking b : bookings) {
				amount = amount.add(b.getDiningHall().getPrice());
			}
		}
		return amount;
	}

	public Student findStudent(long id) throws InstanceNotFoundException {
		for (Student s : students) {
			if (s.getId() == id) {
				return s;
			}
		}
		throw new InstanceNotFoundException(id, "Student");
	}

	public Household findHousehold(long id) {
		return null;

	}
}
