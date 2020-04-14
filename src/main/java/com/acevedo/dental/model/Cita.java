package com.acevedo.dental.model;

public class Cita {

	private int id;
	private long startDate;
	private long endDate;
	private Paciente paciente;
	private Tratamiento tratamiento;
	private int confirmacion;
	
	public long getStartDate() {
		return startDate;
	}
	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}
	public long getEndDate() {
		return endDate;
	}
	public void setEndDate(long endDate) {
		this.endDate = endDate;
	}
	public Paciente getPaciente() {
		return paciente;
	}
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
	public Tratamiento getTratamiento() {
		return tratamiento;
	}
	public void setTratamiento(Tratamiento tratamiento) {
		this.tratamiento = tratamiento;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getConfirmacion() {
		return confirmacion;
	}
	public void setConfirmacion(int confirmacion) {
		this.confirmacion = confirmacion;
	}
	
	
}
