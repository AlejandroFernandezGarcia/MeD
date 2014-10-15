package ampa.sa.test;

import java.util.ArrayList;
import java.util.List;

import ampa.sa.activity.Activity;
import ampa.sa.booking.Booking;
import ampa.sa.diningHall.DiningHall;
import ampa.sa.student.Household;
import ampa.sa.student.Student;

public class DatabaseMock {
	private List<Household> houseHold = new ArrayList<Household>();
	private List<Student> students = new ArrayList<Student>();
	private List<Activity> activities = new ArrayList<Activity>();
	private List<Booking> bookings = new ArrayList<Booking>();
	private List<DiningHall> diningHall = new ArrayList<DiningHall>();
	private static DatabaseMock singleton = null;

	private DatabaseMock() {
		//crear usuarios
		//crear actividades
		//crear reservas
		//crear comedores
		
		//poner dependencias
		//meter en los arraylist
	}

	public static DatabaseMock getInstance() {
		if (singleton == null) {
			singleton = new DatabaseMock();
		}
		return singleton;
	}
}
