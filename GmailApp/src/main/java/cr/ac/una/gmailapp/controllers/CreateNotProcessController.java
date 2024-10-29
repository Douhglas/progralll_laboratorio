package cr.ac.una.gmailapp.controllers;

import cr.ac.una.gmailapp.App;
import cr.ac.una.gmailapp.model.ProcesoDto;
import cr.ac.una.gmailapp.model.VariableDto;
import cr.ac.una.gmailapp.service.ProcessService;
import cr.ac.una.gmailapp.util.AppManager;
import cr.ac.una.gmailapp.util.FlowController;
import cr.ac.una.gmailapp.util.Mensaje;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author stwar
 */
public class CreateNotProcessController extends Controller implements Initializable {

    private ProcesoDto process;
    private String content;
    private String title;
    @FXML
    private TextArea textArea;
    @FXML
    private Button infoBtn;
    private List<String> vars;
    @FXML
    private TextField nameField;
    @FXML
    private Button doneBtn;
    private List<VariableDto> aux;
    private NotificationProcessMaintenanceController controllerN = (NotificationProcessMaintenanceController) FlowController.getInstance().getController("notificationProcessMaintenance");
    @FXML
    private VBox contenedor;
    @FXML
    private HBox header;
    @FXML
    private AnchorPane father;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        father.getStylesheets().add(App.class.getResource("styles/createnotprocess.css").toExternalForm());

    }

    @Override
    public void initialize() {

        process = new ProcesoDto();

        nameField.clear();
        textArea.clear();

    }
 
    @FXML
    private void infoAction(ActionEvent event) {

        Label variablesLabel = new Label("**Variables:**");
        Label varInstructions1 = new Label(" - Las variables deben colocarse en el formato ${nombre_variable}. Ejemplo: ${nombre}, ${direccion}.");
        Label varInstructions2 = new Label(" - Dentro de ${aqui}, se debe usar un nombre diferente para cada variable.");
        Label varInstructions3 = new Label(" - La variable 'url' el valor por defecto sera lo que se muestre, se podra clickear una vez\n se haya agregado el link ");
        Label varInstructions4 = new Label(" - La variable 'img' se le debe pasar una url valida. Ejemplo: https://www.example.com/images/imagen.jpg");
        Label varInstructions5 = new Label(" - La columna de la variable en el excel se identificara con el valor por defecto y por el tipo");
        Label varInstructions6 = new Label(" - Las variables de tipo condicional les podras agregar una clave y un valor;\n"
                + " La clave sera ingresada en el excel y luego se reemplazara la variable por el valor asociado a esa clave");
        Label htmlLabel = new Label("**HTML:**");
        Label htmlInstructions1 = new Label(" - Puedes usar cualquier etiqueta HTML para dar formato al texto o agregar enlaces, imágenes y videos.");
        Label htmlInstructions2 = new Label(" - Ejemplos de etiquetas: <a> para enlaces, <img> para imágenes, <video> para videos.");

        VBox vbox = new VBox(10, variablesLabel, varInstructions1, varInstructions2, varInstructions3, varInstructions4, varInstructions5,varInstructions6,
                htmlLabel, htmlInstructions1, htmlInstructions2);
        vbox.setStyle("-fx-padding: 20; -fx-font-family: 'Arial'; -fx-background-color: #f5f5f5;");
        Stage stage = new Stage();

        Scene scene = new Scene(vbox, 600, 300);
        stage.setTitle("Instrucciones de Uso");
        stage.setScene(scene);
        stage.show();

    }

    private List<String> extractVariables() {
        List<String> variableNames = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\$\\{(.*?)\\}");
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            variableNames.add(matcher.group(1));
        }

        return variableNames;
    }

    public List<String> getVarList() {
        return vars;
    }

    public ProcesoDto getProcess() {
        return process;
    }

    @FXML
    private void finishAction(ActionEvent event) {
        //analize html and add the vars to the notification process with their default values and more
        //later i can set a validating process to ensure the html file is good

        content = textArea.getText();
        if (content.isBlank() || nameField.getText().isBlank()) {
            Mensaje.show(Alert.AlertType.INFORMATION, "finishing", "This Process wont be saved due to lack of information");
            getStage().close();
            return;
        }
        process.setTitle(nameField.getText());
        process.setContenido(content);
        process.updateHtml();
        System.out.println(process.getHtml());
        vars = extractVariables();

        if (!vars.isEmpty()) {
            FlowController.getInstance().goViewInWindowModal("setVariablesWindow", getStage(), Boolean.FALSE);
        } else {
            AppManager.getInstance().saveProcess(process);
            getStage().close();
        }

    }

    public NotificationProcessMaintenanceController getControllerN() {
        return controllerN;
    }

    public void setControllerN(NotificationProcessMaintenanceController controllerN) {
        this.controllerN = controllerN;
    }

}
