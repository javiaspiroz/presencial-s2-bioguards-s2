package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import controlador.ControladorBBDD;

public class EntradaCalendario {
	
	private int _id;
	private String _texto;
	private LocalDate _startDate;
	private LocalTime _startTime;
	private LocalDate _endDate;
	private LocalTime _endTime;
	private String _dniPaciente;
	

	public EntradaCalendario(){}
	
	public EntradaCalendario(int _id, String _texto, LocalDate _startDate, LocalTime _startTime, LocalDate _endDate,
			LocalTime _endTime, String _dniPaciente) {
		super();
		this._id = _id;
		this._texto = _texto;
		this._startDate = _startDate;
		this._startTime = _startTime;
		this._endDate = _endDate;
		this._endTime = _endTime;
		this._dniPaciente = _dniPaciente;
	}
	
	// Contructor de lectura de BBDD a traves de un ResultSet 
	public EntradaCalendario (ResultSet rs) {
		try {
			_id = rs.getInt("id");
			_texto =rs.getString("texto");
			Date parsedDateStart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("tiempoInicio"));
			_startDate = parsedDateStart.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			_startTime = parsedDateStart.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
			Date parsedDateEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("TiempoFin"));
			_endDate = parsedDateEnd.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			_endTime = parsedDateEnd.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
			_dniPaciente = rs.getString("dni_pac");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	// Funcion que añade un nuevo evento a BBDD y devuelve el objeto EntradaCalendario asociado.
	public EntradaCalendario addDB () {
		EntradaCalendario result = null;
		LocalDateTime fechaInicio = LocalDateTime.of(_startDate, _startTime);
		LocalDateTime fechaFin = LocalDateTime.of(_endDate, _endTime);
		String fechaFormateadaInicio = fechaInicio.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		String fechaFormateadaFin = fechaFin.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		Connection conn = ControladorBBDD.inicializarBBDD();
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement("insert into evento(texto, tiempoInicio, TiempoFin, dni_pac) values (?,?,?,?)");
			stmt.setString(1, _texto);
			stmt.setString(2, fechaFormateadaInicio);
			stmt.setString(3, fechaFormateadaFin);
			stmt.setString(4, _dniPaciente);
			stmt.executeUpdate();

			// Se selecciona para obtener el id
			stmt = conn.prepareStatement("select * from evento where texto=? and tiempoInicio=? and TiempoFin=? and dni_pac=?");
			stmt.setString(1, _texto);
			stmt.setString(2, fechaFormateadaInicio);
			stmt.setString(3, fechaFormateadaFin);
			stmt.setString(4, _dniPaciente);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				result = new EntradaCalendario(rs);
			}
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// Funcion que actualiza un evento a BBDD y devuelve el objeto EntradaCalendario asociado.
	public EntradaCalendario updateDB () {
		EntradaCalendario result = null;
		LocalDateTime fechaInicio = LocalDateTime.of(_startDate, _startTime);
		LocalDateTime fechaFin = LocalDateTime.of(_endDate, _endTime);
		String fechaFormateadaInicio = fechaInicio.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		String fechaFormateadaFin = fechaFin.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		Connection conn = ControladorBBDD.inicializarBBDD();
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement("update evento set texto=?, tiempoInicio=?, TiempoFin=? where id=?");
			stmt.setString(1, _texto);
			stmt.setString(2, fechaFormateadaInicio);
			stmt.setString(3, fechaFormateadaFin);
			stmt.setInt(4, _id);
			stmt.executeUpdate();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// Funcion que elimina un evento en BBDD
	public static void removeDB(int id) {
		Connection conn = ControladorBBDD.inicializarBBDD();
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement("delete from evento where id=?");
			stmt.setInt(1, id);
			stmt.execute();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	// GETTERS Y SETTERS
	public int getId() {
		return _id;
	}
	public void setId(int id) {
		_id = id;
	}
	public String getTitle() {
		return _texto;
	}
	public void setTitle(String title) {
		_texto = title;
	}
	public LocalDate getStartDate() {
		return _startDate;
	}
	public void setStartDate(LocalDate startDate) {
		_startDate = startDate;
	}
	public LocalTime getStartTime() {
		return _startTime;
	}
	public void setStartTime(LocalTime startTime) {
		_startTime = startTime;
	}
	public LocalDate getEndDate() {
		return _endDate;
	}
	public void setEndDate(LocalDate endDate) {
		_endDate = endDate;
	}
	public LocalTime getEndTime() {
		return _endTime;
	}
	public void setEndTime(LocalTime endTime) {
		_endTime = endTime;
	}
	public String getdniPaciente() {
		return _dniPaciente;
	}
	public void setdniPaciente(String dniPaciente) {
		this._dniPaciente = dniPaciente;
	}
}
