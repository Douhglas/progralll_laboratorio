 
package cr.ac.una.gmailapp.service;

import cr.ac.una.gmailapp.model.Admin;
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
public class AdminService {
     public Respuesta getAdmin(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("adminId", id);
            Request request = new Request("AdminController/admin", "{adminId}", parametros);
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            Admin admin = (Admin) request.readEntity(Admin.class);
            return new Respuesta(true, "", "", "Administrador", admin);
        } catch (Exception ex) {
            Logger.getLogger(AdminService.class.getName()).log(Level.SEVERE, "Error obteniendo el administrador [" + id + "]", ex);
            return new Respuesta(false, "Error obteniendo el administrador.", "getAdmin: " + ex.getMessage());
        }
    }
 
    public Respuesta guardarAdmin(Admin admin) {
        try {
            Request request = new Request("AdminController/admin");
            request.post(admin);
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            Admin savedAdmin = (Admin) request.readEntity(Admin.class);
            return new Respuesta(true, "", "", "Administrador", savedAdmin);
        } catch (Exception ex) {
            Logger.getLogger(AdminService.class.getName()).log(Level.SEVERE, "Ocurrió un error al guardar el administrador.", ex);
            return new Respuesta(false, "Ocurrió un error al guardar el administrador.", "guardarAdmin: " + ex.getMessage());
        }
    }

    public Respuesta eliminarAdmin(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("adminId", id);
            Request request = new Request("AdminController/admin", "/{adminId}/", parametros);
            request.delete();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            return new Respuesta(true, "", "");
        } catch (Exception ex) {
            Logger.getLogger(AdminService.class.getName()).log(Level.SEVERE, "Error eliminando el administrador", ex);
            return new Respuesta(false, "Error eliminando el administrador.", "eliminarAdmin: " + ex.getMessage());
        }
    }
}
