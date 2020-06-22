package org.modele;

import javafx.scene.control.Button;

public class Demande {
    private String numero;
    private String nom;
    private String date;
    private Button consulter;

    public Demande() {
        consulter = new Button("Consulter");
        consulter.setStyle( "-fx-background-color: #FF9900");
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

    public Button getConsulter() {
        return consulter;
    }

    public void setConsulter(Button consulter) {
        this.consulter = consulter;
    }
}
