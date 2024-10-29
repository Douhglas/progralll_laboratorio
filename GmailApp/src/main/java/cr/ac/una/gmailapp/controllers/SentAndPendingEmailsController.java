package cr.ac.una.gmailapp.controllers;

import cr.ac.una.gmailapp.App;
import cr.ac.una.gmailapp.model.CorreoDto;
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
public class SentAndPendingEmailsController extends Controller implements Initializable {

    @FXML
    private TableView<CorreoDto> tableView;
    @FXML
    private TableColumn<CorreoDto, String> idCol;
    @FXML
    private TableColumn<CorreoDto, String> titleCol;
    @FXML
    private TableColumn<CorreoDto, String> toCol;
    @FXML
    private TableColumn<CorreoDto, String> dateCol;
    @FXML
    private TableColumn<CorreoDto, String> stateCol;
    private ObservableList<CorreoDto> dataList = FXCollections.observableArrayList();
    @FXML
    private TextField searchTF;
    @FXML
    private VBox father;
    @FXML
    private VBox contenedor;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dataList = AppManager.getInstance().getEmails();
        father.getStylesheets().add(App.class.getResource("styles/sentAndPendingEmails.css").toExternalForm());
    }
 
    @FXML
    private void visualizeAction(ActionEvent event) {
        CorreoDto correo = tableView.getSelectionModel().getSelectedItem();
        if(correo != null) {
             showHtml(correo);
        }else{
            Mensaje.show(Alert.AlertType.INFORMATION, "Information", "Debes seleccionar un correo para visualizar.");
        }
    }

    @Override
    public void initialize() {
       
        tableView.refresh();

        idCol.setCellValueFactory(cellData -> cellData.getValue().SimpleId());
        titleCol.setCellValueFactory(cellData -> cellData.getValue().SimpleTitle());
        toCol.setCellValueFactory(cellData -> cellData.getValue().SimpleDestiny());
        stateCol.setCellValueFactory(cellData -> cellData.getValue().SimpleState());
        dateCol.setCellValueFactory(cellData -> cellData.getValue().SimpleDate());

        
        FilteredList<CorreoDto> filteredData = new FilteredList<>(dataList, b -> true);

         
        searchTF.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(correo -> {
               

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                
                String lowerCaseFilter = newValue.toLowerCase();

                if (correo.SimpleId().get().toLowerCase().contains(lowerCaseFilter)) {
                    return true;  
                } else if (correo.getTitle().toLowerCase().contains(lowerCaseFilter)) {
                    return true;  
                } else if (correo.getDestination().toLowerCase().contains(lowerCaseFilter)) {
                    return true; 
                } else if (correo.getSenddate().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else {
                    return correo.getState().toLowerCase().contains(lowerCaseFilter); 
                }                

            });
        });

        
        SortedList<CorreoDto> sortedData = new SortedList<>(filteredData);

         
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());

        tableView.setItems(sortedData);

    }
    
    
    @FXML
    private void deleteAction(ActionEvent event) {
        CorreoDto correo = tableView.getSelectionModel().getSelectedItem();
        if (correo != null) {
            System.out.println("el id es:"+ correo.getCorreoId());
            AppManager.getInstance().deleteCorreo(correo.getCorreoId());
           
            
             
        } else {
              Mensaje.show(Alert.AlertType.INFORMATION, "Information", "Debes seleccionar correo para eliminar.");
        }
    }

    public void showHtml(CorreoDto email) {

        Stage htmlStage = new Stage();
        htmlStage.setTitle("Email");

        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();

        webEngine.loadContent(email.getHtml());
        
        BorderPane root = new BorderPane();
        root.setCenter(webView);
        Scene scene = new Scene(root, 550, 350);

        htmlStage.setScene(scene);
        htmlStage.show();
    }

    @FXML
    private void refreshAction(ActionEvent event) {
        AppManager.getInstance().loadEmails();
        tableView.refresh();
        
    }
}
