package application;

import java.io.File;

import controlador.ControladorMostrarVentana;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Sistema.getUnico().inicializar();
			primaryStage.setMinWidth(980);
			primaryStage.setMinHeight(580);
			ControladorMostrarVentana.inicializar(primaryStage, this.getClass().getProtectionDomain().getCodeSource().getLocation().toString());			
			ControladorMostrarVentana.mostrarLogin();
			primaryStage.setMaximized(true);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}