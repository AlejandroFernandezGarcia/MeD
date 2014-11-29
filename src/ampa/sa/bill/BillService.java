package ampa.sa.bill;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import ampa.sa.activity.Activity;
import ampa.sa.booking.Booking;
import ampa.sa.booking.BookingService;
import ampa.sa.diningHall.DiningHall;
import ampa.sa.student.Household;
import ampa.sa.student.Student;
import ampa.sa.util.exceptions.BillNotFoundException;
import ampa.sa.util.exceptions.InstanceNotFoundException;

@SuppressWarnings("serial")
public class BillService implements Serializable {

	private static BillService instance = null;

	private BillService() {
	}

	private synchronized static void createInstance() {
		if (instance == null) {
			instance = new BillService();
		}
	}

	public static BillService getInstance() {
		createInstance();
		return instance;
	}

	public void createBill(Household household) {
		// FIXME Compruebo si hay algún recibo más con
		// la actividad que no sea del curso actual. Si lo hay
		// esta pagado sino no.
		BookingService bookingService = BookingService.getInstance();
		Set<Student> students = household.getMentored();
		Set<BillLine> billLines = new HashSet<BillLine>();
		Calendar now = Calendar.getInstance();

		BigDecimal total = new BigDecimal(0);
		BillLine rl;
		for (Student student : students) {
			if(student.isDeleted()) continue;
			Set<Activity> activities = student.getActivities();
			List<Booking> bookings = bookingService
					.findStudentBookingsByMonthAndYear(student, now);
			for (Activity activity : activities) {
				rl = new BillLine(activity.getName(), activity.getPrize(), 1,
						activity.getPrize(), student);
				billLines.add(rl);
				if (!studentPaidLicense(activity, student)) {
					billLines.add(new BillLine("Licencia de "
							+ activity.getName(), activity.getLicense(), 1,
							activity.getLicense(), student));
				}
			}
			int count = 0;
			List<DiningHall> dhs = bookingService.getDiningHall();
			for (DiningHall dh : dhs) {
				count = 0;
				for (Booking b : bookings) {
					if (b.getDiningHall().equals(dh)) {
						count++;
					}
				}
				if (count != 0) {
					rl = new BillLine(dh.toString(), dh.getPrice().multiply(
							new BigDecimal(count)), count, dh.getPrice(),
							student);
					billLines.add(rl);
				}
			}

		}
		for (BillLine billLine : billLines) {
			total = total.add(billLine.getPrice());
		}
		now.add(Calendar.MONTH, -1);
		household.setBills(this.removeBillByDate(household.getBills(), now));

		Bill bill = new Bill(household, total, billLines, now);
		Set<Bill> newBills = household.getBills();
		newBills.add(bill);
		household.setBills(newBills);
	}

	public List<Bill> findBillsByHousehold(Household household)
			throws BillNotFoundException {
		Set<Bill> bills = household.getBills();
		List<Bill> result = new ArrayList<Bill>();
		if (bills.size() == 0) {
			throw new BillNotFoundException(household, "Bill");
		}
		result.addAll(bills);
		Collections.sort(result);

		return result;

	}

	public Bill findBillByDate(Household household, Calendar calendar)
			throws InstanceNotFoundException {
		Set<Bill> bills = household.getBills();
		for (Bill bill : bills) {
			if (((bill.getDate().get(Calendar.MONTH)) == (calendar
					.get(Calendar.MONTH)))
					&& ((bill.getDate().get(Calendar.YEAR)) == (calendar
							.get(Calendar.YEAR)))) {
				return bill;
			}
		}
		throw new InstanceNotFoundException(null, "Bill");

	}

	public Set<BillLine> getBillLinesByStudent(Bill bill, Student student) {

		Set<BillLine> billLines = bill.getBillLines();
		Set<BillLine> billLinesByStudent = new HashSet<BillLine>();
		for (BillLine billLine : billLines) {
			if (billLine.getStudent().equals(student)) {
				billLinesByStudent.add(billLine);
			}
		}
		return billLinesByStudent;

	}

	private Set<Bill> removeBillByDate(Set<Bill> b, Calendar c) {

		Iterator<Bill> bIt = b.iterator();
		Set<Bill> bills = b;
		while (bIt.hasNext()) {
			Bill bAux = bIt.next();
			if ((bAux.getDate().get(Calendar.MONTH) == c.get(Calendar.MONTH))
					&& ((bAux.getDate().get(Calendar.YEAR) == c
							.get(Calendar.YEAR)))) {
				bIt.remove();
			}

		}
		return bills;
	}

	private boolean studentPaidLicense(Activity activity, Student student) {
		Calendar previousBillDate = Calendar.getInstance();
		previousBillDate.add(Calendar.MONTH, -1);
		Bill oldBill;
		if(activity.getLicense().equals(new BigDecimal(0))){
			return true;
		}
		try {
			oldBill = findBillByDate(student.getHouseHold(), previousBillDate);
		} catch (InstanceNotFoundException e) {
			return false;
		}
		for (BillLine billLine : oldBill.getBillLines()) {
			if ((activity.getName().compareTo(billLine.getConcept()) == 0)
					&& (billLine.getStudent().equals(student))) {
				return true;
			}
		}
		return false;

	}
}
