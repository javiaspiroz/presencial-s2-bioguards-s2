package application;

import java.sql.ResultSet;
import java.util.Vector;

import controlador.ControladorBBDD;

public class Cuidador_Familiar extends Perfil{
	
	private Vector<Paciente> _pacientes = new Vector<Paciente>();

	// Contructor de lectura de BBDD a traves de un ResultSet 
	public Cuidador_Familiar (ResultSet rs) {
		super(rs);
	}
	
	public void cargarPacientesCuidador() {
		try {
			_pacientes.addAll(ControladorBBDD.getPacientesDeCuidador(_dni));
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	
	// GETTERS Y SETTERS
	public Vector<Paciente> getPacientes() {
		return _pacientes;
	}
	public void addPaciente(Paciente paciente) {
		this._pacientes.add(paciente);
	}
}
