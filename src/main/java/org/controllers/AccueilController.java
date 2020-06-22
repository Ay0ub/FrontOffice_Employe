package org.controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.metier.beansManager.DemandeManager;
import org.metier.beansManager.LoginManager;

import java.net.URL;
import java.util.ResourceBundle;

public class AccueilController implements Initializable {
    public Label nbDepots;
    public Label nbProcsLancees;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nbDepots.setText(
                DemandeManager.demandes(
                        LoginManager.nomCompletChef().getNumero()) +
                        " Demandes deposées");
        nbProcsLancees.setText(DemandeManager.procedures(
                LoginManager.nomCompletChef().getNumero()) +
                " Demandes Lancées");
    }
}
