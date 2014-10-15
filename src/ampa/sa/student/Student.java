package ampa.sa.student;

import java.util.Calendar;
import java.util.Set;

import ampa.sa.activity.Activity;
import ampa.sa.booking.Booking;

public class Student {
	
	private long id;
	private Household nucleoFamiliar;
	private String nombre;
	private String apellidos;
	private Calendar fechaNacimiento;
	private Set<Activity> actividades;
	private Set<Booking> reservas;
	
	
}
