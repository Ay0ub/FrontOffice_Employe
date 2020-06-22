package org.dao;

import org.metier.beans.Document;
import org.metier.beans.Employe;
import org.metier.beans.Procedure;

import java.util.ArrayList;

public interface InterfaceDaoProcedure {
    ArrayList<Procedure> getAllProcedurebyEmploye(Employe employe);
    void updateEtapeActuelle(Procedure procedure);
    void updateEtapeActuelleMAJ(Procedure procedure, ArrayList<Document> documents);
}
