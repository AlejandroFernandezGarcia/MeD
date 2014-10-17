package ampa.sa.student;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class FamiliaService implements Serializable {

	List<Student> students = new ArrayList<Student>();

	private static FamiliaService instance = null;

	private FamiliaService() {
	}

	private synchronized static void createInstance() {
		if (instance == null) {
			instance = new FamiliaService();
		}
	}

	public static FamiliaService getInstance() {
		createInstance();
		return instance;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> bookings) {
		this.students = bookings;
	}

	public void crearAlumno(Student student) {

	}

	public void crearNucleoFamiliar(Household household) {

	}

	public void borrarAlumno(long id) {

	}

	public void borrarNucleoFamiliar(long id) {

	}

	public List<Student> listarAlumnos() {
		return students;

	}

	public List<Student> listarAlumnosNucleofamiliar(Household household) {
		return students;

	}

	public BigDecimal obtenerGastoNucleoFamiliar(Household household) {
		return null;

	}

	public Student buscarAlumno(Student student) {
		return null;

	}

	public Household buscarNucleoFamiliar(long id) {
		return null;

	}
}
