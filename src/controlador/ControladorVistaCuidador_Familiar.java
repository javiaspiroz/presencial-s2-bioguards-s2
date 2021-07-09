package controlador;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import application.Clinico;
import application.Cuidador_Familiar;
import application.Paciente;
import application.Sistema;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javafx.scene.control.TableCell;
import javafx.util.Callback;
import javafx.util.Duration;

public class ControladorVistaCuidador_Familiar {


	@FXML
	private AnchorPane CuidadorPane;
	@FXML
	private Button btnCuidadorCerrarSesion;
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

	private Timer timer= new Timer();
	
	// ----------- PACIENTES -----------
	@FXML
	private Button btnPacientes;
	@FXML
	private BorderPane vistaPacientes;   
	
	@FXML
    private Label lblEstadoVerde;
	@FXML
	private Label lblEstadoRojo;

	@FXML
	private TableView<Paciente> tablaPacienteDoc;
	@FXML
	private TableColumn<Paciente, String> nombrePacienteDoc;
	@FXML
	private TableColumn<Paciente, String> apellidosPacienteDoc;
	@FXML
	private TableColumn<Paciente, String> direccionPacienteDoc;
	@FXML
	private TableColumn<Paciente, String> botonPacienteDoc ;

	@FXML
	private Label lblNombrePaciente;
	@FXML
	private Label lblApellidosPaciente;
	@FXML
	private Label lblEstado;

	@FXML
	private Button btnConsultarPacienteDoc;	
	@FXML
	private Button btnActualizarPacienteDoc;

	private Cuidador_Familiar cuidadorLogueado;
	public String pacienteElegido;
	private ObservableList<Paciente> listaPacientes = FXCollections.observableArrayList();


	@FXML
	void initialize() {
		buscarCuidadorLogueado();
		identidad.setText(cuidadorLogueado.getNombre() + " " + cuidadorLogueado.getApellidos() + "  ");
		mostrarPacientes(null);
		vistaPerfil.setVisible(false);
		vistaPacientes.setVisible(true);
		mostrarAlerta();
	}

	// ------------------ PANEL PERFIL ------------------
	@FXML
	void mostrarPerfil(ActionEvent event) {
		setDatosPerfil();
		vistaPerfil.setVisible(true);
		vistaPacientes.setVisible(false);
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
		FuncionesAuxiliares.editarEmail(cuidadorLogueado, emailPerfil, emailEditable, btnEditarEmail, btnGuardarEmail);
	}	
	@FXML
	void guardarEmail(ActionEvent event) {
		guardarDatosCuidador(FuncionesAuxiliares.guardarEmail(cuidadorLogueado, emailPerfil, emailEditable, btnGuardarEmail, btnEditarEmail));
	} 

	@FXML
	void editarTelefono(ActionEvent event) {
		FuncionesAuxiliares.editarTelefono(cuidadorLogueado, telefonoPerfil, telefonoEditable, btnEditarTelefono, btnGuardarTelefono);
	}        
	@FXML
	void guardarTelefono(ActionEvent event) {
		guardarDatosCuidador(FuncionesAuxiliares.guardarTelefono(cuidadorLogueado, telefonoPerfil, telefonoEditable, btnGuardarTelefono, btnEditarTelefono));
	}

	@FXML
	void editarDireccion(ActionEvent event) {
		FuncionesAuxiliares.editarDireccion(cuidadorLogueado, direccionPerfil, direccionEditable, btnEditarDireccion, btnGuardarDireccion);
	}
	@FXML
	void guardarDireccion(ActionEvent event) {
		guardarDatosCuidador(FuncionesAuxiliares.guardarDireccion(cuidadorLogueado, direccionPerfil, direccionEditable, btnGuardarDireccion, btnEditarDireccion));
	}

	@FXML
	void editarCiudad(ActionEvent event) {
		FuncionesAuxiliares.editarCiudad(cuidadorLogueado, ciudadPerfil, ciudadEditable, btnEditarCiudad, btnGuardarCiudad);
	}   
	@FXML
	void guardarCiudad(ActionEvent event) {
		guardarDatosCuidador(FuncionesAuxiliares.guardarCiudad(cuidadorLogueado, ciudadPerfil, ciudadEditable, btnGuardarCiudad, btnEditarCiudad));
	}

