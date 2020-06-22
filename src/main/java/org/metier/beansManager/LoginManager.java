package org.metier.beansManager;

import org.dao.ChefConnexion;
import org.dao.EmployeConnexion;
import org.metier.beans.Chef;
import org.metier.beans.Employe;

public class LoginManager {
    private static EmployeConnexion employeConnexion = new EmployeConnexion();
    private static ChefConnexion chefConnexion = new  ChefConnexion();

    public static boolean validationChefDivision(String nom, String password)
    {
        return chefConnexion.validationChefDeDivision(nom,password);
    }

    public static boolean validationEmploye(String nom, String password)
    {
        return employeConnexion.validationEmploye(nom,password);
    }

    public static Chef nomCompletChef()
    {
       return chefConnexion.dataEmploye();
    }

    public static Employe getEmploye()
    {
        return employeConnexion.dataEmploye();
    }

    public static Chef getChef()
    {
        return chefConnexion.dataEmploye();
    }

}
