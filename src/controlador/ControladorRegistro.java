package controlador;

import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import application.Administrador;
import application.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class ControladorRegistro {

	@FXML
	private ResourceBundle resources;
	@FXML
	private URL location;
	@FXML
	private AnchorPane RegistroPane;

	@FXML
	private Button btnCancelar;
	@FXML
	private Button btnContinuar;
	@FXML
	private Button btnAceptar;

	@FXML
	private GridPane gridPane1;
	@FXML
	private JFXTextField miNombre;
	@FXML
	private JFXTextField miApellidos;
	@FXML
	private JFXTextField miDni;
	@FXML
	private JFXDatePicker miFechaDeNacimiento;
	@FXML
	private JFXPasswordField miContrasena;
	@FXML
	private JFXPasswordField miContrasenaCorrecta;
	@FXML
	private ToggleGroup tipoAdmin;
	@FXML
	private RadioButton tipoEmpresa;
	@FXML
	private RadioButton tipoParticular;

	@FXML
	private GridPane gridPane2;
	@FXML
	private JFXTextField miEmail;
	@FXML
	private JFXTextField miTelefono;
	@FXML
	private JFXTextField miDireccion;
	@FXML
	private JFXTextField miCiudad;
	@FXML
	private JFXTextField miProvincia;
	@FXML
	private JFXTextField miCodigoPostal;


	// Panel 1
	String nombre= null;
	String apellidos= null;
	String dni= null;
	//Timestamp fechaNacimiento = null;
	java.sql.Date fechaNacimiento = null;
	String contrasena= null;
	String contrasenaCorrecta= null;
	int tipo =0;
	// Panel 2
	String email = null;
	int telefono = 0;	
	String direccion = null;	
	String ciudad = null;
	String provincia = null;
	int codigoPostal = 0;	


	@FXML
	void initialize() {
		gridPane1.setVisible(true);
		gridPane2.setVisible(false);
		btnContinuar.setVisible(true);
		btnAceptar.setVisible(false);
	}

	@FXML
	void continuarRegistro(ActionEvent event) {	
		//Recoge los datos del panel 1 verificando que son correctos
		nombre = miNombre.getText();
		apellidos = miApellidos.getText();
		dni = miDni.getText();
		if (miFechaDeNacimiento.getValue()!=null) {
			fechaNacimiento = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		}
		contrasena = miContrasena.getText();
		contrasenaCorrecta = miContrasenaCorrecta.getText();
		tipo =0;
		if (tipoEmpresa.isSelected()) {
			tipo = 1;
		} else if (tipoParticular.isSelected()) {
			tipo = 2;
		}

		if(verificarDatosGridPane1(nombre, apellidos, contrasena, dni, fechaNacimiento, contrasenaCorrecta, tipo)) {
			gridPane1.setVisible(false);
			gridPane2.setVisible(true);
			btnContinuar.setVisible(false);
			btnAceptar.setVisible(true);
		}
	}

	@FXML
	void registrar(ActionEvent event) {
		// Recoge los datos del panel 1 verificando que son correctos
		email = miEmail.getText();
		if (miTelefono.getText() != null && FuncionesAuxiliares.esNumerico(miTelefono.getText()) ) {
			telefono = Integer.parseInt(miTelefono.getText());
		}	
		direccion = miDireccion.getText();	
		ciudad = miCiudad.getText();
		provincia = miProvincia.getText();
		if (miCodigoPostal.getText() != null && FuncionesAuxiliares.esNumerico(miCodigoPostal.getText()) ) {
			codigoPostal = Integer.parseInt(miCodigoPostal.getText());
		}	

		if (verificarDatosGridPane2(email, telefono, direccion,  ciudad, provincia,  codigoPostal)) {
			Usuario usuario = new Usuario (dni, contrasena, "administrador");
			Administrador administrador = new Administrador (nombre, apellidos, dni, email, fechaNacimiento, direccion, 
					ciudad, provincia, codigoPostal, telefono, tipo);
			usuario.addDB();
			administrador.addDB();
			ControladorMostrarVentana.mostrarLogin();
		}
	}
	

	@FXML
	void volverLogin(ActionEvent event) {
		ControladorMostrarVentana.mostrarLogin();
	}   


	private boolean verificarDatosGridPane1(String nombre, String apellidos, String contrasena ,String dni, 
			java.sql.Date fechaNacimiento , String contrasenaCorrecta, int tipo ) {	
		if (!nombre.equalsIgnoreCase("") && !apellidos.equalsIgnoreCase("") && !dni.equalsIgnoreCase("") && 
				FuncionesAuxiliares.validarDNI(dni) && fechaNacimiento!=null && !contrasena.equalsIgnoreCase("") && 
				contrasena.equalsIgnoreCase(contrasenaCorrecta) && FuncionesAuxiliares.validarPassword(contrasena).equalsIgnoreCase("Password segura") &&
				((tipoEmpresa.isSelected()&&!tipoParticular.isSelected()) || (!tipoEmpresa.isSelected()&&tipoParticular.isSelected()))) {
			return true;	
		}

		else {
			if(nombre.equalsIgnoreCase("")) { //Verifica que se introduce un texto 
				FuncionesAuxiliares.getAlertaError("Error", "El nombre no es válido.");
			} else if(apellidos.equalsIgnoreCase("")) { //Verifica que se introduce un texto 
				FuncionesAuxiliares.getAlertaError("Error", "Los apellidos no son válidos.");
			} else if(dni.equalsIgnoreCase("") || !FuncionesAuxiliares.validarDNI(dni)) { 
				FuncionesAuxiliares.getAlertaError("Error", "El DNI no es válido.");		
			} else if(fechaNacimiento==null) { //Verifica que se introduce un texto 
				FuncionesAuxiliares.getAlertaError("Error", "La fecha de nacimiento no es válida");
			} else if(!FuncionesAuxiliares.validarPassword(contrasena).equalsIgnoreCase("Password segura")) { //Verifica que la contraseña es segura
				String infoSeguridad = FuncionesAuxiliares.validarPassword(contrasena);
				FuncionesAuxiliares.getAlertaInformacion("Seguridad", infoSeguridad);
			} else if(!contrasena.equalsIgnoreCase(contrasenaCorrecta)) {
				FuncionesAuxiliares.getAlertaError("Error", "Las contraseñas no coinciden.");
			} else if (!tipoEmpresa.isSelected()&& !tipoParticular.isSelected()){ //Verifica que una de las opciones ha sido señalada
				FuncionesAuxiliares.getAlertaError("Error", "Debe seleccionar una opciÃ³n");
			}
			return false;
		}
	}


	private boolean verificarDatosGridPane2(String email, int telefono, String direccion, String ciudad, 
			String provincia, int codigoPostal) {

		if (FuncionesAuxiliares.validarEmail(email) && FuncionesAuxiliares.validarValorNumerico(miTelefono.getText(), 9) &&
				!direccion.equalsIgnoreCase("") && !ciudad.equalsIgnoreCase("") && !provincia.equalsIgnoreCase("") && 
				!email.equalsIgnoreCase("")	&& FuncionesAuxiliares.validarValorNumerico(miCodigoPostal.getText(), 5)) {
			return true;			
		} else {
			if(!FuncionesAuxiliares.validarEmail(email)) { //Comprueba el formato del email
				FuncionesAuxiliares.getAlertaError("Error", "El email no es valido");
			} else if (!FuncionesAuxiliares.validarValorNumerico(miTelefono.getText(), 9)) { //Comprueba que el número introducido tiene 9 numeros
				FuncionesAuxiliares.getAlertaError("Error", "El telefono no es valido");
			} else if(direccion.equalsIgnoreCase("")) { //Verifica que se introduce un texto 
				FuncionesAuxiliares.getAlertaError("Error", "La direccion no es valida");
			} else if(ciudad.equalsIgnoreCase("")) {//Verifica que se introduce un texto 
				FuncionesAuxiliares.getAlertaError("Error", "La ciudad no es valida");
			} else if(provincia.equalsIgnoreCase("")) {//Verifica que se introduce un texto 
				FuncionesAuxiliares.getAlertaError("Error", "La provincia no es valida");
			} else if (!FuncionesAuxiliares.validarValorNumerico(miCodigoPostal.getText(), 5)) { //Comprueba que el número introducido tiene 9 numeros
				FuncionesAuxiliares.getAlertaError("Error", "El codigo postal no es valido");
			}
			return false;
		}
	}
}