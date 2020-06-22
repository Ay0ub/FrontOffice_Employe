package org.controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.metier.beansManager.LoginManager;
import java.net.URL;
import java.util.ResourceBundle;

public class  compteController implements Initializable {

    public Label numero;
    public Label nom;
    public Label prenom;
    public Label tel;
    public Label email;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        numero.setText(String.valueOf(LoginManager.getEmploye().getNumero()));
        nom.setText(LoginManager.getEmploye().getNom());
        prenom.setText(LoginManager.getEmploye().getPrenom());
        tel.setText(LoginManager.getEmploye().getTel());
        email.setText(LoginManager.getEmploye().getEmail());
    }
}
