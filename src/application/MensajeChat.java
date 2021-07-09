package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

import controlador.ControladorBBDD;

public class MensajeChat {

	String _dniEmisor;
	String _dniReceptor;
	Timestamp _fechaMensaje;
	String _mensaje;
	
	public MensajeChat(String dniEmisor, String dniReceptor, Timestamp fechaMensaje, String mensaje) {
		super();
		this._dniEmisor = dniEmisor;
		this._dniReceptor = dniReceptor;
		this._fechaMensaje = fechaMensaje;
		this._mensaje = mensaje;
	}
	
	// Contructor de lectura de BBDD a traves de un ResultSet 
	public MensajeChat (ResultSet rs) {
		try {
			_mensaje = rs.getString("texto");
			_dniEmisor = rs.getString("emisor");
			_dniReceptor = rs.getString("receptor");
			_fechaMensaje = rs.getTimestamp("fecha");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	// Funcion que añade un nuevo mensaje a BBDD y devuelve el objeto MesajeChat asociado.
	public static MensajeChat addDB (String dniEmisor, String dniReceptor, String mensaje, String dniPaciente) {
		MensajeChat result = null;
		Timestamp fecha = Timestamp.from(Instant.now());
		Connection conn = ControladorBBDD.inicializarBBDD();
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement("insert into mensaje(texto, fecha, emisor, receptor, dni_paciente) values (?,?,?,?,?)");
			stmt.setString(1, mensaje);
			stmt.setTimestamp(2, fecha);
			stmt.setString(3, dniEmisor);
			stmt.setString(4, dniReceptor);
			stmt.setString(5, dniPaciente);
			stmt.executeUpdate();
			result = new MensajeChat(dniEmisor, dniReceptor, fecha, mensaje);
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	public String getDniEmisor() {
		return _dniEmisor;
	}
	public String getDniReceptor() {
		return _dniReceptor;
	}
	public Timestamp getFechaMensaje() {
		return _fechaMensaje;
	}
	public String getMensaje() {
		return _mensaje;
	}
}
