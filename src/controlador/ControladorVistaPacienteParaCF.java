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
import java.util.List;
import java.util.Vector;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import application.InformacionMedica;
import application.MensajeChat;
import application.Paciente;
import application.Sistema;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


public class ControladorVistaPacienteParaCF {	
	
	@FXML
    private AnchorPane PacienteParaCuidadorPane;
    @FXML
    private Button btnVolver;
    @FXML
    private Label identidad;

// ----------- PERFIL -----------
    @FXML
    private Button btnPerfil;
    @FXML
    private BorderPane vistaPerfil;

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
    private Label telefonoPerfil;
    @FXML
    private Label direccionPerfil; 
    @FXML
    private Label ciudadPerfil;
    @FXML
    private Label codigoPostalPerfil;
    @FXML
    private Label provinciaPerfil;

    
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
    
    
 // ----------- INFORMACION MEDICA -----------
    @FXML
    private Button btnInfoMedica;
    @FXML
    private BorderPane vistaInfoMedica;
   
    @FXML
    private TextField buscarInfo;
    @FXML
    private Button btnAmpliarInfo;
    
    // General
    @FXML
    private BorderPane vistaInfoMedicaGeneral;
    @FXML
    private TableView<InformacionMedica> tablaInfoMedica;
    @FXML
    private TableColumn<InformacionMedica, String> infoMedicaPacienteDoc;
    
    @FXML
    private BorderPane vistaInfoMedicaDetalles;

    @FXML
    private JFXTextField tituloInfoMedica;
    @FXML
    private Label fechaInfoMedica;
    @FXML
    private Button btnVolverInfoMedica;
    @FXML
    private JFXTextArea textDescripcion;
    @FXML
    private JFXTextArea textTratamiento;
    
    
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
    private boolean activarChat;
   
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
    
    private Paciente pacienteAMostrar;
    private String dniPacienteElegido;
    private ObservableList<InformacionMedica> listaInformacionMedica = FXCollections.observableArrayList();
    private String fechaSensores= DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
    
    
    @FXML
    void initialize() {
    	setDatosPerfil();
    	identidad.setText(pacienteAMostrar.getNombre() + " " + pacienteAMostrar.getApellidos() + "  ");
    	if(activarChat==true) {
    		mostrarContactar(null);
    	} else {
    		vistaPerfil.setVisible(false);
        	vistaSensores.setVisible(true);
        	vistaCalendario.setVisible(false);
        	vistaContactar.setVisible(false);
        	vistaInfoMedica.setVisible(false);
        	LocalDate fechaDatePicker = LocalDate.now();
        	datePickerSensores.setValue(fechaDatePicker);
    	}
	}
   
// ------------------ PANEL PERFIL ------------------
	@FXML
    void mostrarPerfil(ActionEvent event) {
		vistaPerfil.setVisible(true);
    	vistaSensores.setVisible(false);
    	vistaCalendario.setVisible(false);
    	vistaContactar.setVisible(false);
    	vistaInfoMedica.setVisible(false);
    }

    
// ------------------ PANEL SENSORES ------------------
	@FXML
	void mostrarSensores(ActionEvent event) {
		vistaPerfil.setVisible(false);
    	vistaSensores.setVisible(true);
    	vistaCalendario.setVisible(false);	
    	vistaContactar.setVisible(false);
        vistaInfoMedica.setVisible(false);
        
    	LocalDate fechaDatePicker = LocalDate.now();
    	datePickerSensores.setValue(fechaDatePicker);
        graficalineal.getData().clear();
		xAxis.setLabel("Horas");
		String [] Tipo = {"Sensor 1", "Sensor 2", "Sensor 3"};
		String [] Nombre = {"Temperatura", "Presion", "Pulsioximetro"};
		for (int i = 0; i < Nombre.length; i++) {
			FuncionesAuxiliares.mostrarSensoresFecha(dniPacienteElegido,graficalineal, fechaSensores, Tipo[i], Nombre[i]);
		}
    }

	@FXML
    void mostrarSensorTemperatura(ActionEvent event) {
		FuncionesAuxiliares.mostrarSensores(dniPacienteElegido, graficalineal, fechaSensores, "Sensor 1", "temperatura");
    }

    @FXML
    void mostrarSensorPresion(ActionEvent event) {
    	FuncionesAuxiliares.mostrarSensores(dniPacienteElegido, graficalineal, fechaSensores, "Sensor 2", "presion");
    }

    @FXML
    void mostrarSensorPulsioximetro(ActionEvent event) {
    	FuncionesAuxiliares.mostrarSensores(dniPacienteElegido,graficalineal, fechaSensores, "Sensor 3", "pulsioximetro");
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
			FuncionesAuxiliares.mostrarSensoresFecha(dniPacienteElegido,graficalineal, fechaSensores, Tipo[i], Nombre[i]);
		}
    }

	
    
