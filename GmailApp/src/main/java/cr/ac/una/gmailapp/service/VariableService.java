 
package cr.ac.una.gmailapp.service;

import cr.ac.una.gmailapp.model.VariableDto;
import cr.ac.una.gmailapp.util.Request;
import cr.ac.una.gmailapp.util.Respuesta;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
 

/**
 *
 * @author stwar
 */ 
public class VariableService {

    public Respuesta getVariable(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("varId", id);
            Request request = new Request("VariableController/variable", "{varId}", parametros);
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            VariableDto variable = (VariableDto) request.readEntity(VariableDto.class);
            return new Respuesta(true, "", "", "Variable", variable);
        } catch (Exception ex) {
            Logger.getLogger(VariableService.class.getName()).log(Level.SEVERE, "Error obteniendo la variable [" + id + "]", ex);
            return new Respuesta(false, "Error obteniendo la variable.", "getVariable: " + ex.getMessage());
        }
    }

    public Respuesta guardarVariable(VariableDto variable) {
        try {
            Request request = new Request("VariableController/variable");
            request.post(variable);
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            VariableDto savedVariable = (VariableDto) request.readEntity(VariableDto.class);
            return new Respuesta(true, "", "", "Variable", savedVariable);
        } catch (Exception ex) {
            Logger.getLogger(VariableService.class.getName()).log(Level.SEVERE, "Ocurrió un error al guardar la variable.", ex);
            return new Respuesta(false, "Ocurrió un error al guardar la variable.", "guardarVariable: " + ex.getMessage());
        }
    }

    public Respuesta eliminarVariable(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("varId", id);
            Request request = new Request("VariableController/variable", "/{varId}/", parametros);
            request.delete();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            return new Respuesta(true, "", "");
        } catch (Exception ex) {
            Logger.getLogger(VariableService.class.getName()).log(Level.SEVERE, "Error eliminando la variable", ex);
            return new Respuesta(false, "Error eliminando la variable.", "eliminarVariable: " + ex.getMessage());
        }
    }
}
