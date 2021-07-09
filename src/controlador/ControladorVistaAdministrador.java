package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import application.Administrador;
import application.Sistema;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;


public class ControladorVistaAdministrador {	
	
	@FXML
    private AnchorPane AdministradorPane;
    @FXML
    private Button btnAdminCerrarSesion;
    @FXML
    private Label identidad;

// ----------- PERFIL -----------
    @FXML
    private Button btnPerfil;
    @FXML
    private BorderPane vistaPerfil;
    
  // DATOS DE PERFIL
    @FXML
    private Button btnPerfilGeneral;
    @FXML
    private GridPane gridPanePerfilGeneral;
  
    @FXML
    private Label nombrePerfil;
    @FXML
    private Label apellidosPerfil;
    @FXML
    private Label dniPerfil;
    @FXML
    private Label fechaNacimientoPerfil;
    
    @FXML
    private Label emailPerfil; 
    @FXML
    private JFXTextField emailEditable;
    @FXML
    private Button btnEditarEmail;  
    @FXML
    private Button btnGuardarEmail;

    @FXML
    private Label telefonoPerfil;
    @FXML
    private JFXTextField telefonoEditable;
    @FXML
    private Button btnEditarTelefono; 
    @FXML
    private Button btnGuardarTelefono;

    @FXML
    private Label direccionPerfil;
    @FXML
    private JFXTextField direccionEditable;
    @FXML
    private Button btnEditarDireccion;
    @FXML
    private Button btnGuardarDireccion;
    
    @FXML
    private Label ciudadPerfil;
    @FXML
    private JFXTextField ciudadEditable;
    @FXML
    private Button btnEditarCiudad;
    @FXML
    private Button btnGuardarCiudad;
    
    @FXML
    private Label codigoPostalPerfil;
    @FXML
    private JFXTextField codigoPostalEditable;
    @FXML
    private Button btnEditarCodigoPostal;
    @FXML
    private Button btnGuardarCodigoPostal;

    @FXML
    private Label provinciaPerfil;
    @FXML
    private JFXTextField provinciaEditable;
    @FXML
    private Button btnEditarProvincia;
    @FXML
    private Button btnGuardarProvincia;
    
  // DATOS PERFIL DE SEGURIDAD
    @FXML
    private Button btnPerfilSeguridad;
    @FXML
    private GridPane gridPaneSeguridad;

    @FXML
    private JFXTextField usuario;
    
    @FXML
    private JFXPasswordField contrasena;
    @FXML
    private Button btnEditarContrasena;  
    @FXML
    private Button btnGuardarContrasena;


// ----------- CLINICO -----------
    @FXML
    private Button btnClinico;
    @FXML
    private BorderPane vistaClinico;

// ----------- PACIENTE -----------
    @FXML
    private Button btnPaciente;
    @FXML
    private BorderPane vistaPaciente;

// ----------- FAMILIAR -----------
    @FXML
    private Button btnFamiliar;
    @FXML
    private BorderPane vistaFamiliar;
    

    private Administrador adminLogueado;
    

    @FXML
    void initialize() {
    	buscarAdministradorLogueado();
    	setDatosPerfil();
    	identidad.setText(adminLogueado.getNombre() + " " + adminLogueado.getApellidos() + "  ");
    	gridPaneSeguridad.setVisible(false);
	}
   
// ------------------ PANEL PERFIL ------------------
	@FXML
    void mostrarPerfil(ActionEvent event) {
		vistaPerfil.setVisible(true);
		vistaFamiliar.setVisible(false);
		vistaClinico.setVisible(false);
		vistaPaciente.setVisible(false);
		gridPaneSeguridad.setVisible(false);
		gridPanePerfilGeneral.setVisible(true);
    }
	
	// Perfil general
	 @FXML
    void mostrarPerfilGeneral(ActionEvent event) {
		 gridPaneSeguridad.setVisible(false);
		 gridPanePerfilGeneral.setVisible(true);
    }
	 	