// ------------------ PANEL INFORMACION MEDICA ------------------
	@FXML
    void mostrarInfoMedica(ActionEvent event) {
		vistaPerfil.setVisible(false);
    	vistaSensores.setVisible(false);
    	vistaCalendario.setVisible(false);
    	vistaContactar.setVisible(false);
    	vistaInfoMedica.setVisible(true);
    	vistaInfoMedicaGeneral.setVisible(true);
		vistaInfoMedicaDetalles.setVisible(false);
		cargarInfoMedica();
    }
    
	@FXML
    void ampliarInfo(ActionEvent event) {
    	InformacionMedica informacion = tablaInfoMedica.getSelectionModel().getSelectedItem();
		if (informacion!=null) {
			cambiarEstadoCamposInfoMedica(false);
			
			try {
				Connection conn = ControladorBBDD.inicializarBBDD();
				PreparedStatement stmt = conn.prepareStatement(
						"select * from infoMedica where dni_paciente= ? and titulo=?");
				stmt.setString(1, dniPacienteElegido);
				stmt.setString(2, informacion.getTitulo());
				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					InformacionMedica infoMedica = new InformacionMedica(rs);
			        tituloInfoMedica.setText(infoMedica.getTitulo());
			        fechaInfoMedica.setText(FuncionesAuxiliares.TimestampToString(infoMedica.getFechaCreacionTimeStamp()));
					textDescripcion.setText(infoMedica.getDescripcion());
					textTratamiento.setText(infoMedica.getTratamiento());
				}
				stmt.close();
				conn.close();
			} catch (Exception e) {
				System.out.println("Error: " + e);
			}
			vistaInfoMedicaGeneral.setVisible(false);
			vistaInfoMedicaDetalles.setVisible(true);
		} else {
			FuncionesAuxiliares.getAlertaError("Error", "No hay ningun elemento seleccionado");
		}
    }
    
	@FXML
    void volverInfoMedica(ActionEvent event) {
    	vistaInfoMedicaGeneral.setVisible(true);
		vistaInfoMedicaDetalles.setVisible(false);
    }
    
    
// ------------------ PANEL CALENDARIO ------------------
	@FXML
    void mostrarCalendario(ActionEvent event) {
    	// Se crea y se muestra el calendario
		ManejadorCalendario manejadorCalendario = new ManejadorCalendario();
    	Control cal = manejadorCalendario.CrearCalendario(dniPacienteElegido, false);
    	AnchorPane.setTopAnchor(cal, 0.0);
    	AnchorPane.setLeftAnchor(cal, 0.0);
    	AnchorPane.setRightAnchor(cal, 0.0);
    	AnchorPane.setBottomAnchor(cal, 0.0);
    	vistaCalendario.getChildren().add(cal);
    	
		vistaPerfil.setVisible(false);
    	vistaSensores.setVisible(false);
    	vistaCalendario.setVisible(true);
    	vistaContactar.setVisible(false);
    	vistaInfoMedica.setVisible(false);
    }
	
	
