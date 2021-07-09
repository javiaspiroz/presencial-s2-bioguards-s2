package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import application.Clinico;
import application.Paciente;
import application.Sistema;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import javafx.scene.control.TableCell;



public class ControladorVistaClinico {	
	
	@FXML
    private AnchorPane ClinicoPane;
    @FXML
    private Button btnClinicoCerrarSesion;
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
    private Label especialidadPerfil;
    @FXML
    private JFXTextField especialidadEditable;
    @FXML
    private Button btnEditarEspecialidad;  
    @FXML
    private Button btnGuardarEspecialidad;
    
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

    
// ----------- PACIENTES -----------
    @FXML
    private Button btnPacientes;
    @FXML
    private BorderPane vistaPacientes;
    
    @FXML
    private TextField buscarPac;
    @FXML
    private TableView<Paciente> tablaPacienteDoc;
    @FXML
    private TableColumn<Paciente, String> nombrePacienteDoc;
    @FXML
    private TableColumn<Paciente, String> apellidoPacienteDoc;
    @FXML
    private TableColumn<Paciente, String> botonPacienteDoc ;
    
    @FXML
    private TableColumn<Paciente, String> dniPacienteDoc;
    private ObservableList<Paciente> listaPacientes = FXCollections.observableArrayList();
    @FXML
    private Button btnConsultarPacienteDoc;
    
