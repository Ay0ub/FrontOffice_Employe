package org.dao;

import com.mongodb.*;
import org.metier.beans.*;
import org.utils.Etat;

import java.util.ArrayList;

public class ProcedureDao implements InterfaceDaoProcedure{
    private static final DB conn = ConnexionMBD.mdbConnexion("test");
    private final DBCollection dbCollection = conn.getCollection("proceduresLancées");

    public ArrayList<Procedure> getAllProcedurebyEmploye(Employe employe)
    {
        ArrayList<Procedure> procedures = new ArrayList<>();
        DBCursor dbCursor = dbCollection.find();

        while (dbCursor.hasNext())
        {
            DBObject objectProcedure = dbCursor.next();
            int indexEtape = (int) objectProcedure.get("etapeActuelle");
            ArrayList<DBObject> etapes =
                    (ArrayList<DBObject>) objectProcedure.get("etapes");

            DBObject objectEtape = etapes.get(indexEtape-1);

            int idRespo = (int)((DBObject)
                    objectEtape.get("responsable")).get("numero");

            if((idRespo == employe.getNumero()) && !isTermine(objectProcedure,
                    objectEtape)) {

                procedures.add(createProcedure(objectProcedure, objectEtape));
            }

        }
        return procedures;
    }

    /**
     * Méthode qui permet de vérifier si une procédure
     * est terminée ou pas
     * @param objectProcedure procedure
     * @param objectEtape etape courante
     * @return <i>True</i> si elle est terminé
     * <i>False</i> sinon
     */
    private boolean isTermine(DBObject objectProcedure, DBObject objectEtape) {

        int numeroEtape = (int) objectProcedure.get("numero");
        int nombreEtapes = countEtapes(numeroEtape);

        int numero = (int) objectEtape.get("numero");
        String etat = (String) objectEtape.get("etat");

        return (numero == nombreEtapes) && (etat.equals("ACCEPTE"));
    }

    private Procedure createProcedure(DBObject objectProcedure,
                                      DBObject objectEtape) {
        int numero =(int) objectProcedure.get("numero");
        String nom = (String) objectProcedure.get("nom");
        String cin = (String) objectProcedure.get("cin");
        String date = (String) objectProcedure.get("date");


        Procedure procedure = new Procedure();
        procedure.setNumero(numero);
        procedure.setNom(nom);
        procedure.setDate(date);
        procedure.setCin(cin);
        procedure.setEtapeActuelle(createEtape(objectEtape));
        procedure.setListeDocuments(findDocumentsList(objectProcedure, objectEtape));

        return procedure;
    }

    private Etape createEtape(DBObject objectEtape) {
        Etape etape = new Etape();

        int numero = (int) objectEtape.get("numero");
        String nomEtape = (String) objectEtape.get("nom");

        etape.setNumero(numero);
        etape.setNom(nomEtape);

        return etape;
    }

    private ArrayList<Document> findDocumentsList(DBObject objectProcedure,
                                                  DBObject objectEtape) {
        ArrayList<Document> listeDocuments = new ArrayList<>();

        int numero = 1;
        ArrayList<DBObject> documents =
                (ArrayList<DBObject>) objectProcedure.get("documents");

        //Les documents de l'étape
        ArrayList<DBObject> documentsEtape =
                (ArrayList<DBObject>)objectEtape.get("documents");
        if (documentsEtape != null){
            documents.addAll(documentsEtape);
        }

        for (DBObject documentObject :
                documents) {
            String nom = (String) documentObject.get("nom");
            String url = (String) documentObject.get("url");

            Document document = new Document();
            document.setNumero(String.valueOf(numero++));
            document.setNom(nom);
            document.setUrl(url);

            listeDocuments.add(document);
        }
        return listeDocuments;
    }

    public void updateEtapeActuelle(Procedure procedure)
    {

        BasicDBObject basicDBObject = new BasicDBObject();

        Etape etapeActuelle = procedure.getEtapeActuelle();
        int numeroEtape = etapeActuelle.getNumero();
        BasicDBObject  objectSetter = new BasicDBObject();

        basicDBObject.put("numero",procedure.getNumero());
        basicDBObject.put("etapes.numero", numeroEtape);
        objectSetter.put("etapes.$.rapport",etapeActuelle.getRapport());
        objectSetter.put("etapes.$.etat",etapeActuelle.getEtat());
        objectSetter.put("etapes.$.jour",etapeActuelle.getJour());
        objectSetter.put("etapes.$.date",etapeActuelle.getDate());

        setEtapeActuelle(procedure, objectSetter);

        BasicDBObject updateQuery = new BasicDBObject();
        updateQuery.append("$set",objectSetter);

        dbCollection.update(basicDBObject,updateQuery);
    }

