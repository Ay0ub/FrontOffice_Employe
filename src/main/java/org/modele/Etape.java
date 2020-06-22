package org.modele;

import javafx.scene.control.Button;

public class Etape {
    private int numero;
    private String nom;
    private String responsable;
    private String contact;
    private String etat;
    private String date;
    private Button consulter;

    public Etape() {
        consulter = new Button("Consulter Rapport");
        consulter.setStyle( "-fx-background-color: white");
    }

    public int getNumero() {
        return numero;
    }

    public Button getConsulter() {
        return consulter;
    }

    public void setConsulter(Button consulter) {
        this.consulter = consulter;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
