package org.utils;

import javafx.scene.control.Alert;

public class PrintMessage {
    public static void afficherErreur(String header, String content){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText(header);
        errorAlert.setContentText(content);
        errorAlert.showAndWait();
    }
    public static void afficherSucces(String header, String content){
        Alert information = new Alert(Alert.AlertType.INFORMATION);
        information.setHeaderText(header);
        information.setContentText(content);
        information.showAndWait();
    }
}
