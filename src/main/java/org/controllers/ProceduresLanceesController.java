package org.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.Main;
import org.metier.beans.Demande;
import org.metier.beansManager.DemandeManager;
import org.metier.beansManager.LoginManager;
import org.modele.ProcedureLance;
import org.utils.CastFrom;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProceduresLanceesController implements Initializable {
    public ComboBox<ProcedureLance> optionsProcedures;
    public TableColumn<ProcedureLance,String> numero;
    public TableColumn<ProcedureLance,String> nomProc;
    public TableColumn<ProcedureLance,String> date;
    public TableColumn<ProcedureLance, ProgressBar> tauxAvancement;
    public TableColumn<ProcedureLance, Button> boutonConsulter;
    public TableView<ProcedureLance> procedures;
    private ArrayList<org.metier.beans.ProcedureLance> procedureLances = new ArrayList<>();
    private ObservableList<ProcedureLance> dataProcedureView = FXCollections.observableArrayList();
    private static int id;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCellValueFactory();
        load();
        onChangeProcedure();
    }

    private void setCellValueFactory(){
        numero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        nomProc.setCellValueFactory(new PropertyValueFactory<>("nom"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        tauxAvancement.setCellValueFactory(new PropertyValueFactory<>("avcm"));
        boutonConsulter.setCellValueFactory(new PropertyValueFactory<>("consulter"));
    }

    public void load()
    {
        dataProcedureView.clear();
        procedureLances.clear();
        ObservableList<ProcedureLance> dataProcedure = optionsProcedures.getItems();
        procedureLances = DemandeManager.getAllProcedures(LoginManager.nomCompletChef().getNumero());
        for(org.metier.beans.ProcedureLance procedureLance: procedureLances )
        {
            ProcedureLance procedureLance1 = new  ProcedureLance(procedureLance.getNom());
            dataProcedure.add(procedureLance1);
            //On fait la conversion du metier ou modéle
            org.modele.ProcedureLance proc = CastFrom.beansToModele(procedureLance);
            proc.getConsulter().setOnAction(event -> {
                ConsulterProceduresController.setId(procedureLance.getNumero());
                redirection(procedureLance.getNumero());
            });
            dataProcedureView.add(proc);//On l'ajoute dans le tableau
        }
        procedures.setItems(dataProcedureView);
    }

    private void  redirection(int id)
    {
        setId(id);
        ConsulterDemandeController.setId(id);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/views/consulterProcedures.fxml"));
        try {
            MenuChefController.main.setCenter(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        ProceduresLanceesController.id = id;
    }

    public void onChangeProcedure()
    {
        this.optionsProcedures.valueProperty().addListener((ov, oldProcedureLance, newProcedureLance) -> {
            //On charge les nouvelles informations
            loadProcedure(newProcedureLance);
        });
    }

    private void loadProcedure(ProcedureLance procedureLance) {

        dataProcedureView.clear();
        procedureLances.clear();
        procedureLances = DemandeManager.getAllProcedures(procedureLance.getNom());
        for(org.metier.beans.ProcedureLance procedureLance1 : procedureLances )
        {
            //On fait la conversion du metier ou modéle
            org.modele.ProcedureLance proc = CastFrom.beansToModele(procedureLance1);
            proc.getConsulter().setOnAction(event -> {
                ConsulterProceduresController.setId(procedureLance1.getNumero());
                redirection(procedureLance1.getNumero());
            });
            dataProcedureView.add(proc);//On l'ajoute dans le tableau
        }
        procedures.setItems(dataProcedureView);
    }
}
