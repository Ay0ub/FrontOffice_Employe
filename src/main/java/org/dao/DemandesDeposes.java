package org.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.*;
import com.mongodb.util.JSON;
import org.metier.beans.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;


public class DemandesDeposes {
    private static final DB conn = ConnexionMBD.mdbConnexion("test");
    private DBCollection dbCollection1 = conn.getCollection("proceduresLancées");
    private DBCollection dbCollection2 = conn.getCollection("procedures");
    private DBCollection dbCollection3 = conn.getCollection("Archives");

    public ArrayList<Demande> getAllDemandes (int idProcedure)
    {
        ArrayList<Demande> demandes = new ArrayList<>();
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("idChef",idProcedure);
        DBCursor dbCursor = dbCollection1.find(basicDBObject);
        return getDemandes(demandes,dbCursor);
    }

    public ArrayList<Demande> getAllDemandes (String nom)
    {
        ArrayList<Demande> demandes = new ArrayList<>();
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("nom",nom);
        DBCursor dbCursor = dbCollection1.find(basicDBObject);
        return getDemandes(demandes,dbCursor);
    }

    private ArrayList<Demande> getDemandes(ArrayList<Demande> demandes, DBCursor dbCursor) {
        String etat;
        while (dbCursor.hasNext())
        {
            Demande demande = new Demande();
            DBObject dbObject = dbCursor.next();
            etat = (String) dbObject.get("etat");
            if (etat.equals("En attente de traitement"))
            {
                demande.setNumero((int)dbObject.get("numero"));
                demande.setNom((String) dbObject.get("nom"));
                demande.setDateDebut((String) dbObject.get("date"));
                demandes.add(demande);
            }
        }
        return demandes;
    }

    public ArrayList<ProcedureLance> getAllProcedures (int idProcedure)
    {
        ArrayList<ProcedureLance> procedureLances = new ArrayList<>();
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("idChef",idProcedure);
        DBCursor dbCursor = dbCollection1.find(basicDBObject);
        return getProcedures(procedureLances,dbCursor);
    }

    public ArrayList<ProcedureLance> getAllProcedures (String  nom)
    {
        ArrayList<ProcedureLance> procedureLances = new ArrayList<>();
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("nom",nom);
        DBCursor dbCursor = dbCollection1.find(basicDBObject);
        return getProcedures(procedureLances,dbCursor);
    }

    public ArrayList<org.metier.beans.ProcedureArchive> getAllProceduresArchives (int idP)
    {
        ArrayList<ProcedureArchive> procedureArchives = new ArrayList<>();
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("idChef",idP);
        DBCursor dbCursor = dbCollection3.find(basicDBObject);
        return getProceduresA(procedureArchives,dbCursor);
    }

    public ArrayList<org.metier.beans.ProcedureArchive> getAllProceduresArchives (String nom)
    {
        ArrayList<ProcedureArchive> procedureArchives = new ArrayList<>();
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("nom",nom);
        DBCursor dbCursor = dbCollection3.find(basicDBObject);
        return getProceduresA(procedureArchives,dbCursor);
    }

    private ArrayList<ProcedureArchive> getProceduresA(ArrayList<ProcedureArchive> procedureArchives, DBCursor dbCursor) {
        String etat;
        while (dbCursor.hasNext())
        {
            ProcedureArchive procedureArchive = new ProcedureArchive();
            DBObject dbObject = dbCursor.next();
            procedureArchive.setNumero((int)dbObject.get("numero"));
            procedureArchive.setNom((String) dbObject.get("nom"));
            procedureArchive.setDateDebut((String) dbObject.get("dateDebut"));
            procedureArchive.setDateFin((String) dbObject.get("dateFin"));
            procedureArchives.add(procedureArchive);

        }
        return procedureArchives;
    }
    private ArrayList<ProcedureLance> getProcedures(ArrayList<ProcedureLance> procedureLances, DBCursor dbCursor) {
        String etat;
        while (dbCursor.hasNext())
        {
            ProcedureLance procedureLance = new ProcedureLance();
            DBObject dbObject = dbCursor.next();
            etat = (String) dbObject.get("etat");
            if (etat.equals("En cours de Traitement"))
            {
                procedureLance.setNumero((int)dbObject.get("numero"));
                procedureLance.setNom((String) dbObject.get("nom"));
                procedureLance.setDate((String) dbObject.get("date"));
                procedureLance.setEtapes(getAllEtapesL((int)dbObject.get("numero")));
                procedureLance.setEtapeActuelle(String.valueOf((int) dbObject.get("etapeActuelle")));
                procedureLances.add(procedureLance);
            }
        }
        return procedureLances;
    }

