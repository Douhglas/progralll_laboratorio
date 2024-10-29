package cr.ac.una.gmailapp.model;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author stward segura
 */
public class VariableDto {

    private SimpleStringProperty id;
    private SimpleStringProperty type;
    private SimpleStringProperty value;
    private SimpleStringProperty defaultValue;
    private ProcesoDto processId;
    private String conditionals;
    
    public ProcesoDto getProcessId() {
        return processId;
    }

    public void setProcessId(ProcesoDto processId) {
        this.processId = processId;
    }

    public VariableDto() {
        this.id = new SimpleStringProperty("");
        this.processId = new ProcesoDto();
        this.value = new SimpleStringProperty("");
        this.defaultValue = new SimpleStringProperty(""); 
        this.type = new SimpleStringProperty("");
        this.conditionals = "";
    }

    

    
    public String getType() {
        return type.get();
    }

  
    public void setType(String tipo) {
        this.type.set(tipo);
    }

    public String getValue() {
        return value.get();
    }

    public void setValue(String value) {
        this.value.set(value);
    }

    public Long getVarId() {
          if (this.id.get() != null && !this.id.get().isEmpty()) {
            return Long.valueOf(this.id.get());
        } else {
            return null;
        }       
    }

    public void setVarId(Long id) {
        this.id.set(id.toString());
    }

    public String getDefaultValue() {
        return defaultValue.get();
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue.set(defaultValue);
    }

    public String getConditionals() {
        return conditionals;
    }

    public void setConditionals(String conditionals) {
        this.conditionals = conditionals;
    }
  
}
