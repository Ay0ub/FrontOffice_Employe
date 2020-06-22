package org.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import org.Main;
import org.metier.beans.Etape;
import org.metier.beans.ProcedureLance;
import org.metier.beansManager.DemandeManager;
import org.utils.CastFrom;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ConsulterProceduresController implements Initializable {
    public Label nomProc;
    public Label numDemande;
    public Label numCitoyen;
    public TableColumn<org.modele.Etape, Integer> numero;
    public TableColumn<org.modele.Etape,String> nomEtape;
    public TableColumn<org.modele.Etape,String> responsable;
    public TableColumn<org.modele.Etape,String> contactResp;
    public TableColumn<org.modele.Etape,String> etat;
    public TableColumn<org.modele.Etape, Button> boutonConsulter;
    public TableView<org.modele.Etape> etape;
    public Button deliver;
    private ObservableList<org.modele.Etape> dataEtapeView = FXCollections.observableArrayList();
    private static int id;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCellValueFactory();
        load(id);
    }

    private void setCellValueFactory(){
        numero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        nomEtape.setCellValueFactory(new PropertyValueFactory<>("nom"));
        responsable.setCellValueFactory(new PropertyValueFactory<>("responsable"));
        contactResp.setCellValueFactory(new PropertyValueFactory<>("contact"));
        etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        boutonConsulter.setCellValueFactory(new PropertyValueFactory<>("consulter"));

    }

    public void load(int id)
    {
        ProcedureLance procedureLance;
        procedureLance = DemandeManager.getProcedureById(id);
        nomProc.setText(procedureLance.getNom());
        numDemande.setText(String.valueOf(procedureLance.getNumero()));
        numCitoyen.setText(procedureLance.getCin());
        if (Integer.valueOf(procedureLance.getEtapeActuelle()) < procedureLance.getEtapes().size())
        {
            deliver.setDisable(true);
        }
        else
        {
            deliver.setDisable(false);
        }
        for(Etape etapes: procedureLance.getEtapes())
        {
            //On fait la conversion du metier ou modéle
            org.modele.Etape etape = CastFrom.beansToModele(etapes);
            etape.getConsulter().setOnAction(event -> {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Rapport ");
                alert.setContentText("Rapport de MAJ : " + etapes.getRapportMAJ() + "\n" +
                        "Rapport normale : " + etapes.getRapport());
                alert.showAndWait();
            });
            dataEtapeView.add(etape);//On l'ajoute dans le tableau
        }
        etape.setItems(dataEtapeView);
    }

    public void onDelivrer(ActionEvent actionEvent) throws JsonProcessingException {
        DemandeManager.deliverDocument(id);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/views/proceduresLancees.fxml"));
        try {
            MenuChefController.main.setCenter(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void onArreter(ActionEvent actionEvent) throws JsonProcessingException {
        DemandeManager.rejeterProcedure(id);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/views/proceduresLancees.fxml"));
        try {
            MenuChefController.main.setCenter(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onRetour(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/views/proceduresLancees.fxml"));
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
        ConsulterProceduresController.id = id;
    }
}
