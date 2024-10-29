package cr.ac.una.gmailapp.model;

import java.time.LocalDate;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author rocic
 */
public class UsuarioDto {


    public SimpleStringProperty id = new SimpleStringProperty("");
    public SimpleBooleanProperty estado = new SimpleBooleanProperty(false);
    public SimpleStringProperty estadoStringProperty = new SimpleStringProperty("");
    public ObjectProperty<LocalDate> fechaRegistro = new SimpleObjectProperty<>();
    public SimpleStringProperty nombre = new SimpleStringProperty("");
    public SimpleStringProperty primerApellido = new SimpleStringProperty("");
    public SimpleStringProperty segundoApellido = new SimpleStringProperty("");
    public SimpleStringProperty cedula = new SimpleStringProperty("");
    public SimpleStringProperty correo = new SimpleStringProperty("");
    public SimpleStringProperty clave = new SimpleStringProperty("");
    public SimpleStringProperty telefono = new SimpleStringProperty("");
    public SimpleStringProperty celular = new SimpleStringProperty("");
    public ObjectProperty<byte[]> foto = new SimpleObjectProperty<>();
    public SimpleStringProperty idiomaPreferido = new SimpleStringProperty("");
    public ObjectProperty<LocalDate> fechaSalida = new SimpleObjectProperty<>();
    public SimpleStringProperty usuario = new SimpleStringProperty("");
    public Long version;


    public UsuarioDto() {
    }

 

    // Getters y Setters con propiedades
    public Long getId() {
         if (this.id.get() != null && !this.id.get().isEmpty()) {
            return Long.valueOf(this.id.get());
        } else {
            return null;
        }
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getEstado() {
        return estado.get() ? "A" : "I";
    }

    public SimpleBooleanProperty estadoProperty() {
        return estado;
    }

    public void setEstado(String estado) {
       this.estado.set(estado.equals("A"));
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro.get();
    }

    public ObjectProperty<LocalDate> fechaRegistroProperty() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro.set(fechaRegistro);
    }

    public String getNombre() {
        return nombre.get();
    }

    public SimpleStringProperty nombreProperty() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public String getPrimerApellido() {
        return primerApellido.get();
    }

    public SimpleStringProperty primerApellidoProperty() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido.set(primerApellido);
    }

    public String getSegundoApellido() {
        return segundoApellido.get();
    }

    public SimpleStringProperty segundoApellidoProperty() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido.set(segundoApellido);
    }

    public String getCedula() {
        return cedula.get();
    }

    public SimpleStringProperty cedulaProperty() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula.set(cedula);
    }

    public String getCorreo() {
        return correo.get();
    }

    public SimpleStringProperty correoProperty() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo.set(correo);
    }

    public String getClave() {
        return clave.get();
    }

    public SimpleStringProperty claveProperty() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave.set(clave);
    }

    public String getTelefono() {
        return telefono.get();
    }

    public SimpleStringProperty telefonoProperty() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono.set(telefono);
    }

    public String getCelular() {
        return celular.get();
    }

    public SimpleStringProperty celularProperty() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular.set(celular);
    }

    public byte[] getFoto() {
        return foto.get();
    }

    public ObjectProperty<byte[]> fotoProperty() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto.set(foto);
    }

    public String getIdiomaPreferido() {
        return idiomaPreferido.get();
    }

    public SimpleStringProperty idiomaPreferidoProperty() {
        return idiomaPreferido;
    }

    public void setIdiomaPreferido(String idiomaPreferido) {
        this.idiomaPreferido.set(idiomaPreferido);
    }

    public LocalDate getFechaSalida() {
        return fechaSalida.get();
    }

    public ObjectProperty<LocalDate> fechaSalidaProperty() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida.set(fechaSalida);
    }

    public String getUsuario() {
        return usuario.get();
    }

    public SimpleStringProperty usuarioProperty() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario.set(usuario);
    }

   public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
    
}
