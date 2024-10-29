package cr.ac.una.gmailapp.service;

import cr.ac.una.gmailapp.model.SenderDto;
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
public class SenderService {
    
     public Respuesta guardarSender(SenderDto sender) {
        try {
            Request request = new Request("SenderController/sender");
            request.post(sender);
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            SenderDto senderDto = (SenderDto) request.readEntity(SenderDto.class);
            return new Respuesta(true, "", "", "Sender", senderDto);
        } catch (Exception ex) {
            Logger.getLogger(CorreoService.class.getName()).log(Level.SEVERE, "Ocurrio un error al guardar el senderDto.", ex);
            return new Respuesta(false, "Ocurrio un error al guardar el senderDto.", "guardarsenderDto " + ex.getMessage());
        }
    }

     public Respuesta getSender(Long id){
        try{ 
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("senderId", id);
            Request request = new Request("SenderController/sender","{senderId}", parametros);
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            SenderDto pro = (SenderDto) request.readEntity(SenderDto.class);    
            return new Respuesta(true,"","", "Sender", pro);
        } catch (Exception ex) {
            Logger.getLogger(ProcessService.class.getName()).log(Level.SEVERE, "Error obteniendo el tipo sender [" + id + "]", ex);
            return new Respuesta(false, "Error obteniendo el tipo de planilla.", "getPRocesp" + ex.getMessage());
        } 
    }
     
     
      public Respuesta startSender(SenderDto sender) {
        try {
            
         
            Request request = new Request("SenderController/processSender");
            request.post(sender);
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            SenderDto senderDto = (SenderDto) request.readEntity(SenderDto.class);
            return new Respuesta(true, "", "", "Sender", senderDto);
        } catch (Exception ex) {
            Logger.getLogger(CorreoService.class.getName()).log(Level.SEVERE, "Ocurrio un error al guardar el senderDto.", ex);
            return new Respuesta(false, "Ocurrio un error al guardar el senderDto.", "guardarsenderDto " + ex.getMessage());
        }
    }
}
