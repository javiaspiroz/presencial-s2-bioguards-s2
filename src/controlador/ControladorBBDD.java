package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import application.Clinico;
import application.Cuidador_Familiar;
import application.EntradaCalendario;
import application.Paciente;
import application.Usuario;

public class ControladorBBDD {

	// Conexión a BBDD
	//private static Connection _conn;
	static final String USER = "pr_bioguards";
	static final String PASS = "bIoguarDs.87";


	public static Connection inicializarBBDD() {
		//boolean result = false;
		// Se crea la conexion a BBDD. 
		Connection _conn = null;
		try {
			
			Class.forName("org.mariadb.jdbc.Driver");
			System.out.println("Connecting to a selected database...");
			_conn = DriverManager.getConnection(
					"jdbc:mariadb://2.139.176.212/prbioguards", USER, PASS);
			System.out.println("Conectado a la Base de Datos");
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		return _conn;
	}

	public static Usuario getUsuario(String login, String password) {
		Usuario result = null;
		try {
			Connection _conn = inicializarBBDD();
			PreparedStatement stmt = _conn.prepareStatement(
					"select usuario.dni, usuario.password, usuario.rol from usuario where usuario.dni= ? AND usuario.password= ?");
			stmt.setString(1, login);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				result = new Usuario(rs);
			}
			rs.close();
			_conn.close();

		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		return result;
	}

	public static Vector<Paciente> getPacientesDeClinico (String dniClinico) {
		Vector<Paciente> result = new Vector<Paciente>();
		try {
			Connection _conn = inicializarBBDD();
			PreparedStatement stmt = _conn.prepareStatement("select * from paciente where paciente.dni_clinico=?");
			stmt.setString(1, dniClinico);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Paciente paciente = new Paciente(rs);
				if (paciente != null) {
					result.add(paciente);
				}
			}
			rs.close();
			_conn.close();
		}
		catch (Exception e) {
			System.out.println("Error: "+e);			
		}
		return result;
	}

	public static Clinico getClinicoDePaciente (String dniPaciente) {
		Clinico result = null;
		try {
			Connection _conn = inicializarBBDD();
			String sql = "select clinico.* from paciente join clinico on paciente.dni_clinico=clinico.dni where paciente.dni='" + dniPaciente + "'";
			PreparedStatement stmt = _conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				result = new Clinico (rs);
			}
			rs.close();
			_conn.close();
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		return result;
	}

	public static Clinico getClinicoPorDni (String dniClinico) {
		Clinico result = null;
		try {
			Connection _conn = inicializarBBDD();
			String sql = "select * from clinico where dni='" + dniClinico + "'";
			PreparedStatement stmt = _conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				result = new Clinico (rs);
			}
			rs.close();
			_conn.close();
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		return result;
	}

	public static Vector<Cuidador_Familiar> getCuidadoresDePaciente (String dniPaciente) {
		Vector<Cuidador_Familiar> result = new Vector<Cuidador_Familiar>();
		try {
			Connection _conn = inicializarBBDD();
			PreparedStatement stmt = _conn.prepareStatement("select cuidador_familiar.* from cuidador_familiar join asiste on cuidador_familiar.dni = asiste.dni_fam where asiste.dni_pac=?");
			stmt.setString(1, dniPaciente);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Cuidador_Familiar cuidador = new Cuidador_Familiar(rs);
				if (cuidador != null) {
					result.add(cuidador);
				}
			}
			rs.close();
			_conn.close();
		}
		catch (Exception e) {
			System.out.println("Error: "+e);			
		}
		return result;
	}

	public static Vector<Paciente> getPacientesDeCuidador (String dniCuidador) {
		Vector<Paciente> result = new Vector<Paciente>();
		try {
			Connection _conn = inicializarBBDD();
			PreparedStatement stmt = _conn.prepareStatement("select paciente.* from asiste "
					+ " JOIN paciente on asiste.dni_pac=paciente.dni "
					+ "where asiste.dni_fam=?");
			stmt.setString(1, dniCuidador);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Paciente paciente = new Paciente(rs);
				if (paciente != null) {
					result.add(paciente);
				}
			}
			rs.close();
			_conn.close();
		}
		catch (Exception e) {
			System.out.println("Error: "+e);			
		}
		return result;
	}

	public static Vector<EntradaCalendario> getCalendarioPaciente(String dniPaciente) {
		Vector<EntradaCalendario> listaEntry = new Vector<EntradaCalendario>();
		try {
			Connection _conn = inicializarBBDD();
			PreparedStatement stmt = _conn.prepareStatement(
					"select * from evento where dni_pac = ?");
			stmt.setString(1, dniPaciente);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				EntradaCalendario entrada = new EntradaCalendario(rs);
				listaEntry.add(entrada);
			}
			rs.close();
			_conn.close();
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		return listaEntry;
	}

	public static String[] getSensoresDePaciente(String _dni) {

		return null;
	}

}
