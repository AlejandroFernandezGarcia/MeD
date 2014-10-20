package ampa.sa.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ampa.sa.activity.Activity;
import ampa.sa.booking.Booking;
import ampa.sa.student.Student;
import ampa.sa.student.Household;;

@SuppressWarnings("serial")
public class Database implements Serializable {
	private List<Student> students = new ArrayList<Student>();
	private List<Household> households = new ArrayList<Household>();
	private List<Activity> activities = new ArrayList<Activity>();
	private List<Booking> bookings = new ArrayList<Booking>();

	public Database() {

	}

	public Database(List<Student> students, List<Activity> activities,
			List<Booking> bookings, List<Household> household) {
		this.households = household;
		this.students = students;
		this.activities = activities;
		this.bookings = bookings;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

	public List<Household> getHouseholds() {
		return households;
	}

	public void setHouseholds(List<Household> households) {
		this.households = households;
	}

}
