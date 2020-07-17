package com.acevedo.dental.data;

import java.util.Date;
import java.util.List;

import com.acevedo.dental.model.Cita;
import com.acevedo.dental.model.Item;
import com.acevedo.dental.model.ListaEspera;
import com.acevedo.dental.model.Paciente;
import com.acevedo.dental.model.Tratamiento;

public interface PacienteDAO {
	
	public static final String SELECT_LAST_INSERT_SQL = "SELECT LAST_INSERT_ID()";
	public static final String PACIENTE_INSERT_SQL = "INSERT INTO PACIENTE(NOMBRE, APELLIDO1, APELLIDO2,TELEFONO) VALUES(?,?,?,?)";
	public static final String FIND_PACIENTE_SQL = "SELECT * FROM PACIENTE WHERE ID=?";
	public static final String PACIENTE_BUSQUEDA_SQL = "INSERT INTO PACIENTE_BUSQUEDA(NOMBRE_COMPLETO,ID_PACIENTE) VALUES(?, ?)";
	public static final String FIND_PACIENTES_SQL = "SELECT * FROM PACIENTE";
	public static final String FIND_TRATAMIENTOS_SQL = "SELECT * FROM TRATAMIENTOS ORDER BY TRATAMIENTO ASC";
	public static final String BUSCAR_PACIENTE_SQL = "SELECT * FROM PACIENTE_BUSQUEDA WHERE MATCH(NOMBRE_COMPLETO) AGAINST (?)";
	public static final String AGREGA_CITA_SQL = "INSERT INTO CITA(ID_PACIENTE, ID_TRATAMIENTO, STARTDATE, ENDDATE, NOTAS) VALUES (?,?,?,?,?)";
	public static final String FIND_CITAS_SQL = "SELECT C.ID, C.ID_PACIENTE, C.ID_TRATAMIENTO, C.STARTDATE, C.ENDDATE, C.CONFIRMACION, P.NOMBRE, P.APELLIDO1, P.APELLIDO2, P.TELEFONO, T.TRATAMIENTO, T.DURACION, T.COSTO, C.NOTAS FROM CITA C, PACIENTE P, TRATAMIENTOS T WHERE C.ID_PACIENTE = P.ID AND C.ID_TRATAMIENTO = T.ID";
	public static final String FIND_CITAS_POR_DIA = FIND_CITAS_SQL + " AND FROM_UNIXTIME(C.STARTDATE/1000) >= ? AND (FROM_UNIXTIME(C.STARTDATE/1000) < (? + INTERVAL 1 DAY)) ORDER BY C.STARTDATE ASC ";
	public static final String FIND_CITAS_EN_90_DIAS_SQL = FIND_CITAS_SQL + " AND (FROM_UNIXTIME(C.STARTDATE/1000) > (NOW() - INTERVAL 7 DAY)) AND (FROM_UNIXTIME(C.STARTDATE/1000) < (NOW() + INTERVAL 360 DAY))";
	public static final String FIND_CITA_BY_ID_SQL = FIND_CITAS_SQL + " AND C.ID = ?";
	public static final String CAMBIAR_CITA = "UPDATE CITA SET STARTDATE = ?, ENDDATE = ?, ID_TRATAMIENTO=?, CONFIRMACION = 0, NOTAS=? WHERE ID = ?";
	public static final String CONFIRMAR_CITA = "UPDATE CITA SET CONFIRMACION = ? WHERE ID = ?";
	public static final String CANCELAR_CITA = "DELETE FROM CITA WHERE ID = ?";
	public static final String BORRAR_LISTA_ESPERA = "DELETE FROM LISTA_ESPERA WHERE ID = ?";
	public static final String LISTA_DE_ESPERA = "SELECT A.ID, B.ID AS ID_PACIENTE, B.NOMBRE, B.APELLIDO1, B.APELLIDO2, B.TELEFONO, A.INFORMACION FROM LISTA_ESPERA A, PACIENTE B WHERE B.ID=A.ID_PACIENTE";
	public static final String LISTA_DE_ESPERA_BY_ID = LISTA_DE_ESPERA + " AND A.ID=?";
	public static final String AGREGAR_LISTA_ESPERA_SQL = "INSERT INTO LISTA_ESPERA(ID_PACIENTE, INFORMACION) VALUES (?,?)";
	public static final String ACTUALIZAR_PACIENTE_SQL = "UPDATE PACIENTE SET NOMBRE=?, APELLIDO1=?, APELLIDO2=?, TELEFONO=? WHERE ID=?";

	public int agregarPaciente(Paciente p);
	public int agregaCita(Paciente p, Tratamiento t, long startDate, long endDate, String notas);
	public Paciente findPaciente(int id);
	public Cita findCita(int id);	
	public List<Paciente> findPacientes();
	public List<ListaEspera> findPacientesEnListaDeEspera();
	public ListaEspera findListaEspera(int id);
	public List<Tratamiento> findTratamientos();
	
	public List<Item> buscarPaciente(String term);	
	public List<Cita> findCitas();
	public List<Cita> findCitasPorDia(String startDate);
	
	public Cita cambiarCita(Cita original);
	public int confirmarCita(Cita c);
	public int cancelarCita(Cita c);
	public int borrarListaEspera(int id);
	
	public int agregarListaDeEspera(ListaEspera espera);
	
	public int actuailzarPaciente(Paciente p);
}
