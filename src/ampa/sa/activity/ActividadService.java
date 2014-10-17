package ampa.sa.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ampa.sa.student.Student;

@SuppressWarnings("serial")
public class ActividadService implements Serializable{
	
	List<Activity> activities = new ArrayList<Activity>();
	
	private static ActividadService instance = null;

    private ActividadService() {
    }

    private synchronized static void createInstance() {
        if (instance == null) {
            instance = new ActividadService();
        }
    }

    public static ActividadService getInstance() {
        createInstance();
        return instance;
    }
	
	
	
	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

	public void crearActividad() {
	}
	
	public void borrarActividad(){
	}
	
	public void modificarActividad(){
	}
	
	public List<Student> listarAlumnosActividad(){
		return null;
	}
	
	public List<Activity> listarActividades(){
		return null;
	}
	
	public Activity buscarActividad(){
		return null;
	}
}
