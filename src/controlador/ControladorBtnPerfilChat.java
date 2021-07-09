package controlador;

import javafx.scene.control.Label;
import java.io.IOException;
import java.net.URL;

import application.IButtonChatListener;
import application.Perfil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;


public class ControladorBtnPerfilChat{
	@FXML
    private Label lblNombre;
    @FXML
    private Label lblUltimoMensaje;
    
	private Perfil perfil;
	private Node ihm;
	private IButtonChatListener listener;

	
	public ControladorBtnPerfilChat(Perfil perfil, String fxml, IButtonChatListener listener) throws IOException {
		this.perfil = perfil;
		this.listener=listener;
		
		URL url= new URL(this.getClass().getProtectionDomain().getCodeSource().getLocation().toString() + "vista/" + fxml);
		FXMLLoader loader = new FXMLLoader (url);
		loader.setController(this);
		this.ihm = (Node) loader.load();
		lblNombre.setText(perfil.getNombre() + " " + perfil.getApellidos());
	}
	
	
	@FXML
    void clickChat(ActionEvent event) {
		if(listener != null) {
			listener.onClick(perfil);
		}
	}
	
	public Node getIhm() {
		return this.ihm;
	}
}