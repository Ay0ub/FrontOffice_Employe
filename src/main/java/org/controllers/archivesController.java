package org.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.Main;
import org.metier.beansManager.DemandeManager;
import org.metier.beansManager.LoginManager;
import org.modele.Demande;
import org.modele.ProcedureArchive;
import org.modele.ProcedureLance;
import org.utils.CastFrom;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class archivesController implements Initializable {
    public ComboBox<ProcedureArchive> optionsProcs;
    public TableColumn<ProcedureArchive,String> numero;
    public TableColumn<ProcedureArchive,String> nomDemande;
    public TableColumn<ProcedureArchive,String> dateDebut;
    public TableColumn<ProcedureArchive,String> dateFin;
    public TableColumn<ProcedureArchive, Button> boutonSupprimer;
    public TableView<ProcedureArchive> procedures;

    private ArrayList<org.metier.beans.ProcedureArchive> procedureArchives = new ArrayList<>();
    private ObservableList<ProcedureArchive> dataProcedureView = FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCellValueFactory();
        load();
        onChangeProcedure();
    }

    private void setCellValueFactory(){
        numero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        nomDemande.setCellValueFactory(new PropertyValueFactory<>("nom"));
        dateDebut.setCellValueFactory(new PropertyValueFactory<>("dateD"));
        dateFin.setCellValueFactory(new PropertyValueFactory<>("dateF"));
        boutonSupprimer.setCellValueFactory(new PropertyValueFactory<>("supprimer"));
    }
    public void load()
    {
        dataProcedureView.clear();
        procedureArchives.clear();
        ObservableList<ProcedureArchive> dataProcedure = optionsProcs.getItems();
        procedureArchives = DemandeManager.getAllProceduresArchives(LoginManager.nomCompletChef().getNumero());
        for(org.metier.beans.ProcedureArchive procedureArchive: procedureArchives )
        {
            ProcedureArchive procedureArchive1 = new ProcedureArchive(procedureArchive.getNom());
            dataProcedure.add(procedureArchive1);
            //On fait la conversion du metier ou modéle
            org.modele.ProcedureArchive proc = CastFrom.beansToModele(procedureArchive);

            proc.getSupprimer().setOnAction(event -> redirection(procedureArchive.getNumero()));
            dataProcedureView.add(proc);//On l'ajoute dans le tableau
        }
        procedures.setItems(dataProcedureView);
    }
    private void  redirection(int id)
    {
        if (!DemandeManager.deleteArchive(id))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Suppresiion");
            alert.setContentText("L'archive a ete supprimée ");
            alert.showAndWait();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/views/archives.fxml"));
            try {
                MenuChefController.main.setCenter(loader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void onChangeProcedure()
    {
        this.optionsProcs.valueProperty().addListener((ov, oldProcedureArchive, newProcedureArchive) -> {
            //On charge les nouvelles informations
            loadProcedure(newProcedureArchive);
        });
    }

    private void loadProcedure(ProcedureArchive procedureArchive) {

        dataProcedureView.clear();
        procedureArchives.clear();
        procedureArchives = DemandeManager.getAllProceduresArchives(procedureArchive.getNom());
        for(org.metier.beans.ProcedureArchive procedureArchiveA : procedureArchives )
        {
            //On fait la conversion du metier ou modéle
            org.modele.ProcedureArchive proc = CastFrom.beansToModele(procedureArchiveA);

            proc.getSupprimer().setOnAction(event -> redirection(procedureArchiveA.getNumero()));
            dataProcedureView.add(proc);//On l'ajoute dans le tableau
        }
        procedures.setItems(dataProcedureView);
    }
}
