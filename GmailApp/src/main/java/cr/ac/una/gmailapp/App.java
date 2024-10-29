package cr.ac.una.gmailapp;

import cr.ac.una.gmailapp.util.AppContext;
import cr.ac.una.gmailapp.util.FlowController;
import javafx.application.Application;
import javafx.stage.Stage; 
 

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
      
        FlowController.getInstance().InitializeFlow(stage, null);
        FlowController.getInstance().goViewInWindow("loginView");
        AppContext.getInstance();
        
    }

    public static void main(String[] args) {
        launch();
    }
 
}