package controlador;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Vector;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import application.MensajeChat;
import application.Paciente;
import application.Sistema;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;


public class ControladorVistaPaciente {	
	
	@FXML
    private AnchorPane PacientePane;
    @FXML
    private Button btnPacienteCerrarSesion;
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
    private Label clinicoPerfil;    
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

    
// ----------- SENSORES -----------
    @FXML
    private Button btnSensores;
    @FXML
    private BorderPane vistaSensores;
 
    @FXML
    private LineChart<String, Double> graficalineal;
    @FXML
    private CategoryAxis xAxis;

    @FXML
    private Button btnSensorTemperatura;
    @FXML
    private Button btnSensorPresion;
    @FXML
    private Button btnSensorPulsioximetro;

    @FXML
    private JFXDatePicker datePickerSensores;
    
// ----------- CALENDARIO -----------
    @FXML
    private Button btnCalendario;
    @FXML
    private AnchorPane vistaCalendario;
   
 // ----------- CONTACTAR -----------
    @FXML
    private Button btnContactar;
    @FXML
    private BorderPane vistaContactar;
   
    @FXML
    private ScrollPane scrollChat;
    @FXML
    private VBox VBoxChat;
    @FXML
    private TextField newMsg;
    @FXML
    private Button sendMsg;
    @FXML
    private Label lblNombreClinicoChat;
    
    private Paciente pacienteLogueado;
    private String fechaSensores= DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
    

    
    @FXML
    void initialize() {
    	buscarPacienteLogueado();
    	setDatosPerfil();
    	identidad.setText(pacienteLogueado.getNombre() + " " + pacienteLogueado.getApellidos() + "  ");
    	vistaPerfil.setVisible(false);
    	vistaSensores.setVisible(true);
    	vistaCalendario.setVisible(false);
    	vistaContactar.setVisible(false);
    	LocalDate fechaDatePicker = LocalDate.now();
    	datePickerSensores.setValue(fechaDatePicker);
	}
   
// ------------------ PANEL PERFIL ------------------
	@FXML
    void mostrarPerfil(ActionEvent event) {
		vistaPerfil.setVisible(true);
    	vistaSensores.setVisible(false);
    	vistaCalendario.setVisible(false);
    	vistaContactar.setVisible(false);
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
    	FuncionesAuxiliares.editarEmail(pacienteLogueado, emailPerfil, emailEditable, btnEditarEmail, btnGuardarEmail);
    }	
    @FXML
    void guardarEmail(ActionEvent event) {
		guardarDatosPaciente(FuncionesAuxiliares.guardarEmail(pacienteLogueado, emailPerfil, emailEditable, btnGuardarEmail, btnEditarEmail));
    } 
    
    @FXML
    void editarTelefono(ActionEvent event) {
    	FuncionesAuxiliares.editarTelefono(pacienteLogueado, telefonoPerfil, telefonoEditable, btnEditarTelefono, btnGuardarTelefono);
    }        
	@FXML
    void guardarTelefono(ActionEvent event) {
		guardarDatosPaciente(FuncionesAuxiliares.guardarTelefono(pacienteLogueado, telefonoPerfil, telefonoEditable, btnGuardarTelefono, btnEditarTelefono));
    }

    @FXML
    void editarDireccion(ActionEvent event) {
    	FuncionesAuxiliares.editarDireccion(pacienteLogueado, direccionPerfil, direccionEditable, btnEditarDireccion, btnGuardarDireccion);
    }
    @FXML
    void guardarDireccion(ActionEvent event) {
    	guardarDatosPaciente(FuncionesAuxiliares.guardarDireccion(pacienteLogueado, direccionPerfil, direccionEditable, btnGuardarDireccion, btnEditarDireccion));
    }

