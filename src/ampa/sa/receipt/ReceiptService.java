package ampa.sa.receipt;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ampa.sa.activity.Activity;
import ampa.sa.booking.Booking;
import ampa.sa.booking.BookingService;
import ampa.sa.diningHall.DiningHall;
import ampa.sa.student.Household;
import ampa.sa.student.Student;
import ampa.sa.util.exceptions.ReceiptsNotFoundException;

public class ReceiptService {

	private static List<Receipt> receipts = new ArrayList<Receipt>();

	
	private static ReceiptService instance = null;

	private ReceiptService() {
	}

	private synchronized static void createInstance() {
		if (instance == null) {
			instance = new ReceiptService();
		}
	}

	public static ReceiptService getInstance() {
		createInstance();
		return instance;
	}

	public void createReceipt(Household household) {
		//TODO Falta el tema de licencias.
		BookingService bookingService = BookingService.getInstance();
		Set<Student> students = household.getMentored();
		Set<ReceiptLine> receiptLines = new HashSet<ReceiptLine>();
		Calendar now = Calendar.getInstance();
		BigDecimal total = new BigDecimal(0);
		ReceiptLine rl;
		for (Student student : students) {
			Set<Activity> activities = student.getActivities();
			List<Booking> bookings = bookingService
					.findStudentBookingsByMonthAndYear(student, now);
			for (Activity activity : activities) {
				rl = new ReceiptLine(activity.getName(),
						activity.getPrize(), 1, activity.getPrize(), student);
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
					rl = new ReceiptLine(dh.getSchedule()
							.toString(), dh.getPrice().multiply(
							new BigDecimal(count)), count, dh.getPrice(),
							student);
					receiptLines.add(rl);
				}
			}

		}
		for (ReceiptLine receiptLine : receiptLines) {
			total.add(receiptLine.getPrice());
		}
		Receipt receipt = new Receipt(household, total, receiptLines, now);
		household.getReceipts().add(receipt);
	}

	public List<Receipt> findReceiptsByHousehold(Household household)
			throws ReceiptsNotFoundException {
		Set<Receipt> receipts = household.getReceipts();
		List<Receipt> result = new ArrayList<Receipt>();
		if (receipts.size() == 0) {
			throw new ReceiptsNotFoundException(household, "Receipt");
		}
		result.addAll(receipts);
		Collections.sort(result);

		return result;

	}

	public static List<Receipt> getReceipts() {
		return receipts;
	}

	public static void setReceipts(List<Receipt> receipts) {
		ReceiptService.receipts = receipts;
	}
}
