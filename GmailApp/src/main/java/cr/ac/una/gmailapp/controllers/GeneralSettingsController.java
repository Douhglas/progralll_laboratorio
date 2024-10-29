package cr.ac.una.gmailapp.controllers;

import cr.ac.una.gmailapp.App;
import cr.ac.una.gmailapp.model.Admin;
import cr.ac.una.gmailapp.util.AppManager;
import cr.ac.una.gmailapp.util.Mensaje;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author stward segura
 */
public class GeneralSettingsController extends Controller implements Initializable {

    @FXML
    private TextField accountField;
    @FXML
    private MFXPasswordField passWordField;
    private Admin admin = AppManager.getInstance().getAdministrador();
    @FXML
    private AnchorPane father;
    @FXML
    private VBox contenedor;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        father.getStylesheets().add(App.class.getResource("styles/generalSettings.css").toExternalForm());
    }

    @Override
    public void initialize() {
        if (admin != null) {
            accountField.setText(admin.getEmail());
            passWordField.setText(admin.getPassword());
        }
    }

    @FXML
    private void exitAction(ActionEvent event) throws Exception {
        if (accountField.getText().isBlank()) {
            Mensaje.show(Alert.AlertType.INFORMATION, "account", "Debes ingresar una cuenta");
            return;
        }
        iniciarSesion(accountField.getText(), passWordField.getText());

    }

    public Session iniciarSesion(String gmail, String password) throws Exception {
        // Configurar las propiedades del servidor de correo
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        // Crear la sesión de JavaMail con autenticación
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(gmail, password); // Autenticación con las credenciales del Admin
            }
        });

        // Probar si la autenticación fue exitosa intentando conectarse
        try {
            session.getTransport().connect(); // Intentamos conectarnos al servidor
            System.out.println("Sesión iniciada correctamente.");
            Mensaje.show(Alert.AlertType.CONFIRMATION, "Credentials", "You are succesfully sign in"); 
            admin.setEmail(gmail);
            admin.setPassword(password);
            AppManager.getInstance().saveAdmin();
            return session; // Retornamos la sesión si todo es exitoso
        } catch (Exception e) {
            Mensaje.show(Alert.AlertType.CONFIRMATION, "Credentials", "Make sure that your account is correct and your app password as well");
            throw new Exception("Error de autenticación: credenciales inválidas o configuración incorrecta.", e);
        }
    }

    @FXML
    private void infoAction(ActionEvent event) {
        Stage infoStage = new Stage();
        infoStage.initModality(Modality.APPLICATION_MODAL);
        infoStage.setTitle("Información Sobre el Envío de Correos");
        infoStage.setMinWidth(400);
        infoStage.setMinHeight(200);

        // Texto de explicación
        String explanation = "Para poder enviar correos electrónicos desde esta aplicación, es necesario proporcionar un correo electrónico y una 'App Password'.\n\n"
                + "1. **Correo Electrónico**: Debes agregar un correo electrónico desde el cual deseas enviar los mensajes. Este será el remitente de los correos que se enviarán.\n\n"
                + "2. **App Password**: Muchas aplicaciones de correo, como Gmail, requieren que uses una 'App Password' para enviar correos a través de aplicaciones de terceros.\n"
                + "   Esto es una medida de seguridad para proteger tu cuenta. La 'App Password' es un código único que puedes generar desde la configuración de seguridad de tu cuenta de correo.\n\n"
                + "Al utilizar estos dos elementos, puedes asegurarte de que los correos sean enviados de manera segura y efectiva.";

        Label infoLabel = new Label(explanation);
        infoLabel.setWrapText(true); // Permite el ajuste de línea

        Button closeButton = new Button("Cerrar");
        closeButton.setOnAction(e -> infoStage.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(infoLabel, closeButton);

        Scene scene = new Scene(layout);
        infoStage.setScene(scene);
        infoStage.showAndWait(); // Espera hasta que se cierre la ventana

    }

}
