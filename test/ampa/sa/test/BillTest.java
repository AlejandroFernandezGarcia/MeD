package ampa.sa.test;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import ampa.sa.activity.Activity;
import ampa.sa.activity.ActivityService;
import ampa.sa.bill.Bill;
import ampa.sa.bill.BillLine;
import ampa.sa.bill.BillService;
import ampa.sa.booking.Booking;
import ampa.sa.booking.BookingService;
import ampa.sa.student.FamilyService;
import ampa.sa.student.Household;
import ampa.sa.student.Student;
import ampa.sa.util.exceptions.BillNotFoundException;
import ampa.sa.util.exceptions.InstanceNotFoundException;

public class BillTest {

	private BillService billService = BillService.getInstance();
	private FamilyService familyService = FamilyService.getInstance();
	private BookingService bookingService = BookingService.getInstance();
	private ActivityService activityService = ActivityService.getInstance();
	public static final String DATE_FORMAT = "dd/MM/yyyy";

	@Before
	public void before() throws ParseException {
		new DatosMock();
	}

	@Test
	public void createBillTest() {
		Household hh = null;
		try {
			hh = familyService.findHousehold(0);
		} catch (InstanceNotFoundException e) {
			fail("Household not found");
		}
		billService.createBill(hh);
		assertEquals(1, hh.getBills().size());
		ArrayList<Bill> ar = new ArrayList<Bill>();
		ar.addAll(hh.getBills());
		assertEquals(Calendar.getInstance().get(Calendar.MONTH) - 1, ar.get(0)
				.getDate().get(Calendar.MONTH));
		assertEquals(Calendar.getInstance().get(Calendar.YEAR), ar.get(0)
				.getDate().get(Calendar.YEAR));
	}

	@Test
	public void findBillssByHouseholdTest() throws BillNotFoundException {
		Household hh = null;
		try {
			hh = familyService.findHousehold(0);
		} catch (InstanceNotFoundException e) {
			fail("Household not found");
		}
		billService.createBill(hh);
		assertEquals(1, hh.getBills().size());

		ArrayList<Bill> ar = new ArrayList<Bill>();
		ar.addAll(hh.getBills());

		ArrayList<Bill> ar2 = new ArrayList<Bill>();
		ar2.addAll(billService.findBillsByHousehold(hh));

		assertEquals(ar.get(0), ar2.get(0));
	}

	@Test(expected = BillNotFoundException.class)
	public void findBillsNotExistByHouseholdTest()
			throws BillNotFoundException {
		Household hh = null;
		try {
			hh = familyService.findHousehold(0);
		} catch (InstanceNotFoundException e) {
			fail("Household not found");
		}
		billService.findBillsByHousehold(hh);
	}

	@Test
	public void getBillLinesByStudentTest() throws InstanceNotFoundException {

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
		billService.createBill(hh);
		assertEquals(1, hh.getBills().size());

		ArrayList<Bill> ar = new ArrayList<Bill>();
		ar.addAll(hh.getBills());

		HashSet<BillLine> hs = null;
		HashSet<BillLine> hs2 = null;
		try {
			hs = (HashSet<BillLine>) billService.getBillLinesByStudent(
					ar.get(0), familyService.findStudent(0));
			hs2 = (HashSet<BillLine>) billService.getBillLinesByStudent(
					ar.get(0), familyService.findStudent(1));
		} catch (InstanceNotFoundException e1) {
			fail("Student not found");
		}

		Iterator<BillLine> irl = hs.iterator();
		Iterator<BillLine> irl2 = hs2.iterator();
		int count = 0;
		while (irl.hasNext()) {
			count += irl.next().getUnits();
		}
		while (irl2.hasNext()) {
			count += irl2.next().getUnits();
		}

		try {
			//This +2 is because two activities need license.
			assertEquals(familyService.findStudent(0).getActivities().size()
					+ familyService.findStudent(0).getBookings().size()
					+ familyService.findStudent(1).getActivities().size()
					+ familyService.findStudent(1).getBookings().size()+2, count);
		} catch (InstanceNotFoundException e) {
			fail("Student not found");
		}
	}
	
	@Test
	public void findBillByDateTest() throws InstanceNotFoundException{
		Household hh = null;
		try {
			hh = familyService.findHousehold(0);
		} catch (InstanceNotFoundException e) {
			fail("Household not found");
		}
		billService.createBill(hh);
		
		Calendar date = Calendar.getInstance();
		date.add(Calendar.MONTH, -1);
		
		billService.findBillByDate(hh,date );
	}
	
	@Test
	public void removeBillByDateTest(){
		Household hh = null;
		try {
			hh = familyService.findHousehold(0);
		} catch (InstanceNotFoundException e) {
			fail("Household not found");
		}
		int billOld = hh.getBills().size();
		billService.createBill(hh);
		
		Calendar date = Calendar.getInstance();
		date.add(Calendar.MONTH, -1);
		billService.removeBillByDate(hh.getBills(), date);
		
		assertTrue(billOld==hh.getBills().size());
	}
	
	@Test
	public void studentPaidLicenseTest() throws InstanceNotFoundException{
		Student s = familyService.findStudent(0);
		activityService.enrollmentStudentInActivity(s, activityService.find(0));
		
		assertFalse(billService.studentPaidLicense(activityService.find(0), s));
	}
}
