package ampa.sa.booking;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
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
				if (SchoolCalendar.isNotHoliday(booking.getDate())) {
					bookings.add(booking);
				} else {
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
		if (bookings.contains(booking)) {
			bookings.remove(booking);
		} else {
			throw new InstanceNotFoundException(booking, "Booking");
		}
	}

	public Booking find(long id) throws InstanceNotFoundException {
		try {
			return bookings.get((int) id);
		} catch (IndexOutOfBoundsException e) {
			throw new InstanceNotFoundException(id, "Booking");
		}
	}

	public DiningHall findDiningHall(long id) throws InstanceNotFoundException {
		try {
			return diningHall.get((int) id);
		} catch (IndexOutOfBoundsException e) {
			throw new InstanceNotFoundException(id, "DiningHall");
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

	public List<Booking> getBookingsByDate(Calendar date) {
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
							.get(Calendar.YEAR)) {
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

	public void checkCapacity(Booking b) throws MaxCapacityException {
		Calendar bookingDate = b.getDate();
		Category studentCat = b.getStudent().getCategory();
		int dHallMonitors = b.getDiningHall().getMonitors();
		double cntChld = 0;
		double cntPrmy = 0;

		if (studentCat == Category.INFANTIL)
			cntChld++;
		else
			cntPrmy++;

		List<Booking> bookingsThatDay = getBookingsByDate(bookingDate);
		Iterator<Booking> iter;
		iter = bookingsThatDay.iterator();
		while (iter.hasNext()) {
			Booking booking = iter.next();
			if (booking.getStudent().getCategory() == Category.INFANTIL)
				cntChld++;
			else
				cntPrmy++;
		}
		double chldM = Math.ceil(cntChld / MONITOR_CHLD_RATIO);
		double prmyM = Math.ceil(cntPrmy / MONITOR_PRMY_RATIO);

		double result = chldM + prmyM;

		if (result > dHallMonitors)
			throw new MaxCapacityException(b, "Booking");
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

	public String isBookingAllDayOfWeekInMonth(Student student) {
		String result = "";
		Calendar calendar = Calendar.getInstance();
		int days[] = { 0, 0, 0, 0, 0, 0, 0, 0 };
		int month = calendar.get(Calendar.MONTH);
		while (calendar.get(Calendar.MONTH) == month) {
			try {
				if (SchoolCalendar.isNotHoliday(calendar)) {
					days[calendar.get(Calendar.DAY_OF_WEEK)]++;
				}
			} catch (ParseException e) {
			}
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		List<Booking> bookingsForStudent = findStudentBookingsByMonthAndYear(
				student, Calendar.getInstance());
		for (Booking booking : bookingsForStudent) {
			days[booking.getDate().get(Calendar.DAY_OF_WEEK)]--;
		}

		result += "Comedor mensual: ";
		if (days[Calendar.MONDAY] <= 0) {
			result += "L";
		}
		if (days[Calendar.TUESDAY] <= 0) {
			result += "M";
		}
		if (days[Calendar.WEDNESDAY] <= 0) {
			result += "X";
		}
		if (days[Calendar.THURSDAY] <= 0) {
			result += "J";
		}
		if (days[Calendar.FRIDAY] <= 0) {
			result += "V";
		}
		if (result.compareTo("Comedor mensual: ") == 0) {
			return "";
		}

		return result;

	}

}
