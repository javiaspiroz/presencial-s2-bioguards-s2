package application;

import java.sql.ResultSet;
import java.util.Vector;

import controlador.ControladorBBDD;

public class Clinico extends Perfil{

    private String _especialidad;
    private Vector<Paciente> _pacientes = new Vector<Paciente>();

	
	// Contructor de lectura de BBDD a traves de un ResultSet 
	public Clinico (ResultSet rs) {
		super(rs);
		try {
			_especialidad = rs.getString("especialidad");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	public void cargarPacientesClinico() {
		try {
			_pacientes.addAll(ControladorBBDD.getPacientesDeClinico(_dni));
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	// GETTERS Y SETTERS
	public String getEspecialidad() {
		return _especialidad;
	}
	public void setEspecialidad(String especialidad) {
		this._especialidad = especialidad;
	}
	public Vector<Paciente> getPacientes() {
		return _pacientes;
	}
	public void addPaciente(Paciente paciente) {
		this._pacientes.add(paciente);
	}
}
