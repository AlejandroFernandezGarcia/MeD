package ampa.sa.persistence;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import ampa.sa.activity.Activity;
import ampa.sa.activity.ActivityService;
import ampa.sa.booking.Booking;
import ampa.sa.booking.BookingService;
import ampa.sa.student.FamilyService;
import ampa.sa.student.Student;
import ampa.sa.student.Household;

public class Persistence {
	ActivityService activityService = ActivityService.getInstance();
	BookingService bookingService = BookingService.getInstance();
	FamilyService familyService = FamilyService.getInstance();
	private static Persistence instance = null;
	private static String pathDB = "DB.bin";

	private synchronized static void createInstance() {
		if (instance == null) {
			instance = new Persistence();
		}
	}

	public static Persistence getInstance() {
		createInstance();
		return instance;
	}

	public static Persistence getInstanceTest(){
		pathDB = "DBTest.bin";
		createInstance();
		return instance;
	}
	
	public Persistence() {
		// AQUI LOS RECUPERO (usarlo cuando abres la iu)
		try {
			FileInputStream fis = new FileInputStream(pathDB);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Database database = (Database) ois.readObject();
			activityService.setActivities(database.getActivities());
			bookingService.setBookings(database.getBookings());
			familyService.setStudents(database.getStudents());
			ois.close();
		} catch (FileNotFoundException e) {
			this.save();
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void save() {
		Database database = new Database(familyService.getStudents(),
				activityService.getActivities(), bookingService.getBookings(), familyService.getHousehold());

		// AQUI GUARDO LOS OBJETOS (usarlo cuando cierras la iu)
		try {
			FileOutputStream fs = new FileOutputStream(pathDB);
			ObjectOutputStream os = new ObjectOutputStream(fs);
			if(pathDB.compareTo("DBTest.bin")==0){
				database = new Database(new ArrayList<Student>(),new ArrayList<Activity>()
						,new ArrayList<Booking>(), new ArrayList<Household>());
			}
			os.writeObject(database);

			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
