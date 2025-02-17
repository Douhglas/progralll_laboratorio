/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.unaplanillaws.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author cbcar
 */
public class TipoPlanillaDto {
    
   @Schema(description = "Id del tipo de planilla", example = "1")
    private Long id;

    @Schema(description = "Código del tipo de planilla", example = "PLN001")
    public String codigo;

    @Schema(description = "Descripción del tipo de planilla", example = "Planilla Mensual")
    public String descripcion;

    @Schema(description = "Cantidad de planillas por mes", example = "2")
    public Integer planillaPorMes;

    @Schema(description = "Año de la última planilla", example = "2024")
    private Integer anoUltimaPlanilla;

    @Schema(description = "Mes de la última planilla", example = "8")
    private Integer mesUltimaPlanilla;

    @Schema(description = "Número de la última planilla", example = "15")
    private Integer numeroUltimaPlanilla;

    @Schema(description = "Estado del tipo de planilla", example = "Activo", allowableValues = "Activo, Inactivo")
    public String estado;

    @Schema(description = "Versión de registro del tipo de planilla", example = "1")
    private Long version;

    @Schema(description = "Indica si el registro fue modificado", example = "True")
    private Boolean modificado;

    @Schema(description = "Lista de empleados asociados al tipo de planilla")
    private List<EmpleadoDto> empleados;

    @Schema(description = "Lista de empleados eliminados del tipo de planilla")
    private List<EmpleadoDto> empleadosEliminados;

    private String token;
    
    public TipoPlanillaDto() {
        this.empleados = new ArrayList<>();
        this.empleadosEliminados = new ArrayList<>();
    }

    public TipoPlanillaDto(TipoPlanilla tipoPlanilla) {
        this();
        this.id = tipoPlanilla.getId();
        this.codigo = tipoPlanilla.getCodigo();
        this.descripcion = tipoPlanilla.getDescripcion();
        this.planillaPorMes = tipoPlanilla.getPlanillaPorMes();
        this.anoUltimaPlanilla = tipoPlanilla.getAnoUltimaPlanilla();
        this.mesUltimaPlanilla = tipoPlanilla.getMesUltimaPlanilla();
        this.numeroUltimaPlanilla = tipoPlanilla.getNumeroUltimaPlanilla();
        this.estado = tipoPlanilla.getEstado();
        this.version = tipoPlanilla.getVersion();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getPlanillaPorMes() {
        return planillaPorMes;
    }

    public void setPlanillaPorMes(Integer planillaPorMes) {
        this.planillaPorMes = planillaPorMes;
    }

    public Integer getAnoUltimaPlanilla() {
        return anoUltimaPlanilla;
    }

    public void setAnoUltimaPlanilla(Integer anoUltimaPlanilla) {
        this.anoUltimaPlanilla = anoUltimaPlanilla;
    }

    public Integer getMesUltimaPlanilla() {
        return mesUltimaPlanilla;
    }

    public void setMesUltimaPlanilla(Integer mesUltimaPlanilla) {
        this.mesUltimaPlanilla = mesUltimaPlanilla;
    }

    public Integer getNumeroUltimaPlanilla() {
        return numeroUltimaPlanilla;
    }

    public void setNumeroUltimaPlanilla(Integer numeroUltimaPlanilla) {
        this.numeroUltimaPlanilla = numeroUltimaPlanilla;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
    
    public Boolean getModificado() {
        return modificado;
    }

    public void setModificado(Boolean modificado) {
        this.modificado = modificado;
    }

    public List<EmpleadoDto> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<EmpleadoDto> empleados) {
        this.empleados = empleados;
    }

    public List<EmpleadoDto> getEmpleadosEliminados() {
        return empleadosEliminados;
    }

    public void setEmpleadosEliminados(List<EmpleadoDto> empleadosEliminados) {
        this.empleadosEliminados = empleadosEliminados;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TipoPlanillaDto other = (TipoPlanillaDto) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "TipoPlanillaDto{" + "codigo=" + codigo + ", descripcion=" + descripcion + '}';
    }

}
