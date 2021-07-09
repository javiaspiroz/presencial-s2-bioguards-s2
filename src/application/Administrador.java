package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;

import controlador.ControladorBBDD;

public class Administrador extends Perfil {

    private int _tipo; // 1- empresa; 2- particular

    
    public Administrador(String nombre, String apellido, String dni, String email, java.sql.Date fecha, 
			 			 String direccion, String ciudad, String provincia, int codigoPostal,int tel, int tipo) {
    	super(nombre, apellido, dni, email, fecha, direccion, ciudad, provincia, codigoPostal, tel);
    	this._tipo = tipo;
    }
    
	// Contructor de lectura de BBDD a traves de un ResultSet. 
	public Administrador (ResultSet rs) {
		super(rs);
		try {
			_tipo = rs.getInt("tipo");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//Funcion que añade un nuevo administrador a BBDD.
	public Administrador addDB () {
		Administrador result = null;
		Timestamp fecha = Timestamp.from(Instant.now());
		String fechaFormateada = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(fecha);
		Connection conn = ControladorBBDD.inicializarBBDD();
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement("insert into admin values (?,?,?,?,?,?,?,?,?,?,?)");
			stmt.setString(1, _nombre);
			stmt.setString(2, _apellidos);
			stmt.setString(3, _dni);
			stmt.setString(4, fechaFormateada);
			stmt.setString(5, _email);
			stmt.setInt(6, _telefono);
			stmt.setString(7, _ciudad);
			stmt.setString(8, _provincia);
			stmt.setInt(9, _codigoPostal);
			stmt.setString(10, _direccion);
			stmt.setInt(11, _tipo);
			stmt.executeUpdate();
			
			result = new Administrador(_nombre, _apellidos, _dni, _email, _fechaNacimiento, _direccion, _ciudad, _provincia, _codigoPostal, _telefono, _tipo);
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
		
		
	
	// GETTERS Y SETTERS
	public int getTipo() {
		return _tipo;
	}
	public void setTipo(int t) {
		_tipo=t;
	}
}