	@FXML
	void editarCodigoPostal(ActionEvent event) {
		FuncionesAuxiliares.editarCodigoPostal(cuidadorLogueado, codigoPostalPerfil, codigoPostalEditable, btnEditarCodigoPostal, btnGuardarCodigoPostal);
	} 
	@FXML
	void guardarCodigoPostal(ActionEvent event) {
		guardarDatosCuidador(FuncionesAuxiliares.guardarCodigoPostal(cuidadorLogueado, codigoPostalPerfil, codigoPostalEditable, btnGuardarCodigoPostal, btnEditarCodigoPostal));
	}

	@FXML
	void editarProvincia(ActionEvent event) {
		FuncionesAuxiliares.editarProvincia(cuidadorLogueado, provinciaPerfil, provinciaEditable, btnEditarProvincia, btnGuardarProvincia);
	}
	@FXML
	void guardarProvincia(ActionEvent event) {
		guardarDatosCuidador(FuncionesAuxiliares.guardarProvincia(cuidadorLogueado, provinciaPerfil, provinciaEditable, btnGuardarProvincia, btnEditarProvincia));
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


	// ------------------ PANEL PACIENTES ------------------
	@FXML
	void mostrarPacientes(ActionEvent event) {
		vistaPerfil.setVisible(false);
		vistaPacientes.setVisible(true);
		lblEstadoRojo.setVisible(false);
		lblEstadoVerde.setVisible(true);
		
		botonPacienteDoc.setCellValueFactory(new PropertyValueFactory<>("Contactar"));
		Callback<TableColumn<Paciente, String>, TableCell<Paciente, String>> cellFactory
		=  new Callback<TableColumn<Paciente, String>, TableCell<Paciente, String>>() {
			@Override
			public TableCell call(final TableColumn<Paciente, String> param) {
				final TableCell<Paciente, String> cell = new TableCell<Paciente, String>() {
					final Button btn = new Button();
					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
							setText(null);
						} else {
							btn.setPrefSize(30, 30);
                        	btn.getStyleClass().add("btnChat");
							btn.setOnAction(event -> {
								btn.isPressed();
								Paciente paciente = getTableView().getItems().get(getIndex());

								ControladorMostrarVentana.mostrarVentanaPacienteParaCF(paciente,true);
							});
							setGraphic(btn);
							setText(null);
						}
					}
				};
				return cell;
			}
		};
		botonPacienteDoc.setCellFactory(cellFactory);
		
		//Se inicializan las columnas
		nombrePacienteDoc.setCellValueFactory(new PropertyValueFactory</*Paciente, String*/>("Nombre"));
		apellidosPacienteDoc.setCellValueFactory(new PropertyValueFactory</*Paciente, String*/>("Apellidos"));
		direccionPacienteDoc.setCellValueFactory(new PropertyValueFactory</*Paciente, String*/>("Direccion"));

