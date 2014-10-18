package ampa.sa.booking;

import java.util.Calendar;

import ampa.sa.diningHall.DiningHall;
import ampa.sa.student.Student;

public class Booking {

	private long id;
	private Calendar date;
	private Student student;
	private DiningHall diningHall;

	public Booking(long id, Calendar date, Student student,
			DiningHall diningHall) {
		super();
		this.id = id;
		this.date = date;
		this.student = student;
		this.diningHall = diningHall;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public DiningHall getDiningHall() {
		return diningHall;
	}

	public void setDiningHall(DiningHall diningHall) {
		this.diningHall = diningHall;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result
				+ ((diningHall == null) ? 0 : diningHall.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((student == null) ? 0 : student.hashCode());
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
		Booking other = (Booking) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (diningHall == null) {
			if (other.diningHall != null)
				return false;
		} else if (!diningHall.equals(other.diningHall))
			return false;
		if (id != other.id)
			return false;
		if (student == null) {
			if (other.student != null)
				return false;
		} else if (!student.equals(other.student))
			return false;
		return true;
	}
	
	

}