    public ProcedureLance getProcedureById(int id) {
        String etat;
        ProcedureLance procedureLance = new ProcedureLance();
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("numero",id);
        DBObject dbObject = dbCollection1.findOne(basicDBObject);
        etat = (String) dbObject.get("etat");
        procedureLance.setNumero((int)dbObject.get("numero"));
        procedureLance.setNom((String) dbObject.get("nom"));
        procedureLance.setDate((String) dbObject.get("date"));
        procedureLance.setCin((String) dbObject.get("cin"));
        procedureLance.setEtapeActuelle(String.valueOf((int) dbObject.get("etapeActuelle")));
        procedureLance.setEtapes(getAllEtapesL(id));
        return procedureLance;
    }


    public ProcedureArchive getProcedureForArchive(int id) {
        LocalDateTime createdAt = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");
        String date = createdAt.format(dateTimeFormatter);
        BasicDBObject basicDBObject1 = new BasicDBObject();
        basicDBObject1.append("$set", new BasicDBObject().append("fin", date));
        BasicDBObject basicDBObject2 = new BasicDBObject();
        basicDBObject2.append("$set", new BasicDBObject().append("etat", "Terminé"));
        ArrayList<Document> documents = new ArrayList<>();
        ProcedureArchive procedureArchive = new ProcedureArchive();
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("numero",id);
        dbCollection1.update(basicDBObject,basicDBObject1);
        dbCollection1.update(basicDBObject,basicDBObject2);
        DBObject dbObject = dbCollection1.findOne(basicDBObject);
        procedureArchive.setNumero((int)dbObject.get("numero"));
        procedureArchive.setNumP((Integer) dbObject.get("id_procedure"));
        procedureArchive.setNom((String) dbObject.get("nom"));
        procedureArchive.setIdChef((int)dbObject.get("idChef"));
        procedureArchive.setDateDebut((String) dbObject.get("date"));
        procedureArchive.setDateFin((String) dbObject.get("fin"));
        procedureArchive.setCin((String) dbObject.get("cin"));
        procedureArchive.setEtapeActuelle(String.valueOf((int) dbObject.get("etapeActuelle")));
        procedureArchive.setEtapes(getAllEtapesL(id));
        ArrayList<DBObject> allDocuments = (ArrayList<DBObject>) dbObject.get("documents");
        for(DBObject ob : allDocuments)
        {
            Document document = new Document();
            document.setNumero((String) ob.get("numero"));
            document.setNom((String) ob.get("nom"));
            document.setUrl((String) ob.get("url"));
            documents.add(document);
        }
        procedureArchive.setDocuments(documents);
        return procedureArchive;
    }

    public ProcedureArchive getProcedureForArchiveR(int id) {
        LocalDateTime createdAt = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");
        String date = createdAt.format(dateTimeFormatter);
        BasicDBObject basicDBObject1 = new BasicDBObject();
        basicDBObject1.append("$set", new BasicDBObject().append("fin", date));
        BasicDBObject basicDBObject2 = new BasicDBObject();
        basicDBObject2.append("$set", new BasicDBObject().append("etat", "Rejeté"));
        ArrayList<Document> documents = new ArrayList<>();
        ProcedureArchive procedureArchive = new ProcedureArchive();
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("numero",id);
        dbCollection1.update(basicDBObject,basicDBObject1);
        dbCollection1.update(basicDBObject,basicDBObject2);
        DBObject dbObject = dbCollection1.findOne(basicDBObject);
        procedureArchive.setNumero((int)dbObject.get("numero"));
        procedureArchive.setNumP((Integer) dbObject.get("id_procedure"));
        procedureArchive.setNom((String) dbObject.get("nom"));
        procedureArchive.setDateDebut((String) dbObject.get("date"));
        procedureArchive.setDateFin((String) dbObject.get("fin"));
        procedureArchive.setCin((String) dbObject.get("cin"));
        procedureArchive.setEtapeActuelle(String.valueOf((int) dbObject.get("etapeActuelle")));
        procedureArchive.setEtapes(getAllEtapesL(id));
        ArrayList<DBObject> allDocuments = (ArrayList<DBObject>) dbObject.get("documents");
        for(DBObject ob : allDocuments)
        {
            Document document = new Document();
            document.setNumero((String) ob.get("numero"));
            document.setNom((String) ob.get("nom"));
            document.setUrl((String) ob.get("url"));
            documents.add(document);
        }
        procedureArchive.setDocuments(documents);
        return procedureArchive;
    }