    @FXML
    void editarEmail(ActionEvent event) {
    	FuncionesAuxiliares.editarEmail(adminLogueado, emailPerfil, emailEditable, btnEditarEmail, btnGuardarEmail);
    }	
    @FXML
    void guardarEmail(ActionEvent event) {
    	guardarDatosAdmin(FuncionesAuxiliares.guardarEmail(adminLogueado, emailPerfil, emailEditable, btnGuardarEmail, btnEditarEmail));
    } 
    
    @FXML
    void editarTelefono(ActionEvent event) {
    	FuncionesAuxiliares.editarTelefono(adminLogueado, telefonoPerfil, telefonoEditable, btnEditarTelefono, btnGuardarTelefono);
    }        
	@FXML
    void guardarTelefono(ActionEvent event) {
		guardarDatosAdmin(FuncionesAuxiliares.guardarTelefono(adminLogueado, telefonoPerfil, telefonoEditable, btnGuardarTelefono, btnEditarTelefono));
    }

    @FXML
    void editarDireccion(ActionEvent event) {
    	FuncionesAuxiliares.editarDireccion(adminLogueado, direccionPerfil, direccionEditable, btnEditarDireccion, btnGuardarDireccion);
    }
    @FXML
    void guardarDireccion(ActionEvent event) {
    	guardarDatosAdmin(FuncionesAuxiliares.guardarDireccion(adminLogueado, direccionPerfil, direccionEditable, btnGuardarDireccion, btnEditarDireccion));
    }

    @FXML
    void editarCiudad(ActionEvent event) {
    	FuncionesAuxiliares.editarCiudad(adminLogueado, ciudadPerfil, ciudadEditable, btnEditarCiudad, btnGuardarCiudad);
    }   
    @FXML
    void guardarCiudad(ActionEvent event) {
    	guardarDatosAdmin(FuncionesAuxiliares.guardarCiudad(adminLogueado, ciudadPerfil, ciudadEditable, btnGuardarCiudad, btnEditarCiudad));
    }
    
    @FXML
    void editarCodigoPostal(ActionEvent event) {
    	FuncionesAuxiliares.editarCodigoPostal(adminLogueado, codigoPostalPerfil, codigoPostalEditable, btnEditarCodigoPostal, btnGuardarCodigoPostal);
    } 
    @FXML
    void guardarCodigoPostal(ActionEvent event) {
    	guardarDatosAdmin(FuncionesAuxiliares.guardarCodigoPostal(adminLogueado, codigoPostalPerfil, codigoPostalEditable, btnGuardarCodigoPostal, btnEditarCodigoPostal));
    }
    
    @FXML
    void editarProvincia(ActionEvent event) {
    	FuncionesAuxiliares.editarProvincia(adminLogueado, provinciaPerfil, provinciaEditable, btnEditarProvincia, btnGuardarProvincia);
    }
    @FXML
    void guardarProvincia(ActionEvent event) {
    	guardarDatosAdmin(FuncionesAuxiliares.guardarProvincia(adminLogueado, provinciaPerfil, provinciaEditable, btnGuardarProvincia, btnEditarProvincia));
    }
    
    // Perfil Seguridad
    @FXML
    void mostrarPerfilSeguridad(ActionEvent event) {
    	gridPanePerfilGeneral.setVisible(false);
    	gridPaneSeguridad.setVisible(true);	 
    }
    
