package ampa.sa.student;

import java.math.BigDecimal;
import java.util.List;

public interface FamiliaService {
	
	public void crearAlumno();
	
	public void crearNucleoFamiliar();
	
	public void borrarAlumno();
	
	public void borrarNucleoFamiliar();
	
	public List<Student> listarAlumnos();
	
	public List<Student> listarAlumnosNucleofamiliar();
	
	public BigDecimal obtenerGastoNucleoFamiliar();
	
	public Student buscarAlumno();
	
	public Household buscarNucleoFamiliar();
}
