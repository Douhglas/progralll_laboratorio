package cr.ac.una.gmailapp.controllers;

import cr.ac.una.gmailapp.App;
import cr.ac.una.gmailapp.model.ProcesoDto;
import cr.ac.una.gmailapp.util.AppManager;
import cr.ac.una.gmailapp.util.FlowController;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author stward segura
 */
public class ChooseProcessController extends Controller implements Initializable {

    @FXML
    private TableView<ProcesoDto> tableView;
    @FXML
    private TableColumn<ProcesoDto, String> notCol;
    @FXML
    private TextField textField;
    private ObservableList<ProcesoDto> dataList = FXCollections.observableArrayList();
    @FXML
    private BorderPane contenedor;
    @FXML
    private HBox header;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          tableView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                ProcesoDto process = tableView.getSelectionModel().getSelectedItem();
                if (process != null) {
                 //close the window and set the process of the emailSender 
                 EmailSenderController sender =  (EmailSenderController) FlowController.getInstance().getController("emailSender");
                 sender.setProcess(process);
                 getStage().close();
                }
            }
        });
          
                   contenedor.getStylesheets().add(App.class.getResource("styles/notificationprocessmaintenance.css").toExternalForm());

    }

    @Override
    public void initialize() {
         
        dataList.clear();
        List<ProcesoDto> procesos = AppManager.getInstance().getProcesses();
        if (procesos != null && !procesos.isEmpty()) {
            dataList.addAll(procesos);
        } else {
            // Mensaje.show(Alert.AlertType.ERROR, "Error mantenimiento", "No existen preguntas para cargar");
        }

        tableView.refresh();

        notCol.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        

        // wrapping the observable list in a filtered list initially we show all the questions
        FilteredList<ProcesoDto> filteredData = new FilteredList<>(dataList, b -> true);

        //setting the filter predicate 
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(process -> {
                // If filter text is empty, display all persons.

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (process.getTitle().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches id
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

}
