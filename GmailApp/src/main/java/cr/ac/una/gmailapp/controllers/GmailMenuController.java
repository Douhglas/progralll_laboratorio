package cr.ac.una.gmailapp.controllers;

import cr.ac.una.gmailapp.App;
import cr.ac.una.gmailapp.util.AppContext;
import cr.ac.una.gmailapp.util.AppManager;
import cr.ac.una.gmailapp.util.FlowController;
import cr.ac.una.gmailapp.util.Mensaje;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author stward segura
 */
public class GmailMenuController extends Controller implements Initializable {

    
    @FXML
    private VBox header;
    @FXML
    private Button massiveBtn;
    @FXML
    private Button processBtn;
    @FXML
    private Button mailBtn;
    @FXML
    private Button settingsBtn;
    @FXML
    private Button loginBtn;
    @FXML
    private BorderPane root;
    @FXML
    private Label rolLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        root.getStylesheets().add(App.class.getResource("styles/gmailmenu.css").toExternalForm());
        
        if (!(boolean) AppContext.getInstance().get("admin?")) {
            processBtn.setDisable(true);
            settingsBtn.setDisable(true);
            mailBtn.setDisable(true);
            rolLabel.setText("Normal");
        }else{
            rolLabel.setText("Administrador");
            processBtn.setDisable(false);
            settingsBtn.setDisable(false);
            mailBtn.setDisable(false);
        }
    }

    @Override
    public void initialize() {
       
    }

    @FXML
    private void goToMassiveAction(ActionEvent event) {
        if (AppManager.getInstance().getProcesses().isEmpty()) {
            Mensaje.show(Alert.AlertType.INFORMATION, "Processes", "You need to have at least one notification process created to enter here");
        } else { 
            FlowController.getInstance().goView("emailSender");
        }
    }

    @FXML
    private void goToMaintenanceAction(ActionEvent event) { 
         FlowController.getInstance().goView("notificationProcessMaintenance");

    }

    @FXML
    private void goToMaiLogAction(ActionEvent event) { 
         FlowController.getInstance().goView("sentAndPendingEmails");

    }

    @FXML
    private void generalSettingsAction(ActionEvent event) { 
        FlowController.getInstance().goView("generalSettings");
    }

    @FXML
    private void logInAction(ActionEvent event) {

        FlowController.getInstance().goViewInWindow("loginView");
        FlowController.getInstance().salir();
         
       
    }

}