    @FXML
    void editarCiudad(ActionEvent event) {
    	FuncionesAuxiliares.editarCiudad(pacienteLogueado, ciudadPerfil, ciudadEditable, btnEditarCiudad, btnGuardarCiudad);
    }   
    @FXML
    void guardarCiudad(ActionEvent event) {
    	guardarDatosPaciente(FuncionesAuxiliares.guardarCiudad(pacienteLogueado, ciudadPerfil, ciudadEditable, btnGuardarCiudad, btnEditarCiudad));
    }

    @FXML
    void editarCodigoPostal(ActionEvent event) {
    	FuncionesAuxiliares.editarCodigoPostal(pacienteLogueado, codigoPostalPerfil, codigoPostalEditable, btnEditarCodigoPostal, btnGuardarCodigoPostal);
    } 
    @FXML
    void guardarCodigoPostal(ActionEvent event) {
    	guardarDatosPaciente(FuncionesAuxiliares.guardarCodigoPostal(pacienteLogueado, codigoPostalPerfil, codigoPostalEditable, btnGuardarCodigoPostal, btnEditarCodigoPostal));
    }

    @FXML
    void editarProvincia(ActionEvent event) {
    	FuncionesAuxiliares.editarProvincia(pacienteLogueado, provinciaPerfil, provinciaEditable, btnEditarProvincia, btnGuardarProvincia);
    }
    @FXML
    void guardarProvincia(ActionEvent event) {
    	guardarDatosPaciente(FuncionesAuxiliares.guardarProvincia(pacienteLogueado, provinciaPerfil, provinciaEditable, btnGuardarProvincia, btnEditarProvincia));
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
    
    
// ------------------ PANEL SENSORES ------------------
    @FXML
    void mostrarSensores(ActionEvent event) {
		vistaPerfil.setVisible(false);
    	vistaSensores.setVisible(true);
    	vistaCalendario.setVisible(false);
    	
    	LocalDate fechaDatePicker = LocalDate.now();
    	datePickerSensores.setValue(fechaDatePicker);
    	graficalineal.getData().clear();
		xAxis.setLabel("Horas");
		String [] Tipo = {"Sensor 1", "Sensor 2", "Sensor 3"};
		String [] Nombre = {"Temperatura", "Presion", "Pulsioximetro"};
		for (int i = 0; i < Nombre.length; i++) {
			FuncionesAuxiliares.mostrarSensoresFecha(pacienteLogueado.getDni(),graficalineal, fechaSensores, Tipo[i], Nombre[i]);
		}
    }
	
	@FXML
    void mostrarSensorTemperatura(ActionEvent event) {
		 FuncionesAuxiliares.mostrarSensores(pacienteLogueado.getDni(),graficalineal, fechaSensores, "Sensor 1", "temperatura");
    }

    @FXML
    void mostrarSensorPresion(ActionEvent event) {
    	FuncionesAuxiliares.mostrarSensores(pacienteLogueado.getDni(), graficalineal, fechaSensores, "Sensor 2", "presion");
    }

    @FXML
    void mostrarSensorPulsioximetro(ActionEvent event) {
    	FuncionesAuxiliares.mostrarSensores(pacienteLogueado.getDni(),graficalineal, fechaSensores, "Sensor 3", "pulsioximetro");
    }
    
    @FXML
    void seleccionarFecha(ActionEvent event) {
    	LocalDate ld = datePickerSensores.getValue();
    	fechaSensores = ld.toString();
    	graficalineal.getData().clear();
		xAxis.setLabel("Horas");
		String [] Tipo = {"Sensor 1", "Sensor 2", "Sensor 3"};
		String [] Nombre = {"Temperatura", "Presion", "Pulsioximetro"};
		for (int i = 0; i < Nombre.length; i++) {
			FuncionesAuxiliares.mostrarSensoresFecha(pacienteLogueado.getDni(),graficalineal, fechaSensores, Tipo[i], Nombre[i]);
		}
    }
	
	
// ------------------ PANEL CALENDARIO ------------------
	@FXML
    void mostrarCalendario(ActionEvent event) {
		// Se crea y se muestra el calendario
		ManejadorCalendario manejadorCalendario = new ManejadorCalendario();
    	Control cal = manejadorCalendario.CrearCalendario(pacienteLogueado.getDni(), true);
    	AnchorPane.setTopAnchor(cal, 0.0);
    	AnchorPane.setLeftAnchor(cal, 0.0);
    	AnchorPane.setRightAnchor(cal, 0.0);
    	AnchorPane.setBottomAnchor(cal, 0.0);
    	vistaCalendario.getChildren().add(cal);
    	
		vistaPerfil.setVisible(false);
    	vistaSensores.setVisible(false);
    	vistaCalendario.setVisible(true);
    	vistaContactar.setVisible(false);
    }
	
	
	// ------------------ PANEL CONTACTAR ------------------
	@FXML
    void mostrarContactar(ActionEvent event) {
		vistaPerfil.setVisible(false);
    	vistaSensores.setVisible(false);
    	vistaCalendario.setVisible(true);
    	vistaContactar.setVisible(true);
    	
    	sendMsg.setDefaultButton(true);
    	cargarChatPacienteClinico();
    }
    
	private void cargarChatPacienteClinico() {
		lblNombreClinicoChat.setText(pacienteLogueado.getClinico().getNombre() + " " + pacienteLogueado.getClinico().getApellidos());
			
		// Se buscan los mensajes.
		Vector<MensajeChat> chatPacienteClinico = new Vector <MensajeChat>();
		try {
			Connection conn = ControladorBBDD.inicializarBBDD();
			PreparedStatement stmt = conn.prepareStatement(
					"SELECT * from mensaje_paciente where (mensaje_paciente.emisor=? AND mensaje_paciente.receptor=?) "
					+ "or (mensaje_paciente.emisor=? AND mensaje_paciente.receptor=?)");
			stmt.setString(1, pacienteLogueado.getDni());
			stmt.setString(2, pacienteLogueado.getClinico().getDni());
			stmt.setString(3, pacienteLogueado.getClinico().getDni());
			stmt.setString(4, pacienteLogueado.getDni());
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				MensajeChat mensaje = new MensajeChat(rs);
				chatPacienteClinico.add(mensaje);
			}
			stmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		
		// Se cargan los mensajes.
		VBoxChat.getChildren().clear();
		for (MensajeChat mensajeChat: chatPacienteClinico) {
			if (mensajeChat.getDniEmisor().equalsIgnoreCase(pacienteLogueado.getDni())) {
				ControladorMensajeChatEmisor entradaMensajeEmisor;
				try {
					entradaMensajeEmisor = new ControladorMensajeChatEmisor(mensajeChat);
					entradaMensajeEmisor.setWidthAnchorPane(VBoxChat.getWidth());
					VBoxChat.getChildren().add(entradaMensajeEmisor.getIhm());
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				ControladorMensajeChatReceptor entradaMensajeReceptor;
				try {
					entradaMensajeReceptor = new ControladorMensajeChatReceptor(mensajeChat);
					entradaMensajeReceptor.setWidthAnchorPane(VBoxChat.getWidth());
					VBoxChat.getChildren().add(entradaMensajeReceptor.getIhm());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@FXML
    void enviarMensaje(ActionEvent event) {
		// Se crea el mensaje 
		MensajeChat mensajeEmisorPaciente = new MensajeChat(pacienteLogueado.getDni(), pacienteLogueado.getClinico().getDni(), Timestamp.from(Instant.now()), newMsg.getText());
		ControladorMensajeChatEmisor entradaMensajeEmisorPaciente;
		try {
			entradaMensajeEmisorPaciente = new ControladorMensajeChatEmisor(mensajeEmisorPaciente);
			entradaMensajeEmisorPaciente.setWidthAnchorPane(VBoxChat.getWidth());
			VBoxChat.getChildren().add(entradaMensajeEmisorPaciente.getIhm());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Se guarda el mensaje
		try {
			Connection conn = ControladorBBDD.inicializarBBDD();
			String fecha = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
			String sql = "insert into mensaje_paciente(texto, emisor, receptor) values ('"+newMsg.getText()+"', '"+pacienteLogueado.getDni()+"','"+pacienteLogueado.getClinico().getDni()+"')";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();
			stmt.close();
			conn.close();
		}
		catch (Exception e) {
			System.out.println("Error: "+e);			
		}
		newMsg.clear();
    }
	
	
    @FXML
	void cerrarSesionPaciente(ActionEvent event) {
		Sistema.getUnico().logoutUsuario();
		ControladorMostrarVentana.mostrarLogin();
	}    

    
    
// ------------------ Funciones auxiliares ------------------
	private void buscarPacienteLogueado() {
		// Se busca el clinico en funcion del usuario logueado;
		try {
			Connection conn = ControladorBBDD.inicializarBBDD();
			String busqueda = "SELECT * FROM paciente WHERE paciente.dni=?";
			PreparedStatement stmt = conn.prepareStatement(busqueda);
			stmt.setString(1, Sistema.getUnico().getUsuarioLogueado().getUsuario());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				pacienteLogueado = new Paciente(rs);
			}
			stmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
	}
	
    private void setDatosPerfil() {    	
    	clinicoPerfil.setText(pacienteLogueado.getClinico().getDni());
    	nombrePerfil.setText(pacienteLogueado.getNombre());
		apellidosPerfil.setText(pacienteLogueado.getApellidos());
		dniPerfil.setText(pacienteLogueado.getDni());
		fechaNacimientoPerfil.setText(FuncionesAuxiliares.DateToString(pacienteLogueado.getFechaNacimiento()));
    	
		emailPerfil.setText(pacienteLogueado.getEmail());
		FuncionesAuxiliares.mostrarElementosNoEditables(emailPerfil, emailEditable, btnGuardarEmail, btnEditarEmail);
		
		telefonoPerfil.setText(Integer.toString(pacienteLogueado.getTelefono()));
		FuncionesAuxiliares.mostrarElementosNoEditables(telefonoPerfil, telefonoEditable, btnGuardarTelefono, btnEditarTelefono);

		direccionPerfil.setText(pacienteLogueado.getDireccion());
		FuncionesAuxiliares.mostrarElementosNoEditables(direccionPerfil, direccionEditable, btnGuardarDireccion, btnEditarDireccion);

		ciudadPerfil.setText(pacienteLogueado.getCiudad());
		FuncionesAuxiliares.mostrarElementosNoEditables(ciudadPerfil, ciudadEditable, btnGuardarCiudad, btnEditarCiudad);

		codigoPostalPerfil.setText(Integer.toString(pacienteLogueado.getCodigoPostal()));
		FuncionesAuxiliares.mostrarElementosNoEditables(codigoPostalPerfil, codigoPostalEditable, btnGuardarCodigoPostal, btnEditarCodigoPostal);

		provinciaPerfil.setText(pacienteLogueado.getProvincia());
		FuncionesAuxiliares.mostrarElementosNoEditables(provinciaPerfil, provinciaEditable, btnGuardarProvincia, btnEditarProvincia);
		
		usuario.setText(Sistema.getUnico().getUsuarioLogueado().getUsuario());
		usuario.setEditable(false);
		
		contrasena.setText(Sistema.getUnico().getUsuarioLogueado().getContrasena());
		contrasena.setEditable(false);
		btnGuardarContrasena.setVisible(false);
		btnEditarContrasena.setVisible(true);
    }
    
    private void guardarDatosPaciente(boolean valido) {
    	if (valido) {
    		FuncionesAuxiliares.getAlertaInformacion("Guardado", "Dato actualizado correctamente.");
    	} 
    }
}