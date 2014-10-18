package ampa.sa.activity;

import java.math.BigDecimal;
import java.util.Set;

import ampa.sa.student.Student;

public class Activity {

	private long id;
	private String name;
	private int places;
	private BigDecimal prize;
	private Set<Student> students;

	public Activity(long id, String name, int places, BigDecimal prize,
			Set<Student> students) {
		super();
		this.id = id;
		this.name = name;
		this.places = places;
		this.prize = prize;
		this.students = students;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPlaces() {
		return places;
	}

	public void setPlaces(int places) {
		this.places = places;
	}

	public BigDecimal getPrize() {
		return prize;
	}

	public void setPrize(BigDecimal prize) {
		this.prize = prize;
	}

	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}

}
