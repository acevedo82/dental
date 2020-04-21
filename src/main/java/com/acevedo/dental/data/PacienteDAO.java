package com.acevedo.dental.data;

import java.util.Date;
import java.util.List;

import com.acevedo.dental.model.Cita;
import com.acevedo.dental.model.Item;
import com.acevedo.dental.model.Paciente;
import com.acevedo.dental.model.Tratamiento;

public interface PacienteDAO {
	
	public static final String SELECT_LAST_INSERT_SQL = "SELECT LAST_INSERT_ID()";
	public static final String PACIENTE_INSERT_SQL = "INSERT INTO PACIENTE(NOMBRE, APELLIDO1, APELLIDO2) VALUES(?,?,?)";
	public static final String FIND_PACIENTE_SQL = "SELECT * FROM PACIENTE WHERE ID=?";
	public static final String PACIENTE_BUSQUEDA_SQL = "INSERT INTO PACIENTE_BUSQUEDA(NOMBRE_COMPLETO,ID_PACIENTE) VALUES(?, ?)";
	public static final String FIND_PACIENTES_SQL = "SELECT * FROM PACIENTE";
	public static final String FIND_TRATAMIENTOS_SQL = "SELECT * FROM TRATAMIENTOS";
	public static final String BUSCAR_PACIENTE_SQL = "SELECT * FROM PACIENTE_BUSQUEDA WHERE MATCH(nombre_completo) AGAINST (?);";
	public static final String AGREGA_CITA_SQL = "INSERT INTO CITA(ID_PACIENTE, ID_TRATAMIENTO, STARTDATE, ENDDATE) VALUES (?,?,?,?)";
	public static final String FIND_CITAS_SQL = "SELECT c.id, c.id_paciente, c.id_tratamiento, c.startDate, c.EndDate, c.confirmacion, p.nombre, p.apellido1, p.apellido2, p.telefono, p.ESPERA, t.tratamiento, t.duracion, t.costo FROM CITA c, PACIENTE p, TRATAMIENTOS t WHERE c.id_paciente = p.id AND c.id_tratamiento = t.id";
	public static final String FIND_CITA_BY_ID_SQL = FIND_CITAS_SQL + " AND c.id = ?";
	public static final String CAMBIAR_CITA = "UPDATE CITA SET startDate = ?, endDate = ?, confirmacion = 0 WHERE id = ?";
	public static final String CONFIRMAR_CITA = "UPDATE CITA SET confirmacion = ? WHERE id = ?";
	public static final String CANCELAR_CITA = "DELETE FROM CITA WHERE id = ?";
	public static final String LISTA_DE_ESPERA = "SELECT B.ID, B.NOMBRE, B.APELLIDO1, B.APELLIDO2, B.TELEFONO, B.ESPERA FROM PACIENTE b WHERE b.ESPERA = 'Y'"; 

	public int agregarPaciente(Paciente p);
	public int agregaCita(Paciente p, Tratamiento t, long startDate, long endDate);
	public Paciente findPaciente(int id);
	public Cita findCita(int id);
	public List<Paciente> findPacientes();
	public List<Paciente> findPacientesEnListaDeEspera();
	public List<Tratamiento> findTratamientos();
	
	public List<Item> buscarPaciente(String term);	
	public List<Cita> findCitas();	
	
	public Cita cambiarCita(Cita original);
	public int confirmarCita(Cita c);
	public int cancelarCita(Cita c);
}
