package ampa.sa.booking;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import ampa.sa.persistence.Persistence;
import ampa.sa.student.Student;
import ampa.sa.util.exceptions.InstanceNotFoundException;

@SuppressWarnings("serial")
public class BookingService implements Serializable {

	List<Booking> bookings = new ArrayList<Booking>();

	private static BookingService instance = null;

	private BookingService() {
	}

	private synchronized static void createInstance() {
		if (instance == null) {
			instance = new BookingService();
		}
	}

	public static BookingService getInstance() {
		createInstance();
		return instance;
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

	public void create(Booking booking) throws InstanceNotFoundException {
		if (!bookings.contains(booking)) {
			bookings.add(booking);
			Persistence.getInstance().save();
		} else {
			throw new InstanceNotFoundException(booking, "Booking");
		}
	}

	public void remove(Booking booking) throws InstanceNotFoundException {
		boolean found = false;
		Iterator<Booking> iter;
		iter = bookings.iterator();
		while (iter.hasNext() && !found) {
			if (iter.next().getId() == booking.getId()) {
				found = true;
				bookings.remove(iter.next());
				Persistence.getInstance().save();
			}
		}
		if (!found) {
			throw new InstanceNotFoundException(booking.getId(), "Booking");
		}
	}

	public Booking find(long id) throws InstanceNotFoundException {
		for (Booking b : bookings) {
			if (b.getId() == id) {
				return b;
			}
		}
		throw new InstanceNotFoundException(id, "Booking");
	}

	public void update(Booking booking) {
		boolean found = false;
		Iterator<Booking> iter;
		iter = bookings.iterator();
		while (iter.hasNext() && !found) {
			Booking b = iter.next();
			if (b.getId() == booking.getId()) {
				found = true;
				b.setDate(booking.getDate());
				b.setStudent(booking.getStudent());
				b.setDiningHall(booking.getDiningHall());
				Persistence.getInstance().save();
			}
		}
	}

	public List<Booking> getStudentBookingsByDate(Student student, Calendar date) {
		List<Booking> b = new ArrayList<Booking>();
		Iterator<Booking> iter;
		iter = bookings.iterator();
		while (iter.hasNext()) {
			Booking booking = iter.next();
			if ((booking.getDate() == date)
					&& (booking.getStudent().equals(student))) {
				b.add(iter.next());
			}
		}
		return b;
	}

	public BigDecimal getBookingsPrize(List<Booking> bookings) {
		Iterator<Booking> iter;
		iter = bookings.iterator();
		BigDecimal amount = new BigDecimal(0.0);
		while (iter.hasNext()) {
			amount = amount.add(iter.next().getDiningHall().getPrice());
		}
		return amount;
	}

}
