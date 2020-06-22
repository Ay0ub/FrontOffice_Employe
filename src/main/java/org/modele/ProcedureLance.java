package org.modele;

import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;

public class ProcedureLance {
    private String numero;
    private String  nom;
    private String date;
    private ProgressBar avcm;
    private Button consulter;

    public ProcedureLance() {
        avcm = new ProgressBar();
        consulter = new Button("Consulter");
        consulter.setStyle( "-fx-background-color: #FF9900");
    }

    public ProcedureLance(String nom) {
        this.nom = nom;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ProgressBar getAvcm() {
        return avcm;
    }

    public void setAvcm(ProgressBar avcm) {
        this.avcm = avcm;
    }

    public Button getConsulter() {
        return consulter;
    }

    public void setConsulter(Button consulter) {
        this.consulter = consulter;
    }


    @Override
    public String toString() {
        return "Procedure : " + nom;
    }
}
