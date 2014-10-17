package ampa.sa.booking;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ampa.sa.student.Student;

@SuppressWarnings("serial")
public class ReservasService implements Serializable {

	List<Booking> bookings = new ArrayList<Booking>();

	private static ReservasService instance = null;

	private ReservasService() {
	}

	private synchronized static void createInstance() {
		if (instance == null) {
			instance = new ReservasService();
		}
	}

	public static ReservasService getInstance() {
		createInstance();
		return instance;
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

	public void create(Booking booking) {

	}

	public void remove(Booking booking) {

	}

	public Booking find(long id) {
		return null;

	}

	public void update(Booking booking) {

	}

	public List<Booking> listarReservasAlumnoFecha(Student student,
			Calendar period) {
		return null;

	}

	public long obtenerPrecioReservasFecha(List<Booking> bookings) {
		return 0;

	}

}
