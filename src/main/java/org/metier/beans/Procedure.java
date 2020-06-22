package org.metier.beans;

import java.util.ArrayList;

public class Procedure {
    private int numero;
    private String cin;
    private String nom;
    private String date;
    private String etat;
    private Etape etapeActuelle;
    private ArrayList<Document> listeDocuments;

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
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

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Etape getEtapeActuelle() {
        return etapeActuelle;
    }

    public void setEtapeActuelle(Etape etapeActuelle) {
        this.etapeActuelle = etapeActuelle;
    }

    public ArrayList<Document> getListeDocuments() {
        return listeDocuments;
    }

    public void setListeDocuments(ArrayList<Document> listeDocuments) {
        this.listeDocuments = listeDocuments;
    }
}
