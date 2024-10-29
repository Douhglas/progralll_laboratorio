package cr.ac.una.gmailapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author stward segura
 */
public class CorreoDto {

    private StringProperty correoId;
    private StringProperty destination;
    private StringProperty html;
    private StringProperty title;
    private StringProperty state;
    private StringProperty senddate;
    private SenderDto senderId;
    private List<VariableDto> vars;

    public CorreoDto() {
        this.correoId = new SimpleStringProperty("");
        this.destination = new SimpleStringProperty("");
        this.html = new SimpleStringProperty("");
        this.state = new SimpleStringProperty("No enviado");
        this.title = new SimpleStringProperty("");
        this.senddate = new SimpleStringProperty("-- --");
        this.vars = new ArrayList<>();
        this.senderId = new SenderDto();
    }

    public List<VariableDto> obtenerVars() {
        return vars;
    }

    public void establecerVars(List<VariableDto> vars) {
        this.vars = vars;
    }

    public SenderDto getSenderId() {
        return senderId;
    }

    public void setSenderId(SenderDto senderId) {
        this.senderId = senderId;
    }

    public Long getCorreoId() {
        if (this.correoId.get() != null && !this.correoId.get().isEmpty()) {
            return Long.valueOf(this.correoId.get());
        } else {
            return null;
        }
    }

    public StringProperty SimpleId() {
        return correoId;
    }

    public void setCorreoId(Long id) {
        this.correoId.set(id.toString());
    }

    public String getSenddate() {
        return senddate.get();
    }

    public void setSenddate(String date) {
        this.senddate.set(date);
    }

    public StringProperty SimpleDate() {
        return senddate;
    }

    public void setDestination(String Destino) {
        this.destination.set(Destino);
    }

    public String getDestination() {
        return this.destination.get();
    }

    public StringProperty SimpleDestiny() {
        return this.destination;
    }

    public StringProperty SimpleDestination() {
        return this.destination;
    }

    public void setHtml(String html) {
        this.html.set(html);
    }

    public String getHtml() {
        return this.html.get();
    }

    public void setState(String estado) {
        this.state.set(estado);
    }

    public String getState() {
        return this.state.get();
    }

    public StringProperty SimpleState() {
        return this.state;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getTitle() {
        return this.title.get();
    }

    public StringProperty SimpleTitle() {
        return this.title;
    }

    public void updateHtml() {
        if (html == null || html.get().isBlank()) {
            System.out.println("El HTML está vacío o es nulo.");
            return;
        }

        for (VariableDto variable : vars) {
            String defaultValue = variable.getDefaultValue();
            String value = variable.getValue();

            // Validar que defaultValue y value no sean nulos o vacíos
            if (defaultValue != null && !defaultValue.isBlank() && value != null) {
                if (html.get().contains(defaultValue)) {
                    // Reemplazar solo la primera aparición si es necesario
                    html.set(html.get().replace(defaultValue, value));
                }
            } else {
                System.out.println("Variable o valor por defecto nulo/vacío: " + defaultValue);
            }
        }
    }

}
