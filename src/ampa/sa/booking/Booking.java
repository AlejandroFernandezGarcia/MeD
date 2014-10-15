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

}
