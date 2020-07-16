package com.acevedo.dental.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acevedo.dental.model.Cita;
import com.acevedo.dental.model.Item;
import com.acevedo.dental.model.ListaEspera;
import com.acevedo.dental.model.Paciente;
import com.acevedo.dental.model.Tratamiento;

@Repository
@Transactional
@Service
public class PacienteDAOImpl implements PacienteDAO {

	@Autowired
	private JdbcTemplate JdbcTemplate;
	
	private final Logger logger = LoggerFactory.getLogger(PacienteDAOImpl.class);
	
	@Override
	@Transactional
	public int agregarPaciente(Paciente p) {
		// Inserta paciente en la tabla PACIENTES
		logger.info("DAO - agregar paciente");
		
		Object[] parametros = new Object[] {p.getNombre(), p.getApellido1(), p.getApellido2(), p.getTelefono()};
		logger.info("parametros a usar [" + parametros[0] + "," + parametros[1] + "," + parametros[2] + ", " + parametros[3] +"]");
		int inserted = this.getJdbcTemplate().update(PACIENTE_INSERT_SQL, parametros);
		int id_inserted = this.getJdbcTemplate().queryForObject(SELECT_LAST_INSERT_SQL, Integer.class);
		String nombreCompleto = new StringBuffer().append(p.getNombre()).append(" ").append(p.getApellido1()).append(" ").append(p.getApellido2()).toString();
		int paciente_busqueda = this.getJdbcTemplate().update(PACIENTE_BUSQUEDA_SQL, new Object[] { nombreCompleto,  id_inserted} );
		logger.info("Sincronizar con paciente_busqueda = " + paciente_busqueda);
		if(inserted > 0) {
			return id_inserted;
		}
		return 0;
	}
	
	
	

	public JdbcTemplate getJdbcTemplate() {
		return JdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		JdbcTemplate = jdbcTemplate;
	}




	@Override
	public Paciente findPaciente(int id) {
		// Get paciente by Id
		
		Paciente p = this.getJdbcTemplate().queryForObject(FIND_PACIENTE_SQL, new Object [] {id}, new PacienteRowMapper());
		return p;
	}
	
	private class PacienteRowMapper implements RowMapper<Paciente> {

		@Override
		public Paciente mapRow(ResultSet rs, int row) throws SQLException {
			Paciente p = new Paciente();
			p.setId(rs.getInt("ID"));
			p.setNombre(rs.getString("NOMBRE"));
			p.setApellido1(rs.getString("APELLIDO1"));
			p.setApellido2(rs.getString("APELLIDO2"));
			p.setTelefono(rs.getString("TELEFONO"));
			return p;
		}
		
	}

	@Override
	public List<Paciente> findPacientes() {
		// SELECT ALL PACIENTES
		
		List<Paciente> allPacs = this.getJdbcTemplate().query(FIND_PACIENTES_SQL, new PacienteRowMapper());
		return allPacs;
	}




	@Override
	public List<Tratamiento> findTratamientos() {
		// FIND TRATAMIENTO
		
		List<Tratamiento> t = this.getJdbcTemplate().query(FIND_TRATAMIENTOS_SQL, new TratamientoMapper());
		return t;
	}
	
	private class TratamientoMapper implements RowMapper<Tratamiento> {

		@Override
		public Tratamiento mapRow(ResultSet rs, int row) throws SQLException {
			Tratamiento p = new Tratamiento();
			p.setId(rs.getInt("ID"));
			p.setTratamiento(rs.getString("TRATAMIENTO"));
			p.setDuracion(rs.getString("DURACION"));
			p.setCosto(rs.getString("COSTO"));
			return p;
		}
		
	}

	@Override
	@Transactional
	public int agregaCita(Paciente p, Tratamiento t, long startDate, long endDate, String notas) {
		// ADD CITA
		int affected = 0;
		int id_pac = 0;
		int id_inserted = 0;
		// Si hay cita, proceder
		if(p.getId() == 0) {
			logger.debug("Tenemos que agregar un nuevo paciente");
			id_pac = this.agregarPaciente(p);
			logger.debug("PAciente agregado " + id_pac);
		} else {
			logger.debug("Ya teniamos el paciente, hay que obtenerlo de la base de datos");
			id_pac = p.getId();
		}
			
		p = findPaciente(id_pac);
					
		logger.debug("Start: " + startDate + " || End: " + endDate);		
		//Paciente pac = findPaciente(id_pac);
		affected = this.getJdbcTemplate().update(AGREGA_CITA_SQL, new Object[] {id_pac, t.getId(), startDate, endDate, notas});
		if(affected > 0) 
			id_inserted = this.getJdbcTemplate().queryForObject(SELECT_LAST_INSERT_SQL, Integer.class);
		return id_inserted;
	}