// ------------------ PANEL CONTACTAR ------------------
	@FXML
    void mostrarContactar(ActionEvent event) {
		vistaPerfil.setVisible(false);
    	vistaSensores.setVisible(false);
    	vistaCalendario.setVisible(false);
    	vistaContactar.setVisible(true);
    	vistaInfoMedica.setVisible(false);
    	
    	sendMsg.setDefaultButton(true);
    	
		new Thread(new Runnable() {
		    @Override public void run() {
		        Platform.runLater(new Runnable() {
		            @Override public void run() {
		            	
		            	cargarChatCuidadorClinico();
		            	
		            }
		        });
		    }
		}).start();
    	
    }
	
	private void cargarChatCuidadorClinico() {
		lblNombreClinicoChat.setText(pacienteAMostrar.getClinico().getNombre() + " " + pacienteAMostrar.getClinico().getApellidos());
		
		// Se buscan los mensajes.
		Vector<MensajeChat> chatCuidadorClinico = new Vector <MensajeChat>();
		try {
			Connection conn = ControladorBBDD.inicializarBBDD();
			PreparedStatement stmt = conn.prepareStatement(
					"SELECT * from mensaje where (mensaje.emisor=? AND mensaje.receptor=?) "
					+ "or (mensaje.emisor=? AND mensaje.receptor=?) AND mensaje.dni_paciente=?");
			stmt.setString(1, Sistema.getUnico().getUsuarioLogueado().getUsuario());
			stmt.setString(2, pacienteAMostrar.getClinico().getDni());
			stmt.setString(3, pacienteAMostrar.getClinico().getDni());
			stmt.setString(4, Sistema.getUnico().getUsuarioLogueado().getUsuario());
			stmt.setString(5, pacienteAMostrar.getDni());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				MensajeChat mensaje = new MensajeChat(rs);
				chatCuidadorClinico.add(mensaje);
			}
			stmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		
		// Se cargan los mensajes.
		VBoxChat.getChildren().clear();
		for (MensajeChat mensajeChat: chatCuidadorClinico) {
			if (mensajeChat.getDniEmisor().equalsIgnoreCase(Sistema.getUnico().getUsuarioLogueado().getUsuario())) {
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
		String dniCuidadorLogueado = Sistema.getUnico().getUsuarioLogueado().getUsuario();
		// Se crea el mensaje 
		MensajeChat mensajeEmisorCuidador = new MensajeChat(dniCuidadorLogueado, pacienteAMostrar.getClinico().getDni(), Timestamp.from(Instant.now()), newMsg.getText());
		ControladorMensajeChatEmisor entradaMensajeEmisorCuidador;
		try {
			entradaMensajeEmisorCuidador = new ControladorMensajeChatEmisor(mensajeEmisorCuidador);
			entradaMensajeEmisorCuidador.setWidthAnchorPane(VBoxChat.getWidth());
			VBoxChat.getChildren().add(entradaMensajeEmisorCuidador.getIhm());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Se guarda el mensaje
		try {
			Connection conn = ControladorBBDD.inicializarBBDD();
			String sql = "insert into mensaje(texto, emisor, receptor, dni_paciente) values ('"+newMsg.getText()+"', '"+dniCuidadorLogueado+"','"+dniPacienteElegido+"','"+ dniPacienteElegido+"')";
			System.out.println(sql);
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
	void volverVistaCuidador(ActionEvent event) {
    	ControladorMostrarVentana.mostrarVentanaCuidador_Familiar();
	}
    
    
 // ------------------ Funciones auxiliares ------------------
    public void setPaciente(Paciente paciente) {
    	pacienteAMostrar = paciente;
    	dniPacienteElegido = paciente.getDni();
    }

    private void setDatosPerfil() {    	
    	nombrePerfil.setText(pacienteAMostrar.getNombre());
		apellidosPerfil.setText(pacienteAMostrar.getApellidos());
		dniPerfil.setText(pacienteAMostrar.getDni());
		fechaNacimientoPerfil.setText(FuncionesAuxiliares.DateToString(pacienteAMostrar.getFechaNacimiento()));
		emailPerfil.setText(pacienteAMostrar.getEmail());
		telefonoPerfil.setText(Integer.toString(pacienteAMostrar.getTelefono()));
		direccionPerfil.setText(pacienteAMostrar.getDireccion());
		ciudadPerfil.setText(pacienteAMostrar.getCiudad());
		codigoPostalPerfil.setText(Integer.toString(pacienteAMostrar.getCodigoPostal()));
		provinciaPerfil.setText(pacienteAMostrar.getProvincia());
    }
    
    
    private void cargarInfoMedica() {

		try {
			Connection conn = ControladorBBDD.inicializarBBDD();
			PreparedStatement stmt = conn.prepareStatement(
					"select * from infoMedica where dni_paciente= ?");
			stmt.setString(1, dniPacienteElegido);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				InformacionMedica informacion = new InformacionMedica(rs);
				listaInformacionMedica.add(informacion);
			}
			stmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
    	
		//Se inicializan las columnas
    	infoMedicaPacienteDoc.setCellValueFactory(new PropertyValueFactory<>("Titulo"));
    	infoMedicaPacienteDoc.setStyle("-fx-alignment: CENTER;");
    	
    	//Se cargan los datos
    	tablaInfoMedica.setItems(listaInformacionMedica);
    	
    	//Busqueda de pacientes
    	busqueda(buscarInfo.getText());
    }
    
    public void busqueda(String  busqueda) {
		FilteredList<InformacionMedica> filteredData = new FilteredList<>(listaInformacionMedica, p -> true);
		tablaInfoMedica.setItems(filteredData);
		
		buscarInfo.textProperty().addListener((prop, old, text) -> {
		    filteredData.setPredicate(informacion -> {
		        if(text == null || text.isEmpty()) return true;
		        
		        String titulo = informacion.getTitulo().toLowerCase();
		        return titulo.contains(text.toLowerCase());
		    });
		});
		
		SortedList<InformacionMedica> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(tablaInfoMedica.comparatorProperty());

		tablaInfoMedica.setItems(sortedData);
	}  
    
    private void cambiarEstadoCamposInfoMedica(boolean editar) {
		tituloInfoMedica.setEditable(false);
		fechaInfoMedica.setVisible(true);
		textDescripcion.setEditable(false);
		textTratamiento.setEditable(false);
	}
    
    public void setChat(boolean activar) { 
    	activarChat = activar; 
    }
}