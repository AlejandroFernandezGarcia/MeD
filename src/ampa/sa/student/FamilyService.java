package ampa.sa.student;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import ampa.sa.activity.Activity;
import ampa.sa.booking.Booking;
import ampa.sa.persistence.Persistence;
import ampa.sa.util.exceptions.DuplicateInstanceException;
import ampa.sa.util.exceptions.InstanceNotFoundException;

@SuppressWarnings("serial")
public class FamilyService implements Serializable {

	List<Student> students = new ArrayList<Student>();
	List<Household> households = new ArrayList<Household>();

	private static FamilyService instance = null;

	private FamilyService() {
	}

	private synchronized static void createInstance() {
		if (instance == null) {
			instance = new FamilyService();
		}
	}

	public static FamilyService getInstance() {
		createInstance();
		return instance;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> bookings) {
		this.students = bookings;
	}

	public List<Household> getHousehold() {
		return households;
	}

	public void setHousehold(List<Household> household) {
		this.households = household;
	}

	public void createStudent(Student student)
			throws DuplicateInstanceException {
		if (!students.contains(student)) {
			students.add(student);
			Persistence.getInstance().save();
		} else {
			throw new DuplicateInstanceException(student, "Student");
		}
	}

	public void createHousehold(Household household)
			throws DuplicateInstanceException {
		if (!this.households.contains(household)) {
			this.households.add(household);
			Persistence.getInstance().save();
		} else {
			throw new DuplicateInstanceException(household, "Household");
		}
	}

	public void removeStudent(long id) throws InstanceNotFoundException {
		Student s = null;
		try{
			s = students.get((int)id);
		}catch(IndexOutOfBoundsException e){	
			throw new InstanceNotFoundException(id, "Student");
		}
		s.getHouseHold().getMentored().remove(s);
		students.remove(s);
		Persistence.getInstance().save();
		
	}

	public void removeHousehold(String bankAccount)
			throws InstanceNotFoundException {
		Household hh = findHousehold(bankAccount);
		if (hh == null) {
			throw new InstanceNotFoundException(bankAccount, "Household");
		}
		for(Student s : hh.getMentored()){
			students.remove(s);
		}
		households.remove(hh);
		Persistence.getInstance().save();

	}

	//FIXME Arreglar update
	public void updateStudent(Student student) {
		boolean found = false;
		Iterator<Student> iter;
		iter = students.iterator();
		while (iter.hasNext() && !found) {
			Student s = iter.next();
			if (s.equals(student)) {
				found = true;
				s.setHouseHold(student.getHouseHold());
				s.setName(student.getName());
				s.setLastname(student.getLastname());
				s.setDateBorn(student.getDateBorn());
				s.setActivities(student.getActivities());
				s.setBookings(student.getBookings());
				Persistence.getInstance().save();
			}
		}
	}

	public void updateHousehold(Household household) {
		boolean found = false;
		Iterator<Household> iter;
		iter = households.iterator();
		while (iter.hasNext() && !found) {
			Household h = iter.next();
			if (h.getBanckAccount() == household.getBanckAccount()) {
				found = true;
				h.setBanckAccount(household.getBanckAccount());
				h.setMentored(household.getMentored());
				Persistence.getInstance().save();
			}
		}
	}

	public List<Student> findStudents() {
		return students;
	}

	public List<Household> findHouseholds() {
		return households;
	}

	public List<Student> findStudents(Household household) {
		List<Student> s = new ArrayList<Student>();
		Set<Student> mentored = household.getMentored();
		for (Student m : mentored) {
			s.add(m);
		}
		return s;
	}

	public BigDecimal getHouseholdExpenses(Household household) {
		Set<Student> mentored = household.getMentored();
		BigDecimal amount = new BigDecimal(0.0);
		for (Student s : mentored) {
			// 1º Get activities prize
			Set<Activity> activities = s.getActivities();
			for (Activity a : activities) {
				amount = amount.add(a.getPrize());
			}
			// 2º Get bookings prize
			Set<Booking> bookings = s.getBookings();
			for (Booking b : bookings) {
				amount = amount.add(b.getDiningHall().getPrice());
			}
		}
		return amount;
	}

	public Student findStudent(long id) throws InstanceNotFoundException {
		try{
			return students.get((int) id);
		}catch(IndexOutOfBoundsException e){
			throw new InstanceNotFoundException(id, "Student");			
		}
	}

	public Household findHousehold(String bankAccount)
			throws InstanceNotFoundException {
		for (Household h : households) {
			if (h.getBanckAccount() == bankAccount) {
				return h;
			}
		}
		throw new InstanceNotFoundException(bankAccount, "Household");

	}

	public BigDecimal getStudentExpenses(Student s) {
		BigDecimal amount = new BigDecimal(0.0);
		// 1º Get activities prize
		Set<Activity> activities = s.getActivities();
		for (Activity a : activities) {
			amount = amount.add(a.getPrize());
		}
		// 2º Get bookings prize
		Set<Booking> bookings = s.getBookings();
		for (Booking b : bookings) {
			amount = amount.add(b.getDiningHall().getPrice());
		}
		return amount;
	}

	public String getStudentType(Student student) {
		if (student.getCategory() == Student.Category.PRIMARIA)
			return "PRIMARIA";
		else
			return "INFANTIL";
	}
}
