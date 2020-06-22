package org.controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.metier.beans.Employe;
import org.metier.beansManager.LoginManager;
import org.metier.beansManager.ProcedureManager;

import java.net.URL;
import java.util.ResourceBundle;

public class AccueilEmployeController implements Initializable {
    public Label nbProcsAtraiter;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Employe employe = LoginManager.getEmploye();
        nbProcsAtraiter.setText(String.valueOf(ProcedureManager.getAllProcedureByEmploye(employe).size()));
    }
}
