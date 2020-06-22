package org.utils;

import org.metier.beans.Etape;
import org.modele.*;

public class CastFrom {

    public static Demande beansToModele(org.metier.beans.Demande demande)
    {
        Demande demande1 = new Demande();
        demande1.setNumero(String.valueOf(demande.getNumero()));
        demande1.setNom(demande.getNom());
        demande1.setDate(demande.getDateDebut());
        return demande1;
    }

    public static Document beansToModele(org.metier.beans.Document document)
    {
        Document document1 = new Document();
        document1.setNumero(String.valueOf(document.getNumero()));
        document1.setNom(document.getNom());
        return document1;
    }


    public static org.modele.Etape beansToModele(Etape etapes)
    {
        org.modele.Etape etape = new org.modele.Etape();
        etape.setNumero(etapes.getNumero());
        etape.setNom(etapes.getNom());
        etape.setResponsable(etapes.getResponsable().getNom());
        etape.setContact(etapes.getResponsable().getTel());
        etape.setEtat(etapes.getEtat());
        return etape;
    }

    public static ProcedureArchive beansToModele(org.metier.beans.ProcedureArchive procedureArchive) {
        ProcedureArchive proc = new ProcedureArchive();
        proc.setNumero(String.valueOf(procedureArchive.getNumero()));
        proc.setNom(procedureArchive.getNom());
        proc.setDateD(procedureArchive.getDateDebut());
        proc.setDateF(procedureArchive.getDateFin());
        return proc;
    }

    public static ProcedureLance beansToModele(org.metier.beans.ProcedureLance procedureLance)
    {
        String av = procedureLance.getEtapeActuelle();
        ProcedureLance proc = new ProcedureLance();
        proc.setNumero(String.valueOf(procedureLance.getNumero()));
        proc.setDate(procedureLance.getDate());
        proc.setNom(procedureLance.getNom());
        int avc = procedureLance.getEtapes().size();
        int etat = Integer.valueOf(procedureLance.getEtapeActuelle());
        double rap = (double) etat/avc;
        if (rap < 0.25)
        {
            proc.getAvcm().setProgress(rap);
            proc.getAvcm().setStyle("-fx-accent: red");
            proc.getAvcm().setMaxSize(170,50);
            proc.getAvcm().setAccessibleText("10%");
        }else if (rap>=0.25 && rap<0.5)
        {
            proc.getAvcm().setProgress(rap);
            proc.getAvcm().setMaxSize(170,50);
            proc.getAvcm().setStyle("-fx-accent: blue");
        }else if (rap >= 0.5 && rap <0.75)
        {
            proc.getAvcm().setProgress(rap);
            proc.getAvcm().setMaxSize(170,50);
            proc.getAvcm().setStyle("-fx-accent: #FF9900");

        } else if (rap >= 0.75 && rap < 1)
        {
            proc.getAvcm().setProgress(rap);
            proc.getAvcm().setMaxSize(170,50);
            proc.getAvcm().setStyle("-fx-accent:yellow");
        }
        else{
            proc.getAvcm().setProgress(rap);
            proc.getAvcm().setMaxSize(170,50);
            proc.getAvcm().setStyle("-fx-accent:green");
        }
        return proc;
    }
}
