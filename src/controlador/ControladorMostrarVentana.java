package controlador;

import java.net.URL;

import application.Paciente;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

public class ControladorMostrarVentana {

	private static Stage primaryStage;
	private static String resourcePath;
	private static Scene scene;
	private static SceneSizeChangeListener sizeListener;
	
	private final static double initWidth  = 960;
	private final static double initHeight = 540;
	private final static double ratio = initWidth / initHeight;
	
	public static void inicializar(Stage stage, String path) {
		primaryStage = stage;
		resourcePath = path;
	}
	
	private static Object mostrarVentana(String fxmlFile, Object controlador){
		try {
			URL url= new URL(resourcePath + "vista/"+ fxmlFile);
			FXMLLoader loader = new FXMLLoader (url);
			loader.setController(controlador);
			Pane root = (Pane) loader.load();
			
			if (scene == null) {
				scene = new Scene(new Group(root));
			    sizeListener = new SceneSizeChangeListener(scene, ratio, initHeight, initWidth, root);
			    scene.widthProperty().addListener(sizeListener);
			    scene.heightProperty().addListener(sizeListener);
			}
			else {
				scene.setRoot(new Group(root));
				sizeListener.setRoot(root);
			}
		    sizeListener.refresh();
			primaryStage.setScene(scene);
			primaryStage.show();				
		} catch(Exception e) {
			e.printStackTrace();
		}
		return controlador;
	}
	
	public static Object mostrarLogin(){
		return mostrarVentana("Login.fxml", new ControladorLogin());	
	}
	
	public static Object mostrarRecuperarPassword1() {
		return mostrarVentana("RPasswordV1.fxml", new ControladorValidacion1());
	}
	
	public static Object mostrarRecuperarPassword2(String codigo) {
		ControladorValidacion2 controlador = new ControladorValidacion2();
		controlador.setCodigoGenerado(codigo);
		return mostrarVentana("RPasswordV2.fxml", controlador);
	}
	
	public static Object mostrarRecuperarPassword3() {
		return mostrarVentana("RPasswordV3.fxml", new ControladorValidacion3());
	}
	
	public static Object mostrarVentanaAdministrador(){
		return mostrarVentana("InterfazAdministrador.fxml", new ControladorVistaAdministrador());
	}
	
	public static Object mostrarVentanaClinico(){
		ControladorVistaClinico controlador = new ControladorVistaClinico();
		return mostrarVentana("InterfazClinico.fxml", controlador);
	}
	
	public static Object mostrarVentanaCuidador_Familiar(){
		ControladorVistaCuidador_Familiar controlador = new ControladorVistaCuidador_Familiar();
		return mostrarVentana("InterfazCuidador_Familiar.fxml", controlador);
	}
	
	public static Object mostrarVentanaPaciente(){
		return mostrarVentana("InterfazPaciente.fxml", new ControladorVistaPaciente());
	}
	
	public static Object mostrarVentanaPacienteParaClinico(Paciente paciente, boolean activarChat){
		ControladorVistaPacienteParaClinico controlador = new ControladorVistaPacienteParaClinico();
		controlador.setPaciente(paciente);
		controlador.setChat(activarChat);
		return mostrarVentana("InterfazPacienteParaClinico.fxml", controlador);
	}
	
	public static Object mostrarVentanaPacienteParaCF(Paciente paciente, boolean activarChat){
		ControladorVistaPacienteParaCF controlador = new ControladorVistaPacienteParaCF();
		controlador.setPaciente(paciente);
		controlador.setChat(activarChat);
		return mostrarVentana("InterfazPacienteParaCF.fxml", controlador);
	}
	
	public static Object mostrarVentanaRegistrar() {
		return mostrarVentana("InterfazRegistro.fxml", new ControladorRegistro());		
	}
	
	
	private static class SceneSizeChangeListener implements ChangeListener<Number> {
	    private final Scene scene;
	    private final double ratio;
	    private final double initHeight;
	    private final double initWidth;
	    private Pane contentPane;

	    public SceneSizeChangeListener(Scene scene, double ratio, double initHeight, double initWidth, Pane contentPane) {
	      this.scene = scene;
	      this.ratio = ratio;
	      this.initHeight = initHeight;
	      this.initWidth = initWidth;
	      this.contentPane = contentPane;
	    }
	    
	    public void setRoot(Pane contentPane) {
	    	this.contentPane = contentPane;
	    }
	    
	    public void refresh() {
		      final double newWidth  = scene.getWidth();
		      final double newHeight = scene.getHeight();

		      double scaleFactor =
		          newWidth / newHeight > ratio
		              ? newHeight / initHeight
		              : newWidth / initWidth;

		      if (scaleFactor >= 1) {
		        Scale scale = new Scale(scaleFactor, scaleFactor);
		        scale.setPivotX(0);
		        scale.setPivotY(0);
		        scene.getRoot().getTransforms().setAll(scale);
		        contentPane.setPrefWidth (newWidth  / scaleFactor);
		        contentPane.setPrefHeight(newHeight / scaleFactor);
		      } else {
		        contentPane.setPrefWidth (Math.max(initWidth,  newWidth));
		        contentPane.setPrefHeight(Math.max(initHeight, newHeight));
		      }
	    }

	    @Override
	    public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
	    	refresh();
	    }
	  }
}