    public Demande getDemandeById(int id)
    {
        Demande demande = new Demande();
        ArrayList<Document> documents = new ArrayList<>();
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("numero",id);
        DBObject dbObject = dbCollection1.findOne(basicDBObject);
        demande.setCin((String) dbObject.get("cin"));
        demande.setNom((String) dbObject.get("nom"));
        demande.setNumero(id);
        ArrayList<DBObject> allDocuments = (ArrayList<DBObject>) dbObject.get("documents");
        for(DBObject ob : allDocuments)
        {
            Document document = new Document();
            document.setNumero((String) ob.get("numero"));
            document.setNom((String) ob.get("nom"));
            document.setUrl((String) ob.get("url"));
            documents.add(document);
        }
        demande.setDocuments(documents);
        return demande;
    }

    public void putJeton(int jeton,int id,int idP) throws JsonProcessingException {
        ArrayList<Etape> etapes = getAllEtapes(idP);
        ObjectMapper objectMapper1 = new ObjectMapper();
        String json1 = objectMapper1.writeValueAsString(etapes);
        BasicDBObject basicDBObject1 = new BasicDBObject().append("numero",id);
        BasicDBObject basicDBObject2 = new BasicDBObject();
        BasicDBObject basicDBObject3 = new BasicDBObject();
        BasicDBObject basicDBObject4 = new BasicDBObject();
        basicDBObject2.append("$set", new BasicDBObject().append("jeton", String.valueOf(jeton)));
        ArrayList<BasicDBObject> dbObject = (ArrayList<BasicDBObject>) JSON.parse(json1);
        BasicDBObject basicDBObject = new BasicDBObject("$set", new BasicDBObject("etapes", dbObject));
        basicDBObject3.append("$set", new BasicDBObject().append("etapeActuelle", 1));
        basicDBObject4.append("$set", new BasicDBObject().append("etat", "En cours de Traitement"));
        dbCollection1.update(basicDBObject1,basicDBObject2);
        dbCollection1.update(basicDBObject1,basicDBObject);
        dbCollection1.update(basicDBObject1,basicDBObject3);
        dbCollection1.update(basicDBObject1,basicDBObject4);
    }

    public void archiveProcedure(int id) throws JsonProcessingException {
        ProcedureArchive procedureArchive;
        procedureArchive = getProcedureForArchive(id);
        ObjectMapper objectMapper1 = new ObjectMapper();
        String json1 = objectMapper1.writeValueAsString(procedureArchive);
        DBObject dbObject = (DBObject) JSON.parse(json1);
        dbCollection3.insert(dbObject);
    }

    public void archiveProcedureR(int id) throws JsonProcessingException {
        ProcedureArchive procedureArchive;
        procedureArchive = getProcedureForArchiveR(id);
        ObjectMapper objectMapper1 = new ObjectMapper();
        String json1 = objectMapper1.writeValueAsString(procedureArchive);
        DBObject dbObject = (DBObject) JSON.parse(json1);
        dbCollection3.insert(dbObject);
    }

    public void rejectDemande(int id)
    {
        BasicDBObject basicDBObject1 = new BasicDBObject().append("numero",id);
        BasicDBObject basicDBObject2 = new BasicDBObject();
        basicDBObject2.append("$set", new BasicDBObject().append("etat", "Rejeté"));
        dbCollection1.update(basicDBObject1,basicDBObject2);
    }

    public ArrayList<Etape> getAllEtapes(int idProcedure)
    {
        ArrayList<Etape> etapes = new ArrayList<>();
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("division.chefDivision.numero",idProcedure);
        DBObject dbObject = dbCollection2.findOne(basicDBObject);
        ArrayList<DBObject> allEtapes = (ArrayList<DBObject>) dbObject.get("etapes");
        int i = 1;
        for(DBObject et : allEtapes) {
                DBObject dbObject2 = (DBObject) et.get( "employe");
                Etape etape = new Etape();
                Employe employe = new Employe();
                employe.setNumero((Integer) dbObject2.get("numero"));
                employe.setNom((String) dbObject2.get("nom"));
                employe.setContact((String) dbObject2.get("email"));
                if(i==1)
                {
                    etape.setEtat("En cours de traitement");
                }
                else {
                    etape.setEtat("En attente de traitement");
                }
                etape.setResponsable(employe);
                etape.setNom((String) et.get("nom"));
                etape.setNumero(i);
                etapes.add(etape);
                i = i+1;
        }
        return etapes;
    }

