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
import application.Cuidador_Familiar;
import application.IButtonChatListener;
import application.InformacionMedica;
import application.MensajeChat;
import application.Paciente;
import application.Perfil;
import application.Sistema;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


public class ControladorVistaPacienteParaClinico implements IButtonChatListener{	
	
	@FXML
    private AnchorPane PacienteParaClinicoPane;
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
    @FXML
    private Button btnAddInfo;
    
    // General
    @FXML
    private BorderPane vistaInfoMedicaGeneral;
    @FXML
    private TableView<InformacionMedica> tablaInfoMedica;
    @FXML
    private TableColumn<InformacionMedica, String> infoMedicaPacienteDoc;
    
    // Especifica
    @FXML
    private BorderPane vistaInfoMedicaDetalles;
    @FXML
    private JFXTextField tituloInfoMedica;
    @FXML
    private Label fechaInfoMedica;
    @FXML
    private JFXDatePicker fechaElegirInfoMedica;
    @FXML
    private Button btnEditarInfoMedica;
    @FXML
    private Button btnGuardarInfoMedica;
    @FXML
    private Button btnEliminarInfoMedica;
    @FXML
    private Button btnVolverInfoMedica;
    @FXML
    private JFXTextArea textDescripcion;
    @FXML
    private JFXTextArea textTratamiento;
    
    
// ----------- CONTACTAR -----------
    @FXML
    private Button btnContactar;
    @FXML
    private BorderPane vistaContactar;
   
    @FXML
    private VBox VBoxChatPaciente;
    @FXML
    private VBox VBoxChatFamiliares;
    @FXML
    private VBox VBoxChat;
    @FXML
    private ScrollPane scrollChat;
    @FXML
    private TextField newMsg;
    @FXML
    private Button sendMsg;
    @FXML
    private Label lblNombreChatAbierto;
    @FXML
    private ImageView imgRolChat;
    
    private Paciente pacienteAMostrar;
    private String dniPacienteElegido;
    private boolean activarChat;
    private Perfil perfilChat;
    private String fechaSensores= DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
    
    private ObservableList<InformacionMedica> listaInformacionMedica = FXCollections.observableArrayList();
    private boolean nuevaInfoMedica = false;
    
    
    
