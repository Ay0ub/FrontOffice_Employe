package org.metier.beans;

public class Etape {
    private int numero;
    private String nom;
    private String etat;
    private int jour;
    private String date;
    private String rapportMAJ;
    private String rapport;
    private Employe responsable;

    public Employe getResponsable() {
        return responsable;
    }

    public void setResponsable(Employe responsable) {
        this.responsable = responsable;
    }

    public int getJour() {
        return jour;
    }

    public void setJour(int jour) {
        this.jour = jour;
    }


    public String getRapportMAJ() {
        return rapportMAJ;
    }

    public void setRapportMAJ(String rapportMAJ) {
        this.rapportMAJ = rapportMAJ;
    }

    public String getRapport() {
        return rapport;
    }

    public void setRapport(String rapport) {
        this.rapport = rapport;
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

    @Override
    public String toString() {
        return getNom().toUpperCase();
    }
}