	@Override
	public List<Item> buscarPaciente(String term) {
		// Buscar paciente en la tabla de paciente_busqueda
		
		return this.getJdbcTemplate().query(BUSCAR_PACIENTE_SQL, new String[] {term}, new ItemRowMapper());
	}
	
	
	
	private class ItemRowMapper implements RowMapper<Item> {

		@Override
		public Item  mapRow(ResultSet rs, int row) throws SQLException {
			Item p = new Item();
			p.setId(rs.getInt("ID_PACIENTE"));
			p.setValue(rs.getString("NOMBRE_COMPLETO"));
			return p;
		}
		
	}

	@Override
	public List<Cita> findCitas() {
		// Obtener todas las citas
		List<Cita> retVal = null;
		retVal = this.getJdbcTemplate().query(FIND_CITAS_EN_90_DIAS_SQL, new CitaRowMapper());
		return retVal;
	}	
	
	private class CitaRowMapper implements RowMapper<Cita> {

		@Override
		public Cita  mapRow(ResultSet rs, int row) throws SQLException {
			Cita c = new Cita();
			
			c.setId(rs.getInt("ID"));
			c.setStartDate(rs.getLong("STARTDATE"));
			c.setEndDate(rs.getLong("ENDDATE"));
			c.setConfirmacion(rs.getInt("CONFIRMACION"));
			c.setNotas(rs.getString("NOTAS"));
			
			Paciente p = new Paciente();
			p.setId(rs.getInt("ID_PACIENTE"));
			p.setNombre(rs.getString("NOMBRE"));
			p.setApellido1(rs.getString("APELLIDO1"));
			p.setApellido2(rs.getString("APELLIDO2"));
			p.setTelefono(rs.getString("TELEFONO"));
			
			c.setPaciente(p);
			
			Tratamiento t = new Tratamiento();
			t.setId(rs.getInt("ID_TRATAMIENTO"));
			t.setTratamiento(rs.getString("TRATAMIENTO"));
			t.setDuracion(rs.getString("DURACION"));
			t.setCosto(rs.getString("COSTO"));
			
			c.setTratamiento(t);
			
			return c;
		}
		
	}

	@Override
	public Cita findCita(int id) {
		// Encuentra una cita en particular
		logger.debug("Buscar cita con ID=" + id);
		Cita c = null;
		try {
			c = this.getJdbcTemplate().queryForObject(FIND_CITA_BY_ID_SQL, new Object[] {id}, new CitaRowMapper()); 
			logger.debug("Encontramos cita de " + c.getPaciente());

		} catch(DataAccessException e) {
			c= null;
			logger.debug("Cita no encontrada" );
		}
		
		return c;
	}
	

	@Override
	@Transactional
	public Cita cambiarCita(Cita original) {
		// Cambiar datos
		Cita nuevaCita=  null;
		long startDate = original.getStartDate();
		long endDate = original.getEndDate();
		int id = original.getId();
		int tratamientoId = original.getTratamiento().getId();	
		String notas = original.getNotas();
		
		logger.debug("cambiar cita a nueva fecha [start=" + new Date(startDate) + "end=" + new Date(endDate) + "] for id=" + id + ", notas=["+notas+"]" );
		int updated = this.getJdbcTemplate().update(CAMBIAR_CITA, new Object[] {startDate, endDate, tratamientoId, notas, id});
		logger.debug("Affected rows = " + updated);
		this.actuailzarPaciente(original.getPaciente());
		nuevaCita = findCita(id);
		return nuevaCita;
	}




	@Override
	@Transactional
	public int cancelarCita(Cita c) {
		int id = c.getId();
		logger.debug("cancelar cita for id=" + id );
		int deleted = this.getJdbcTemplate().update(CANCELAR_CITA, new Object[] {id});
		logger.debug("Affected rows = " + deleted);
		return deleted;
	}




