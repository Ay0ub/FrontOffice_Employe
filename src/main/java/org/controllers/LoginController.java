package org.controllers;


import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.metier.beansManager.LoginManager;

public class LoginController {
    public Button loginBoutton;
    public Button logoutButton;
    public TextField password;
    public TextField username;


    public void login() {
        String usernameText = username.getText();
        String passwordText = password.getText();
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        if (usernameText.isEmpty() || passwordText.isEmpty()) {

            errorAlert.setHeaderText("Aucun nom ou password");
            errorAlert.setContentText("Veillez remplire tous les champs");
            errorAlert.showAndWait();
        }
        else
        {
            boolean chef = LoginManager.validationChefDivision(usernameText,passwordText);
            boolean employe = LoginManager.validationEmploye(usernameText,passwordText);
            if (chef)
            {
                MenuChefController.menu();
            }
            else if (employe)
             {
                 MenuEmployeController.menu();
             }else {
                errorAlert.setHeaderText("Mauvaise nom ou mot de passe");
                errorAlert.setContentText("Veillez entrer le bon nom ou mot de passe");
                errorAlert.showAndWait();
            }
        }

    }

    public void exit() {
        System.exit(0);
    }
}
