package org.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import org.metier.beans.Employe;
import org.metier.beansManager.LoginManager;
import org.metier.beansManager.ProcedureManager;
import org.utils.Jour;

import java.net.URL;
import java.util.ResourceBundle;

public class PerformancesEmployeController implements Initializable {
    public PieChart piechart;

    public CategoryAxis jourAxis;
    public NumberAxis nbreProcsAxis;
    public LineChart<CategoryAxis, NumberAxis> lineChart;
    public TitledPane firstPane;
    private Employe employeConnecte;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        employeConnecte = LoginManager.getEmploye();
        loadDataLineChart();
        loadDataPieChart();
    }

    /**
     * Charger les données
     * pour afficher l'évolution
     * du traitement de procédures
     */
    private void loadDataLineChart(){
        lineChart.getData().clear();
        XYChart.Series<CategoryAxis, NumberAxis> dataSeries = new XYChart.Series<>();
        dataSeries.getData().add(new XYChart.Data( Jour.LUNDI.toString(),
                ProcedureManager.countProceduresTraitees(employeConnecte, 1)));

        dataSeries.getData().add(new XYChart.Data( Jour.MARDI.toString(),
                ProcedureManager.countProceduresTraitees(employeConnecte, 2)));

        dataSeries.getData().add(new XYChart.Data( Jour.MERCREDI.toString(),
                ProcedureManager.countProceduresTraitees(employeConnecte, 3)));

        dataSeries.getData().add(new XYChart.Data( Jour.JEUDI.toString(),
                ProcedureManager.countProceduresTraitees(employeConnecte, 4)));

        dataSeries.getData().add(new XYChart.Data( Jour.VENDREDI.toString(),
                ProcedureManager.countProceduresTraitees(employeConnecte, 5)));

        dataSeries.getData().add(new XYChart.Data( Jour.SAMEDI.toString(),
                ProcedureManager.countProceduresTraitees(employeConnecte, 6)));

        dataSeries.setName("Toutes les procédures");
        lineChart.getData().add(dataSeries);
    }

    /**
     * Charger les données
     * pour afficher la proportion
     * des procédures traitées
     */
    private void loadDataPieChart(){
        ObservableList<PieChart.Data> data = piechart.getData();
        data.clear();

        data.add(new PieChart.Data(
                "Procédures non traitées",
                ProcedureManager.countProceduresNonTraitees(employeConnecte)));
        data.add(new PieChart.Data(
                "Procédures traitées",
                ProcedureManager.countProceduresTraitees(employeConnecte)));
        piechart.getData().forEach(donnee -> {
            String value = String.valueOf(donnee.getPieValue());
            Tooltip toolTip = new Tooltip(value);
            Tooltip.install(donnee.getNode(), toolTip);
        });
    }
}
