package org.metier.beans;

public class ProceduresLancees {
    private int numero;
    private String nom;
    private String date;
    private String etat;

    public ProceduresLancees(int numero, String nom, String date, String etat) {
        this.numero = numero;
        this.nom = nom;
        this.date = date;
        this.etat = etat;
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

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
}