    @FXML
    void editarContrasena(ActionEvent event) {
    	FuncionesAuxiliares.editarContrasena(contrasena, btnEditarContrasena, btnGuardarContrasena);
    }   
    @FXML
    void guardarContrasena(ActionEvent event) {
    	FuncionesAuxiliares.guardarContrasena(contrasena, btnGuardarContrasena, btnEditarContrasena);
    }
    
// ------------------ PANEL CLINICOS ------------------
	@FXML
    void mostrarClincos(ActionEvent event) {
		vistaPerfil.setVisible(false);
		vistaFamiliar.setVisible(false);
		vistaClinico.setVisible(true);
		vistaPaciente.setVisible(false);
    }
	
// ------------------ PANEL PACIENTES ------------------
	@FXML
    void mostrarPacientes(ActionEvent event) {
		vistaPerfil.setVisible(false);
		vistaFamiliar.setVisible(false);
		vistaClinico.setVisible(false);
		vistaPaciente.setVisible(true);		
    }
	
// ------------------ PANEL FAMILIARES ------------------
	@FXML
    void mostrarFamiliares(ActionEvent event) {
		vistaPerfil.setVisible(false);
		vistaFamiliar.setVisible(true);
		vistaClinico.setVisible(false);
		vistaPaciente.setVisible(false);		
    }
	
    @FXML
	void cerrarSesionAdmin(ActionEvent event) {
		Sistema.getUnico().logoutUsuario();
		ControladorMostrarVentana.mostrarLogin();
	}

    
    
    
 // ------------------ Funciones auxiliares ------------------
	private void buscarAdministradorLogueado() {
		try {
			Connection conn = ControladorBBDD.inicializarBBDD();
			PreparedStatement stmt;
			String busqueda = "SELECT * FROM admin WHERE admin.dni=?";
			stmt = conn.prepareStatement(busqueda);
			stmt.setString(1, Sistema.getUnico().getUsuarioLogueado().getUsuario());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				adminLogueado = new Administrador(rs);
			}
			stmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
	}
	
    private void setDatosPerfil() {    	
    	nombrePerfil.setText(adminLogueado.getNombre());
		apellidosPerfil.setText(adminLogueado.getApellidos());
		dniPerfil.setText(adminLogueado.getDni());
		fechaNacimientoPerfil.setText(FuncionesAuxiliares.DateToString(adminLogueado.getFechaNacimiento()));
    	
		emailPerfil.setText(adminLogueado.getEmail());
		FuncionesAuxiliares.mostrarElementosNoEditables(emailPerfil, emailEditable, btnGuardarEmail, btnEditarEmail);
		
		telefonoPerfil.setText(Integer.toString(adminLogueado.getTelefono()));
		FuncionesAuxiliares.mostrarElementosNoEditables(telefonoPerfil, telefonoEditable, btnGuardarTelefono, btnEditarTelefono);

		direccionPerfil.setText(adminLogueado.getDireccion());
		FuncionesAuxiliares.mostrarElementosNoEditables(direccionPerfil, direccionEditable, btnGuardarDireccion, btnEditarDireccion);

		ciudadPerfil.setText(adminLogueado.getCiudad());
		FuncionesAuxiliares.mostrarElementosNoEditables(ciudadPerfil, ciudadEditable, btnGuardarCiudad, btnEditarCiudad);

		codigoPostalPerfil.setText(Integer.toString(adminLogueado.getCodigoPostal()));
		FuncionesAuxiliares.mostrarElementosNoEditables(codigoPostalPerfil, codigoPostalEditable, btnGuardarCodigoPostal, btnEditarCodigoPostal);

		provinciaPerfil.setText(adminLogueado.getProvincia());
		FuncionesAuxiliares.mostrarElementosNoEditables(provinciaPerfil, provinciaEditable, btnGuardarProvincia, btnEditarProvincia);
		
		usuario.setText(Sistema.getUnico().getUsuarioLogueado().getUsuario());
		usuario.setEditable(false);
		
		contrasena.setText(Sistema.getUnico().getUsuarioLogueado().getContrasena());
		contrasena.setEditable(false);
		btnGuardarContrasena.setVisible(false);
		btnEditarContrasena.setVisible(true);
    }
    
    private void guardarDatosAdmin(boolean valido) {
    	if (valido) {
    		FuncionesAuxiliares.getAlertaInformacion("Guardado", "Dato actualizado correctamente.");
    	} 
    }
}