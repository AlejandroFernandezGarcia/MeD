package ampa.sa.booking;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import ampa.sa.diningHall.DiningHall;
import ampa.sa.persistence.Persistence;
import ampa.sa.student.Student;
import ampa.sa.util.exceptions.DuplicateInstanceException;
import ampa.sa.util.exceptions.InstanceNotFoundException;

@SuppressWarnings("serial")
public class BookingService implements Serializable {

	private static List<Booking> bookings = new ArrayList<Booking>();
	private static List<DiningHall> diningHall = new ArrayList<DiningHall>();
	

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

	public List<DiningHall> getDiningHall() {
		return diningHall;
	}

	public void setDiningHall(List<DiningHall> diningHall) {
		this.diningHall = diningHall;
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
		
			//Persistence.getInstance().save();
		} else {
			throw new InstanceNotFoundException(booking, "Booking");
		}
	}
	
	//TODO Nuevo método
	public int getPlacesForDiningSchedule(Calendar cal,DiningHall dh){
		Iterator<Booking> iter;
		iter = bookings.iterator();
		int places = 0;
		while (iter.hasNext()) {
			Booking booking = iter.next();
			if ((booking.getDate().compareTo(cal) == 0)
					&& (booking.getDiningHall().equals(dh))) {
				places++;
			}
		}
		return (dh.getPlaces() - places);
	}

	public void remove(Booking booking) throws InstanceNotFoundException {
		boolean found = false;
		Iterator<Booking> iter;
		Booking aux;
		iter = bookings.iterator();
		while (iter.hasNext() && !found) {
			if ((aux=iter.next()).getId() == booking.getId()) {
				found = true;
				bookings.remove(aux);
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
			if ((booking.getDate().get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH))
					&& booking.getDate().get(Calendar.MONTH) == date.get(Calendar.MONTH)
					&& booking.getDate().get(Calendar.YEAR) == date.get(Calendar.YEAR)
					&& (booking.getStudent().equals(student))) {
				b.add(booking);
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

	public void createByDayOfWeek(List<Integer> days, Student st, DiningHall dh) throws InstanceNotFoundException {
		int actualMonth = Calendar.getInstance().get(Calendar.MONTH);
		Calendar cal1 = Calendar.getInstance();
		while(cal1.get(Calendar.MONTH) == actualMonth)
		{
			if(days.contains(cal1.get(Calendar.DAY_OF_WEEK)))
			{
				Calendar c = Calendar.getInstance();
				c.setTime(cal1.getTime());
				Booking b = new Booking(1, c, st,
						dh);
				this.create(b);
			}
			cal1.add(Calendar.DATE, 1);
		}
		
	}

}
