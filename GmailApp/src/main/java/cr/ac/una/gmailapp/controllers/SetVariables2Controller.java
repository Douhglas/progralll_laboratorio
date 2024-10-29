package cr.ac.una.gmailapp.controllers;

import com.google.gson.Gson;
import cr.ac.una.gmailapp.App;
import cr.ac.una.gmailapp.model.ProcesoDto;
import cr.ac.una.gmailapp.model.VariableDto;
import cr.ac.una.gmailapp.util.AppManager;
import cr.ac.una.gmailapp.util.FlowController;
import cr.ac.una.gmailapp.util.Mensaje;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author stwar
 */
public class SetVariables2Controller extends Controller implements Initializable {

    @FXML
    private Label varName;
    @FXML
    private Button doneBtn;
    private List<String> vars;
    private ProcesoDto proceso;

    private Integer index;
    private EditProcessController editController;
    @FXML
    private ChoiceBox<String> typeChoice;
    @FXML
    private TextField defaultField;
    @FXML
    private AnchorPane father;
    @FXML
    private TextField claveField;
    @FXML
    private TextField valueField;
    @FXML
    private Button addBtn;
    private Map<String, String> map;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        typeChoice.getItems().addAll("normal", "url", "img", "conditional");
        father.getStylesheets().add(App.class.getResource("styles/setvariableswindow.css").toExternalForm());

    }

    @Override
    public void initialize() {
        getStage().setOnCloseRequest(e->{
         e.consume(); 
         Mensaje.show(Alert.AlertType.INFORMATION, "Variable", "You must end the process");
        });
        map = new HashMap<>();
        typeChoice.setValue("normal");
        defaultField.clear();
        index = 0;
        //getting the controller where is the list of vars
        editController = (EditProcessController) FlowController.getInstance().getController("editProcessView");

        //assigning the list of vars
        vars = editController.getVars();
        proceso = editController.getProceso();
        varName.setText(vars.get(0));
          
        for (VariableDto var : proceso.getTarVariableList()) {
            var.setDefaultValue("");
        }
       
    }

    @FXML
    private void doneVarAction(ActionEvent event) {

        
      
        
        VariableDto newVar = new VariableDto();

        if (defaultField.getText().isBlank()) {
            Mensaje.show(Alert.AlertType.ERROR, "Default", "Default value cant be empty");
            return;
        }
        newVar.setDefaultValue(defaultField.getText());
        newVar.setValue(vars.get(index));
        newVar.setType(typeChoice.getValue());
        if (proceso.getHtml().contains(vars.get(index))) {
            String value = "";
            switch (newVar.getType()) {
                case "img":

                    value = "<img src=\"" + newVar.getDefaultValue() + "\" width=\"200\" height=\"150\" alt=\"imagen\">";
                    break;

                case "url":

                    value = "<a href=\"" + newVar.getDefaultValue() + "\">" + newVar.getValue() + "</a>";
                    break;
                case "normal":
                    value = newVar.getDefaultValue();
                    break;
                default:
                    value = newVar.getDefaultValue();
                    break;
            }
            proceso.setContenido(proceso.getContenido().replace("${" + vars.get(index) + "}", value));

            proceso.updateHtml();

        }
        if (newVar.getType().equals("conditional") && map.isEmpty()) {
            Mensaje.show(Alert.AlertType.INFORMATION, "Var info", "You need to add at least one condition for this variable or change the type of the var");
            return;
        }
        if (newVar.getType().equals("conditional") && !map.isEmpty()) {
            newVar.setConditionals(mapToJson());

        }

        proceso.addToList(newVar);

        index++;
        if (index < vars.size()) {

            varName.setText(vars.get(index));
            defaultField.clear();
            map.clear();

        } else {

            AppManager.getInstance().saveProcess(proceso);
            getStage().close();
            editController.getStage().close();
        }
    }

    @FXML
    private void addAction(ActionEvent event) {
        if (!claveField.getText().isEmpty() && !valueField.getText().isEmpty()) {
            map.put(claveField.getText(), valueField.getText());
            claveField.setText("valor");
            valueField.setText("agregado");
            addBtn.setDisable(true);
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2),
                    e -> {
                        claveField.setText("");
                        valueField.setText("");
                        addBtn.setDisable(false);
                    }
            ));
            timeline.setCycleCount(1);
            timeline.play();
        }
    }

    public String mapToJson() {
        Gson gson = new Gson();
        String jsonString = gson.toJson(map);

        return jsonString;
    }

}
