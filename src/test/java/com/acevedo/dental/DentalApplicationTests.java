package com.acevedo.dental;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.acevedo.dental.data.PacienteDAO;
import com.acevedo.dental.model.Cita;
import com.acevedo.dental.model.ListaEspera;
import com.acevedo.dental.model.Paciente;
import com.acevedo.dental.model.Tratamiento;

@SpringBootTest
class DentalApplicationTests {
	
	@Autowired
	private PacienteDAO dao;

	@Test
	void contextLoads() {
	}
	
	@Test
	void testAddPaciente() {
		Paciente p = new Paciente();
		p.setNombre("Test Paciente");
		p.setApellido1("testAddPaciente");
		p.setApellido2("1");
		p.setTelefono("1");
		int id = dao.agregarPaciente(p);
		Paciente newPac = dao.findPaciente(id);
		assertEquals(id, newPac.getId());
	}
	
	@Test
	void testObtenerCita() {
		testAddCita();
		assertNotNull(dao.findCitas());
	}
	
	@Test
	void listaDeEspera() {
		Paciente p = dao.findPaciente(1);
		ListaEspera espera = new ListaEspera();
		espera.setPaciente(p);
		espera.setInformacion("Nueva Lista Espera");
		dao.agregarListaDeEspera(espera);
		
		Paciente p2 = new Paciente();
		p2.setNombre("Test");
		p2.setApellido1("Paciente");
		p2.setApellido2("Lista de Espera");
		p2.setTelefono("1234567890");
		
		ListaEspera espera2 = new ListaEspera();
		espera2.setInformacion("Segunda lista de espera");
		espera2.setPaciente(p2);
		dao.agregarListaDeEspera(espera2);
		
		List<ListaEspera> listaEspera =this.dao.findPacientesEnListaDeEspera();
		
		assertNotNull(p);
		assertNotNull(listaEspera);
		assertEquals(2, listaEspera.size());
	}
	
	/**
	 * 
	 */
	void testAddCita() {
 		Paciente p = new Paciente();
		p.setNombre("Test Cita");
		p.setApellido1("testAddCita");
		p.setApellido2("2");
		p.setTelefono("2");
		Tratamiento t = new Tratamiento();
		t.setId(1);
		t.setTratamiento("CONSULTA");
		long startDate = 1586358000000L;
		long endDate = 1586359800000L;
		int id = dao.agregaCita(p, t, startDate, endDate);		
		Cita c = dao.findCita(id);
		assertEquals(c.getId(), id);
		
		long newStartDate = 1686358000000L;
		long newendDate = 1686359800000L;
		
		c.setStartDate(newStartDate);
		c.setEndDate(newendDate);
		
		Cita nuevaCita = dao.cambiarCita(c);
		int borrada = dao.cancelarCita(nuevaCita);
		assertEquals(borrada, 1);
		
		c = dao.findCita(id);
		assertNull(c);
	}
	
	
}