    private void setEtapeActuelle(Procedure procedure,
                                  BasicDBObject objectSetter) {
        Etape etapeActuelle = procedure.getEtapeActuelle();
        Etat etat = Etat.valueOf(etapeActuelle.getEtat());
        switch (etat){
            case REJETE:
                if (etapeActuelle.getNumero() > 1){
                    objectSetter.put("etapeActuelle",
                            etapeActuelle.getNumero()-1);
                }
                break;
            case ACCEPTE:
                if (etapeActuelle.getNumero() <
                        countEtapes(procedure.getNumero())){
                    objectSetter.put("etapeActuelle",
                            etapeActuelle.getNumero()+1);
                }
                break;
        }
    }

    /**
     * Méthode qui permet de modifier une étape
     * lorsque l'employé demande une mise à jour
     * @param procedure procédure à mettre à jour
     * @param documents liste des documents à mettre à ajouter
     */
    public void updateEtapeActuelleMAJ(Procedure procedure,
                                       ArrayList<Document> documents){
        Etape etapeActuelle = procedure.getEtapeActuelle();

        if (Etat.valueOf(etapeActuelle.getEtat()) == Etat.MAJ){
            BasicDBObject basicDBObject = new BasicDBObject();

            int numeroEtape = etapeActuelle.getNumero();

            BasicDBObject  objectSetter = new BasicDBObject();

            basicDBObject.put("numero",procedure.getNumero());
            basicDBObject.put("etapes.numero", numeroEtape);
            objectSetter.put("etapes.$.rapportMAJ",etapeActuelle.getRapportMAJ());
            objectSetter.put("etapes.$.etat",etapeActuelle.getEtat());
            objectSetter.put("etapes.$.jour",etapeActuelle.getJour());
            objectSetter.put("etapes.$.date",etapeActuelle.getDate());
            objectSetter.put("etapes.$.documents", createDocuments(documents));

            BasicDBObject updateQuery = new BasicDBObject();
            updateQuery.append("$set",objectSetter);

            dbCollection.update(basicDBObject,updateQuery);
        }

    }

    /**
     * Méthode qui permet de convertir la
     * liste des documents en object java
     * en liste de documents object json
     * @param documents liste des documents java
     * @return liste des documents format json
     */
    private ArrayList<BasicDBObject> createDocuments(ArrayList<Document> documents) {
        ArrayList<BasicDBObject> objectArrayList = new ArrayList<>();

        for (Document document:
             documents) {
            BasicDBObject documentObject = new BasicDBObject();
            documentObject.put("numero", document.getNumero());
            documentObject.put("nom", document.getNom());
            documentObject.put("url", document.getUrl());

            objectArrayList.add(documentObject);
        }

        return objectArrayList;
    }

    /**
     * Méthode qui permet de
     * compter le nombre d'étapes d'une procédure
     * @param idProcedure id de la procédure
     * @return le nombre d'étapes
     */
    private int countEtapes(int idProcedure){
        BasicDBObject query = new BasicDBObject();
        query.put("numero",idProcedure);

        DBCursor cursor = dbCollection.find(query);
        if (cursor.hasNext()){
            DBObject dbObject = cursor.next();
            ArrayList<DBObject> etapes = (ArrayList<DBObject>)
                    dbObject.get("etapes");
            return etapes.size();
        }

        return 0;
    }

    /**
     * Méthode qui permet de calculer
     * le nombre de procédures déjà traitées par un employé
     * @param employe employé
     * @return le nombre de procédures traitées
     */
    public int countProceduresTraitees(Employe employe) {
        int count = 0;

        DBCursor dbCursor = dbCollection.find();
        while (dbCursor.hasNext())
        {
            DBObject objectProcedure = dbCursor.next();

            ArrayList<DBObject> etapes =
                    (ArrayList<DBObject>) objectProcedure.get("etapes");

            for (DBObject etape : etapes) {
                int idRespo = (int)((DBObject)
                        etape.get("responsable")).get("numero");
                String etat = (String) etape.get("etat");

                if((idRespo == employe.getNumero()) && (etat != null)) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Méthode qui permet de déterminer
     * le nombre de procédures traitées
     * par jour en fonction l'employé
     * connecté
     * @param employeConnecte employé connecté
     * @param jour jour Lundi à Samedi: 1-6
     * @return le nombre de procédures traitées
     */
    public int countProceduresTraitees(Employe employeConnecte, int jour) {
        int count = 0;
        DBCursor dbCursor = dbCollection.find();
        while (dbCursor.hasNext())
        {
            DBObject objectProcedure = dbCursor.next();
            ArrayList<DBObject> etapes =
                    (ArrayList<DBObject>) objectProcedure.get("etapes");

            for (DBObject etape : etapes) {
                int idRespo = (int)((DBObject)
                        etape.get("responsable")).get("numero");
                String etat = (String) etape.get("etat");
                int jourEtape = (int)etape.get("jour");
                if((idRespo == employeConnecte.getNumero()) &&
                        (etat != null) &&
                        (jourEtape == jour)) {
                    count++;
                }
            }
        }
        return count;
    }
}
