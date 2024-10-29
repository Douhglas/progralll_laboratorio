package cr.ac.una.gmailapp.controllers;

import cr.ac.una.gmailapp.App;
import cr.ac.una.gmailapp.model.ProcesoDto;
import cr.ac.una.gmailapp.util.AppManager;
import cr.ac.una.gmailapp.util.FlowController;
import cr.ac.una.gmailapp.util.Mensaje;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author stward segura
 */
public class NotificationProcessMaintenanceController extends Controller implements Initializable {

    @FXML
    private TextField searchField;
    @FXML
    private TableView<ProcesoDto> tableView;
    @FXML
    private TableColumn<ProcesoDto, String> idCol;
    @FXML
    private TableColumn<ProcesoDto, String> titleCol;
    @FXML
    private Button editBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button createNewBtn;
    @FXML
    private Button visualizeBtn;
    private ObservableList<ProcesoDto> dataList = FXCollections.observableArrayList();
    private ProcesoDto notProcess;
   
    @FXML
    private AnchorPane father;
    @FXML
    private VBox contenedor;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         
        father.getStylesheets().add(App.class.getResource("styles/notificationprocessmaintenance.css").toExternalForm());
    }

    @Override
    public void initialize() {
        notProcess = null;
        
        dataList = AppManager.getInstance().getProcesses();

        tableView.refresh();

        idCol.setCellValueFactory(cellData -> cellData.getValue().processIdProperty());
        titleCol.setCellValueFactory(cellData -> cellData.getValue().titleProperty());

        // wrapping the observable list in a filtered list initially we show all the questions
        FilteredList<ProcesoDto> filteredData = new FilteredList<>(dataList, b -> true);

        //setting the filter predicate 
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(process -> {
                // If filter text is empty, display all persons.

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (process.processIdProperty().get().contains(lowerCaseFilter)) {
                    return true; // Filter matches id
                } else if (process.getTitle().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches category
                }
                return false;// does not match

            });
        });

        //wrapping the filtered list in a sorted list 
        SortedList<ProcesoDto> sortedData = new SortedList<>(filteredData);

        //binding the sortedlist comparator to the questionTable comparator otherwise the sorting wont have an effect
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());

        tableView.setItems(sortedData);

    }

   
    @FXML
    private void editAction(ActionEvent event) {
         notProcess = tableView.getSelectionModel().getSelectedItem();
        if(notProcess != null){ 
        FlowController.getInstance().goViewInWindowModal("editProcessView", getStage(), true);
        }else{
            Mensaje.show(Alert.AlertType.INFORMATION, "Choose", "Choose a notification process to edit");
        }
    }

    @FXML
    private void deleteAction(ActionEvent event) {
        ProcesoDto proceso = tableView.getSelectionModel().getSelectedItem();
        if (proceso != null) {
            
            AppManager.getInstance().deleteProcess(proceso.getProcessId());
            
            dataList.remove(proceso);
            AppManager.getInstance().getProcesses().remove(proceso);
             
        } else {
            // Mensaje.show(Alert.AlertType.INFORMATION, "Information", "Debes seleccionar una pregunta para eliminar.");
        }
    }

    @FXML
    private void createNewAction(ActionEvent event) {
        //the new process must be added in the dataList
      
        FlowController.getInstance().goViewInWindowModal("createNotProcess", getStage(), true);
    }

    @FXML
    private void visualizeAction(ActionEvent event) {
        notProcess = tableView.getSelectionModel().getSelectedItem();
       
        if (notProcess != null) {
            Stage htmlStage = new Stage();
            htmlStage.setTitle("Process");

            WebView webView = new WebView();
            WebEngine webEngine = webView.getEngine();

            webEngine.loadContent(notProcess.getHtml());

            BorderPane root = new BorderPane();
            root.setCenter(webView);
            Scene scene = new Scene(root, 550, 350);

            htmlStage.setScene(scene);
            htmlStage.show();
        } else {
            Mensaje.show(Alert.AlertType.INFORMATION, "Process", "There is no Process selected");
        }
    }

    public ObservableList<ProcesoDto> getDataList() {
        return dataList;
    }

    public TableView<ProcesoDto> getTable() {
        return tableView;
    }

    public ProcesoDto getProcess() {
        return notProcess;
    }
}
