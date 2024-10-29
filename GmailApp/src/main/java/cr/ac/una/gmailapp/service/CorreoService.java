package cr.ac.una.gmailapp.service;

import cr.ac.una.gmailapp.model.CorreoDto;
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
public class CorreoService {

    public Respuesta getCorreos() {
        Request request = new Request("CorreosController/correos");
        request.get();

        if (request.isError()) {
            return new Respuesta(false, request.getError(), "");
        }
        List<CorreoDto> correos = (List<CorreoDto>) request.readEntity(new GenericType<List<CorreoDto>>() {
        });
        return new Respuesta(true, "", "", "Correos", correos);
    }

    public Respuesta getCorreosOfSender(Long id) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("senderId", id);

        Request request = new Request("CorreosController/correosBySender", "/{senderId}/", parametros);
        request.get();

        if (request.isError()) {
            return new Respuesta(false, request.getError(), "");
        }
        List<CorreoDto> correos = (List<CorreoDto>) request.readEntity(new GenericType<List<CorreoDto>>() {
        });
        return new Respuesta(true, "", "", "Correos", correos);
    }

    public Respuesta guardarCorreo(CorreoDto empleadoDto) {
        try {
            Request request = new Request("CorreosController/correo");
            request.post(empleadoDto);
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            CorreoDto empleado = (CorreoDto) request.readEntity(CorreoDto.class);
            return new Respuesta(true, "", "", "Correo", empleado);
        } catch (Exception ex) {
            Logger.getLogger(CorreoService.class.getName()).log(Level.SEVERE, "Ocurrio un error al guardar el Correo.", ex);
            return new Respuesta(false, "Ocurrio un error al guardar el Correo.", "guardarCorreoo " + ex.getMessage());
        }
    }

    public Respuesta eliminarCorreo(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();

            parametros.put("correoId", id);
            Request request = new Request("CorreosController/correo", "/{correoId}/", parametros);
            request.delete();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            return new Respuesta(true, "", "");
        } catch (Exception ex) {
            Logger.getLogger(CorreoService.class.getName()).log(Level.SEVERE, "Error eliminando el Correo", ex);
            return new Respuesta(false, "Error eliminando el Correo.", "eliminarCorreo " + ex.getMessage());
        }
    }

    public Respuesta enviarCorreo(String destino, String contenido) {
        try {
            Request request = new Request("CorreosController/enviarCorreo");
            CorreoDto aux = new CorreoDto();
            aux.setDestination(destino);
            aux.setHtml(contenido);
            request.post(aux);
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "Algo no salio bien");
            }
            return new Respuesta(true, "se ha completado el metodo enviarCorreo del cliente", "");
        } catch (Exception ex) {
            Logger.getLogger(CorreoService.class.getName()).log(Level.SEVERE, "Error eliminando el Correo", ex);
            return new Respuesta(false, "Error eliminando el Correo.", "eliminarCorreo " + ex.getMessage());
        }
    }

    public Respuesta activarUsuarioEmail(String destino, String link) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("destino", destino);
        parametros.put("link", link);

        Request request = new Request("CorreosController/activarUsuario", "/{destino}/{link}", parametros);
        request.get();

        if (request.isError()) {
            return new Respuesta(false, request.getError(), "");
        }
        return new Respuesta(true, "se ha completado el metodo activarUsuario", "");
    }

    public Respuesta recuperarPasswordEmail(String destino, String password) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("destino", destino);
        parametros.put("password", password);

        Request request = new Request("CorreosController/recuperarContra", "/{destino}/{password}", parametros);
        request.get();

        if (request.isError()) {
            return new Respuesta(false, request.getError(), "");
        }
        return new Respuesta(true, "se ha completado el metodo activarUsuario", "");
    }
}
