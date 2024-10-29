package cr.ac.una.gmailapp.controllers;

import cr.ac.una.gmailapp.model.UsuarioDto;
import cr.ac.una.gmailapp.service.UsuarioService;
import cr.ac.una.gmailapp.util.AppContext;
import cr.ac.una.gmailapp.util.FlowController;
import cr.ac.una.gmailapp.util.Mensaje;
import cr.ac.una.gmailapp.util.Respuesta;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;


public class LoginController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private VBox loginHBox;
    @FXML
    private ImageView imgLogo;
    @FXML
    private ImageView imgUsuario;
    @FXML
    private MFXTextField txtUsuario;
    @FXML
    private ImageView imgContrasena;
    @FXML
    private MFXPasswordField psfClave;
    
     
    private UsuarioService usuarioService;
    
    
    @FXML
    private Button btnIngresar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
    }    


    @FXML
    private void onActionBtnIngresar(ActionEvent event) {
        if (!txtUsuario.getText().isEmpty() && !psfClave.getText().isEmpty()) {
            Respuesta respuesta = usuarioService.validateUser(txtUsuario.getText(), psfClave.getText());
            if (!respuesta.getEstado()) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Login", getStage(), "Ocurrio un error validando el usuario");
                return;
            }
            UsuarioDto usuario = (UsuarioDto) respuesta.getResultado("Usuario");
            if(respuesta.getEstado() && usuario.getEstado().equals("A")){
            Respuesta respuesta2 = usuarioService.validarRole(usuario.getId(), "Sistema de correo", "Administrador"); 
            if (respuesta2.getEstado()) {
                
                AppContext.getInstance().set("admin?", true);   
                FlowController.getInstance().goMain();
                getStage().close(); 
                return;
            }
          
            Respuesta respuesta3 = usuarioService.validarRole(usuario.getId(), "Sistema de correo", "Normal");
            if (respuesta3.getEstado()) {
               
                AppContext.getInstance().set("admin?", false);  
                FlowController.getInstance().goMain();
                getStage().close();
                return;
            }   
            Mensaje.show(Alert.AlertType.INFORMATION, "Login", "El usuario no tiene roles validos para entrar a GmailApp");
        }else{
             new Mensaje().showModal(Alert.AlertType.ERROR, "Login", getStage(), "El usuario no esta acitivo");
        }
        }else{
             Mensaje.show(Alert.AlertType.INFORMATION, "Login", "Llena los campos requeridos");
        }
    }
    
    @Override
    public void initialize() {
        usuarioService = new UsuarioService();
        txtUsuario.clear();
        psfClave.clear();
    }



    @FXML
    private void onActionHLinkRecuperarContrasena(ActionEvent event) {
    }

    @FXML
    private void onActionHLinkRegistrarme(ActionEvent event) {
       
    }
    
}
