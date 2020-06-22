package org.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TitledPane;
import org.metier.beansManager.DemandeManager;
import org.metier.beansManager.LoginManager;
import org.modele.ProcedureArchive;
import org.modele.ProcedureLance;
import org.utils.CastFrom;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class PerformancesController implements Initializable {
    public ComboBox<ProcedureLance> optionsProcedures;
    public BarChart<String, Number> NETPJ;
    public CategoryAxis jourAxis;
    public NumberAxis nbreProcsAxis;
    public NumberAxis nbreEtapesAxis;
    public CategoryAxis employesAxis;
    public BarChart<String,Number> NETPE;
    public PieChart demandes;
    public PieChart procedures;
    public TitledPane emp;
    private int id;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        id = LoginManager.nomCompletChef().getNumero();
        loadDataS1();
        loadDataS2();
        loadDataS3();
        loadDataS4();
        load();
        onChangeProcedure();
    }

    public void load()
    {
        ArrayList<org.metier.beans.ProcedureLance> procedureLances;
        ObservableList<ProcedureLance> dataProcedure = optionsProcedures.getItems();
        procedureLances = DemandeManager.getAllProcedures(LoginManager.nomCompletChef().getNumero());
        for(org.metier.beans.ProcedureLance procedureLance: procedureLances )
        {
            ProcedureLance procedureLance1 = new  ProcedureLance(procedureLance.getNom());
            dataProcedure.add(procedureLance1);
            //On fait la conversion du metier ou modéle
            org.modele.ProcedureLance proc = CastFrom.beansToModele(procedureLance);
        }
    }

    private void loadDataS1()
    {
        var data = new XYChart.Series<String, Number>();
        HashMap<String,Integer> donnees;
        donnees = DemandeManager.statistiques(id);
        for (Map.Entry mapentry : donnees.entrySet()) {
            data.getData().add(new XYChart.Data<>(mapentry.getKey().toString(),
                    Integer.parseInt(mapentry.getValue().toString())));
        }
        ObservableList<XYChart.Series<String, Number>> d1 =
                FXCollections.<XYChart.Series<String, Number>>observableArrayList();
        d1.add(data);
        NETPE.setData(d1);
    }

    private void loadDataS2()
    {

        var dat = new XYChart.Series<String, Number>();
        HashMap<Integer,Integer> donnee = new HashMap<>();
        donnee =   DemandeManager.statistiquesJ(id);
        int key;
        for (Map.Entry mapentry : donnee.entrySet()) {
            key = Integer.parseInt(mapentry.getKey().toString());
            switch (key)
            {
                case 1:
                    dat.getData().add(new XYChart.Data<>("Lundi",Integer.parseInt(mapentry.getValue().toString())));
                    break;
                case 2:
                    dat.getData().add(new XYChart.Data<>("Mardi",Integer.parseInt(mapentry.getValue().toString())));
                    break;
                case 3:
                    dat.getData().add(new XYChart.Data<>("Mercredi",Integer.parseInt(mapentry.getValue().toString())));
                    break;
                case 4:
                    dat.getData().add(new XYChart.Data<>("Jeudi",Integer.parseInt(mapentry.getValue().toString())));
                    break;
                case 5:
                    dat.getData().add(new XYChart.Data<>("Vendredi",Integer.parseInt(mapentry.getValue().toString())));
                    break;
                case 6:
                    dat.getData().add(new XYChart.Data<>("Samedi",Integer.parseInt(mapentry.getValue().toString())));
                    break;
                case 7:
                    dat.getData().add(new XYChart.Data<>("Dimanche",Integer.parseInt(mapentry.getValue().toString())));
                    break;
            }
        }
        ObservableList<XYChart.Series<String, Number>> d2 =
                FXCollections.<XYChart.Series<String, Number>>observableArrayList();
        d2.add(dat);
        NETPJ.setData(d2);
    }

    private void loadDataS3()
    {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        demandes.setLegendSide(Side.LEFT);
        data.add(new PieChart.Data("Demande non acceptées",
                DemandeManager.demandes(id)));
        data.add(new PieChart.Data("Demande acceptées",
                DemandeManager.procedures(id)));
        data.add(new PieChart.Data("Demande rejetés",
                DemandeManager.nbProceduresRejete(id)));
        demandes.setData(data);
    }

    private void loadDataS4()
    {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        procedures.setLegendSide(Side.LEFT);
        data.add(new PieChart.Data("Procedures traitées",
                DemandeManager.nbProceduresT(id)));
        data.add(new PieChart.Data("Procedures non traitées",
                DemandeManager.nbProceduresNT(id)));
        procedures.setData(data);
    }

    public void onChangeProcedure()
    {
        this.optionsProcedures.valueProperty().addListener((ov, oldProcedureLance, newProcedureLance) -> {
            //On charge les nouvelles informations
            loadProcedure(newProcedureLance);
        });
    }

    /**
     * telechargemnt des graphes en fonction de la procedure
     * @param procedureLance
     */
    private void loadProcedure(ProcedureLance procedureLance) {
        loadDataS4(procedureLance.getNom());
        loadDataS3(procedureLance.getNom());
        loadDataS2(procedureLance.getNom());
        loadDataS1(procedureLance.getNom());
    }


    /**
     * graphe du nombre de procedure traites par employes en fonction de la procedure
     * @param nom
     */
    private void loadDataS1(String nom)
    {
        var data = new XYChart.Series<String, Number>();
        HashMap<String,Integer> donnees = new HashMap<>();
        donnees = DemandeManager.statistiques(id,nom);
        for (Map.Entry mapentry : donnees.entrySet()) {
            data.getData().add(new XYChart.Data<>(mapentry.getKey().toString(),Integer.parseInt(mapentry.getValue().toString())));
        }
        ObservableList<XYChart.Series<String, Number>> d1 =
                FXCollections.<XYChart.Series<String, Number>>observableArrayList();
        d1.add(data);
        NETPE.setData(d1);
    }

    /**
     * graphe du nomnre de procedures traitées par jours en fonction de la procedure
     * @param nom
     */
    private void loadDataS2(String nom)
    {

        var dat = new XYChart.Series<String, Number>();
        HashMap<Integer,Integer> donnee = new HashMap<>();
        donnee =   DemandeManager.statistiquesJ(id,nom);
        int key;
        for (Map.Entry mapentry : donnee.entrySet()) {
            key = Integer.parseInt(mapentry.getKey().toString());
            switch (key)
            {
                case 1:
                    dat.getData().add(new XYChart.Data<>("Lundi",Integer.parseInt(mapentry.getValue().toString())));
                    break;
                case 2:
                    dat.getData().add(new XYChart.Data<>("Mardi",Integer.parseInt(mapentry.getValue().toString())));
                    break;
                case 3:
                    dat.getData().add(new XYChart.Data<>("Mercredi",Integer.parseInt(mapentry.getValue().toString())));
                    break;
                case 4:
                    dat.getData().add(new XYChart.Data<>("Jeudi",Integer.parseInt(mapentry.getValue().toString())));
                    break;
                case 5:
                    dat.getData().add(new XYChart.Data<>("Vendredi",Integer.parseInt(mapentry.getValue().toString())));
                    break;
                case 6:
                    dat.getData().add(new XYChart.Data<>("Samedi",Integer.parseInt(mapentry.getValue().toString())));
                    break;
                case 7:
                    dat.getData().add(new XYChart.Data<>("Dimanche",Integer.parseInt(mapentry.getValue().toString())));
                    break;
            }
        }
        ObservableList<XYChart.Series<String, Number>> d2 =
                FXCollections.<XYChart.Series<String, Number>>observableArrayList();
        d2.add(dat);
        NETPJ.setData(d2);
    }

    /**
     * graph des demandes acceptées en fonction des demandes
     * @param nom
     */
    private void loadDataS3(String nom)
    {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        demandes.setLegendSide(Side.LEFT);
        data.add(new PieChart.Data("Demande non acceptées",
                DemandeManager.demandes(id)));
        data.add(new PieChart.Data("Demande acceptées",
                DemandeManager.procedures(id)));
        data.add(new PieChart.Data("Demande rejetés",
                DemandeManager.nbProceduresRejete(id)));
        demandes.setData(data);
    }

    /**
     * graph des procedures traites en fonction des procedures
     * @param nom
     */
    private void loadDataS4(String nom)
    {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        procedures.setLegendSide(Side.LEFT);
        data.add(new PieChart.Data("Procedures traitées",
                DemandeManager.nbProceduresT(id,nom)));
        data.add(new PieChart.Data("Procedures non traitées",
                DemandeManager.nbProceduresNT(id,nom)));
        procedures.setData(data);
    }

}