	@Override
	public int confirmarCita(Cita c) {
		int id = c.getId();
		logger.debug("confirmar cita for id=" + id );
		int confirmar = this.getJdbcTemplate().update(CONFIRMAR_CITA, new Object[] {1,id});
		logger.debug("Affected rows = " + confirmar);
		return confirmar;
	}




	@Override
	public List<ListaEspera> findPacientesEnListaDeEspera() {
		// Lista de espera
		
		List<ListaEspera> listaDeEspera = this.getJdbcTemplate().query(LISTA_DE_ESPERA, new ListaEsperaRowMapper());
		return listaDeEspera;
	}


	private class ListaEsperaRowMapper implements RowMapper<ListaEspera> {

		@Override
		public ListaEspera mapRow(ResultSet rs, int rowNum) throws SQLException {
			ListaEspera espera = new ListaEspera();
			espera.setInformacion(rs.getString("INFORMACION"));
			espera.setId(rs.getInt("ID"));
			Paciente p = new Paciente();
			p.setId(rs.getInt("ID_PACIENTE"));
			p.setNombre(rs.getString("NOMBRE"));
			p.setApellido1(rs.getString("APELLIDO1"));
			p.setApellido2(rs.getString("APELLIDO2"));
			p.setTelefono(rs.getString("TELEFONO"));
			espera.setPaciente(p);
			return espera;
		}
		
	}


	@Override
	@Transactional
	public int agregarListaDeEspera(ListaEspera espera) {
		// Agregar a la lista de espera
		Paciente p = null;
		int affected = 0;
		int id_inserted = 0;
		int id_paciente = 0;
		String info = null;
		try {
			p = espera.getPaciente();
			id_paciente = p.getId();
					
			if(p.getId() == 0) {
				logger.debug("Tenemos que agregar un nuevo paciente");
				id_paciente = this.agregarPaciente(p);
				logger.debug("PAciente agregado " + id_paciente);
			} else {
				logger.debug("Ya teniamos el paciente, hay que obtenerlo de la base de datos");
				id_paciente = p.getId();
			}
			
			info = espera.getInformacion();
			affected = this.getJdbcTemplate().update(AGREGAR_LISTA_ESPERA_SQL, new Object[] {id_paciente, info});
			if(affected > 0) 
				id_inserted = this.getJdbcTemplate().queryForObject(SELECT_LAST_INSERT_SQL, Integer.class);
			logger.info("Sincronizar con lista_espera = " + id_inserted);			

		} catch(Exception e) {
			logger.warn("Error mientras agregamos una lista de espera", e);		
			id_inserted = 0;
		}
		
		return id_inserted;
	}




	@Override
	public ListaEspera findListaEspera(int id) {
		// 
		ListaEspera espera = null;
		espera = this.getJdbcTemplate().queryForObject(LISTA_DE_ESPERA_BY_ID, new Object[] {id}, new ListaEsperaRowMapper());				
		return espera;
	}




	@Override
	public int borrarListaEspera(int id) {		
		return this.getJdbcTemplate().update(BORRAR_LISTA_ESPERA, new Object[] {id});
	}




	@Override
	public List<Cita> findCitasPorDia(String startDate) {
		// Obtener todas las citas del dia
		List<Cita> retVal = null;
		retVal = this.getJdbcTemplate().query(FIND_CITAS_POR_DIA, new Object[] {startDate, startDate}, new CitaRowMapper());
		return retVal;
	}

	@Override
	public int actuailzarPaciente(Paciente p) {
		int updated = 0;
		try {
			String nombre = p.getNombre();
			String apellido1 = p.getApellido1();
			String apellido2 = p.getApellido2();
			String telefono = p.getTelefono();
			int id = p.getId();
			logger.info("Vamos a actualizar informacion del paciente " + id);
			updated = this.getJdbcTemplate().update(ACTUALIZAR_PACIENTE_SQL, new Object [] {nombre, apellido1, apellido2, telefono, id});
			logger.info("Paciente actualizado = "+ updated);
			
			
		} catch(Exception e) {
			logger.warn("Error while updating paciente", e);
		}
		return updated;
	}

	
	
	
}
