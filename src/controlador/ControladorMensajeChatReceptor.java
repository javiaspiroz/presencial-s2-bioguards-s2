package controlador;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import com.jfoenix.controls.JFXTextArea;
import application.MensajeChat;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class ControladorMensajeChatReceptor {

	@FXML
    private AnchorPane anchorPane;
    @FXML
    private Label lblFechaMensaje;
    @FXML
    private JFXTextArea mensaje;
    
    private Node ihm;
    
    public ControladorMensajeChatReceptor(MensajeChat mensaje) throws IOException {
    
    	URL url= new URL(this.getClass().getProtectionDomain().getCodeSource().getLocation().toString() + "vista/MensajeChatReceptor.fxml");
		FXMLLoader loader = new FXMLLoader (url);
		loader.setController(this);
		this.ihm = (Node) loader.load();
		
		lblFechaMensaje.setText(FuncionesAuxiliares.TimestampToString(mensaje.getFechaMensaje()));
		this.mensaje.setText(mensaje.getMensaje());
    }
    
	public Node getIhm() {
		return this.ihm;
	}
	
	public void setWidthAnchorPane(double width) {
		this.anchorPane.setPrefWidth(width);
	}
}