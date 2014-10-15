package ampa.sa.student;

import java.util.Calendar;
import java.util.Set;

import ampa.sa.activity.Activity;
import ampa.sa.booking.Booking;

public class Student {

	private long id;
	private Household houseHold;
	private String name;
	private String lastname;
	private Calendar dateBorn;
	private Set<Activity> activities;
	private Set<Booking> bookings;

	public Student(long id, Household houseHold, String name, String lastname,
			Calendar dateBorn, Set<Activity> activities, Set<Booking> bookings) {
		super();
		this.id = id;
		this.houseHold = houseHold;
		this.name = name;
		this.lastname = lastname;
		this.dateBorn = dateBorn;
		this.activities = activities;
		this.bookings = bookings;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Household getHouseHold() {
		return houseHold;
	}

	public void setHouseHold(Household houseHold) {
		this.houseHold = houseHold;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Calendar getDateBorn() {
		return dateBorn;
	}

	public void setDateBorn(Calendar dateBorn) {
		this.dateBorn = dateBorn;
	}

	public Set<Activity> getActivities() {
		return activities;
	}

	public void setActivities(Set<Activity> activities) {
		this.activities = activities;
	}

	public Set<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(Set<Booking> bookings) {
		this.bookings = bookings;
	}

}
