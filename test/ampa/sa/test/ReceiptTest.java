package ampa.sa.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import ampa.sa.activity.Activity;
import ampa.sa.activity.ActivityService;
import ampa.sa.booking.Booking;
import ampa.sa.booking.BookingService;
import ampa.sa.receipt.Receipt;
import ampa.sa.receipt.ReceiptLine;
import ampa.sa.receipt.ReceiptService;
import ampa.sa.student.FamilyService;
import ampa.sa.student.Household;
import ampa.sa.util.exceptions.InstanceNotFoundException;
import ampa.sa.util.exceptions.ReceiptsNotFoundException;

public class ReceiptTest {

	private ReceiptService receiptService = ReceiptService.getInstance();
	private FamilyService familyService = FamilyService.getInstance();
	private BookingService bookingService = BookingService.getInstance();
	private ActivityService activityService = ActivityService.getInstance();
	public static final String DATE_FORMAT = "dd/MM/yyyy";

	@Before
	public void before() throws ParseException {
		new DatosMock();
	}

	@Test
	public void createReceiptTest() {
		Household hh = null;
		try {
			hh = familyService.findHousehold(0);
		} catch (InstanceNotFoundException e) {
			fail("Household not found");
		}
		receiptService.createReceipt(hh);
		assertEquals(1, hh.getReceipts().size());
		ArrayList<Receipt> ar = new ArrayList<Receipt>();
		ar.addAll(hh.getReceipts());
		assertEquals(Calendar.getInstance().get(Calendar.MONTH), ar.get(0)
				.getDate().get(Calendar.MONTH));
		assertEquals(Calendar.getInstance().get(Calendar.YEAR), ar.get(0)
				.getDate().get(Calendar.YEAR));
	}

	@Test
	public void findReceiptsByHouseholdTest() throws ReceiptsNotFoundException {
		Household hh = null;
		try {
			hh = familyService.findHousehold(0);
		} catch (InstanceNotFoundException e) {
			fail("Household not found");
		}
		receiptService.createReceipt(hh);
		assertEquals(1, hh.getReceipts().size());

		ArrayList<Receipt> ar = new ArrayList<Receipt>();
		ar.addAll(hh.getReceipts());

		ArrayList<Receipt> ar2 = new ArrayList<Receipt>();
		ar2.addAll(receiptService.findReceiptsByHousehold(hh));

		assertEquals(ar.get(0), ar2.get(0));
	}

	@Test(expected = ReceiptsNotFoundException.class)
	public void findReceiptsNotExistByHouseholdTest()
			throws ReceiptsNotFoundException {
		Household hh = null;
		try {
			hh = familyService.findHousehold(0);
		} catch (InstanceNotFoundException e) {
			fail("Household not found");
		}
		receiptService.findReceiptsByHousehold(hh);
	}

	@Test
	public void getReceiptLinesByStudentTest() throws InstanceNotFoundException {

		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

		Calendar cb3 = Calendar.getInstance();
		Booking b3 = new Booking(cb3, familyService.findStudent(0),
				bookingService.findDiningHall(0));

		HashSet<Activity> activityForS = new HashSet<Activity>();
		HashSet<Booking> bookingForS = new HashSet<Booking>();
		activityForS.add(activityService.find(0));
		activityForS.add(activityService.find(1));
		bookingForS.add(b3);
		familyService.findStudent(0).setActivities(activityForS);
		familyService.findStudent(0).setBookings(bookingForS);
		
		familyService.findStudent(1).setActivities(activityForS);
		
		Household hh = null;
		try {
			hh = familyService.findHousehold(0);
		} catch (InstanceNotFoundException e) {
			fail("Household not found");
		}
		receiptService.createReceipt(hh);
		assertEquals(1, hh.getReceipts().size());

		ArrayList<Receipt> ar = new ArrayList<Receipt>();
		ar.addAll(hh.getReceipts());

		HashSet<ReceiptLine> hs = null;
		try {
			hs = (HashSet<ReceiptLine>) receiptService
					.getReceiptLinesByStudent(ar.get(0),
							familyService.findStudent(0));
		} catch (InstanceNotFoundException e1) {
			fail("Student not found");
		}

		try {
			assertEquals(familyService.findStudent(0).getActivities().size()
					+ familyService.findStudent(0).getBookings().size(),
					hs.size());
		} catch (InstanceNotFoundException e) {
			fail("Student not found");
		}
	}
}
