package cr.ac.una.gmailapp.controllers;

import cr.ac.una.gmailapp.App;
import cr.ac.una.gmailapp.model.CorreoDto;
import cr.ac.una.gmailapp.model.ProcesoDto;
import cr.ac.una.gmailapp.model.SenderDto;
import cr.ac.una.gmailapp.util.AppManager;
import cr.ac.una.gmailapp.util.FlowController;
import cr.ac.una.gmailapp.util.Mensaje;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author stward segura
 */
public class EmailSenderController extends Controller implements Initializable {

    @FXML
    private Label processLabel;
    @FXML
    private TableView<CorreoDto> emailsTable;
    @FXML
    private TableColumn<CorreoDto, String> destinyCol;
    private ObservableList<CorreoDto> dataList = FXCollections.observableArrayList();
    private SenderDto sender;
    private ProcesoDto process;
    @FXML
    private VBox contenedor;
    @FXML
    private AnchorPane father;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        father.getStylesheets().add(App.class.getResource("styles/emailsender.css").toExternalForm());
    }

    @Override
    public void initialize() {
        process = null;
        //delete the content in the table everytime   
        emailsTable.setItems(dataList);
        destinyCol.setCellValueFactory(cellData -> cellData.getValue().SimpleDestination());
    }

    @FXML
    private void chooseProcessAction(ActionEvent event) {
        //open a view, a table that shows all the process so we can select one, as well we set how many emails per hour the process will be sending

        FlowController.getInstance().goViewInWindowModal("chooseProcessWindow", getStage(), Boolean.FALSE);

    }

    @FXML
    private void setDataAction(ActionEvent event) {
        //this opens a window to create and load the excel with the information
        if (process != null) {
            emailsTable.getItems().clear();
            dataList.clear();
            FlowController.getInstance().goViewInWindowModal("setDataWindow", getStage(), Boolean.FALSE);
        } else {
            Mensaje.show(Alert.AlertType.INFORMATION, "process", "Choose a process to continue");
        }
    }

    @FXML
    private void sentAction(ActionEvent event) {
        //this start the send action 
        if (sender != null) {//save the sender and start to send its emails with a new thread
            emailsTable.getItems().clear();
            dataList.clear();
            processLabel.setText("Elige otro proceso");
            sender = AppManager.getInstance().saveSender(sender);
            System.out.println(sender.getSenderId());
            AppManager.getInstance().enviarCorreos(sender);
            AppManager.getInstance().getCorreosOfSender(sender.getSenderId());
            Mensaje.show(Alert.AlertType.CONFIRMATION, "Envio", "El envio ha sido exitosooo");
        } else {
            Mensaje.show(Alert.AlertType.INFORMATION, "process", "Choose a process to continue");
        }
    }

    @FXML
    private void visualizeAction(ActionEvent event) {
        //this visualize the selected email, just show the html of the email with the variables settled 
        CorreoDto correo = emailsTable.getSelectionModel().getSelectedItem();

        if (correo != null) {
            showHtml(correo);
        } else {
            Mensaje.show(Alert.AlertType.INFORMATION, "Email", "There is no email selected");
        }

    }

    private void goBackAction(ActionEvent event) {
        dataList.clear();

        emailsTable.refresh();

        FlowController.getInstance().goViewInStage("gmailMenu", getStage(), true);
    }

    public ProcesoDto getProcess() {
        return process;
    }

    public void setProcess(ProcesoDto process) {

        this.process = process;
        if (this.process != null) {
            processLabel.setText(this.process.getTitle());
        }
    }

    public void updateTable() {

    }

    public Label getProcessLabel() {
        return processLabel;
    }

    public void setProcessLabel(Label processLabel) {
        this.processLabel = processLabel;
    }

    public TableView<CorreoDto> getEmailsTable() {
        return emailsTable;
    }

    public void setEmailsTable(TableView<CorreoDto> emailsTable) {
        this.emailsTable = emailsTable;
    }

    public ObservableList<CorreoDto> getDataList() {
        return dataList;
    }

    public void setDataList(ObservableList<CorreoDto> dataList) {
        this.dataList = dataList;
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

    public SenderDto getSender() {
        return sender;
    }

    public void setSender(SenderDto sender) {
        this.sender = sender;
    }

}