    public ArrayList<Etape> getAllEtapesL(int id)
    {
        ArrayList<Etape> etapes = new ArrayList<>();
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("numero",id);
        DBObject dbObject = dbCollection1.findOne(basicDBObject);
        ArrayList<DBObject> allEtapes = (ArrayList<DBObject>) dbObject.get("etapes");
        for(DBObject et : allEtapes) {
            Employe employe = new Employe();
            Etape etape = new Etape();
            DBObject dbObject1 = (DBObject) et.get( "responsable");
            employe.setNumero((Integer) dbObject1.get("numero"));
            employe.setNom((String) dbObject1.get("nom"));
            employe.setContact((String) dbObject1.get("contact"));
            etape.setNom((String) et.get("nom"));
            etape.setNumero((Integer) et.get("numero"));
            etape.setEtat((String) et.get("etat"));
            etape.setRapport((String) et.get("rapport"));
            etape.setJour((int) et.get("jour"));
            etape.setResponsable(employe);
            etape.setRapportMAJ((String) et.get("rapportMAJ"));
            etapes.add(etape);
        }
        return etapes;
    }

    public int nombreDeDemande(int idP)
    {
        return getAllDemandes(idP).size();
    }

    public int nombreDeProcedureLancees(int idp)
    {
        return getAllProcedures(idp).size();
    }

    public boolean removeProcedure(int id)
    {
        BasicDBObject document = new BasicDBObject();
        document.put("numero", id);
        DBObject dbObject = dbCollection3.findOne(document);
        dbCollection3.remove(dbObject);
        return isExisteProcedure(id);

    }
    /**
     * on verifie si une procedure existe deja dans la base de données
     * @param id id d la procedure a tester
     * @return
     */
    public boolean isExisteProcedure (int id)
    {
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("numero",id);
        DBObject dbObject = dbCollection3.findOne(basicDBObject);
        return dbObject != null;
    }

    public HashMap<String,Integer> statistiques(int idp)
    {
        HashMap<String,Integer> stats = new HashMap<>();
        ArrayList<ProcedureLance> procedureLances;
        procedureLances = getAllProcedures(idp);
        int calcul;
        for (ProcedureLance procedureLance:procedureLances)
        {
            ArrayList<Etape> etapes;
            etapes = procedureLance.getEtapes();
            for (Etape etapes1 : etapes)
            {
                if (stats.containsKey(etapes1.getResponsable().getNom()))
                {
                    if (etapes1.getEtat() != null)
                    {
                        calcul = stats.get(etapes1.getResponsable().getNom());
                        stats.replace(etapes1.getResponsable().getNom(),calcul+1);
                    }
                }
                else {
                    if(etapes1.getEtat() == null)
                        stats.put(etapes1.getResponsable().getNom(),0);
                    else
                        stats.put(etapes1.getResponsable().getNom(),1);
                }
            }
        }
        return stats;
    }

    public HashMap<Integer,Integer> statistiquesJ(int idp)
    {
        HashMap<Integer,Integer> stats = new HashMap<>();
        ArrayList<ProcedureLance> procedureLances;
        stats.put(1,0);
        stats.put(2,0);
        stats.put(3,0);
        stats.put(4,0);
        stats.put(5,0);
        stats.put(6,0);
        stats.put(7,0);
        procedureLances = getAllProcedures(idp);
        int calcul;
        for (ProcedureLance procedureLance:procedureLances)
        {
            ArrayList<Etape> etapes;
            etapes = procedureLance.getEtapes();
            for (Etape etapes1 : etapes)
            {
                if (etapes1.getEtat() != null)
                {
                    calcul = stats.get(etapes1.getJour());
                    stats.replace(etapes1.getJour(),calcul+1);
                }
            }
        }
        return stats;
    }

    public int nbProceduresRejete (int idProcedure)
    {
        ArrayList<ProcedureLance> procedureLances = new ArrayList<>();
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("idChef",idProcedure);
        DBCursor dbCursor = dbCollection1.find(basicDBObject);
        return getProceduresR(procedureLances,dbCursor);
    }

    private int getProceduresR(ArrayList<ProcedureLance> procedureLances, DBCursor dbCursor) {
        String etat,jeton;
        int i=0;
        while (dbCursor.hasNext())
        {
            DBObject dbObject = dbCursor.next();
            etat = (String) dbObject.get("etat");
            if (etat.equals("Rejeté"))
            {
                jeton = (String) dbObject.get("jeton");
               if (jeton == null)
                   i = i+1;
            }
        }
        return i;
    }

