package application;

import java.sql.ResultSet;
import java.util.Vector;
import controlador.ControladorBBDD;

public class Paciente extends Perfil {

	private String[] _sensores;
    private Clinico _clinico;
    private Vector<Cuidador_Familiar> _cuidadores_familiares;


	//Constructor para tabla pacientes
	public Paciente(String nombre, String apellido, String dni) {
		super(nombre, apellido, dni);
	}
	

	public Paciente(String nombre, String apellidos, String dni,String direccion) {
		super (nombre, apellidos, dni, direccion);
	}

	// Contructor de lectura de BBDD a traves de un ResultSet. 
	public Paciente (ResultSet rs) {
		super(rs);
		try {
		   String dniClinico = rs.getString("dni_clinico");
		   _clinico = ControladorBBDD.getClinicoPorDni(dniClinico);
		   _cuidadores_familiares = ControladorBBDD.getCuidadoresDePaciente(_dni);
		   _sensores = ControladorBBDD.getSensoresDePaciente(_dni);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	// GETTERS Y SETTERS
	public String[] getSensores() {
		return _sensores;
	}
	public void setSensores(String[] sensores) {
		this._sensores = sensores;
	}
	public Clinico getClinico() {
		return _clinico;
	}
	public void setClinico(Clinico clinico) {
		this._clinico = clinico;
	}
	public Vector<Cuidador_Familiar> getCuidadores_familiares() {
		return _cuidadores_familiares;
	}
	public void addCuidadores_familiares(Cuidador_Familiar cuidadores_familiares) {
		this._cuidadores_familiares.add(cuidadores_familiares);
	}
}
