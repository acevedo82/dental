package com.acevedo.dental.controllers;


import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.acevedo.dental.DentalConstants;
import com.acevedo.dental.data.PacienteDAO;
import com.acevedo.dental.model.Cita;
import com.acevedo.dental.model.Item;
import com.acevedo.dental.model.ListaEspera;
import com.acevedo.dental.model.Paciente;
import com.acevedo.dental.model.Respuesta;
import com.acevedo.dental.model.Tratamiento;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class MainRestController {
	
	@Autowired	
	private PacienteDAO dao;
	
	private final Logger logger = LoggerFactory.getLogger(MainRestController.class);
	public static final ObjectMapper mapper = new ObjectMapper();

	@RequestMapping(value = "/paciente/{id}", method = RequestMethod.GET)
	public Paciente obtenerPaciente(@PathVariable Integer id) {
		Paciente p = this.dao.findPaciente(id);
		
		return p;
	}
	
	@RequestMapping(value = "/paciente", method = RequestMethod.GET)
	public @ResponseBody String obtenerPacientes() throws Exception {
		List<Paciente> p = this.dao.findPacientes();		 		
		return mapper.writeValueAsString(p);
	}
	
	
	@RequestMapping(value = "/listaEspera", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String listaDeEspera() throws Exception {
		List<ListaEspera> p = this.dao.findPacientesEnListaDeEspera();		 		
		return mapper.writeValueAsString(p);
	}	
	
	@RequestMapping(value = "/listaEspera", method = RequestMethod.PUT, produces = "application/json")
	public Respuesta listaDeEspera(@RequestBody ListaEspera espera) throws Exception {
		logger.debug("Agregando lista de espera al paciente " + espera.getPaciente().getNombre());
		Respuesta r = new Respuesta();
		int affected = 0;
		affected = this.dao.agregarListaDeEspera(espera);
		r.setCodigo(affected);
		r.setData(DentalConstants.SUCCESS);
		r.setMensaje(DentalConstants.OK);
		return r;
	}	
	
	@RequestMapping(value = "/listaEspera/{id}", method = RequestMethod.GET, produces = "application/json")
	public ListaEspera listaDeEspera(@PathVariable Integer id) throws Exception {
		logger.debug("Buscando lista de espera " + id);
		Respuesta r = new Respuesta();
		ListaEspera espera = this.dao.findListaEspera(id);
		return espera;
	}	
	
	@RequestMapping( value = "/paciente", method = RequestMethod.POST, consumes = "application/json", produces = "application/json" )
	public Respuesta agregarPaciente(@RequestBody Paciente p) {
		logger.debug("Empezando metodo para agregar paciente por id");
		
		Respuesta r = new Respuesta();		
		int affected = dao.agregarPaciente(p);
			
		logger.info("Insertamos " + affected + " pacientes!");
		logger.info("Construir resultado REST");
		
		r.setCodigo(affected);
		r.setData(DentalConstants.SUCCESS);
		r.setMensaje(DentalConstants.OK);
		
		logger.info("Completedo");
		return r;
	}

	
	@RequestMapping( value = "/cita", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Respuesta> cambiarCita(@RequestBody Cita c) {
		Respuesta r = new Respuesta();
		Cita nuevaCita = null;
		try {
			nuevaCita = this.dao.cambiarCita(c);
			if(nuevaCita == null) {
				throw new Exception("No cita");
			}
			r.setCodigo(200);
			r.setData("Cambio efectuado");
			r.setMensaje(DentalConstants.SUCCESS);
			return ResponseEntity.ok().body(r);
		} catch(Exception e) {
			// Sin cita, no op
			r.setCodigo(DentalConstants.BAD_REQUEST);
			r.setData(DentalConstants.BAD_REQUEST_ERROR);
			r.setMensaje(DentalConstants.FAILED);
			return ResponseEntity.badRequest().body(r);
		}
	}
	
	@RequestMapping( value = "/cita/{id}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Respuesta> confirmarCita(@PathVariable(name = "id")  Integer id) {	
		Respuesta r = new Respuesta();
		Cita c = null;
		int confirmado = 0;
		try {
			c = this.dao.findCita(id);
			confirmado = this.dao.confirmarCita(c);
			logger.info("Cita confirmada " + confirmado);
			r.setCodigo(200);
			r.setData("Cita confirmada");
			r.setMensaje(DentalConstants.SUCCESS);
			return ResponseEntity.ok().body(r);
		} catch(Exception e) {
			// Sin cita, no op
			r.setCodigo(DentalConstants.BAD_REQUEST);
			r.setData(DentalConstants.BAD_REQUEST_ERROR);
			r.setMensaje(DentalConstants.FAILED);
			return ResponseEntity.badRequest().body(r);
		}
	}
	
	@RequestMapping( value = "/cita/{id}", method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Respuesta> cancelarCita(@PathVariable(name = "id")  Integer id) {
		Respuesta r = new Respuesta();
		Cita c = null;
		int borrado = 0;
		try {
			c = this.dao.findCita(id);
			borrado = this.dao.cancelarCita(c);
			if(borrado == 0) {
				throw new Exception("La cita no fue borrada");
			}
			r.setCodigo(200);
			r.setData("Cita Borrada");
			r.setMensaje(DentalConstants.SUCCESS);
			return ResponseEntity.ok().body(r);
		} catch(Exception e) {
			// Sin cita, no op
			r.setCodigo(DentalConstants.BAD_REQUEST);
			r.setData(DentalConstants.BAD_REQUEST_ERROR);
			r.setMensaje(DentalConstants.FAILED);
			return ResponseEntity.badRequest().body(r);
		}
	}	
	
	@RequestMapping( value = "/cita", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Respuesta> agregarCita(@RequestBody Cita c, @RequestParam(name = "id_espera", required = false) Integer id_espera) {
		Respuesta r = new Respuesta();
		Paciente p = c.getPaciente();
		Tratamiento t = c.getTratamiento();
		long startDate = c.getStartDate();
		long endDate = c.getEndDate();		

		try {
			if(startDate == 0 || endDate == 0) {
				throw new Exception();
				
			}			
			logger.debug("Vamos a agregar cita nueva");
			int citaAgregada = this.dao.agregaCita(p, t, startDate, endDate);
			logger.debug("Cita agregada " + citaAgregada);
			if(citaAgregada > 0 && id_espera != null) {
				logger.debug("Lista de espera asociada borrada id=" + id_espera);
				try {
					this.dao.borrarListaEspera(id_espera);
				} catch(Exception x) {
					logger.debug("Error al borrar de lista de espera, a lo mejor no habia lista de espera", x);
				}
			}
			r.setMensaje(DentalConstants.SUCCESS);
			r.setData(new StringBuffer().append(citaAgregada).toString());
			return ResponseEntity.ok().body(r);
		} catch(Exception e) {
			// Sin cita, no op
			r.setCodigo(DentalConstants.BAD_REQUEST);
			r.setData(DentalConstants.BAD_REQUEST_ERROR);
			r.setMensaje(DentalConstants.FAILED);
			return ResponseEntity.badRequest().body(r);
		}	
	}
	
	@RequestMapping( value = "/cita", method = RequestMethod.GET)
	public @ResponseBody String citas() throws Exception {
		List<Cita> citas = this.dao.findCitas();
		return mapper.writeValueAsString(citas);
	}
	
	@RequestMapping( value = "/cita/{id}", method = RequestMethod.GET)
	public @ResponseBody Cita citas(@PathVariable(name = "id") Integer id) throws Exception {
		Cita cita = this.dao.findCita(id);
		return cita; 
	}	
	
	@RequestMapping( value = "/agenda", method = RequestMethod.GET)
	public @ResponseBody String citas(@RequestParam(name = "startDate", required = true) String startDate, @RequestParam(name = "endDate", required = true) String endDate) throws Exception {
		List<Cita> citas = this.dao.findCitasPorDia(startDate, endDate);
		return  mapper.writeValueAsString(citas);
	}		
	
	@RequestMapping(value = "/tratamiento", method = RequestMethod.GET)
	public @ResponseBody String obtenerTratamientos() throws Exception {
		List<Tratamiento> p = this.dao.findTratamientos();		 
		return mapper.writeValueAsString(p);
	}	

	@RequestMapping(value="/busqueda", method = RequestMethod.GET)
	public @ResponseBody String busqueda(@RequestParam(name = "term", required = true) String term) throws Exception {
		logger.debug("Vamos a buscar un paciente llamado " + term);		
		List<Item> l = this.dao.buscarPaciente(term);
		return mapper.writeValueAsString(l);
	}
	
	public PacienteDAO getDao() {
		return dao;
	}

	public void setDao(PacienteDAO dao) {
		this.dao = dao;
	}
	
	
}
