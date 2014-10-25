package ampa.sa.booking;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import ampa.sa.diningHall.DiningHall;
import ampa.sa.student.Student;

@SuppressWarnings("serial")
public class Booking implements Serializable{

	private long id;
	private Calendar date;
	private Student student;
	private DiningHall diningHall;

	private static final String DATE_FORMAT = "dd/MM/yyyy";
	
	public Booking(long id, Calendar date, Student student,
			DiningHall diningHall) {
		super();
		this.id = id;
		this.date = date;
		this.date.set(Calendar.HOUR_OF_DAY,0);
		this.date.set(Calendar.MINUTE,0);
		this.date.set(Calendar.SECOND,0);
		this.date.set(Calendar.MILLISECOND,0);
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
	
	@Override
	public String toString(){
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		return this.student.getName() + " " + this.student.getLastname() + " | "
				+ sdf.format(this.date.getTime()) + " | " + this.getDiningHall().getSchedule().toString() + "\n";
	}

}
