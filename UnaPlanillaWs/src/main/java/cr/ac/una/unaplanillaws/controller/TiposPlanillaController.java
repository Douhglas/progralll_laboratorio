package cr.ac.una.unaplanillaws.controller;

import cr.ac.una.unaplanillaws.model.EmpleadoDto;
import cr.ac.una.unaplanillaws.model.TipoPlanillaDto;
import cr.ac.una.unaplanillaws.service.EmpleadoService;
import cr.ac.una.unaplanillaws.service.TiposPlanillaService;
import cr.ac.una.unaplanillaws.util.CodigoRespuesta;
import cr.ac.una.unaplanillaws.util.JwTokenHelper;
import cr.ac.una.unaplanillaws.util.Respuesta;
import cr.ac.una.unaplanillaws.util.Secure;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Secure 
@Path("/TiposPlanillaController")
@Tag(name = "TiposPlanilla", description = "Operaciones sobre tipos de planilla")
@SecurityRequirement(name = "jwt-auth")
public class TiposPlanillaController {

    @EJB
    TiposPlanillaService tiposPlanillaService;

    @GET
    @Path("/tipoPlanilla/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTipoPlanilla(@PathParam("id") Long id) {
        try {
            Respuesta res = tiposPlanillaService.getTipoPlanilla(id);
            if (!res.getEstado()) {
                return Response.status(res.getCodigoRespuesta().getValue()).entity(res.getMensaje()).build();
            }
            TipoPlanillaDto tipoPlanillaDto = (TipoPlanillaDto) res.getResultado("TipoPlanilla");
            //tipoPlanillaDto.setToken();
            return Response.ok(tipoPlanillaDto).build();
        } catch (Exception ex) {
            Logger.getLogger(TiposPlanillaController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error obteniendo el tipo de planilla").build();
        }
    }

    @GET
    @Path("/tiposPlanillas/{codigo}/{descripcion}/{planillaPorMes}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getTiposPlanillas(@PathParam("codigo") String codigo, @PathParam("descripcion") String descripcion, @PathParam("planillaPorMes") String planillaPorMes) {
        try {
            Respuesta res = tiposPlanillaService.getTiposPlanillas(codigo, descripcion, planillaPorMes);
            if (!res.getEstado()) {
                return Response.status(res.getCodigoRespuesta().getValue()).entity(res.getMensaje()).build();
            }
            return Response.ok(new GenericEntity<List<TipoPlanillaDto>>((List<TipoPlanillaDto>) res.getResultado("TiposPlanillas")) {
            }).build();
        } catch (Exception ex) {
            Logger.getLogger(TiposPlanillaController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error obteniendo el tipoPlanilla").build();
        }
    }

    @POST
    @Path("/tipoPlanilla")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response guardarTipoPlanilla(TipoPlanillaDto tipoPlanillaDto) {
        try {
            Respuesta res = tiposPlanillaService.guardarTipoPlanilla(tipoPlanillaDto);
            if (!res.getEstado()) {
                return Response.status(res.getCodigoRespuesta().getValue()).entity(res.getMensaje()).build();
            }

            return Response.ok((TipoPlanillaDto) res.getResultado("TipoPlanilla")).build();
        } catch (Exception ex) {
            Logger.getLogger(TiposPlanillaController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error obteniendo el tipoPlanilla").build();
        }

    }

    @DELETE
    @Path("/tipoPlanilla/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarTipoPlanilla(@PathParam("id") Long id) {
        try {
            Respuesta res = tiposPlanillaService.eliminarTipoPlanilla(id);
            if (!res.getEstado()) {
                return Response.status(res.getCodigoRespuesta().getValue()).entity(res.getMensaje()).build();
            }
            return Response.ok().build();
        } catch (Exception ex) {
            Logger.getLogger(TiposPlanillaController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error eliminando el tipoPlanilla").build();
        }
    }
}