		//Se cargan los datos
		tablaPacienteDoc.setItems(listaPacientes);
	}

	//Sale el nombre y los apellidos de la persona seleccionada en la tabla
	@FXML
	void actualizar(ActionEvent event) {
		Paciente paciente = tablaPacienteDoc.getSelectionModel().getSelectedItem();
		if (paciente !=null) {
			lblNombrePaciente.setText(paciente.getNombre());
			lblApellidosPaciente.setText(paciente.getApellidos());

			String dniPac="";
			boolean color = true;

			try {
				Connection conn = ControladorBBDD.inicializarBBDD();
				String sql = "SELECT dni FROM paciente "
						+ "JOIN asiste ON paciente.dni=asiste.dni_pac "
						+ "WHERE nombre='" + paciente.getNombre() + "' AND apellido='"+paciente.getApellidos()+"' AND "
						+ "asiste.dni_fam='"+cuidadorLogueado.getDni()+"'";
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					dniPac = rs.getString("dni");
				}
				stmt.close();
				conn.close();
			} catch (Exception e) {
				System.out.println("Error: " + e);
			}

			float estado = -1000;
			try {
				Connection conn = ControladorBBDD.inicializarBBDD();
				String sql = "SELECT medida.valor FROM medida\r\n"
						+ "JOIN sensor ON medida.id_sensor=sensor.id\r\n"
						+ "WHERE sensor.dni_pac='" + dniPac + "'AND sensor.tipo=\"Sensor 1\"\r\n"
						+ "ORDER BY medida.fecha DESC\r\n"
						+ "LIMIT 1";
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					estado = rs.getFloat("medida.valor");
				}
				stmt.close();
				conn.close();
			} catch (Exception e) {
				System.out.println("Error: " + e);
			}

			if ((estado<25 || estado>28) && estado!=-1000) {
				color = false;
			}

			estado = -1;
			try {
				Connection conn = ControladorBBDD.inicializarBBDD();
				String sql = "SELECT medida.valor FROM medida\r\n"
						+ "JOIN sensor ON medida.id_sensor=sensor.id\r\n"
						+ "WHERE sensor.dni_pac='" + dniPac + "'AND sensor.tipo=\"Sensor 2\"\r\n"
						+ "ORDER BY medida.fecha DESC\r\n"
						+ "LIMIT 1";
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					estado = rs.getInt("medida.valor");
				}
				stmt.close();
				conn.close();
			} catch (Exception e) {
				System.out.println("Error: " + e);
			}

			if (estado==1) {
				color = false;
			}

			estado = -1000;
			try {
				Connection conn = ControladorBBDD.inicializarBBDD();
				String sql = "SELECT medida.valor FROM medida\r\n"
						+ "JOIN sensor ON medida.id_sensor=sensor.id\r\n"
						+ "WHERE sensor.dni_pac='" + dniPac + "'AND sensor.tipo=\"Sensor 3\"\r\n"
						+ "ORDER BY medida.fecha DESC\r\n"
						+ "LIMIT 1";
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					estado = rs.getFloat("medida.valor");
				}
				stmt.close();
				conn.close();
			} catch (Exception e) {
				System.out.println("Error: " + e);
			}

			if ((estado<60 || estado>110) && estado!=-1000) {
				color = false;		
			}

			if (color==false) {
				lblEstadoRojo.setVisible(true);
				lblEstadoVerde.setVisible(false);
			}
			else {
				lblEstadoRojo.setVisible(false);
				lblEstadoVerde.setVisible(true);
			}

		} else {
			FuncionesAuxiliares.getAlertaError("Error", "No hay ningun paciente seleccionado");
		}		
	}
	

	@FXML
	void consultarPaciente(ActionEvent event) {
		Paciente paciente = tablaPacienteDoc.getSelectionModel().getSelectedItem();
		if (paciente!=null) {
			ControladorMostrarVentana.mostrarVentanaPacienteParaCF(paciente, false);
		} else {
			FuncionesAuxiliares.getAlertaError("Error", "No hay ningun paciente seleccionado");
		}
	}
	

	@FXML
	void cerrarSesionCuidador(ActionEvent event) {
		Sistema.getUnico().logoutUsuario();
		timer.cancel(); //Cerramos el hilo de las alertas
		timer.purge();
		ControladorMostrarVentana.mostrarLogin();
	}


	// ------------------ Funciones auxiliares ------------------
	private void buscarCuidadorLogueado() {
		Connection conn;
		PreparedStatement stmt;
		ResultSet rs;
		// Se busca el clinico en funcion del usuario logueado;
		try {
			conn = ControladorBBDD.inicializarBBDD();
			String busqueda = "SELECT * FROM cuidador_familiar WHERE cuidador_familiar.dni=?";
			stmt = conn.prepareStatement(busqueda);
			stmt.setString(1, Sistema.getUnico().getUsuarioLogueado().getUsuario());
			rs = stmt.executeQuery();
			while (rs.next()) {
				cuidadorLogueado = new Cuidador_Familiar(rs);
				cuidadorLogueado.cargarPacientesCuidador();
			}
			stmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}

		for(Paciente paciente: cuidadorLogueado.getPacientes()) {
			listaPacientes.add(paciente);
		}
	}

	private void setDatosPerfil() {    	
		nombrePerfil.setText(cuidadorLogueado.getNombre());
		apellidosPerfil.setText(cuidadorLogueado.getApellidos());
		dniPerfil.setText(cuidadorLogueado.getDni());
		fechaNacimientoPerfil.setText(FuncionesAuxiliares.DateToString(cuidadorLogueado.getFechaNacimiento()));

		emailPerfil.setText(cuidadorLogueado.getEmail());
		FuncionesAuxiliares.mostrarElementosNoEditables(emailPerfil, emailEditable, btnGuardarEmail, btnEditarEmail);

		telefonoPerfil.setText(Integer.toString(cuidadorLogueado.getTelefono()));
		FuncionesAuxiliares.mostrarElementosNoEditables(telefonoPerfil, telefonoEditable, btnGuardarTelefono, btnEditarTelefono);

		direccionPerfil.setText(cuidadorLogueado.getDireccion());
		FuncionesAuxiliares.mostrarElementosNoEditables(direccionPerfil, direccionEditable, btnGuardarDireccion, btnEditarDireccion);

		ciudadPerfil.setText(cuidadorLogueado.getCiudad());
		FuncionesAuxiliares.mostrarElementosNoEditables(ciudadPerfil, ciudadEditable, btnGuardarCiudad, btnEditarCiudad);

		codigoPostalPerfil.setText(Integer.toString(cuidadorLogueado.getCodigoPostal()));
		FuncionesAuxiliares.mostrarElementosNoEditables(codigoPostalPerfil, codigoPostalEditable, btnGuardarCodigoPostal, btnEditarCodigoPostal);

		provinciaPerfil.setText(cuidadorLogueado.getProvincia());
		FuncionesAuxiliares.mostrarElementosNoEditables(provinciaPerfil, provinciaEditable, btnGuardarProvincia, btnEditarProvincia);

		usuario.setText(Sistema.getUnico().getUsuarioLogueado().getUsuario());
		usuario.setEditable(false);

		contrasena.setText(Sistema.getUnico().getUsuarioLogueado().getContrasena());
		contrasena.setEditable(false);
		btnGuardarContrasena.setVisible(false);
		btnEditarContrasena.setVisible(true);
	}

	private void guardarDatosCuidador(boolean valido) {
		if (valido) {
			FuncionesAuxiliares.getAlertaInformacion("Guardado", "Dato actualizado correctamente.");
		} 
	}
	
	// ------------------ Funciones alertas ------------------
	
	private void getAlertas() {
		//obtengo todos los pacientes del cuidador y obtengo alguno su nombre y apellido
		Vector<String> alertasPacientesDNI = new Vector<String>();
		Vector<String> alertasPacientesNomApe = new Vector<String>();
		try {
			Connection conn = ControladorBBDD.inicializarBBDD();
			String sql = "SELECT asiste.dni_pac, paciente.nombre, paciente.apellido from cuidador_familiar\r\n"
					+ "join asiste on cuidador_familiar.dni = asiste.dni_fam\r\n"
					+ "JOIN paciente ON paciente.dni=asiste.dni_pac\r\n"
					+ "where asiste.dni_fam='" + cuidadorLogueado.getDni() + "'";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			String dniPac = "";
			String nomApe = "";
			while (rs.next()) {
				dniPac = rs.getString("asiste.dni_pac");
				nomApe = rs.getString("paciente.nombre")+" "+rs.getString("paciente.apellido");
				//System.out.println(dniPac + " "+ nomApe);
				alertasPacientesDNI.add(dniPac);
				alertasPacientesNomApe.add(nomApe);
			}
			stmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		//for para recorrer cada uno de los pacientes y detectar sus posibles anomalias
		for (int i =0; i<alertasPacientesDNI.size(); i++) {
			//pasamos a cada una de las funciones el dni y nombre para generar los mensajes correpondientes si son necesarios
			alertaSensor1(alertasPacientesDNI.get(i), alertasPacientesNomApe.get(i));
			alertaSensor2(alertasPacientesDNI.get(i), alertasPacientesNomApe.get(i));
			alertaSensor3(alertasPacientesDNI.get(i), alertasPacientesNomApe.get(i));
		}
	}
	
	private void alertaSensor1(String dniPac, String nomApe) {
		float estado = -1000;
		int alerta = -1;
		int id = -1;
		try {
			Connection conn = ControladorBBDD.inicializarBBDD();
			String sql = "SELECT medida.id, medida.valor, medida.alerta FROM medida\r\n"
					+ "JOIN sensor ON medida.id_sensor=sensor.id\r\n"
					+ "WHERE sensor.dni_pac='" + dniPac + "'AND sensor.tipo=\"Sensor 1\"\r\n"
							+ "ORDER BY medida.fecha DESC\r\n"
							+ "LIMIT 1";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				estado = rs.getFloat("medida.valor");
				alerta = rs.getInt("medida.alerta");
				id = rs.getInt("medida.id");
			}
			stmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		
		if ((estado<25 || estado>28) && estado!=-1000 && alerta == 0) {
			FuncionesAuxiliares.getAlertaInformacion("Sensor Temperatura","Revise la temperatura del paciente "+nomApe);
			try {
				Connection conn = ControladorBBDD.inicializarBBDD();
				PreparedStatement stmt = conn.prepareStatement("update medida set alerta=1 where id= ?");
				stmt.setInt(1, id);
				stmt.executeUpdate();
				stmt.close();
				conn.close();
			}
			catch (Exception e) {
				System.out.println("Error: "+e);			
			}
		}
	}
	
	private void alertaSensor2(String dniPac, String nomApe) {
		int estado = -1;
		int id = 0;
		try {
			Connection conn = ControladorBBDD.inicializarBBDD();
			String sql = "SELECT medida.valor, medida.id FROM medida\r\n"
					+ "JOIN sensor ON medida.id_sensor=sensor.id\r\n"
					+ "WHERE sensor.dni_pac='" + dniPac + "'AND sensor.tipo=\"Sensor 2\"\r\n"
							+ "ORDER BY medida.fecha DESC\r\n"
							+ "LIMIT 1";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				estado = rs.getInt("medida.valor");
				id = rs.getInt("medida.id");
			}
			stmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		
		if (estado==1) {
			FuncionesAuxiliares.getAlertaInformacion("Sensor Presion","Mueva al paciente "+nomApe+" de su posicion actual por riesgo de ulcera");
			//y hacer el update de estado resuelto
			try {
				Connection conn = ControladorBBDD.inicializarBBDD();
				PreparedStatement stmt = conn.prepareStatement("update medida set valor=0, alerta=1 where id= ?");
				stmt.setInt(1, id);
				stmt.executeUpdate();
				stmt.close();
				conn.close();
			}
			catch (Exception e) {
				System.out.println("Error: "+e);			
			}
			
		}
	}
	
	private void alertaSensor3(String dniPac, String nomApe) {
		float estado = -1000;
		int alerta = -1;
		int id = -1;
		try {
			Connection conn = ControladorBBDD.inicializarBBDD();
			String sql = "SELECT medida.valor, medida.id, medida.alerta FROM medida\r\n"
					+ "JOIN sensor ON medida.id_sensor=sensor.id\r\n"
					+ "WHERE sensor.dni_pac='" + dniPac + "'AND sensor.tipo=\"Sensor 3\"\r\n"
							+ "ORDER BY medida.fecha DESC\r\n"
							+ "LIMIT 1";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				estado = rs.getFloat("medida.valor");
				alerta = rs.getInt("medida.alerta");
				id = rs.getInt("medida.id");
			}
			stmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		
		if ((estado<60 || estado>110) && estado!=-1000 && alerta == 0) {
			FuncionesAuxiliares.getAlertaInformacion("Sensor Pulsioximetro","Revise los BPMs de "+nomApe);
			
			try {
				Connection conn = ControladorBBDD.inicializarBBDD();
				PreparedStatement stmt = conn.prepareStatement("update medida set alerta=1 where id= ?");
				stmt.setInt(1, id);
				stmt.executeUpdate();
				stmt.close();
				conn.close();
			}
			catch (Exception e) {
				System.out.println("Error: "+e);			
			}
		}
	}
	
	public void mostrarAlerta() {
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				Platform.runLater(() -> {
					System.out.println("Alerta");
					getAlertas();
				});
			}
		},200,60000);
	}

}