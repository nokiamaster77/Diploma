package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Maksym on 12/13/2016.
 */
public class ProcessWindow {

    String title;
    Stage stage;
    Parent root;

    public ProcessWindow(String t) {
        title = t;
    }

    public void start() {
        stage = new Stage();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ProcessWindow.class.getResource("processWindowStyle.fxml"));
            //fxmlLoader.getClassLoader().getClass().getResource("sample/processWindowStyle.fxml");
            root = fxmlLoader.load();
            root.getStylesheets().add(Main.class.getResource("modena.css").toExternalForm());
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            ControllerProcessWindow cpw = fxmlLoader.getController();
            cpw.setTitle(title);
            stage.setOnCloseRequest(e->{
                Thread.currentThread().interrupt();
            });
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Error while creating new process window");
            alert.showAndWait();
        }
    }
}
