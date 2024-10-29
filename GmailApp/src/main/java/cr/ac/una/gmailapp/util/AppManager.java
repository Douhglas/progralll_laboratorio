package cr.ac.una.gmailapp.util;

import cr.ac.una.gmailapp.model.Admin;
import cr.ac.una.gmailapp.model.CorreoDto;
import cr.ac.una.gmailapp.model.ProcesoDto;
import cr.ac.una.gmailapp.model.SenderDto;
import cr.ac.una.gmailapp.service.AdminService;
import cr.ac.una.gmailapp.service.CorreoService;
import cr.ac.una.gmailapp.service.ProcessService;
import cr.ac.una.gmailapp.service.SenderService;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author stward segura
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AppManager {

    private static AppManager INSTANCE = null;
    private ObservableList<ProcesoDto> processes = FXCollections.observableArrayList();
    private ObservableList<CorreoDto> emails = FXCollections.observableArrayList();
    private Admin administrador = new Admin();
    private SenderService senderService = new SenderService();
    private ProcessService processService = new ProcessService();
    private CorreoService correoService = new CorreoService();
    private AdminService adminService = new AdminService();
    
    public Admin getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Admin administrador) {
        this.administrador = administrador;
    }

    private AppManager() {
        loadAdmin();
        loadEmails();
        loadProcesses();
    }

    private static void createInstance() {
        if (INSTANCE == null) {
            synchronized (FlowController.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppManager();
                }
            }
        }
    }

    public static AppManager getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    public ObservableList<ProcesoDto> getProcesses() {
        return processes;
    }

    public void setProcesses(List<ProcesoDto> processes) {
        // Convertimos la lista normal a una lista observable
        this.processes.setAll(processes);
    }

    public ObservableList<CorreoDto> getEmails() {
        return emails;
    }

    public void setEmails(List<CorreoDto> emails) {
        // Convertimos la lista normal a una lista observable
        this.emails.setAll(emails);
    }

    public void saveProcess(ProcesoDto proceso) {
        Respuesta respuesta = processService.guardarProceso(proceso);
        if (respuesta.getEstado()) {
            processes.remove(proceso);
            ProcesoDto process = (ProcesoDto) respuesta.getResultado("Proceso");
            processes.add(process);
            System.out.println("el id es:"+ process.getProcessId());
        } else {
            System.err.println("Error al cargar proceso: " + respuesta.getMensaje());
        }
    }

    public void loadProcesses() {
        Respuesta respuesta = processService.getProcesos();
        if (respuesta.getEstado()) {
            List<ProcesoDto> procesos = (List<ProcesoDto>) respuesta.getResultado("Procesos");
            setProcesses(procesos);  // Casteamos la lista normal a una observable
        } else {
            System.err.println("Error al cargar procesos: " + respuesta.getMensaje());
        }
    }

    public void loadEmails() {
        Respuesta respuesta = correoService.getCorreos();  
        if (respuesta.getEstado()) {
            List<CorreoDto> correos = (List<CorreoDto>) respuesta.getResultado("Correos");
            setEmails(correos);  
        } else {         
            System.err.println("Error al cargar correos: " + respuesta.getMensaje());
        }
    }

    public SenderDto saveSender(SenderDto senderDto) {
        Respuesta respuesta = senderService.guardarSender(senderDto);  
        if (respuesta.getEstado()) {
            SenderDto savedSender = (SenderDto) respuesta.getResultado("Sender");
            return savedSender;
        } else {
            System.err.println("Error al guardar el sender: " + respuesta.getMensaje());
        }    
        return null;
    }

    public void deleteCorreo(Long correoId) {
        Respuesta respuesta = correoService.eliminarCorreo(correoId);  
        if (respuesta.getEstado()) {
            emails.removeIf(correo -> correo.getCorreoId().equals(correoId));
        } else {
            System.err.println("Error al eliminar el correo: " + respuesta.getMensaje());
        }
    }

    public void deleteProcess(Long processId) {
        Respuesta respuesta = processService.eliminarProceso(processId);  
        if (respuesta.getEstado()) {
            processes.removeIf(process -> process.getProcessId().equals(processId));
        } else {
            System.err.println("Error al eliminar el proceso: " + respuesta.getMensaje());
        }
    }
    
    public void loadAdmin() {
        Respuesta res = adminService.getAdmin(1L);
        if (res.getEstado()) {
            administrador = (Admin) res.getResultado("Administrador");
            System.out.println(administrador.getEmail() + "emailll");
        } else {
            System.out.println("Error al obtener admin");
        }
    }
    
    public void saveAdmin() {
        Respuesta res = adminService.guardarAdmin(administrador);
        if (res.getEstado()) {
            administrador = (Admin) res.getResultado("Administrador");
            System.out.println("Guardado el admin");
        } else {
            System.out.println("Error al guardar admin");
        }  
    }
    
    public void enviarCorreo(String destiny, String contenido){
         Respuesta res =  correoService.enviarCorreo(destiny,contenido);
        if (res.getEstado()) {
            
            System.out.println("Resultado sin error"+ res.getMensaje());
        } else {
            System.out.println("resultado con error al enviar correo");
        }  
    }
    
    
    public void enviarCorreos(SenderDto sender){
         Respuesta res =  senderService.startSender(sender);
        if (res.getEstado()) {  
            System.out.println("Resultado sin error"+ res.getMensaje());
        } else {
            System.out.println("resultado con error al enviar correo");
        }  
    } 
    
    public void getCorreosOfSender(Long id){
          Respuesta res =  correoService.getCorreosOfSender(id);
        if (res.getEstado()) {  
            List<CorreoDto> correos = (List<CorreoDto>) res.getResultado("Correos");
            emails.addAll(correos);
            System.out.println("Resultado sin error para el getCorreosOfSender"+ res.getMensaje());
        } else {
            System.out.println("resultado con erro al obtener correos del sender");
        }  
    }
}
