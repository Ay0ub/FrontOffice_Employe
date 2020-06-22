package org.metier.beansManager;

import org.dao.ProcedureDao;
import org.metier.beans.Document;
import org.metier.beans.Employe;
import org.metier.beans.Procedure;

import java.util.ArrayList;

public class ProcedureManager {
    private static final ProcedureDao procedureDao = new ProcedureDao();

    public static ArrayList<Procedure> getAllProcedureByEmploye(Employe employe)
    {
        return procedureDao.getAllProcedurebyEmploye(employe);
    }

    public static void updateEtapeActuelle(Procedure procedure){
        procedureDao.updateEtapeActuelle(procedure);
    }

    public static void updateEtapeActuelleMAJ(Procedure procedure,
                                       ArrayList<Document> documents){
        procedureDao.updateEtapeActuelleMAJ(procedure, documents);
    }

    public static int countProceduresNonTraitees(Employe employe) {
        return getAllProcedureByEmploye(employe).size();
    }

    public static int countProceduresTraitees(Employe employe) {
        return procedureDao.countProceduresTraitees(employe);
    }

    public static int countProceduresTraitees(Employe employeConnecte, int jour) {
        return procedureDao.countProceduresTraitees(employeConnecte, jour);
    }
}
