package org.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import org.Main;
import org.metier.beans.Demande;
import org.metier.beansManager.DemandeManager;
import org.metier.beansManager.LoginManager;
import org.modele.Document;
import org.utils.CastFrom;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class ConsulterDemandeController implements Initializable {
    public Label nomProc;
    public Label numDemande;
    public Label numCitoyen;
    public TableColumn<Document,String> numero;
    public TableColumn<Document, Button> visualiserDoc;
    public Image imageDoc;
    public TableColumn<Document,String> nomDoc;
    public TableView<Document> documents;
    private final ObservableList<org.modele.Document> dataDocView = FXCollections.observableArrayList();
    private static int id;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCellValueFactory();
        load(id);
    }

    private void setCellValueFactory(){
        numero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        nomDoc.setCellValueFactory(new PropertyValueFactory<>("nom"));
        visualiserDoc.setCellValueFactory(new PropertyValueFactory<>("consulter"));
    }

    public void load(int id)
    {
        Demande demande = new Demande();
        demande = DemandeManager.getDemandeById(id);
        nomProc.setText(demande.getNom());
        numDemande.setText(String.valueOf(demande.getNumero()));
        numCitoyen.setText(demande.getCin());
        for(org.metier.beans.Document doc: demande.getDocuments() )
        {
            //On fait la conversion du metier ou modéle
            org.modele.Document document = CastFrom.beansToModele(doc);
            document.getConsulter().setOnAction(event ->{
                openPdf(doc.getUrl());
            });
            dataDocView.add(document);//On l'ajoute dans le tableau
        }
        documents.setItems(dataDocView);
    }

    private void openPdf(String url)
    {

        PdfController.setUrl(url);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/views/pdfView.fxml"));
        try {
            MenuChefController.main.setCenter(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void onRetour(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/views/demandes.fxml"));
        try {
            MenuChefController.main.setCenter(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onAccepter(ActionEvent actionEvent) throws JsonProcessingException {
        Random rand = new Random();
        int rand_int = rand.nextInt(1000000);
        DemandeManager.addJeton(rand_int,id, LoginManager.nomCompletChef().getNumero());
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/views/demandes.fxml"));
        try {
            MenuChefController.main.setCenter(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onRejeter(ActionEvent actionEvent) {
        DemandeManager.rejectDemande(id);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/views/demandes.fxml"));
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
        ConsulterDemandeController.id = id;
    }
}
