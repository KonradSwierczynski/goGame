package tp.project.goGame.client.View;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import tp.project.goGame.client.Controller.LauncherController;

public class Launcher extends Application {
	
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LogInView.fxml"));
            Parent page = (Parent) loader.load();
            Scene scene = new Scene(page);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Launcher");
            LauncherController controller = (LauncherController)loader.getController();
            controller.setStage(primaryStage);
            primaryStage.show();
        } catch (Exception ex) {
            Logger.getLogger(Launcher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
