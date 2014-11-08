package ampa.sa.student;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

import ampa.sa.activity.Activity;
import ampa.sa.booking.Booking;

@SuppressWarnings("serial")
public class Student implements Serializable {

	public enum Category {
		INFANTIL, PRIMARIA
	} // INFANTIL de 3 a 5 anhos, PRIMARIA de 6 a 12

	private Household houseHold;
	private String name;
	private String lastname;
	private Category category;
	private Calendar dateBorn;
	private Set<Activity> activities;
	private Set<Booking> bookings;

	public Student(Household houseHold, String name, String lastname,
			Category cat, Calendar dateBorn, Set<Activity> activities,
			Set<Booking> bookings) {
		super();
		this.houseHold = houseHold;
		this.name = name;
		this.lastname = lastname;
		this.category = cat;
		this.dateBorn = dateBorn;
		this.activities = activities;
		this.bookings = bookings;
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

	public String getLastname() {
		return lastname;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Calendar getDateBorn() {
		return dateBorn;
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

	@Override
	public boolean equals(Object obj) {
		return ((this.name.compareTo(((Student) obj).getName()) == 0))
				&& ((this.lastname.compareTo(((Student) obj).getLastname()) == 0))
				&& ((this.getDateBorn()
						.compareTo(((Student) obj).getDateBorn()) == 0));
	}

}
