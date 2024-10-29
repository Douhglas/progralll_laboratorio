package cr.ac.una.gmailapp.service;

import cr.ac.una.gmailapp.model.ProcesoDto;
import cr.ac.una.gmailapp.util.Request;
import cr.ac.una.gmailapp.util.Respuesta;
import jakarta.ws.rs.core.GenericType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author stwar
 */
public class ProcessService {
    
    
    public Respuesta getProceso(Long id){
        try{ 
             Map<String, Object> parametros = new HashMap<>();

            parametros.put("processId", id);
            Request request = new Request("ProcessController/proceso","{processId}", parametros);
            request.get();
            if (request.isError()){
                return new Respuesta(false, request.getError(), "");
            }
            ProcesoDto pro = (ProcesoDto) request.readEntity(ProcesoDto.class);
    
            return new Respuesta(true,"","", "Proceso", pro);
        } catch (Exception ex) {
            Logger.getLogger(ProcessService.class.getName()).log(Level.SEVERE, "Error obteniendo el tipo proceso [" + id + "]", ex);
            return new Respuesta(false, "Error obteniendo el tipo de planilla.", "getPRocesp" + ex.getMessage());
        } 
    }
    

    public Respuesta getProcesos() {
      
         
        Request request = new Request("ProcessController/procesos");
        request.get();

        if (request.isError()) {
            return new Respuesta(false, request.getError(), "");
        }
        List<ProcesoDto> processDto = (List<ProcesoDto>) request.readEntity(new GenericType<List<ProcesoDto>>() {
        });
        return new Respuesta(true, "", "", "Procesos", processDto);
    }

    public Respuesta guardarProceso(ProcesoDto processDto) {
        try {
            Request request = new Request("ProcessController/proceso");
            request.post(processDto);
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            ProcesoDto process = (ProcesoDto) request.readEntity(ProcesoDto.class);
            return new Respuesta(true, "", "", "Proceso", process);
        } catch (Exception ex) {
            Logger.getLogger(ProcessService.class.getName()).log(Level.SEVERE, "Ocurrio un error al guardar el Proceso.", ex);
            return new Respuesta(false, "Ocurrio un error al guardar el proceso.", "guardarProceso " + ex.getMessage());
        }
    }

    public Respuesta eliminarProceso(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();

            parametros.put("processId", id);
            Request request = new Request("ProcessController/proceso", "/{processId}/", parametros);
            request.delete();
            if(request.isError()){
                  return new Respuesta(false, request.getError(), "");
            }
            return new Respuesta(true, "", "");
        } catch (Exception ex) {
            Logger.getLogger(CorreoService.class.getName()).log(Level.SEVERE, "Error eliminando el proceso", ex);
            return new Respuesta(false, "Error eliminando el Proceso.", "eliminarProceso " + ex.getMessage());
        }
    }
}
