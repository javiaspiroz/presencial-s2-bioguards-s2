package application;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class Perfil {
	protected String _nombre;
	protected String _apellidos;
	protected String _dni;
	protected String _email;
	protected java.sql.Date _fechaNacimiento ;
	protected String _direccion;
	protected String _ciudad;
	protected String _provincia;
	protected int _codigoPostal;
	protected int _telefono;
	
	
	public Perfil(String nombre, String apellidos, String dni, String email, java.sql.Date fecha, 
				  String direccion, String ciudad, String provincia, int codigoPostal, int tel) {
		super();
		this._nombre = nombre;
		this._apellidos = apellidos;
		this._dni = dni;
		this._email = email;
		this._fechaNacimiento = fecha;
		this._direccion = direccion;
		this._ciudad = ciudad;
		this._provincia = provincia;
		this._codigoPostal = codigoPostal;
		this._telefono = tel;
	}
	
	//Constructor para tabla pacientes.
	public Perfil(String nombre, String apellidos, String dni) {
		super();
		this._nombre = nombre;
		this._apellidos = apellidos;
		this._dni = dni;
	}
	
	public Perfil (String nombre, String apellidos, String dni ,String direccion) {
		super();
		this._nombre = nombre;
		this._apellidos = apellidos;
		this._dni = dni;
		this._direccion = direccion;
		
	}

	// Contructor de lectura de BBDD a traves de un ResultSet.
	public Perfil (ResultSet rs) {
		try {
			_nombre = rs.getString("nombre");
			_apellidos =rs.getString("apellido");
			_dni =rs.getString("dni");
			_email =rs.getString("email");
			_fechaNacimiento = rs.getDate("FechaNacimiento");
			_direccion = rs.getString("direccion");
			_ciudad = rs.getString("ciudad");
			_provincia = rs.getString("provincia");
			_codigoPostal = rs.getInt("codPostal");
			_telefono = rs.getInt("telefono");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	// GETTERS Y SETTERS
	public String getNombre() {
		return _nombre;
	}
	public void setNombre(String nombre) {
		this._nombre = nombre;
	}
	public String getApellidos() {
		return _apellidos;
	}
	public void setApellidos(String apellidos) {
		this._apellidos = apellidos;
	}
	public String getDni() {
		return _dni;
	}
	public void setDni(String dni) {
		this._dni = dni;
	}
	public String getEmail() {
		return _email;
	}
	public void setEmail(String email) {
		this._email = email;
	}
	public java.sql.Date getFechaNacimiento() {
		return _fechaNacimiento;
	}
	public void setFechaNacimiento(java.sql.Date fechaNacimiento) {
		this._fechaNacimiento = fechaNacimiento;
	}
	public String getDireccion() {
		return _direccion;
	}
	public void setDireccion(String direccion) {
		this._direccion = direccion;
	}
	public int getTelefono() {
		return _telefono;
	}
	public void setTelefono(int telefono) {
		this._telefono = telefono;
	}
	public String getCiudad() {
		return _ciudad;
	}
	public void setCiudad(String ciudad) {
		this._ciudad = ciudad;
	}
	public String getProvincia() {
		return _provincia;
	}
	public void setProvincia(String provincia) {
		this._provincia = provincia;
	}
	public int getCodigoPostal() {
		return _codigoPostal;
	}
	public void setCodigoPostal(int codigoPostal) {
		this._codigoPostal = codigoPostal;
	}
}
