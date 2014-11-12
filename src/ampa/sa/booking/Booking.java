package ampa.sa.booking;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import ampa.sa.diningHall.DiningHall;
import ampa.sa.student.Student;

@SuppressWarnings("serial")
public class Booking implements Serializable {

	private Calendar date;
	private Student student;
	private DiningHall diningHall;

	private static final String DATE_FORMAT = "dd/MM/yyyy";

	public Booking(Calendar date, Student student, DiningHall diningHall) {
		super();
		this.date = date;
		this.date.set(Calendar.HOUR_OF_DAY, 0);
		this.date.set(Calendar.MINUTE, 0);
		this.date.set(Calendar.SECOND, 0);
		this.date.set(Calendar.MILLISECOND, 0);
		this.student = student;
		this.diningHall = diningHall;
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
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		return this.student.getName() + " " + this.student.getLastname()
				+ " | " + sdf.format(this.date.getTime()) + " | "
				+ this.getDiningHall().toString() + "\n";
	}

}
