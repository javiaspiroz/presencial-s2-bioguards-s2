package controlador;

import java.util.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import application.Perfil;
import application.Sistema;
import javafx.application.Platform;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

public class FuncionesAuxiliares {

	public static void getAlertaError(String tittle, String msg) {
		Alert alerta = new Alert(AlertType.ERROR);
		alerta.initModality(Modality.APPLICATION_MODAL);
		alerta.setHeaderText(null);
		alerta.initStyle(StageStyle.DECORATED);
		alerta.setTitle(tittle);
		alerta.setContentText(msg);
		alerta.showAndWait();
	}

	public static void getAlertaInformacion(String tittle, String msg) {
		Alert alerta = new Alert(AlertType.INFORMATION);
		alerta.initModality(Modality.APPLICATION_MODAL);
		alerta.setHeaderText(null);
		alerta.initStyle(StageStyle.DECORATED);
		alerta.setTitle(tittle);
		alerta.setContentText(msg);
		alerta.showAndWait();
	}



	// ------------------ VALIDAR DATOS------------------
	public static String DateToString(java.sql.Date date) {
		String stringTime = new SimpleDateFormat("yyyy/MM/dd").format(date);
		return stringTime;
	}
	
	public static String TimestampToString(Timestamp date) {
		String stringTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);
		return stringTime;
	}


	public static Timestamp StringToTimestamp(String str_date, String mensaje) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date parsedDate = dateFormat.parse(str_date);
			Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
			return timestamp;
		} catch (ParseException e) {
			if (mensaje.equalsIgnoreCase("guardar")) {
				getAlertaError("Error", "Error al guardar la fecha");
			} else if (mensaje.equalsIgnoreCase("eliminar")){
				getAlertaError("Error", "Error al eliminar");
			}
			return null;
		}    
	}

	public static Timestamp convertStringToTimestamp(String strDate) {
		try {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			// you can change format of date
			Date date = formatter.parse(strDate);
			Timestamp timeStampDate = new Timestamp(date.getTime());

			return timeStampDate;
		} catch (ParseException e) {
			System.out.println("Exception :" + e);
			return null;
		}
	}
	
	//Valida que los datos introducidos son numericos
	public static boolean esNumerico(String valor){

		try {
			Integer.parseInt(valor);
			return true;
		} catch (NumberFormatException nfe){
			return false;
		}
	}

	
	//Valida que el DNI es correcto (ÃƒÂºumeros y letra)
	public static boolean validarDNI(String dni){
		Pattern dniPattern = Pattern.compile("[0-9]{7,8}[A-Z]");
		Matcher m = dniPattern.matcher(dni);
		if(m.matches()){
			return true;
		}
		else {
			return false;
		}
	}

	// Comprueba si un valor  es numerico y que tiene una longitud determinada
	public static boolean validarValorNumerico(String valor, int longitud){
		if (valor!= "" && valor.length()==longitud && esNumerico(valor)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean validarEmail(String email){

		// PatrÃ³n para validar el email
		Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" 
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		Matcher mather = pattern.matcher(email);
		if (mather.find() == true) {
			return true;
		} else {
			return false;
		}
	}

	public static String validarPassword (String password) {	
		int length = 0; // Se almacena el numero de caracteres en el password
		int numCount = 0; // Variable usada para almacenar numeros en el password
		int capCount = 0; // Variable usada para almacenar mayusculas en el password
		int capSignos = 0; // Variable  usada para almacenar los signos

		String infoSeguridad = "";
		for (int x = 0; x < password.length(); x++) {
			if ((password.charAt(x) >= 47 && password.charAt(x) <= 58) // numeros
					|| (password.charAt(x) >= 64 && password.charAt(x) <= 91) // mayusculas
					|| (password.charAt(x) >= 63 && password.charAt(x) <= 65) // Arroba
					|| (password.charAt(x) >= 32 && password.charAt(x) <= 44) // signos
					|| (password.charAt(x) >= 97 && password.charAt(x) <= 122)) { // minusculas
			}
			if ((password.charAt(x) > 32 && password.charAt(x) < 44)) { // Cuenta la cantidad signos
				capSignos++;
			}
			if ((password.charAt(x) > 47 && password.charAt(x) < 58)) { // Cuenta la cantidad de numero
				numCount++;
			}
			if ((password.charAt(x) > 64 && password.charAt(x) < 91)) { // Cuenta la cantidad de mayuscula
				capCount++;
			}
			length = (x + 1); // Cuenta la longitud del password
		} 

		if (capSignos < 1 || capCount < 1 || numCount < 1 || length < 8) { // Revisa la longitud minima de 8 caracteres del password

			infoSeguridad = "La contraseña debe tener al menos una mayascula, un numero, "

						  + "caracteres especiales como [! # $ % & ' ( ) + - +] y una longitud minima de 8 caracteres";
		} else {
			infoSeguridad = "Password segura";
		}
		return infoSeguridad;
	}
	public static boolean validarCampovacio (String valor){
		if (!valor.equals("")) {
			return true;
		} else {
			return false;
		}
	}
	

	// ------------------ GUARDAR Y EDITAR DATOS PERFIL ------------------

	public static void mostrarElementosNoEditables(Label label, JFXTextField textField, Button btnOcultar, Button btnMostrar) {
		textField.setVisible(false);
		btnOcultar.setVisible(false);
		label.setVisible(true);
		btnMostrar.setVisible(true);
	}

	public static void mostrarElementosEditables(Label label, JFXTextField textField, Button btnOcultar, Button btnMostrar) {
		label.setVisible(false);
		btnOcultar.setVisible(false);
		textField.setVisible(true);
		btnMostrar.setVisible(true);
	}

	static // BBDD
	Connection conn = null;
	static PreparedStatement stmt = null;
	static ResultSet rs = null;

	public static String rolPerfil(String login) {
		String rol = "";

		try {
			conn = ControladorBBDD.inicializarBBDD();
			stmt = conn.prepareStatement(
					"select usuario.rol from usuario where usuario.dni= ?");
			stmt.setString(1, login);
			rs = stmt.executeQuery();

			while (rs.next()) {
				rol = rs.getString("rol");
			}
			stmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}

		if(rol.equals("administrador")) {
			rol = "admin";
		}
		if(rol.equals("cuidador")) {
			rol = "cuidador_familiar";
		}
		System.out.println(rol);
		return rol;
	}

	// Email
	public static void editarEmail(Perfil perfilLogueado, Label emailPerfil, JFXTextField emailEditable, 
			Button btnOcultar, Button btnMostrar ) {
		emailEditable.setText(perfilLogueado.getEmail());
		mostrarElementosEditables(emailPerfil, emailEditable, btnOcultar, btnMostrar);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				emailEditable.requestFocus();
			}
		});
	}

	public static boolean guardarEmail(Perfil perfilLogueado, Label emailPerfil, JFXTextField emailEditable, 
			Button btnOcultar, Button btnMostrar) {
		boolean valido = false;
		String rol = rolPerfil(perfilLogueado.getDni());
		if (validarEmail(emailEditable.getText())) {

			try {
				conn = ControladorBBDD.inicializarBBDD();
				stmt = conn.prepareStatement("update "+rol+" set email=? where dni= ?");
				stmt.setString(1, emailEditable.getText());			
				stmt.setString(2, perfilLogueado.getDni());
				stmt.executeUpdate();
				stmt.close();
				conn.close();
			}
			catch (Exception e) {
				System.out.println("Error: "+e);			
			}
			perfilLogueado.setEmail(emailEditable.getText());
			emailPerfil.setText(emailEditable.getText());
			valido = true;
		} else {
			getAlertaError("Error", "El email no es valido.");
		}
		mostrarElementosNoEditables(emailPerfil, emailEditable, btnOcultar, btnMostrar);
		return valido;
	}  

	// Telefono
	public static void editarTelefono(Perfil perfilLogueado, Label telefonoPerfil, JFXTextField telefonoEditable, 
			Button btnOcultar, Button btnMostrar ) {
		telefonoEditable.setText(Integer.toString(perfilLogueado.getTelefono()));
		mostrarElementosEditables(telefonoPerfil, telefonoEditable, btnOcultar, btnMostrar);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				telefonoEditable.requestFocus();
			}
		});
	}
 
	public static boolean guardarTelefono(Perfil perfilLogueado, Label telefonoPerfil, JFXTextField telefonoEditable, 
			Button btnOcultar, Button btnMostrar) {
		boolean valido = false;
		String rol = rolPerfil(perfilLogueado.getDni());
		if (validarValorNumerico(telefonoEditable.getText(), 9)) {
			int telefono = Integer.parseInt(telefonoEditable.getText());

			try {
				conn = ControladorBBDD.inicializarBBDD();
				stmt = conn.prepareStatement("update "+rol+" set telefono=? where dni= ?");
				stmt.setInt(1, telefono);			
				stmt.setString(2, perfilLogueado.getDni());
				stmt.executeUpdate();
				stmt.close();
				conn.close();
			}
			catch (Exception e) {
				System.out.println("Error: "+e);			
			}
			perfilLogueado.setTelefono(telefono);
			telefonoPerfil.setText(Integer.toString(perfilLogueado.getTelefono()));
			valido = true;
		} else {
			getAlertaError("Error", "El telefono no es valido.");
		}
		mostrarElementosNoEditables(telefonoPerfil, telefonoEditable, btnOcultar, btnMostrar);
		return valido;
	}    

	// Direccion
	public static void editarDireccion(Perfil perfilLogueado, Label direccionPerfil, JFXTextField direccionEditable, 
			Button btnOcultar, Button btnMostrar ) {
		direccionEditable.setText(perfilLogueado.getDireccion());
		mostrarElementosEditables(direccionPerfil, direccionEditable, btnOcultar, btnMostrar);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				direccionEditable.requestFocus();
			}
		});
	}

	public static boolean guardarDireccion(Perfil perfilLogueado, Label direccionPerfil, JFXTextField direccionEditable, 
			Button btnOcultar, Button btnMostrar) {
		boolean valido = false;
		String rol = rolPerfil(perfilLogueado.getDni());
		if (!direccionEditable.getText().trim().equalsIgnoreCase("")) {

			try {
				conn = ControladorBBDD.inicializarBBDD();
				stmt = conn.prepareStatement("update "+rol+" set direccion=? where dni= ?");
				stmt.setString(1,direccionEditable.getText());			
				stmt.setString(2, perfilLogueado.getDni());
				stmt.executeUpdate();
				stmt.close();
				conn.close();
			}
			catch (Exception e) {
				System.out.println("Error: "+e);			
			}

			perfilLogueado.setDireccion(direccionEditable.getText());
			direccionPerfil.setText(direccionEditable.getText());
			valido = true;
		} else {
			getAlertaError("Error", "La direccion no es valida.");
		}
		mostrarElementosNoEditables(direccionPerfil, direccionEditable, btnOcultar, btnMostrar);
		return valido;
		}

	// Ciudad
	public static void editarCiudad(Perfil perfilLogueado, Label ciudadPerfil, JFXTextField ciudadEditable, 
			Button btnOcultar, Button btnMostrar ) {
		ciudadEditable.setText(perfilLogueado.getCiudad());
		mostrarElementosEditables(ciudadPerfil, ciudadEditable, btnOcultar, btnMostrar);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				ciudadEditable.requestFocus();
			}
		});
	}

	public static boolean guardarCiudad(Perfil perfilLogueado, Label ciudadPerfil, JFXTextField ciudadEditable, 
			Button btnOcultar, Button btnMostrar) {
		boolean valido = false;
		String rol = rolPerfil(perfilLogueado.getDni());
		if (!ciudadEditable.getText().trim().equals("")) {

			try {
				conn = ControladorBBDD.inicializarBBDD();
				stmt = conn.prepareStatement("update "+rol+" set ciudad=? where dni= ?");
				stmt.setString(1, ciudadEditable.getText());			
				stmt.setString(2, perfilLogueado.getDni());
				stmt.executeUpdate();
				stmt.close();
				conn.close();
			}
			catch (Exception e) {
				System.out.println("Error: "+e);			
			}


			perfilLogueado.setCiudad(ciudadEditable.getText());
			ciudadPerfil.setText(ciudadEditable.getText());
			valido = true;
		} else {
			getAlertaError("Error", "La ciudad no es valida.");
		}
		mostrarElementosNoEditables(ciudadPerfil, ciudadEditable, btnOcultar, btnMostrar);
		return valido;
	}

	// Codigo Postal
	public static void editarCodigoPostal(Perfil perfilLogueado, Label codigoPostalPerfil, JFXTextField codigoPostalEditable, 
			Button btnOcultar, Button btnMostrar ) {
		codigoPostalEditable.setText(Integer.toString(perfilLogueado.getCodigoPostal()));
		mostrarElementosEditables(codigoPostalPerfil, codigoPostalEditable, btnOcultar, btnMostrar);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				codigoPostalEditable.requestFocus();
			}
		});
	}

	public static boolean guardarCodigoPostal(Perfil perfilLogueado, Label codigoPostalPerfil, JFXTextField codigoPostalEditable, 
			Button btnOcultar, Button btnMostrar) {
		boolean valido = false;
		String rol = rolPerfil(perfilLogueado.getDni());
		if (validarValorNumerico(codigoPostalEditable.getText(), 5)) {
			int codigoPostal = Integer.parseInt(codigoPostalEditable.getText());

			try {
				conn = ControladorBBDD.inicializarBBDD();
				stmt = conn.prepareStatement("update "+rol+" set codPostal=? where dni= ?");
				stmt.setInt(1, codigoPostal);			
				stmt.setString(2, perfilLogueado.getDni());
				stmt.executeUpdate();
				stmt.close();
				conn.close();
			}
			catch (Exception e) {
				System.out.println("Error: "+e);			
			}
			perfilLogueado.setTelefono(codigoPostal);
			codigoPostalPerfil.setText(Integer.toString(perfilLogueado.getTelefono()));
			valido = true;

		} else {
			getAlertaError("Error", "El código postal no es valido, debe contener 5 digitos.");
		}
		mostrarElementosNoEditables(codigoPostalPerfil, codigoPostalEditable, btnOcultar, btnMostrar);
		return valido;
	}

	// Provincia
	public static void editarProvincia(Perfil perfilLogueado, Label provinciaPerfil, JFXTextField provinciaEditable, 
			Button btnOcultar, Button btnMostrar ) {
		provinciaEditable.setText(perfilLogueado.getProvincia());
		mostrarElementosEditables(provinciaPerfil, provinciaEditable, btnOcultar, btnMostrar);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				provinciaEditable.requestFocus();
			}
		});
	}

	public static boolean guardarProvincia(Perfil perfilLogueado, Label provinciaPerfil, JFXTextField provinciaEditable, 
			Button btnOcultar, Button btnMostrar) {
		boolean valido = false;
		String rol = rolPerfil(perfilLogueado.getDni());
		if (!provinciaEditable.getText().trim().equals("")) {

			try {
				conn = ControladorBBDD.inicializarBBDD();
				stmt = conn.prepareStatement("update "+rol+" set provincia=? where dni= ?");
				stmt.setString(1, provinciaEditable.getText());			
				stmt.setString(2, perfilLogueado.getDni());
				stmt.executeUpdate();
				stmt.close();
				conn.close();
			}
			catch (Exception e) {
				System.out.println("Error: "+e);			
			}


			perfilLogueado.setProvincia(provinciaEditable.getText());
			provinciaPerfil.setText(provinciaEditable.getText());
			valido = true;
		} else {
			getAlertaError("Error", "La provincia no es valida.");
		}
		mostrarElementosNoEditables(provinciaPerfil, provinciaEditable, btnOcultar, btnMostrar);
		return valido;
	}

	// Contrasena
	public static void editarContrasena(JFXPasswordField contrasena, Button btnOcultar, Button btnMostrar) {
		contrasena.setEditable(true);
		btnMostrar.setVisible(true);
		btnOcultar.setVisible(false);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				contrasena.requestFocus();
			}
		});
	}   	
	public static void editarContrasena(String contrasenaNueva) {
		
		String dni = Sistema.getUnico().getUsuarioLogueado().getUsuario();
		
		
		try {
			conn = ControladorBBDD.inicializarBBDD();
			String busqueda = "update usuario set password=? WHERE dni=?";
			stmt = conn.prepareStatement(busqueda);
			stmt.setString(1, contrasenaNueva);
			stmt.setString(2, dni);
			stmt.executeUpdate();
			stmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		Sistema.getUnico().getUsuarioLogueado().setContrasena(contrasenaNueva);
		getAlertaInformacion("Guardado", "Dato actualizado correctamente.");
    }  
	public static void guardarContrasena(JFXPasswordField contrasena, Button btnOcultar, Button btnMostrar) {
		String dni = Sistema.getUnico().getUsuarioLogueado().getUsuario();
	   	String informacion = validarPassword(contrasena.getText());
    	if (informacion.equalsIgnoreCase("Password segura")) {  
    		try {
				conn = ControladorBBDD.inicializarBBDD();
				stmt = conn.prepareStatement("update usuario set password=? where dni= ?");
				stmt.setString(1, contrasena.getText());			
				stmt.setString(2, dni);
				stmt.executeUpdate();
				stmt.close();
				conn.close();
			}
			catch (Exception e) {
				System.out.println("Error: "+e);			
			}
    		Sistema.getUnico().getUsuarioLogueado().setContrasena(contrasena.getText());
    		
    	} else {
			FuncionesAuxiliares.getAlertaError("Error", informacion);
			contrasena.setText(Sistema.getUnico().getUsuarioLogueado().getContrasena());
			System.out.print("hola");
		}
    	contrasena.setEditable(false);
    	btnOcultar.setVisible(false);
    	btnMostrar.setVisible(true);
    }



	// ------------------ MOSTRAR SENSORES ------------------
	public static void mostrarSensores(String dni, LineChart<String, Double> graficalineal, String fecha, String tipo, String nombreSensor) {
		if (fecha!=null) {
			XYChart.Series<String, Double> series = new XYChart.Series<String, Double>();

			// BBDD
			Connection conn = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;

			try {
				conn = ControladorBBDD.inicializarBBDD();
				String busqueda = "select medida.valor, date(medida.fecha), time(medida.fecha) from medida join sensor on sensor.id=medida.id_sensor\n "
						+ "where sensor.dni_pac= ? AND sensor.tipo= ? and date(medida.fecha)=?";
				stmt = conn.prepareStatement(busqueda);
				stmt.setString(1, dni);
				stmt.setString(2, tipo);
				stmt.setString(3, fecha);
				rs = stmt.executeQuery();

				//datos auxiliares
				double valor = 0;
				String hora = "";
				while (rs.next()) {
					hora = rs.getString("time(medida.fecha)");
					valor = rs.getDouble("valor");
					series.getData().add(new XYChart.Data<String, Double>(hora, valor));					
				}
				stmt.close();
				conn.close();

			} catch (Exception e) {
				System.out.println("Error: " + e);
			}


			if(series.getData().size()>0) {
				graficalineal.getData().clear();
				series.setName(nombreSensor);
				graficalineal.getData().add(series);
			}else {
				getAlertaError("Error", "No hay datos de " + nombreSensor +" en esta fecha");
			}
		} else {
			getAlertaError("Error", "No hay ninguna fecha seleccionada");
		}
	}

	public static void mostrarSensoresFecha(String dni, LineChart<String, Double> graficalineal, String fecha, String Tipo, String Nombre) {

		XYChart.Series<String, Double> series = new XYChart.Series<String, Double>();

		// BBDD
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = ControladorBBDD.inicializarBBDD();
			String busqueda = "select medida.valor, date(medida.fecha), time(medida.fecha) from medida join sensor on sensor.id=medida.id_sensor\n "
					+ "where sensor.dni_pac= ? AND sensor.tipo= ? and date(medida.fecha)=?";
			stmt = conn.prepareStatement(busqueda);
			stmt.setString(1, dni);
			stmt.setString(2, Tipo);
			stmt.setString(3, fecha);
			rs = stmt.executeQuery();

			//datos auxiliares
			double valor = 0;
			String hora = "";
			while (rs.next()) {
				hora = rs.getString("time(medida.fecha)");
				valor = rs.getDouble("valor");
				series.getData().add(new XYChart.Data<String, Double>(hora, valor));					
			}
			stmt.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		if(series.getData().size()<=0) {
			getAlertaError("Error", "No hay datos de "+Nombre+" en esta fecha");
		}else {
			series.setName(Nombre);
			graficalineal.getData().add(series);
		}
	}
}