    public int nbProceduresT (int idProcedure)
    {
        ArrayList<ProcedureLance> procedureLances = new ArrayList<>();
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("idChef",idProcedure);
        DBCursor dbCursor = dbCollection1.find(basicDBObject);
        return getProceduresT(procedureLances,dbCursor);
    }
    public int nbProceduresT (int idProcedure,String nom)
    {
        ArrayList<ProcedureLance> procedureLances = new ArrayList<>();
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("idChef",idProcedure);
        basicDBObject.put("nom",nom);
        DBCursor dbCursor = dbCollection1.find(basicDBObject);
        return getProceduresT(procedureLances,dbCursor);
    }

    private int getProceduresT(ArrayList<ProcedureLance> procedureLances, DBCursor dbCursor) {
        String etat;
        int i=0,etape;
        while (dbCursor.hasNext())
        {
            DBObject dbObject = dbCursor.next();
            etat = (String) dbObject.get("etat");
            if (etat.equals("En cours de Traitement"))
            {
                etape = (int) dbObject.get("etapeActuelle");
                if (etape > 1)
                    i = i+1;
            }
        }
        return i;
    }

    public int nbProceduresNT (int idProcedure)
    {
        ArrayList<ProcedureLance> procedureLances = new ArrayList<>();
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("idChef",idProcedure);
        DBCursor dbCursor = dbCollection1.find(basicDBObject);
        return getProceduresNT(procedureLances,dbCursor);
    }

    public int nbProceduresNT (int idProcedure,String nom)
    {
        ArrayList<ProcedureLance> procedureLances = new ArrayList<>();
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("idChef",idProcedure);
        basicDBObject.put("nom",nom);
        DBCursor dbCursor = dbCollection1.find(basicDBObject);
        return getProceduresNT(procedureLances,dbCursor);
    }

    private int getProceduresNT(ArrayList<ProcedureLance> procedureLances, DBCursor dbCursor) {
        String etat;
        int i=0,etape;
        while (dbCursor.hasNext())
        {
            DBObject dbObject = dbCursor.next();
            etat = (String) dbObject.get("etat");
            if (etat.equals("En cours de Traitement"))
            {
                etape = (int) dbObject.get("etapeActuelle");
                if (etape == 1)
                    i = i+1;
            }
        }
        return i;
    }

    //stat en fonction de la procedure

    public HashMap<String,Integer> statistiques(int idp,String nom)
    {
        HashMap<String,Integer> stats = new HashMap<>();
        ArrayList<ProcedureLance> procedureLances;
        procedureLances = getAllProcedures(idp,nom);
        int calcul;
        for (ProcedureLance procedureLance:procedureLances)
        {
            ArrayList<Etape> etapes;
            etapes = procedureLance.getEtapes();
            for (Etape etapes1 : etapes)
            {
                if (stats.containsKey(etapes1.getResponsable().getNom()))
                {
                    if (etapes1.getEtat() != null)
                    {
                        calcul = stats.get(etapes1.getResponsable().getNom());
                        stats.replace(etapes1.getResponsable().getNom(),calcul+1);
                    }
                }
                else {
                    if(etapes1.getEtat() == null)
                        stats.put(etapes1.getResponsable().getNom(),0);
                    else
                        stats.put(etapes1.getResponsable().getNom(),1);
                }
            }
        }
        return stats;
    }

    public HashMap<Integer,Integer> statistiquesJ(int idp,String nom)
    {
        HashMap<Integer,Integer> stats = new HashMap<>();
        ArrayList<ProcedureLance> procedureLances;
        stats.put(1,0);
        stats.put(2,0);
        stats.put(3,0);
        stats.put(4,0);
        stats.put(5,0);
        stats.put(6,0);
        stats.put(7,0);
        procedureLances = getAllProcedures(idp,nom);
        int calcul;
        for (ProcedureLance procedureLance:procedureLances)
        {
            ArrayList<Etape> etapes;
            etapes = procedureLance.getEtapes();
            for (Etape etapes1 : etapes)
            {
                if (etapes1.getEtat() != null)
                {
                    calcul = stats.get(etapes1.getJour());
                    stats.replace(etapes1.getJour(),calcul+1);
                }
            }
        }
        return stats;
    }
    public ArrayList<ProcedureLance> getAllProcedures (int idProcedure,String nom)
    {
        ArrayList<ProcedureLance> procedureLances = new ArrayList<>();
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("idChef",idProcedure);
        basicDBObject.put("nom",nom);
        DBCursor dbCursor = dbCollection1.find(basicDBObject);
        return getProcedures(procedureLances,dbCursor);
    }
}
