package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import controlador.ControladorBBDD;

public class Usuario {

	private String _usuario;
	private String _contrasena;
	private String _rol;

	public Usuario(String usuario, String contrasena, String rol) {
		this._usuario = usuario;
		this._contrasena = contrasena;
		this._rol = rol;		
	}
	
	
	// Contructor de lectura de BBDD a traves de un ResultSet.
	public Usuario (ResultSet rs) {
		try {
			_usuario = rs.getString("dni");
			_contrasena = rs.getString("password");
			_rol = rs.getString("rol");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Funcion que añade un nuevo usuario a BBDD.
	public Usuario addDB () {
		Usuario result = null;
		Connection conn = ControladorBBDD.inicializarBBDD();
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement("insert into usuario values (?,?,?)");
			stmt.setString(1, _usuario);
			stmt.setString(2, _contrasena);
			stmt.setString(3, _rol);
			stmt.executeUpdate();
			
			result = new Usuario(_usuario, _contrasena, _rol);
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// GETTERS Y SETTERS
	public String getUsuario() {
		return _usuario;
	}
	public void setUsuario(String usuario) {
		this._usuario = usuario;
	}
	public String getContrasena() {
		return _contrasena;
	}
	public void setContrasena(String contrasena) {
		this._contrasena = contrasena;
	}
	public String getRol() {
		return _rol;
	}
	public void setRol(String rol) {
		this._rol = rol;
	}
}