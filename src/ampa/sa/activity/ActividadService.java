package ampa.sa.activity;

import java.util.List;

import ampa.sa.student.Student;

public interface ActividadService {
	
	public void crearActividad();
	
	public void borrarActividad();
	
	public void modificarActividad();
	
	public List<Student> listarAlumnosActividad();
	
	public List<Activity> listarActividades();
	
	public Activity buscarActividad();
}
