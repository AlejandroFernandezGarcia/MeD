package ampa.sa.receipt;

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
import ampa.sa.util.exceptions.InstanceNotFoundException;
import ampa.sa.util.exceptions.ReceiptsNotFoundException;

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

	public void createReceipt(Household household) {
		//FIXME Compruebo si hay algún recibo más con
		//la actividad que no sea del curso actual. Si lo hay 
		//esta pagado sino no.
		BookingService bookingService = BookingService.getInstance();
		Set<Student> students = household.getMentored();
		Set<BillLine> receiptLines = new HashSet<BillLine>();
		Calendar now = Calendar.getInstance();

		BigDecimal total = new BigDecimal(0);
		BillLine rl;
		for (Student student : students) {
			Set<Activity> activities = student.getActivities();
			List<Booking> bookings = bookingService
					.findStudentBookingsByMonthAndYear(student, now);
			for (Activity activity : activities) {
				rl = new BillLine(activity.getName(), activity.getPrize(), 1,
						activity.getPrize(), student);
				receiptLines.add(rl);
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
					receiptLines.add(rl);
				}
			}

		}
		for (BillLine receiptLine : receiptLines) {
			total = total.add(receiptLine.getPrice());
		}
		now.add(Calendar.MONTH, -1);
		household.setReceipts(this.removeReceiptByDate(household.getReceipts(),
				now));

		Bill receipt = new Bill(household, total, receiptLines, now);
		Set<Bill> newBills = household.getReceipts();
		newBills.add(receipt);
		household.setReceipts(newBills);
	}

	public List<Bill> findReceiptsByHousehold(Household household)
			throws ReceiptsNotFoundException {
		Set<Bill> receipts = household.getReceipts();
		List<Bill> result = new ArrayList<Bill>();
		if (receipts.size() == 0) {
			throw new ReceiptsNotFoundException(household, "Receipt");
		}
		result.addAll(receipts);
		Collections.sort(result);

		return result;

	}

	public Bill findReceiptByDate(Household household, Calendar calendar)
			throws InstanceNotFoundException {
		Set<Bill> receipts = household.getReceipts();
		for (Bill receipt : receipts) {
			if (((receipt.getDate().get(Calendar.MONTH)) == (calendar
					.get(Calendar.MONTH)))
					&& ((receipt.getDate().get(Calendar.YEAR)) == (calendar
							.get(Calendar.YEAR)))) {
				return receipt;
			}
		}
		throw new InstanceNotFoundException(null, "Bill");

	}

	public Set<BillLine> getReceiptLinesByStudent(Bill receipt, Student student) {

		Set<BillLine> receiptLines = receipt.getReceiptLines();
		Set<BillLine> receiptLinesByStudent = new HashSet<BillLine>();
		for (BillLine receiptLine : receiptLines) {
			if (receiptLine.getStudent().equals(student)) {
				receiptLinesByStudent.add(receiptLine);
			}
		}
		return receiptLinesByStudent;

	}

	private Set<Bill> removeReceiptByDate(Set<Bill> b, Calendar c) {

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
}
