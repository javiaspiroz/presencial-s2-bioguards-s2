package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXTextField;
import application.Sistema;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class ControladorValidacion2 {
	
	@FXML
	private ResourceBundle resources;
	@FXML
	private URL location;
	@FXML
    private Button btnAceptar;
	@FXML
    private Button btnCancelar;
    @FXML
    private JFXTextField valor1;
    @FXML
    private JFXTextField valor2;
    @FXML
    private JFXTextField valor3;
    @FXML
    private JFXTextField valor4;

    private String codigoGenerado;

    
	@FXML
	void initialize() {	
		Platform.runLater(new Runnable() {
    	    @Override
    	    public void run() {
    	    	valor1.requestFocus();
    	    }
    	});
    	btnAceptar.setDefaultButton(true);
	}
	
    public EventHandler<KeyEvent> maxLength(final Integer i) {
		return new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent arg0) {
				TextField v1 = (TextField) arg0.getSource();
				if (v1.getText().length() >= i) {
					arg0.consume();
				}
			}
		};
	}
    
    @FXML
	void continuarValidacion(ActionEvent ae) {
    	int c1 = Integer.parseInt(Character.toString(codigoGenerado.charAt(0)));
		int c2 = Integer.parseInt(Character.toString(codigoGenerado.charAt(1)));
		int c3 = Integer.parseInt(Character.toString(codigoGenerado.charAt(2)));
		int c4 = Integer.parseInt(Character.toString(codigoGenerado.charAt(3)));

		if (valor1.getText() =="" || valor2.getText() =="" || valor3.getText() =="" || valor4.getText() =="") {
			FuncionesAuxiliares.getAlertaError("ERROR", "Campos vacios.");
		} else {			
			if (Integer.parseInt(valor1.getText())==c1 && Integer.parseInt(valor2.getText())==c2 && 
				Integer.parseInt(valor3.getText())==c3 && Integer.parseInt(valor4.getText())==c4) {
				ControladorMostrarVentana.mostrarRecuperarPassword3();
			} else {
				FuncionesAuxiliares.getAlertaError("ERROR", "Codigo no valido.");
			}
		}
	}

	@FXML
    void volverLogin(ActionEvent event) {
		 Sistema.getUnico().logoutUsuario();
		 ControladorMostrarVentana.mostrarLogin();
    }
    
	
	public void setCodigoGenerado(String codigo) {
		codigoGenerado = codigo;
	}
}
