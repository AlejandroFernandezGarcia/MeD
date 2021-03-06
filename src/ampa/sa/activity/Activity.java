package ampa.sa.activity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

import ampa.sa.student.Student;

@SuppressWarnings("serial")
public class Activity implements Serializable {

	private String name;
	private int places;
	private BigDecimal prize;
	private BigDecimal license;
	private Set<Student> students;

	public Activity(String name, int places, BigDecimal prize,
			BigDecimal license, Set<Student> students) {
		super();
		this.name = name;
		this.places = places;
		this.prize = prize;
		this.license = license;
		this.students = students;
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

	public BigDecimal getLicense() {
		return license;
	}

	public void setLicense(BigDecimal license) {
		this.license = license;
	}

	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}

	@Override
	public boolean equals(Object activity) {
		if (this.getName().compareTo(((Activity) activity).getName()) == 0) {
			return true;
		} else
			return false;
	}

}