    @FXML
    void initialize() throws IOException {
    	identidad.setText(pacienteAMostrar.getNombre() + " " + pacienteAMostrar.getApellidos() + "  ");
    	setDatosPerfil();
    	if(activarChat==true) {
    		mostrarContactar(null);
    	} else {
    		vistaPerfil.setVisible(false);
        	vistaSensores.setVisible(true);
        	vistaInfoMedica.setVisible(false);
        	vistaContactar.setVisible(false);
        	LocalDate fechaDatePicker = LocalDate.now();
        	datePickerSensores.setValue(fechaDatePicker);
    	}
	}
   
// ------------------ PANEL PERFIL ------------------
	@FXML
    void mostrarPerfil(ActionEvent event) {
		vistaPerfil.setVisible(true);
    	vistaSensores.setVisible(false);
    	vistaInfoMedica.setVisible(false);
    	vistaContactar.setVisible(false);
    }

    
// ------------------ PANEL SENSORES ------------------
	@FXML
    void mostrarSensores(ActionEvent event) {
		vistaPerfil.setVisible(false);
    	vistaSensores.setVisible(true);
    	vistaInfoMedica.setVisible(false);	
    	vistaContactar.setVisible(false);
    	
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
    	FuncionesAuxiliares.mostrarSensores(dniPacienteElegido, graficalineal, fechaSensores, "Sensor 3", "pulsioximetro");
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
    	vistaInfoMedica.setVisible(true);
    	vistaContactar.setVisible(false);
    	
    	mostrarVistaInfoMedicaGeneral(true);
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
            cambiarEstadoCamposInfoMedica(false);
            mostrarVistaInfoMedicaGeneral(false);
        } else {
            FuncionesAuxiliares.getAlertaError("Error", "No hay ningun elemento seleccionado");
        }
    }
	
    @FXML
    void editarInfoMedica(ActionEvent event) {
        InformacionMedica informacionSeleccionada = tablaInfoMedica.getSelectionModel().getSelectedItem();
        if (informacionSeleccionada!=null) {
            try {
            	Connection conn = ControladorBBDD.inicializarBBDD();
                PreparedStatement stmt = conn.prepareStatement(
                        "select * from infoMedica where dni_paciente= ? and titulo=?");
                stmt.setString(1, dniPacienteElegido);
                stmt.setString(2, informacionSeleccionada.getTitulo());
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                	InformacionMedica infoMedica = new InformacionMedica(rs);
			        tituloInfoMedica.setText(infoMedica.getTitulo());
					LocalDate fechaDatePicker = infoMedica.getFechaCreacionTimeStamp().toLocalDateTime().toLocalDate();
                    fechaElegirInfoMedica.setValue(fechaDatePicker);
					textDescripcion.setText(infoMedica.getDescripcion());
					textTratamiento.setText(infoMedica.getTratamiento());
                }
                stmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
            nuevaInfoMedica = false;
            cambiarEstadoCamposInfoMedica(true);
            mostrarVistaInfoMedicaGeneral(false);
        } else {
            FuncionesAuxiliares.getAlertaError("Error", "No hay ningun elemento seleccionado");
        }
    }
   
    @FXML
    void addInfo(ActionEvent event) {
    	cambiarEstadoCamposInfoMedica(true);
		mostrarVistaInfoMedicaGeneral(false);
		nuevaInfoMedica = true;
		// Se asegura que los campos estan vacios
		tituloInfoMedica.setText(null);
		fechaElegirInfoMedica.setPromptText(null);
		textDescripcion.setText(null);
		textTratamiento.setText(null);
    }
	
	@FXML
    void eliminarInfoMedica(ActionEvent event) {
		boolean result = false;
		InformacionMedica informacionSeleccionada = tablaInfoMedica.getSelectionModel().getSelectedItem();
        if (informacionSeleccionada!=null) {
            try {
            	Connection conn = ControladorBBDD.inicializarBBDD();
                PreparedStatement stmt = conn.prepareStatement("delete from infoMedica where titulo= ?");
                stmt.setString(1, informacionSeleccionada.getTitulo());
                stmt.executeUpdate();
                result = true;
                stmt.close();
                conn.close();
             }
            catch (Exception e) {
            }
            if (result) {
            	FuncionesAuxiliares.getAlertaInformacion("Eliminado", "Se han eliminado correctamente los datos.");
            }
            cargarInfoMedica();
        } else {
            FuncionesAuxiliares.getAlertaError("Error", "No hay ningun elemento seleccionado");
        }
    }
    
    @FXML
    void guardarInfoMedica(ActionEvent event) {
		if (!tituloInfoMedica.getText().trim().equals("") && fechaElegirInfoMedica.getValue()!= null && 
    		!textDescripcion.getText().trim().equals("") && !textTratamiento.getText().trim().equals("")) {
			if (tituloRepetido() && nuevaInfoMedica) {
				FuncionesAuxiliares.getAlertaError("Error", "El titulo ya existe");
			} else {
				guardarInfoMedica();
			}
		} else {
    			FuncionesAuxiliares.getAlertaError("Error", "Campos vacios o valores no validos.");
		}
    }
    
	@FXML
    void volverInfoMedica(ActionEvent event) {
		cargarInfoMedica();
		mostrarVistaInfoMedicaGeneral(true);
		tituloInfoMedica.setText(null);
		fechaElegirInfoMedica.setPromptText(null);
		textDescripcion.setText(null);
		textTratamiento.setText(null);
		nuevaInfoMedica = true;
    }
    
		
	
