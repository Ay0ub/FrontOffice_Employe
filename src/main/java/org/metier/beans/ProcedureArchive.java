package org.metier.beans;

import java.util.ArrayList;

public class ProcedureArchive {
    private int numero;
    private String nom;
    private String dateDebut;
    private String dateFin;
    private String cin;
    private int numP;
    private int idChef;
    private String etapeActuelle;
    private ArrayList<Etape> etapes;
    private ArrayList<Document> documents;

    public int getIdChef() {
        return idChef;
    }

    public void setIdChef(int idChef) {
        this.idChef = idChef;
    }

    public int getNumero() {
        return numero;
    }

    public int getNumP() {
        return numP;
    }

    public void setNumP(int numP) {
        this.numP = numP;
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

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getEtapeActuelle() {
        return etapeActuelle;
    }

    public void setEtapeActuelle(String etapeActuelle) {
        this.etapeActuelle = etapeActuelle;
    }

    public ArrayList<Etape> getEtapes() {
        return etapes;
    }

    public void setEtapes(ArrayList<Etape> etapes) {
        this.etapes = etapes;
    }

    public ArrayList<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(ArrayList<Document> documents) {
        this.documents = documents;
    }
}