    private Clinico clinicoLogueado;
    public String pacienteElegido;
    
    
    @FXML
    void initialize() {
    	buscarClinicoLogueado();
    	identidad.setText(clinicoLogueado.getNombre() + " " + clinicoLogueado.getApellidos() + "  ");
    	mostrarPacientes(null);  	
    	vistaPerfil.setVisible(false);
    	vistaPacientes.setVisible(true);
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
    void editarEspecialidad(ActionEvent event) {
		especialidadEditable.setText(clinicoLogueado.getEspecialidad());
		FuncionesAuxiliares.mostrarElementosEditables(especialidadPerfil, especialidadEditable, btnEditarEspecialidad, btnGuardarEspecialidad);
    }	
    @FXML
    void guardarEspecialidad(ActionEvent event) {
		if (!especialidadEditable.getText().trim().equals("")) {
			clinicoLogueado.setEspecialidad(especialidadEditable.getText());
			especialidadPerfil.setText(clinicoLogueado.getEspecialidad());
			guardarDatosClinico(true);
		} else {
			FuncionesAuxiliares.getAlertaError("Error", "La información no es valida.");
		}
		FuncionesAuxiliares.mostrarElementosNoEditables(especialidadPerfil, especialidadEditable, btnGuardarEspecialidad, btnEditarEspecialidad);
    } 
	 	
	@FXML
    void editarEmail(ActionEvent event) {
    	FuncionesAuxiliares.editarEmail(clinicoLogueado, emailPerfil, emailEditable, btnEditarEmail, btnGuardarEmail);
    }	
    @FXML
    void guardarEmail(ActionEvent event) {
		guardarDatosClinico(FuncionesAuxiliares.guardarEmail(clinicoLogueado, emailPerfil, emailEditable, btnGuardarEmail, btnEditarEmail));
    } 
    
    @FXML
    void editarTelefono(ActionEvent event) {
    	FuncionesAuxiliares.editarTelefono(clinicoLogueado, telefonoPerfil, telefonoEditable, btnEditarTelefono, btnGuardarTelefono);
    }        
	@FXML
    void guardarTelefono(ActionEvent event) {
		guardarDatosClinico(FuncionesAuxiliares.guardarTelefono(clinicoLogueado, telefonoPerfil, telefonoEditable, btnGuardarTelefono, btnEditarTelefono));
    }

    @FXML
    void editarDireccion(ActionEvent event) {
    	FuncionesAuxiliares.editarDireccion(clinicoLogueado, direccionPerfil, direccionEditable, btnEditarDireccion, btnGuardarDireccion);
    }
    @FXML
    void guardarDireccion(ActionEvent event) {
    	guardarDatosClinico(FuncionesAuxiliares.guardarDireccion(clinicoLogueado, direccionPerfil, direccionEditable, btnGuardarDireccion, btnEditarDireccion));
    }

    @FXML
    void editarCiudad(ActionEvent event) {
    	FuncionesAuxiliares.editarCiudad(clinicoLogueado, ciudadPerfil, ciudadEditable, btnEditarCiudad, btnGuardarCiudad);
    }   
    @FXML
    void guardarCiudad(ActionEvent event) {
    	guardarDatosClinico(FuncionesAuxiliares.guardarCiudad(clinicoLogueado, ciudadPerfil, ciudadEditable, btnGuardarCiudad, btnEditarCiudad));
    }

    @FXML
    void editarCodigoPostal(ActionEvent event) {
    	FuncionesAuxiliares.editarCodigoPostal(clinicoLogueado, codigoPostalPerfil, codigoPostalEditable, btnEditarCodigoPostal, btnGuardarCodigoPostal);
    } 
    @FXML
    void guardarCodigoPostal(ActionEvent event) {
    	guardarDatosClinico(FuncionesAuxiliares.guardarCodigoPostal(clinicoLogueado, codigoPostalPerfil, codigoPostalEditable, btnGuardarCodigoPostal, btnEditarCodigoPostal));
    }

    @FXML
    void editarProvincia(ActionEvent event) {
    	FuncionesAuxiliares.editarProvincia(clinicoLogueado, provinciaPerfil, provinciaEditable, btnEditarProvincia, btnGuardarProvincia);
    }
    @FXML
    void guardarProvincia(ActionEvent event) {
    	guardarDatosClinico(FuncionesAuxiliares.guardarProvincia(clinicoLogueado, provinciaPerfil, provinciaEditable, btnGuardarProvincia, btnEditarProvincia));
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
                                ControladorMostrarVentana.mostrarVentanaPacienteParaClinico(paciente,true);
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
    	apellidoPacienteDoc.setCellValueFactory(new PropertyValueFactory</*Paciente, String*/>("Apellidos"));
    	dniPacienteDoc.setCellValueFactory(new PropertyValueFactory</*Paciente, String*/>("Dni"));	
    	
    	//Se cargan los datos
    	tablaPacienteDoc.setItems(listaPacientes);
    	
    	//Busqueda de pacientes
    	busqueda(buscarPac.getText());
    }

	@FXML
	void consultarPacienteDoc(ActionEvent event) {
		Paciente paciente = tablaPacienteDoc.getSelectionModel().getSelectedItem();
		if (paciente!=null) {
			ControladorMostrarVentana.mostrarVentanaPacienteParaClinico(paciente, false);
		} else {
			FuncionesAuxiliares.getAlertaError("Error", "No hay ningun paciente seleccionado");
		}
	}
    
	
    @FXML
	void cerrarSesionClinico(ActionEvent event) {
		Sistema.getUnico().logoutUsuario();
		ControladorMostrarVentana.mostrarLogin();
	}    
    
 // ------------------ Funciones auxiliares ------------------
	private void buscarClinicoLogueado() {
		Connection conn;
		PreparedStatement stmt;
		ResultSet rs;
		// Se busca el clinico en funcion del usuario logueado.
		try {
			conn = ControladorBBDD.inicializarBBDD();
			String busqueda = "SELECT * FROM clinico WHERE clinico.dni=?";
			stmt = conn.prepareStatement(busqueda);
			stmt.setString(1, Sistema.getUnico().getUsuarioLogueado().getUsuario());
			rs = stmt.executeQuery();
			while (rs.next()) {
				clinicoLogueado=new Clinico(rs);
				clinicoLogueado.cargarPacientesClinico();
			}
			stmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		
		for(Paciente paciente: clinicoLogueado.getPacientes()) {
			listaPacientes.add(paciente);
		}
	}
	
    private void setDatosPerfil() {  
    	especialidadPerfil.setText(clinicoLogueado.getEspecialidad());
    	FuncionesAuxiliares.mostrarElementosNoEditables(especialidadPerfil, especialidadEditable, btnGuardarEspecialidad, btnEditarEspecialidad);
    	
    	nombrePerfil.setText(clinicoLogueado.getNombre());
		apellidosPerfil.setText(clinicoLogueado.getApellidos());
		dniPerfil.setText(clinicoLogueado.getDni());
		fechaNacimientoPerfil.setText(FuncionesAuxiliares.DateToString(clinicoLogueado.getFechaNacimiento()));
    	
		emailPerfil.setText(clinicoLogueado.getEmail());
		FuncionesAuxiliares.mostrarElementosNoEditables(emailPerfil, emailEditable, btnGuardarEmail, btnEditarEmail);
		
		telefonoPerfil.setText(Integer.toString(clinicoLogueado.getTelefono()));
		FuncionesAuxiliares.mostrarElementosNoEditables(telefonoPerfil, telefonoEditable, btnGuardarTelefono, btnEditarTelefono);

		direccionPerfil.setText(clinicoLogueado.getDireccion());
		FuncionesAuxiliares.mostrarElementosNoEditables(direccionPerfil, direccionEditable, btnGuardarDireccion, btnEditarDireccion);

		ciudadPerfil.setText(clinicoLogueado.getCiudad());
		FuncionesAuxiliares.mostrarElementosNoEditables(ciudadPerfil, ciudadEditable, btnGuardarCiudad, btnEditarCiudad);

		codigoPostalPerfil.setText(Integer.toString(clinicoLogueado.getCodigoPostal()));
		FuncionesAuxiliares.mostrarElementosNoEditables(codigoPostalPerfil, codigoPostalEditable, btnGuardarCodigoPostal, btnEditarCodigoPostal);

		provinciaPerfil.setText(clinicoLogueado.getProvincia());
		FuncionesAuxiliares.mostrarElementosNoEditables(provinciaPerfil, provinciaEditable, btnGuardarProvincia, btnEditarProvincia);
		
		usuario.setText(Sistema.getUnico().getUsuarioLogueado().getUsuario());
		usuario.setEditable(false);
		
		contrasena.setText(Sistema.getUnico().getUsuarioLogueado().getContrasena());
		contrasena.setEditable(false);
		btnGuardarContrasena.setVisible(false);
		btnEditarContrasena.setVisible(true);
    }
    
    private void guardarDatosClinico(boolean valido) {
    	if (valido) {
    		FuncionesAuxiliares.getAlertaInformacion("Guardado", "Dato actualizado correctamente.");
    	} 
    }

    //Funcion de la barra de busqueda 
    public void busqueda(String  busqueda) {
		FilteredList<Paciente> filteredData = new FilteredList<>(listaPacientes, p -> true);
		tablaPacienteDoc.setItems(filteredData);
		
		buscarPac.textProperty().addListener((prop, old, text) -> {
		    filteredData.setPredicate(paciente -> {
		        if(text == null || text.isEmpty()) return true;
		        
		        String nombre = paciente.getNombre().toLowerCase();  
		        String apellido = paciente.getApellidos().toLowerCase();
		        return nombre.contains(text.toLowerCase())||apellido.contains(text.toLowerCase());
		    });
		});
		
		SortedList<Paciente> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(tablaPacienteDoc.comparatorProperty());

		tablaPacienteDoc.setItems(sortedData);
	}  
}