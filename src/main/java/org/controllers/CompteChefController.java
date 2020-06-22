package org.controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.metier.beansManager.LoginManager;

import java.net.URL;
import java.util.ResourceBundle;

public class CompteChefController implements Initializable {
    public Label numero;
    public Label nom;
    public Label prenom;
    public Label email;
    public Label tel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        numero.setText(String.valueOf(LoginManager.getChef().getNumero()));
        nom.setText(LoginManager.getChef().getNom());
        prenom.setText(LoginManager.getChef().getPrenom());
        email.setText(LoginManager.getChef().getEmail());
        tel.setText(LoginManager.getChef().getTel());
    }
}
