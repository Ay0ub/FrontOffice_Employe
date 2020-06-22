package org.metier.beansManager;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.dao.DemandesDeposes;
import org.metier.beans.Demande;
import org.metier.beans.ProcedureLance;

import java.util.ArrayList;
import java.util.HashMap;

public class DemandeManager {

    private static DemandesDeposes deposes = new DemandesDeposes();

    public static ArrayList<Demande> getAllDemandes(int id)
    {
        return deposes.getAllDemandes(id);
    }
    public static ArrayList<Demande> getAllDemandes(String  id)
    {
        return deposes.getAllDemandes(id);
    }

    public static Demande getDemandeById(int id)
    {
        return deposes.getDemandeById(id);
    }

    public static void addJeton(int jeton,int id,int idp) throws JsonProcessingException {
        deposes.putJeton(jeton,id,idp);
    }
    public static void rejectDemande(int id)
    {
        deposes.rejectDemande(id);
    }
    public static ArrayList<ProcedureLance> getAllProcedures (int idProcedure)
    {
        return deposes.getAllProcedures(idProcedure);
    }
    public static ArrayList<ProcedureLance> getAllProcedures (String idProcedure)
    {
        return deposes.getAllProcedures(idProcedure);
    }
    public static ProcedureLance getProcedureById(int id)
    {
        return deposes.getProcedureById(id);
    }
    public static  void deliverDocument(int id) throws JsonProcessingException {
        deposes.archiveProcedure(id);
    }

    public static  void rejeterProcedure(int id) throws JsonProcessingException {
        deposes.archiveProcedureR(id);
    }
    public static int demandes(int id)
    {
        return deposes.nombreDeDemande(id);
    }
    public static int procedures(int id)
    {
        return deposes.nombreDeProcedureLancees(id);
    }
    public static ArrayList<org.metier.beans.ProcedureArchive> getAllProceduresArchives (int idP)
    {
        return deposes.getAllProceduresArchives(idP);
    }
    public static ArrayList<org.metier.beans.ProcedureArchive> getAllProceduresArchives (String idP)
    {
        return deposes.getAllProceduresArchives(idP);
    }
    public static boolean deleteArchive(int id)
    {
        return deposes.removeProcedure(id);
    }
    public static HashMap<String,Integer> statistiques(int idp)
    {
        return deposes.statistiques(idp);
    }

    public static HashMap<Integer,Integer> statistiquesJ(int idp)
    {
        return deposes.statistiquesJ(idp);
    }
    public static int nbProceduresRejete (int idProcedure)
    {
        return deposes.nbProceduresRejete(idProcedure);
    }
    public static int nbProceduresNT (int idProcedure)
    {
        return deposes.nbProceduresNT(idProcedure);
    }
    public static int nbProceduresT (int idProcedure)
    {
        return deposes.nbProceduresT(idProcedure);
    }

    public static HashMap<String,Integer> statistiques(int idp,String nom)
    {
        return deposes.statistiques(idp,nom);
    }

    public static HashMap<Integer,Integer> statistiquesJ(int idp,String nom)
    {
        return deposes.statistiquesJ(idp,nom);
    }

    public static int nbProceduresNT (int idProcedure,String nom)
    {
        return deposes.nbProceduresNT(idProcedure,nom);
    }
    public static int nbProceduresT (int idProcedure,String nom)
    {
        return deposes.nbProceduresT(idProcedure,nom);
    }

}
