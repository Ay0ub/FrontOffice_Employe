package org.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.metier.beans.Demande;
import org.metier.beans.Etape;
import org.metier.beans.ProcedureArchive;
import org.metier.beans.ProcedureLance;

import java.util.ArrayList;
import java.util.HashMap;

public interface InterfaceDemandesDeposes {
    ArrayList<Demande> getAllDemandes(int idProcedure);
    ArrayList<Demande> getAllDemandes(String nom);
    ArrayList<ProcedureLance> getAllProcedures(int idProcedure);
    ArrayList<ProcedureLance> getAllProcedures(String nom);
    ArrayList<org.metier.beans.ProcedureArchive> getAllProceduresArchives(int idP);
    ArrayList<org.metier.beans.ProcedureArchive> getAllProceduresArchives(String nom);
    ProcedureLance getProcedureById(int id);
    ProcedureArchive getProcedureForArchive(int id);
    ProcedureArchive getProcedureForArchiveR(int id);
    Demande getDemandeById(int id);
    void putJeton(int jeton, int id, int idP) throws JsonProcessingException;
    void archiveProcedure(int id) throws JsonProcessingException;
    void archiveProcedureR(int id) throws JsonProcessingException;
    void rejectDemande(int id);
    ArrayList<Etape> getAllEtapes(int idProcedure);
    ArrayList<Etape> getAllEtapesL(int id);
    int nombreDeDemande(int idP);
    int nombreDeProcedureLancees(int idp);
    boolean removeProcedure(int id);
    boolean isExisteProcedure(int id);
    HashMap<String,Integer> statistiques(int idp);
    HashMap<Integer,Integer> statistiquesJ(int idp);
    int nbProceduresRejete(int idProcedure);
    int nbProceduresT(int idProcedure);
    int nbProceduresNT(int idProcedure);
}
