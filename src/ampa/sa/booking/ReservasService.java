package ampa.sa.booking;

import java.util.Calendar;
import java.util.List;

import ampa.sa.student.Student;

public interface ReservasService {
	
	public void crearReserva(Booking reserva);
	
	public void borrarReserva(Booking reserva);
	
	public Booking buscarReserva(long id);
	
	public void modificarReserva(Booking reserva);

	public List<Booking> listarReservasAlumnoFecha(Student alumno, Calendar periodo);
	
	public long obtenerPrecioReservasFecha(List<Booking> reservas);
	
	
}
