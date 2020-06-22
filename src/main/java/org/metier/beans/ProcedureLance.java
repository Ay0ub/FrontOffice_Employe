package org.metier.beans;

import java.util.ArrayList;

public class ProcedureLance {
    private int numero;
    private int numP;
    private String nom;
    private String date;
    private String cin;
    private String etapeActuelle;
    private ArrayList<Etape> etapes;

    public ArrayList<Etape> getEtapes() {
        return etapes;
    }

    public void setEtapes(ArrayList<Etape> etapes) {
        this.etapes = etapes;
    }

    public int getNumP() {
        return numP;
    }

    public void setNumP(int numP) {
        this.numP = numP;
    }

    public int getNumero() {
        return numero;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    @Override
    public String toString() {
        return "Procedure :--->" + nom;
    }

}
