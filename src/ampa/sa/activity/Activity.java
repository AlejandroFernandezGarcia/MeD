package ampa.sa.activity;

import java.util.Set;

import ampa.sa.student.Student;

public class Activity {

	private long id;
	private String name;
	private int places;
	private double prize;
	private Set<Student> students;

	public Activity(long id, String name, int places, double prize,
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

	public double getPrize() {
		return prize;
	}

	public void setPrize(double prize) {
		this.prize = prize;
	}

	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}

}
