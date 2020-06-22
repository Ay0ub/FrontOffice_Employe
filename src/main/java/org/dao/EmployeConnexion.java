package org.dao;

import com.mongodb.*;
import org.metier.beans.Employe;

import java.util.ArrayList;

public class EmployeConnexion {
    private static final DB conn = ConnexionMBD.mdbConnexion("test");
    private final DBCollection dbCollection = conn.getCollection("procedures");
    private String nomComplet;
    private int numero;
    private String prenom;
    private String nomEmploye;
    private String email;
    private String motDePasse;
    private String tel;
    private String division;

    public boolean validationEmploye(String nom, String password)
    {
        DBCursor dbCursor = dbCollection.find();
        while (dbCursor.hasNext())
        {
            DBObject dbObject = dbCursor.next();
            DBObject dbObject1 = (DBObject) dbObject.get("division");
            ArrayList<DBObject> allEmployes =
                    (ArrayList<DBObject>) dbObject1.get("employes");
            for (DBObject object: allEmployes)
            {
                String name  = (String) object.get("nom");
                String pass = (String) object.get("password");
                if (name.equals(nom) && pass.equals(password)) {
                   nomComplet = (String) object.get("nomComplet");
                   numero = (int) object.get("numero");
                   prenom = (String) object.get("prenom");
                   nomEmploye = nom;
                   email = (String) object.get("email");
                   motDePasse = password;
                   tel = (String) object.get("tel");
                   division = (String) dbObject1.get("nomDivision");
                   return true;
                }
            }
        }
        return false;
    }


    public Employe dataEmploye()
    {
        Employe employe = new Employe();
        employe.setNomComplet(nomComplet);
        employe.setNumero(numero);
        employe.setNom(nomEmploye);
        employe.setPrenom(prenom);
        employe.setEmail(email);
        employe.setPassword(motDePasse);
        employe.setTel(tel);
        employe.setDivision(division);
        return employe;
    }



}
