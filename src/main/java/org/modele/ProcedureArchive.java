package org.modele;

import javafx.scene.control.Button;

public class ProcedureArchive {
    private String numero;
    private String nom;
    private String dateD;
    private String dateF;
    private Button supprimer;

    public ProcedureArchive() {
        supprimer = new Button("Supprimer");
        supprimer.setStyle( "-fx-background-color: red");
    }

    public ProcedureArchive(String nom) {
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

    public String getDateD() {
        return dateD;
    }

    public void setDateD(String dateD) {
        this.dateD = dateD;
    }

    public String getDateF() {
        return dateF;
    }

    public void setDateF(String dateF) {
        this.dateF = dateF;
    }

    public Button getSupprimer() {
        return supprimer;
    }

    public void setSupprimer(Button supprimer) {
        this.supprimer = supprimer;
    }

    @Override
    public String toString() {
        return "Procedure : " + nom;
    }

}
