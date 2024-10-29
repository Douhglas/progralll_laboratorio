package cr.ac.una.gmailapp.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cr.ac.una.gmailapp.App;
import cr.ac.una.gmailapp.model.CorreoDto;
import cr.ac.una.gmailapp.model.ProcesoDto;
import cr.ac.una.gmailapp.model.SenderDto;
import cr.ac.una.gmailapp.model.VariableDto;
import cr.ac.una.gmailapp.util.FlowController;
import cr.ac.una.gmailapp.util.Mensaje;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * FXML Controller class
 *
 * @author stward segura
 */
public class SetDataController extends Controller implements Initializable {

    /**
     * Initializes the controller class.
     */
    private SenderDto sender;

    private ProcesoDto process;
    private EmailSenderController senderController = (EmailSenderController) FlowController.getInstance().getController("emailSender");
    private String filePath;
    @FXML
    private TextField numberField;
    @FXML
    private CheckBox check;
    @FXML
    private BorderPane father;
    @FXML
    private HBox header;
    @FXML
    private VBox contenedor;
    @FXML
    private Spinner<Integer> hourSpinner;
    @FXML
    private Spinner<Integer> minSpinner;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        hourSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23));
        minSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59));

        father.getStylesheets().add(App.class.getResource("styles/setdata.css").toExternalForm());

        check.setOnAction(event -> {
            if (check.isSelected()) {
                if (!sender.getTarCorreoList().isEmpty()) {
                    sender.setEmailsperhour(sender.getTarCorreoList().size());
                    numberField.setText(String.valueOf(sender.getTarCorreoList().size()));
                } else {
                    Mensaje.show(Alert.AlertType.INFORMATION, "Emails", "There is cero emails to send, please load the excel with the information for each email you want to send ");

                }
            } else {
                sender.setEmailsperhour(0);
                numberField.setText("0");
            }
        });
    }

    @Override
    public void initialize() {
        filePath = "";
        hourSpinner.getValueFactory().setValue(0);
        minSpinner.getValueFactory().setValue(0);

        check.setSelected(false);
        numberField.clear();
        if (senderController != null) {
            process = senderController.getProcess();

        }
        sender = new SenderDto();
        numberField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (esNumero(newValue)) {
                check.setSelected(false);
                sender.setEmailsperhour(Integer.valueOf(newValue));
            } else {
                numberField.setText("");
            }
        });

        hourSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            sender.setHour(newValue);
            System.out.println("horass :" + sender.getHour());
        });
        minSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            sender.setMinutes(newValue);
            System.out.println("minutos :" + sender.getMinutes());

        });
    }

    @FXML
    private void createAction(ActionEvent event) {
        if (!sender.getTarCorreoList().isEmpty()) {
            if (Mensaje.showConfirmation("Excel", getStage(), "Do you want to fill out the excel again?")) {
                sender.getTarCorreoList().clear();
            } else {
                return;
            }

        }
        String projectDir = System.getProperty("user.dir");
        filePath = projectDir + File.separator + "Massive" + ".xlsx";

        File file = new File(filePath);

        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Existing file deleted");
            } else {
                Mensaje.show(Alert.AlertType.ERROR, "Error", "The file could not be deleted.");
                return;
            }
        }

        try (Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet("Massive");

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Email");

            // asigning the headers
            VariableDto var = null;
            for (int i = 0; i < process.getTarVariableList().size(); i++) {
                var = process.getTarVariableList().get(i);
                if (!var.getDefaultValue().isEmpty() && !var.getType().isEmpty()) {
                    String headerValue = var.getDefaultValue() + ":" + var.getType();
                    headerRow.createCell(i + 1).setCellValue(headerValue);
                }
            }

            //saving the file
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }

            // opening the file
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().open(file);
                } catch (IOException e) {
                    Mensaje.show(Alert.AlertType.ERROR, "Error", "Unable to open the file.");
                    e.printStackTrace();
                }
            } else {
                Mensaje.show(Alert.AlertType.WARNING, "Warning", "Desktop is not supported. File was created but could not be opened.");
            }

        } catch (IOException e) {
            System.out.println("Error in createAction method");
            Mensaje.show(Alert.AlertType.ERROR, "Error", "An error occurred while creating the Excel file.");
            e.printStackTrace();
        }
    }

    @FXML
    private void loadAction(ActionEvent event) {

        if (filePath.isBlank()) {
            Mensaje.show(Alert.AlertType.INFORMATION, "Load", "First create the file");
            return;
        }

        try (FileInputStream fis = new FileInputStream(new File(filePath)); Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.iterator();

            // this assign the headers for the vars
            Row headerRow = rowIterator.next();
            List<String> headers = new ArrayList<>();
            for (Cell cell : headerRow) {
                headers.add(cell.getStringCellValue());
            }
            // iterate through the rows
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                CorreoDto correo = new CorreoDto();
                correo.setHtml(process.getHtml());//setting the html of the correo
                //getting the first cell of the row
                Cell emailCell = row.getCell(0);
                if (emailCell != null) {
                    correo.setDestination(emailCell.getStringCellValue());
                }
                //getting the variables of the row and assigning them to the email
                for (int i = 1; i < headers.size(); i++) {
                    Cell cell = row.getCell(i);
                    if (cell != null) {
                        VariableDto variable = new VariableDto();
                        String[] parts = headers.get(i).split(":");
                        String defaultValue = parts.length > 0 ? parts[0] : "";
                        String tipo = parts.length > 1 ? parts[1] : "";

                        String value = getCellValueAsString(cell);
                        if (tipo.equals("conditional") && !value.isEmpty()) {
                            addConditionalVar(correo, variable, value, defaultValue);
                        } else {

                            if (value.isBlank()) { //to avoid blank values
                                variable.setValue(defaultValue);
                            } else {
                                variable.setValue(value);
                            }
                            variable.setDefaultValue(defaultValue);
                            variable.setType(tipo);
                            correo.obtenerVars().add(variable);
                        }
                    }
                }

                correo.setTitle(process.getTitle());
                correo.updateHtml();
                sender.addToList(correo);

            }

            File file = new File(filePath);

            if (file.exists()) {
                if (file.delete()) {
                    filePath = "";
                    System.out.println("Existing file deleted");
                } else {
                    return;
                }
            }

            Mensaje.show(Alert.AlertType.CONFIRMATION, "File", "Excel file loaded succesfull");
        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    public String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();

            case NUMERIC:

                return String.valueOf(cell.getNumericCellValue());

            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());

            case FORMULA:

                FormulaEvaluator evaluator = cell.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
                CellValue cellValue = evaluator.evaluate(cell);
                switch (cellValue.getCellType()) {
                    case STRING:
                        return cellValue.getStringValue();
                    case NUMERIC:
                        return String.valueOf(cellValue.getNumberValue());
                    case BOOLEAN:
                        return String.valueOf(cellValue.getBooleanValue());
                    case FORMULA:
                    case ERROR:
                    case BLANK:
                    default:
                        return "";
                }

            case BLANK:
                return "";

            case ERROR:
                return "Error";

            default:
                return "";
        }
    }

    @FXML
    private void finishAction(ActionEvent event) {
        if (esNumero(numberField.getText())) {
            if (sender != null) {
                if (sender.getTarCorreoList().isEmpty()) {
                    Mensaje.show(Alert.AlertType.WARNING, "Emails", "You must add data to at least one email");
                } else if (sender.getEmailsperhour() == 0) {
                    Mensaje.show(Alert.AlertType.WARNING, "Emails per hour", "You must add the number of emails per hour");
                } else {
                    Long seconds = 3600L * sender.getHour() + 60L * sender.getMinutes();
                    sender.setSeconds(seconds);
                    sender.setSeconds(seconds);
                    System.out.println("segundos totaales:" + String.valueOf(sender.getSeconds()));
                    sender.setEmailsperhour(Integer.valueOf(numberField.getText()));
                    senderController.setProcess(process);
                    senderController.getDataList().addAll(sender.getTarCorreoList());
                    senderController.getEmailsTable().refresh();
                    senderController.setSender(sender);
                    getStage().close();
                }
            } else {
                Mensaje.show(Alert.AlertType.ERROR, "Error ", "sender is null ");
            }
        } else {
            Mensaje.show(Alert.AlertType.ERROR, "Error ", "Debes ingresar un numero para la cantidad enviada de correos por hora");
        }
    }

    public static boolean esNumero(String str) {
        try {
            Double.valueOf(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void addConditionalVar(CorreoDto correo, VariableDto var, String value, String defaultValue) {
        Map<String, String> map = new HashMap<>();
        for (VariableDto varP : process.getTarVariableList()) {
             System.out.println("default value de pvar: " + varP.getDefaultValue());
             System.out.println("default value de la celda: " + varP.getDefaultValue());
            if (varP.getDefaultValue().equals(defaultValue)) {
                map = jsonToMap(varP.getConditionals());
            }
        }

        if (map != null && !map.isEmpty()) { // Verificación de que el map no esté vacío
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String valor = entry.getValue();
                System.out.println(key + ": " + valor); // Para depurar
                if (value.equals(key)) {
                    var.setDefaultValue(defaultValue);
                    var.setValue(valor);
                    break;
                }
            }
            correo.obtenerVars().add(var);
        } else {
            System.out.println("El map está vacío o no se cargó correctamente.");
            Mensaje.show(Alert.AlertType.WARNING, "Error", "El map está vacío o no se cargó correctamente.");
        }

        // Imprimir el HTML para revisar
        System.out.println("HTML actualizado: " + correo.getHtml());
    }

    public static Map<String, String> jsonToMap(String json) {

        Gson gson = new Gson();

        Type type = new TypeToken<Map<String, String>>() {
        }.getType();

        return gson.fromJson(json, type);
    }

}
