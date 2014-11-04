package ampa.sa.booking;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import ampa.sa.diningHall.DiningHall;
import ampa.sa.persistence.Persistence;
import ampa.sa.student.Student;
import ampa.sa.student.Student.Category;
import ampa.sa.util.SchoolCalendar;
import ampa.sa.util.exceptions.DuplicateInstanceException;
import ampa.sa.util.exceptions.InstanceNotFoundException;
import ampa.sa.util.exceptions.MaxCapacityException;
import ampa.sa.util.exceptions.NotValidDateException;

@SuppressWarnings("serial")
public class BookingService implements Serializable {

	private static List<Booking> bookings = new ArrayList<Booking>();
	private static List<DiningHall> diningHall = new ArrayList<DiningHall>();
	private static int MONITOR_CHLD_RATIO = 15;
	private static int MONITOR_PRMY_RATIO = 30; 

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

	public void create(Booking booking) throws DuplicateInstanceException,
			MaxCapacityException, NotValidDateException {
		if (!bookings.contains(booking)) {
			checkCapacity(booking);
			try {
				if(SchoolCalendar.isNotHoliday(booking.getDate()))
				{
					bookings.add(booking);
				}
				else
				{
					throw new NotValidDateException(booking, "Booking");
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			booking.getStudent().getBookings().add(booking);

			Persistence.getInstance().save();
		} else {
			throw new DuplicateInstanceException(booking, "Booking");
		}
	}

	public int getPlacesForDiningSchedule(Calendar cal, DiningHall dh) {
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
		return (dh.getMonitors() - places);
	}

	public void remove(Booking booking) throws InstanceNotFoundException {
		boolean found = false;
		Iterator<Booking> iter;
		Booking aux;
		iter = bookings.iterator();
		while (iter.hasNext() && !found) {
			if ((aux = iter.next()).getId() == booking.getId()) {
				found = true;
				bookings.remove(aux);
				aux.getStudent().getBookings().remove(aux);
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

	public DiningHall findDiningHall(long id) throws InstanceNotFoundException {
		for (DiningHall d : diningHall) {
			if (d.getId() == id) {
				return d;
			}
		}
		throw new InstanceNotFoundException(id, "DiningHall");
	}

	public void update(Booking booking) throws InstanceNotFoundException {
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
		if (!found) {
			throw new InstanceNotFoundException(booking, "Booking");
		}
	}

	public List<Booking> getStudentBookingsByDate(Student student, Calendar date) {
		List<Booking> b = new ArrayList<Booking>();
		Iterator<Booking> iter;
		iter = bookings.iterator();
		while (iter.hasNext()) {
			Booking booking = iter.next();
			if ((booking.getDate().get(Calendar.DAY_OF_MONTH) == date
					.get(Calendar.DAY_OF_MONTH))
					&& booking.getDate().get(Calendar.MONTH) == date
							.get(Calendar.MONTH)
					&& booking.getDate().get(Calendar.YEAR) == date
							.get(Calendar.YEAR)
					&& (booking.getStudent().equals(student))) {
				b.add(booking);
			}
		}
		return b;
	}
	
	//TODO Nuevo método
	public List<Booking> getBookingsByDate(Calendar date) {
		List<Booking> b = new ArrayList<Booking>();
		Iterator<Booking> iter;
		iter = bookings.iterator();
		while (iter.hasNext()) {
			Booking booking = iter.next();
			if ((booking.getDate().get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH))
					&& booking.getDate().get(Calendar.MONTH) == date.get(Calendar.MONTH)
					&& booking.getDate().get(Calendar.YEAR) == date.get(Calendar.YEAR)){
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

	// TODO tests y que no reserve ninguna si algï¿½n dï¿½a falla
	public void createByDayOfWeek(List<Integer> days, Student st, DiningHall dh)
			throws DuplicateInstanceException, MaxCapacityException, NotValidDateException {
		int actualMonth = Calendar.getInstance().get(Calendar.MONTH);
		Calendar cal1 = Calendar.getInstance();
		while (cal1.get(Calendar.MONTH) == actualMonth) {
			if (days.contains(cal1.get(Calendar.DAY_OF_WEEK))) {
				Calendar c = Calendar.getInstance();
				c.setTime(cal1.getTime());
				Booking b = new Booking(1, c, st, dh);
				this.create(b);
			}
			cal1.add(Calendar.DATE, 1);
		}

	}

	// FIXME no distingue cursos todavï¿½a
	/*public void checkCapacity(Booking b) throws MaxCapacityException {
		DiningHall dh = b.getDiningHall();
		int countBookings = 0;
		Iterator<Booking> iter;
		iter = bookings.iterator();
		while (iter.hasNext()) {
			Booking booking = iter.next();
			if (booking.getDiningHall().equals(dh)
					&& (booking.getDate().compareTo(b.getDate()) == 0)) {
				countBookings++;
			}
		}
		if ((countBookings + 1) > dh.getPlaces())
			throw new MaxCapacityException(b, "Booking");

	}*/
	
	//TODO Tests
	public void checkCapacity (Booking b) throws MaxCapacityException{
		Calendar bookingDate = b.getDate();
		Category studentCat = b.getStudent().getCategory();
		int dHallMonitors = b.getDiningHall().getMonitors();
		double cntChld = 0; double cntPrmy = 0;
		
		if (studentCat == Category.INFANTIL)
			cntChld++;
		else
			cntPrmy++;
		
		List<Booking> bookingsThatDay = getBookingsByDate(bookingDate);
		Iterator<Booking> iter;
		iter = bookingsThatDay.iterator();
		while (iter.hasNext()) {
			Booking booking = iter.next();
			if(booking.getStudent().getCategory() == Category.INFANTIL)
				cntChld++;
			else
				cntPrmy++;	
		}
		double chldM = Math.ceil(cntChld/MONITOR_CHLD_RATIO);
		double prmyM = Math.ceil(cntPrmy/MONITOR_PRMY_RATIO);
		
		double result = chldM + prmyM;
		
		//FIXME borrar, solo prueba
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		System.out.println("****" + sdf.format(bookingDate.getTime()) + "****");
		System.out.println("Reservas infantil -> " + cntChld + "\nReservas primaria -> "+ cntPrmy);
		System.out.println("Monitores infantil -> " + chldM + "\nMonitores primaria -> "+ prmyM);
		System.out.println("Monitores disponibles -> " + dHallMonitors + "\nMonitores necesarios -> " + result);
		//FIXME hasta aqui
		
		if (result > dHallMonitors)
			throw new MaxCapacityException(b,"Booking");
	}

	public int getPlacesBooked(Booking b) {
		DiningHall dh = b.getDiningHall();
		int countBookings = 0;
		Iterator<Booking> iter;
		iter = bookings.iterator();
		while (iter.hasNext()) {
			Booking booking = iter.next();
			if (booking.getDiningHall().equals(dh)
					&& (booking.getDate().compareTo(b.getDate()) == 0)) {
				countBookings++;
			}
		}
		return countBookings;
	}

	public List<Booking> findStudentBookingsByMonthAndYear(Student student,
			Calendar date) {
		List<Booking> b = new ArrayList<Booking>();
		Iterator<Booking> iter;
		iter = student.getBookings().iterator();
		while (iter.hasNext()) {
			Booking booking = iter.next();
			if ((booking.getDate().get(Calendar.MONTH) == date
					.get(Calendar.MONTH))
					&& (booking.getDate().get(Calendar.YEAR) == date
							.get(Calendar.YEAR))) {
				b.add(booking);
			}
		}
		return b;
	}

}
