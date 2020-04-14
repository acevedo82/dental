package com.acevedo.dental;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.acevedo.dental.data.PacienteDAO;
import com.acevedo.dental.model.Cita;
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
