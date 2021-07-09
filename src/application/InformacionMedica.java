package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import controlador.ControladorBBDD;

public class InformacionMedica {

	private String _titulo;
	private Timestamp _fechaCreacion;
	private String _descripcion;
	private String _tratamiento;
	
	public InformacionMedica(String titulo, Timestamp fecha, String descripcion, String tratamiento) {
		this._titulo = titulo;
		this._fechaCreacion = fecha;
		this._descripcion = descripcion;
		this._tratamiento = tratamiento;
	}
	
	//Constructor para tabla 
	public InformacionMedica(String titulo) {
		this._titulo = titulo;
		
	}

	// Contructor de lectura de BBDD a traves de un ResultSet 
	public InformacionMedica (ResultSet rs) {
		try {
			_titulo = rs.getString("titulo");
			_fechaCreacion = rs.getTimestamp("fechaCreacion");
			_descripcion = rs.getString("descripcion");
			_tratamiento = rs.getString("tratamiento");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	// Se añade un nuevo apartado de informacion medica a BBDD y se devuelve el objeto InformacionMedica asociado.
	public static InformacionMedica addDB (String titulo, Timestamp fecha, String desripcion, String tratamiento) {
		InformacionMedica result = null;
		Connection conn = ControladorBBDD.inicializarBBDD();
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement("insert into mensaje(texto, fechaNacimiento, descripcion, tratamiento) values (?,?,?,?)");
			stmt.setString(1, titulo);
			stmt.setTimestamp(2, fecha);
			stmt.setString(3, desripcion);
			stmt.setString(4, tratamiento);
			stmt.executeUpdate();
			result = new InformacionMedica(titulo, fecha, desripcion, tratamiento);
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	
	// GETTERS Y SETTERS
	public String getTitulo() {
		return _titulo;
	}
	public void setTitulo(String titulo) {
		this._titulo = titulo;
	}
	public Timestamp getFechaCreacionTimeStamp() {
		return _fechaCreacion;
	}
/*	public String getFechaCreacionString() {
		return new SimpleDateFormat("yyyy/MM/dd").format(_fechaCreacion);
	}*/
	public void setFechaCreacion(Timestamp fechaCreacion) {
		this._fechaCreacion = fechaCreacion;
	}
	public String getDescripcion() {
		return _descripcion;
	}
	public void setDescripcion(String descripcion) {
		this._descripcion = descripcion;
	}
	public String getTratamiento() {
		return _tratamiento;
	}
	public void setTratamiento(String tratamiento) {
		this._tratamiento = tratamiento;
	}
}