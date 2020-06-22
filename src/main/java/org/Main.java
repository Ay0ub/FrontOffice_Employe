package org;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * JavaFX App
 */
public class Main extends Application {

    public static Stage MainStage;
    @Override
    public void start(Stage stage) throws Exception {
        MainStage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
        //MainStage.initStyle(StageStyle.UNDECORATED);
        MainStage.setScene(new Scene(root));
        MainStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}