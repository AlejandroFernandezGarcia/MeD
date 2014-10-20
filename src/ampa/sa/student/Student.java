package ampa.sa.student;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

import ampa.sa.activity.Activity;
import ampa.sa.booking.Booking;

@SuppressWarnings("serial")
public class Student implements Serializable{

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((activities == null) ? 0 : activities.hashCode());
		result = prime * result
				+ ((bookings == null) ? 0 : bookings.hashCode());
		result = prime * result
				+ ((dateBorn == null) ? 0 : dateBorn.hashCode());
		result = prime * result
				+ ((houseHold == null) ? 0 : houseHold.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result
				+ ((lastname == null) ? 0 : lastname.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (activities == null) {
			if (other.activities != null)
				return false;
		} else if (!activities.equals(other.activities))
			return false;
		if (bookings == null) {
			if (other.bookings != null)
				return false;
		} else if (!bookings.equals(other.bookings))
			return false;
		if (dateBorn == null) {
			if (other.dateBorn != null)
				return false;
		} else if (!dateBorn.equals(other.dateBorn))
			return false;
		if (houseHold == null) {
			if (other.houseHold != null)
				return false;
		} else if (!houseHold.equals(other.houseHold))
			return false;
		if (id != other.id)
			return false;
		if (lastname == null) {
			if (other.lastname != null)
				return false;
		} else if (!lastname.equals(other.lastname))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	

}
