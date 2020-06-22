package org.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.Main;
import org.modele.Document;
import org.utils.PrintMessage;

import java.io.IOException;
import java.util.ArrayList;

import static javafx.stage.Modality.APPLICATION_MODAL;


public class MiseAJourController{
    public TableView<Document> docsAjoutes;
    public TableColumn<Document, String> nomDoc;
    public TableColumn<Document, Button> deleteDoc;
    public TextField documentSaisi;
    public TextField rapportField;

    private static Stage dialog;
    private static ArrayList<org.metier.beans.Document> documents;
    private static String rapport;

    /**
     * Méthode pour ouvrir la boite
     * de dialogue pour saisir
     * les documents à mettre à jour
     * et la raison de la décision
     */
    public static void openDialog(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/views/miseAJour.fxml"));
        try {
            dialog = new Stage();
            dialog.setScene(new Scene(loader.load()));
            dialog.initModality(APPLICATION_MODAL);
            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Pour fermer la boite de dialogue
     */
    public static void closeDialog(){
        dialog.close();
    }

    /**
     * Méthode pour ajouter un nouveau
     * document à la liste des documents
     */
    public void onAjouter() {
        String nomDocument = documentSaisi.getText();
        if (nomDocument.isEmpty()){
            PrintMessage.afficherErreur("Erreur sur le document",
                    "Veuillez saisir le nom du document");
            return;
        }

        Document document = new Document();
        document.setNom(nomDocument);
        document.getConsulter().setText("Supprimer");

        ObservableList<Document> docsAjoutesItems = docsAjoutes.getItems();
        document.getConsulter().setOnAction(event ->
                docsAjoutesItems.remove(document));

        docsAjoutesItems.add(document);
    }

    /**
     * Méthode pour enregistrer les données saisies
     */
    public void onValider() {
        ObservableList<Document> docsAjoutesItems = docsAjoutes.getItems();
        String rapportSaisi = rapportField.getText();

        //On vérifie si les données ont été entrées
        if (docsAjoutesItems.isEmpty() || rapportSaisi.isEmpty()){
            PrintMessage.afficherErreur("Données Insuffisantes",
                    "Vous devez entrer au moins un document et le rapport");
            return;
        }
        rapport = rapportSaisi;
        documents = new ArrayList<>();

        //On enregistre les données dans des arrays
        for (int i = 0; i < docsAjoutesItems.size(); i++) {
            Document  documentModele = docsAjoutesItems.get(i);

            org.metier.beans.Document document = new Document();
            document.setNumero(String.valueOf(i+1));
            document.setNom(documentModele.getNom());

            documents.add(document);
        }

        closeDialog();
    }

    /**
     * Méthode pour recupérer les documents à mettre à jour
     * @return liste des documents à mettre à jour
     */
    public static ArrayList<org.metier.beans.Document> getDocuments() {
        return documents;
    }

    /**
     * Méthode qui permet de recupérer le rapport
     * saisi par l'employé
     * @return rapport de l'employé
     */
    public static String getRapport() {
        return rapport;
    }

}
