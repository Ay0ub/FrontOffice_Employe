package org.dao;

import com.mongodb.*;
import org.metier.beans.Chef;


public class ChefConnexion {
    private static final DB conn = ConnexionMBD.mdbConnexion("test");
    private  DBCollection dbCollection = conn.getCollection("procedures");
    private String nomComplet;
    private int numero;
    private String nomDiv;
    private String nomChef;
    private String prenom;
    private String email;
    private String tel;
    public  boolean validationChefDeDivision(String nom, String password)
    {
        DBCursor dbCursor = dbCollection.find();
        while (dbCursor.hasNext())
        {
            DBObject dbObject = dbCursor.next();
            DBObject dbObject1 = (DBObject) dbObject.get("division");
            nomDiv = (String) dbObject1.get("nomDivision");
            DBObject dbObject2 = (DBObject) dbObject1.get("chefDivision");
            String name  = (String) dbObject2.get("nom");
            String pass = (String) dbObject2.get("password");
            numero = (int) dbObject2.get("numero");
            if (name.equals(nom) && pass.equals(password))
            {
                nomComplet = (String) dbObject2.get("nomComplet");
                nomChef = nom;
                prenom = (String) dbObject2.get("prenom");
                email = (String) dbObject2.get("email");
                tel = (String) dbObject2.get("tel");
                return true;
            }


        }
        return false;
    }

    /**
     * recuperation de touytes les informations d'un citoyen qui souhaite
     * se connecter
     * @return
     */
    public Chef dataEmploye()
    {
        Chef chef = new Chef();
        chef.setNomComplet(nomComplet);
        chef.setNumero(numero);
        chef.setNomDiv(nomDiv);
        chef.setNom(nomChef);
        chef.setPrenom(prenom);
        chef.setEmail(email);
        chef.setTel(tel);
        return chef;
    }


}