// ------------------ PANEL CONTACTAR ------------------
	@FXML
	void mostrarContactar(ActionEvent event) throws IOException {
		vistaPerfil.setVisible(false);
    	vistaSensores.setVisible(false);
    	vistaInfoMedica.setVisible(false);
    	vistaContactar.setVisible(true);

    	// Se cargan los pacientes y los familiares con los que puede hablar el clinico 
    	VBoxChatPaciente.getChildren().clear();
		ControladorBtnPerfilChat btnChatPaciente = new ControladorBtnPerfilChat(pacienteAMostrar, "btnChatPaciente.fxml", this);
		VBoxChatPaciente.getChildren().add(btnChatPaciente.getIhm());
		
		VBoxChatFamiliares.getChildren().clear();
		Vector<String> dniCuidadoresPaciente =  new Vector <String>();
		String aux = "";
		try {
			Connection conn = ControladorBBDD.inicializarBBDD();
            PreparedStatement stmt = conn.prepareStatement(
					"select dni_fam from asiste where asiste.dni_pac=?");
			stmt.setString(1, pacienteAMostrar.getDni());
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				aux = rs.getString("dni_fam");
				System.out.println(aux);
				dniCuidadoresPaciente.add(aux);
			}
			stmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		
		for (String dniCuidador: dniCuidadoresPaciente) {
			Cuidador_Familiar cuidadorLogueado = null;
			try {
				Connection conn = ControladorBBDD.inicializarBBDD();
				String busqueda = "SELECT * FROM cuidador_familiar WHERE cuidador_familiar.dni=?";
				PreparedStatement stmt = conn.prepareStatement(busqueda);
				stmt.setString(1, dniCuidador);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					cuidadorLogueado = new Cuidador_Familiar(rs);
				}
				stmt.close();
				conn.close();
			} catch (Exception e) {
				System.out.println("Error: " + e);
			}
			
			ControladorBtnPerfilChat btnChatCuidador = new ControladorBtnPerfilChat(cuidadorLogueado, "btnChatCuidador_Familiar.fxml", this);
			Node node = btnChatCuidador.getIhm();
			VBoxChatFamiliares.getChildren().add(node);
		}
		
		
		sendMsg.setDefaultButton(true);
		perfilChat = (Paciente) pacienteAMostrar;
		
		new Thread(new Runnable() {
		    @Override public void run() {
		        Platform.runLater(new Runnable() {
		            @Override public void run() {
		            	
		            	cargarChatPaciente();
		            	
		            }
		        });
		    }
		}).start();
    }
	
	@Override
	public void onClick(Perfil perfil) {
		if (perfil instanceof Paciente) {
			perfilChat = (Paciente) perfil;
			cargarChatPaciente();
		} else if (perfil instanceof Cuidador_Familiar) {
			perfilChat = (Cuidador_Familiar) perfil;
			cargarChatCuidador() ;
		}
	}
	
	private void cargarChatPaciente () {
		lblNombreChatAbierto.setText(perfilChat.getNombre() + " " + perfilChat.getApellidos());
		
		// Se buscan los mensajes.
		Vector<MensajeChat> chatClinicoPaciente = new Vector <MensajeChat>();
		String dniClinicoLogueado = Sistema.getUnico().getUsuarioLogueado().getUsuario();
		try {
			Connection conn = ControladorBBDD.inicializarBBDD();
            PreparedStatement stmt = conn.prepareStatement(
					"SELECT * from mensaje_paciente where (mensaje_paciente.emisor=? AND mensaje_paciente.receptor=?) "
					+ "or (mensaje_paciente.emisor=? AND mensaje_paciente.receptor=?)");
			stmt.setString(1, pacienteAMostrar.getDni());
			stmt.setString(2, dniClinicoLogueado);
			stmt.setString(3, dniClinicoLogueado);
			stmt.setString(4, pacienteAMostrar.getDni());
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				MensajeChat mensaje = new MensajeChat(rs);
				chatClinicoPaciente.add(mensaje);
			}
			stmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		
		// Se cargan los mensajes.
		VBoxChat.getChildren().clear();
		for (MensajeChat mensajeChat: chatClinicoPaciente) {
			if (mensajeChat.getDniEmisor().equalsIgnoreCase(dniClinicoLogueado)) {
				ControladorMensajeChatEmisor entradaMensajeEmisor;
				try {
					entradaMensajeEmisor = new ControladorMensajeChatEmisor(mensajeChat);
					entradaMensajeEmisor.setWidthAnchorPane(VBoxChat.getWidth());
					Node node = entradaMensajeEmisor.getIhm();
					VBoxChat.getChildren().add(node);
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
		VBoxChat.autosize();
	}
	
	private void cargarChatCuidador () {
		lblNombreChatAbierto.setText(perfilChat.getNombre() + " " + perfilChat.getApellidos());
		
		// Se buscan los mensajes. 
		Vector<MensajeChat> chatClinicoCuidador = new Vector <MensajeChat>();
		String dniClinicoLogueado = Sistema.getUnico().getUsuarioLogueado().getUsuario();
		try {
			Connection conn = ControladorBBDD.inicializarBBDD();
            PreparedStatement stmt = conn.prepareStatement(
					"SELECT * from mensaje where ((mensaje.emisor=? AND mensaje.receptor=?) OR (mensaje.emisor=? AND mensaje.receptor=?)) AND mensaje.dni_paciente=?");
			stmt.setString(1, perfilChat.getDni());
			stmt.setString(2, dniClinicoLogueado);
			stmt.setString(3, dniClinicoLogueado);
			stmt.setString(4, perfilChat.getDni());
			stmt.setString(5, pacienteAMostrar.getDni());
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				MensajeChat mensaje = new MensajeChat(rs);
				chatClinicoCuidador.add(mensaje);
			}
			stmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		
		// Se cargan los mensajes.
		VBoxChat.getChildren().clear();
		for (MensajeChat mensajeChat: chatClinicoCuidador) {
			if (mensajeChat.getDniEmisor().equalsIgnoreCase(dniClinicoLogueado)) {
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
		String dniClinicoLogueado = Sistema.getUnico().getUsuarioLogueado().getUsuario();
		MensajeChat mensajeEmisorClinico = new MensajeChat(dniClinicoLogueado, perfilChat.getDni(), Timestamp.from(Instant.now()), newMsg.getText());
		ControladorMensajeChatEmisor entradaMensajeEmisorClinico;
		try {
			entradaMensajeEmisorClinico = new ControladorMensajeChatEmisor(mensajeEmisorClinico);
			entradaMensajeEmisorClinico.setWidthAnchorPane(VBoxChat.getWidth());
			VBoxChat.getChildren().add(entradaMensajeEmisorClinico.getIhm());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Se guarda el mensaje
		if(FuncionesAuxiliares.rolPerfil(perfilChat.getDni()).equals("paciente")) {
			try {
				Connection conn = ControladorBBDD.inicializarBBDD();
				String fecha = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
				String sql = "insert into mensaje_paciente(texto, fecha, emisor, receptor) values ('"+newMsg.getText()+"','"+fecha+"','"+dniClinicoLogueado+"','"+ perfilChat.getDni()+"')";
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.executeUpdate();
				stmt.close();
				conn.close();
			}
			catch (Exception e) {
				System.out.println("Error: "+e);			
			}
		} else {
			// Se guarda el mensaje
			try {
				Connection conn = ControladorBBDD.inicializarBBDD();
				String fecha = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
				String sql = "insert into mensaje(texto, fecha, emisor, receptor, dni_paciente) values ('"+newMsg.getText()+"','"+fecha+"','"+dniClinicoLogueado+"','"+perfilChat.getDni()+"','"+ pacienteAMostrar.getDni()+"')";
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
		newMsg.clear();
    }
	
	
    @FXML
	void volverVistaClinico(ActionEvent event) {
    	ControladorMostrarVentana.mostrarVentanaClinico();
	}
    
 // ------------------ FUNCIONES AUXILIARES ------------------  
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
    	listaInformacionMedica = FXCollections.observableArrayList();
        String titulo = "";
        String fecha = "";
        Timestamp timestamp = null;

        try {
        	Connection conn = ControladorBBDD.inicializarBBDD();
            PreparedStatement stmt = conn.prepareStatement(
                    "select * from infoMedica where dni_paciente= ?");
            stmt.setString(1, dniPacienteElegido);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                titulo = rs.getString("titulo");
                fecha = rs.getString("fechaCreacion");
                Date parsedDate = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);
                timestamp = new java.sql.Timestamp(parsedDate.getTime());
                listaInformacionMedica.add(new InformacionMedica(titulo));
            }
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        //Se inicializan las columnas
        infoMedicaPacienteDoc.setCellValueFactory(new PropertyValueFactory</*InformacionMedica, String*/>("Titulo"));
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
		mostrarVistaInfoMedicaGeneral(true);
		
	}  
    
    
    private void mostrarVistaInfoMedicaGeneral(boolean mostrar) {
    	if (mostrar) {
			vistaInfoMedicaGeneral.setVisible(true);
			vistaInfoMedicaDetalles.setVisible(false);
    	} else {
			vistaInfoMedicaGeneral.setVisible(false);
			vistaInfoMedicaDetalles.setVisible(true);
    	}
	}
    
    private boolean tituloRepetido() {
    	String titulo = "";

        try {
        	Connection conn = ControladorBBDD.inicializarBBDD();
            PreparedStatement stmt = conn.prepareStatement(
                    "select * from infoMedica where dni_paciente= ?");
            stmt.setString(1, dniPacienteElegido);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                titulo = rs.getString("titulo");
                if (titulo.equals(tituloInfoMedica.getText())) {
                    return true;
                }
            }
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return false;
	}

    private void guardarInfoMedica() {
    	String fecha = fechaElegirInfoMedica.getEditor().getText();
		String titulo = tituloInfoMedica.getText();
		String descripcion = textDescripcion.getText();
		String tratamiento = textTratamiento.getText();
		boolean resultado = false;
		
    	if (nuevaInfoMedica) {
    		try {
    			Connection conn = ControladorBBDD.inicializarBBDD();
                PreparedStatement stmt = conn.prepareStatement("insert into infoMedica(titulo, fechaCreacion, descripcion, tratamiento, dni_paciente) values (?,?,?,?,?)");
				stmt.setString(1, titulo);
				Date parsedDate = new SimpleDateFormat("dd/MM/yyyy").parse(fecha);
				String fecha2 = new SimpleDateFormat("yyyy-MM-dd").format(parsedDate);
		        stmt.setString(2, fecha2);
				stmt.setString(3, descripcion);
				stmt.setString(4, tratamiento);
				stmt.setString(5, dniPacienteElegido);
				stmt.executeUpdate();
				resultado=true;
				stmt.close();
				conn.close();
			}
			catch (Exception e) {
				System.out.println("Error: "+e);			
			}
		} else {
	    	InformacionMedica informacionSeleccionada = tablaInfoMedica.getSelectionModel().getSelectedItem();
			try {
				Connection conn = ControladorBBDD.inicializarBBDD();
	            PreparedStatement stmt = conn.prepareStatement("update infoMedica set titulo=?, fechaCreacion=?, descripcion=?, tratamiento=? where dni_paciente= ? AND titulo = ?");
				stmt.setString(1, titulo);	
				Date parsedDate = new SimpleDateFormat("dd/MM/yyyy").parse(fecha);
				String fecha2 = new SimpleDateFormat("yyyy-MM-dd").format(parsedDate);
				stmt.setString(2, fecha2);
				stmt.setString(3, descripcion);
				stmt.setString(4, tratamiento);
				stmt.setString(5, dniPacienteElegido);
				stmt.setString(6, informacionSeleccionada.getTitulo());
				stmt.executeUpdate();
				resultado=true;
				stmt.close();
				conn.close();
			}
			catch (Exception e) {
				System.out.println("Error: "+e);			
			}
		}
		
		if (resultado) {
			FuncionesAuxiliares.getAlertaInformacion("Guardado", "Se han guardado correctamente los datos.");
			nuevaInfoMedica = false;
			
		}
		cargarInfoMedica();
	}
    
    
    private void cambiarEstadoCamposInfoMedica(boolean editar) {
    	// Si es true se ponen visibles los datos para editar
		if (editar) {
			tituloInfoMedica.setEditable(true);
			
			fechaInfoMedica.setVisible(false);
			fechaElegirInfoMedica.setVisible(true);
			
			textDescripcion.setEditable(true);
			textTratamiento.setEditable(true);
			
			btnGuardarInfoMedica.setVisible(true);
		} else {
			tituloInfoMedica.setEditable(false);
			
			fechaInfoMedica.setVisible(true);
			fechaElegirInfoMedica.setVisible(false);
			
			textDescripcion.setEditable(false);
			textTratamiento.setEditable(false);
			
			btnGuardarInfoMedica.setVisible(false);
		}
	}
    
    public void setChat(boolean activar) { 
    	activarChat = activar; 
    }
}
