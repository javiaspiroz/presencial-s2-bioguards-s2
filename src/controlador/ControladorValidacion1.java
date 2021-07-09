package controlador;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import com.jfoenix.controls.JFXTextField;
import application.Sistema;
import application.Usuario;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ControladorValidacion1 {

	@FXML
	private ResourceBundle resources;
	@FXML
	private URL location;
	@FXML
	private JFXTextField email;
	@FXML
	private Button btnAceptar;
	@FXML
	private Button btnCancelar;

	private String dniPerfil;
	
	@FXML
	void initialize() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				email.requestFocus();
			}
		});
		btnAceptar.setDefaultButton(true);
	}

	@FXML
	void volverLogin(ActionEvent event) {
		ControladorMostrarVentana.mostrarLogin();
	}

	@FXML
	void validarEmail(ActionEvent event) {

		String emailVerificar = email.getText();

		if (!buscarEmail(emailVerificar)) {
			FuncionesAuxiliares.getAlertaError("Reestablecimiento de contraseña",
					"El correo electronico introducido no esta registrado.");
		} else {

			String codigoGenerado = generarCodigo();
			if (enviarMail(emailVerificar, codigoGenerado)) {
				asignarUsuarioEmail();
				ControladorMostrarVentana.mostrarRecuperarPassword2(codigoGenerado);
			} else {
				ControladorMostrarVentana.mostrarLogin();
			}
		}
	}

	public boolean buscarEmail(String emailVerificar) {
		boolean encontrado = false;
		try {
			Connection conn = ControladorBBDD.inicializarBBDD();
			PreparedStatement stmt;
			String busqueda = "SELECT  dni, email FROM paciente\n" + 
					"WHERE email like ?\n" + 
					"UNION ALL\n" + 
					"SELECT dni, email FROM clinico\n" + 
					"WHERE email like ?\n" + 
					"UNION ALL\n" + 
					"SELECT dni, email FROM admin\n" + 
					"WHERE email like ?\n";
			stmt = conn.prepareStatement(busqueda);
			stmt.setString(1, emailVerificar);
			stmt.setString(2, emailVerificar);
			stmt.setString(3, emailVerificar);
			ResultSet rs = stmt.executeQuery();
			
			// Datos principales
			String email = "";
			String dni="";
			while (rs.next()) {
				email = rs.getString("email");
				dni=rs.getString("dni");
			}
			stmt.close();
			conn.close();

			if(email.equals("")) {
				encontrado=false;
			} else {
				dniPerfil=dni;
				encontrado=true;
			}
			//conn.close();

		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		return encontrado;
	}


	private String generarCodigo() {
		Random r = new Random();
		int n = r.nextInt(9999 - 1) + 1;
		return String.format("%04d", n);
	}

	public boolean enviarMail(String toEmail, String valor) {
		// Origen
		final String fromEmail = "companythenas";
		final String password = "contrasena123";

		Properties props = System.getProperties();
		props.put("mail.smtp.host", "smtp.gmail.com"); // El servidor SMTP de Google
		props.put("mail.smtp.user", fromEmail);
		props.put("mail.smtp.clave", password);
		props.put("mail.smtp.auth", "true"); // Usar autenticacion mediante usuario y clave
		props.put("mail.smtp.starttls.enable", "true"); // Para conectar de manera segura al servidor SMTP
		props.put("mail.smtp.port", "587"); // El puerto SMTP seguro de Google

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		});
		session.setDebug(true);

		try {
			MimeMessage message = new MimeMessage(session);

			// Se rellena el From y los destinatarios
			message.setFrom(new InternetAddress(fromEmail));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));

			// Se rellena el subject
			message.setSubject("Recuperacion de contraseña BioGuards");

			message.setText("Se modificara tu cotraseña al introducir el siguiente codigo de verificacion:\n" + valor);
			
			// Envio
			Transport transport = session.getTransport("smtp");
			transport.connect("smtp.gmail.com", fromEmail, password);
			Transport.send(message, message.getAllRecipients());
			transport.close();
			return true;
		} catch (MessagingException e1) {
			FuncionesAuxiliares.getAlertaError("Error", "El email no se ha podido enviar");
			return false;
		}
	}

	private void asignarUsuarioEmail() {
		try {
			Connection conn = ControladorBBDD.inicializarBBDD();
			PreparedStatement stmt;
			String busqueda = "SELECT * FROM usuario\n" + 
					"WHERE dni=?";
			stmt = conn.prepareStatement(busqueda);
			stmt.setString(1, dniPerfil);
			ResultSet rs = stmt.executeQuery();

			// Datos principales
			String rol = "";
			String password="";
			while (rs.next()) {
				rol = rs.getString("rol");
				password=rs.getString("password");
			}
			stmt.close();
			conn.close();
			Usuario u= new Usuario(dniPerfil, password, rol);	
			Sistema.getUnico().setfUsuarioLogueado(u);

		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
	}
}