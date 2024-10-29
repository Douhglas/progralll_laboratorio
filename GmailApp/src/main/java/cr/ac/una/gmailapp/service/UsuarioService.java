package cr.ac.una.gmailapp.service;

import cr.ac.una.gmailapp.model.UsuarioDto;
import cr.ac.una.gmailapp.util.Request;
import cr.ac.una.gmailapp.util.Respuesta;
import jakarta.ws.rs.core.GenericType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuarioService {

    private static final Logger LOG = Logger.getLogger(UsuarioService.class.getName());

    public Respuesta getUsuario(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);

            Request request = new Request("UsuarioController/usuario", "/{id}/", parametros);
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            UsuarioDto usuario = (UsuarioDto) request.readEntity(UsuarioDto.class);

            return new Respuesta(true, "", "", "Usuario", usuario);

        } catch (Exception ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Error obteniendo el usuario [" + id + "]", ex);
            return new Respuesta(false, "Error al obtener el usuario.", "getUsuario " + ex.getMessage());
        }
    }

    public Respuesta getUsuarios(String nombre, String primerApellido, String segundoApellido, String cedula, String telefono, String correo) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("nombre", nombre);
            parametros.put("primerApellido", primerApellido);
            parametros.put("segundoApellido", segundoApellido);
            parametros.put("cedula", cedula);
            parametros.put("telefono", telefono);
            parametros.put("correo", correo);

            Request request = new Request("UsuarioController/usuarios", "/{nombre}/{primerApellido}/{segundoApellido}/{cedula}/{telefono}/{correo}", parametros);
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            List<UsuarioDto> mensajes = (List<UsuarioDto>) request.readEntity(new GenericType<List<UsuarioDto>>() {
            });
            return new Respuesta(true, "", "", "Usuarios", mensajes);

        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error al obtener los usuarios.", ex);
            return new Respuesta(false, "Error al obtener los usuarios.", "getUsuarios " + ex.getMessage());
        }
    }

    public Respuesta getTodosLosUsuarios() {
        try {
            Request request = new Request("UsuarioController/usuarios/todos");
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            List<UsuarioDto> usuarios = (List<UsuarioDto>) request.readEntity(new GenericType<List<UsuarioDto>>() {
            });

            return new Respuesta(true, "", "", "TodosLosUsuarios", usuarios);

        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error al obtener todos los usuarios.", ex);
            return new Respuesta(false, "Error al obtener todos los usuarios.", "getTodosLosUsuarios " + ex.getMessage());
        }
    }

    public Respuesta guardarUsuario(UsuarioDto usuarioDto) {

        try {
            Request request = new Request("UsuarioController/usuario");
            request.post(usuarioDto);
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            UsuarioDto usuario = (UsuarioDto) request.readEntity(UsuarioDto.class);
            return new Respuesta(true, " ", " ", "Usuario", usuario);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Error guardando el usuario [" + usuarioDto.getNombre() + "]", ex);
            return new Respuesta(false, "Error guardando el usuario.", "getUsuario " + ex.getMessage());
        }
    }

    public Respuesta validateUser(String usuario, String clave) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("usuario", usuario);
            parametros.put("clave", clave);
            Request request = new Request("UsuarioController/usuario", "/{usuario}/{clave}", parametros);
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            UsuarioDto user = (UsuarioDto) request.readEntity(UsuarioDto.class);
            return new Respuesta(true, "", "", "Usuario", user);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Error obteniendo el usuario", ex);
            return new Respuesta(false, "Error obteniendo el user.", "validaruser" + ex.getMessage());
        }
    }

    public Respuesta validarRole(Long id, String sistema, String rol) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            parametros.put("sistema", sistema);
            parametros.put("rol", rol);

            Request request = new Request("UsuarioController/usuario", "/{id}/{sistema}/{rol}/", parametros);
            request.get();

            if (!request.isError()) {
                return new Respuesta(true, "Usuario tiene el rol necesario para ingresar", "validateRole", "UserRole", true);
            } else {
                return new Respuesta(false, "El usuario no tiene los permisos necesarios", "validateRole");
            }

        } catch (Exception ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Error validando el usuario.", ex);
            return new Respuesta(false, "Error validando el usuario.", "validarRol" + ex.getMessage());
        }
    }
}
