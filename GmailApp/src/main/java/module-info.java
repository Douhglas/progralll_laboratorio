module cr.ac.una.gmailapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.logging;
    requires cr.ac.una.homework;
     
    
    requires javafx.web;
    requires MaterialFX;
    exports cr.ac.una.gmailapp;
     opens cr.ac.una.gmailapp.controllers to javafx.fxml;
      requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires org.apache.xmlbeans; 
     opens cr.ac.una.gmailapp.model to javafx.base; 
    requires jakarta.json;
     exports cr.ac.una.gmailapp.model;
     requires jakarta.xml.bind;  // For JAXB
    requires jakarta.mail;
    // Jersey client
    requires jersey.client;
    requires jersey.hk2;
    requires jersey.media.json.binding;
    requires jakarta.ws.rs;
    requires com.google.gson;
}
