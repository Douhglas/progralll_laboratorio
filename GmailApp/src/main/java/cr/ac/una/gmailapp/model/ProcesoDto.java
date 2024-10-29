package cr.ac.una.gmailapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
 
/**
 *  
 *
 * @author stward segura
 */
public class ProcesoDto {

    
    private StringProperty processId;
    private StringProperty html;
    private StringProperty title;
    private ObservableList<VariableDto> tarVariableList;
    private StringProperty contenido;
    
    public ProcesoDto() {
        this.contenido = new SimpleStringProperty("");
        this.processId = new SimpleStringProperty("");
        this.html = new SimpleStringProperty("");
        this.title = new SimpleStringProperty("");
        this.tarVariableList = FXCollections.observableArrayList();
        
    }

     
    public StringProperty processIdProperty() {
        return processId;
    }

    public StringProperty htmlProperty() {
        return html;
    }

    public StringProperty titleProperty() {
        return title;
    }
 
    public Long getProcessId() {
        if (this.processId.get() != null && !this.processId.get().isEmpty()) {
            return Long.valueOf(this.processId.get());
        } else {
            return null;
        }
    }

    public void setContenido(String content) {
        this.contenido.set(content);
    }

    public String getContenido() {
        return this.contenido.get();
    }

    public void setProcessId(Long processId) {
        this.processId.set(processId != null ? processId.toString() : "");
    }

    public String getHtml() {
        return html.get();
    }

    public void setHtml(String html) {
        this.html.set(html);
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public List<VariableDto> getTarVariableList() {
        return new ArrayList<>(tarVariableList);
    }

    public void addToList(VariableDto dto) {
        this.tarVariableList.add(dto);
    }

    public void setTarVariableList(List<VariableDto> tarVariableList) {
        this.tarVariableList = FXCollections.observableArrayList(tarVariableList);
    }

     
    public void updateHtml() {
        String updatedHtml = "<!DOCTYPE html>\n"
                + "<html lang=\"es\">\n"
                + "<head>\n"
                + "    <meta charset=\"UTF-8\">\n"
                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "    <title>Email</title>\n"
                + "    <style>\n"
                + "        body {\n"
                + "            font-family: Arial, sans-serif;\n"
                + "            background-color: #f4f4f4;\n"
                + "        }\n"
                + "        .contenedor {\n"
                + "            width: 500px;\n"
                + "            height: 300px;\n"
                + "            margin: 20px auto;\n"
                + "            background-color: #ffffff;\n"
                + "            border: 2px solid #007BFF;\n"
                + "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n"
                + "        }\n"
                + "        .header {\n"
                + "            background-color: #007BFF;\n"
                + "            color: #ffffff;\n"
                + "            padding: 10px;\n"
                + "            text-align: center;\n"
                + "            font-size: 24px;\n"
                + "        }\n"
                + "        .contenido {\n"
                + "            padding: 20px;\n"
                + "            color: #000000;\n"
                + "            font-size: 16px;\n"
                + "            line-height: 1.5;\n"
                + "        }\n"
                + "    </style>\n"
                + "</head>\n"
                + "<body>\n"
                + "    <div class=\"contenedor\">\n"
                + "        <div class=\"header\">\n"
                + title.get()
                + "        </div>\n"
                + "        <div class=\"contenido\">\n"
                + contenido.get()
                + "        </div>\n"
                + "    </div>\n"
                + "</body>\n"
                + "</html>";
        this.html.set(updatedHtml);
    }

   
    public void updateVars() {
        for (VariableDto variable : tarVariableList) {
            String defaultValue = variable.getDefaultValue();
            String value = variable.getValue();
            String type = variable.getType();

            if (html.get().contains(defaultValue)) {
                switch (type) {
                    case "img":
                        value = "<img src=\"" + value + "\" alt=\"imagen\">";
                        break;
                    case "video":
                        value = "<video controls><source src=\"" + value + "\" type=\"video/mp4\"></video>";
                        break;
                    case "url":
                        value = "<a href=\"" + value + "\">" + value + "</a>";
                        break;
                }
                html.set(html.get().replace(defaultValue, value));
            }
        }
    }
 
   
}
