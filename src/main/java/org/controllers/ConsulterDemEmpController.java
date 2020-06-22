package org.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import org.metier.beans.Etape;
import org.metier.beansManager.ProcedureManager;
import org.modele.Document;
import org.modele.Procedure;
import org.utils.Etat;
import org.utils.PrintMessage;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

public class ConsulterDemEmpController implements Initializable {
    public Label nomProc;
    public Label numDemande;
    public Label numCitoyen;
    public TableColumn<Document,Integer> numero;
    public TableColumn<Document,String> nomDoc;
    public TableColumn<Document,Button> visualiserDoc;
    private static Procedure procedure;
    public TableView<Document> documentTable;

    /**
     * Boîte de dialogue pour recupérer
     * la raison de la décision prise
     * par l'employé
     */
    TextInputDialog dialogRapport;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nomProc.setText(procedure.getNom());
        numCitoyen.setText(procedure.getCin());
        numDemande.setText(String.valueOf(procedure.getNumero()));
        loadDataOnTable();
        initDialogRapport();
    }

    /**
     * Initialiser la boite de dialogue
     */
    private void initDialogRapport() {
        dialogRapport= new TextInputDialog();
        dialogRapport.setTitle("Rédaction du rapport");
        dialogRapport.getEditor().setPromptText("Mon justificatif ...");
        dialogRapport.setHeaderText("Saisissez la raison de votre décision");
    }

    /**
     * Modification de la procédure
     * @param procedure procedure à traiter par l'employé
     */
    public static void setProcedure(Procedure procedure) {

        ConsulterDemEmpController.procedure = procedure;
    }

    private void setCellValueFactory(){
        numero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        nomDoc.setCellValueFactory(new PropertyValueFactory<>("nom"));
        visualiserDoc.setCellValueFactory(new PropertyValueFactory<>("consulter"));
    }

    /**
     * Méthode pour charger les données et le mettre dans
     * le tableview
     */
    private void loadDataOnTable() {
        setCellValueFactory();

        ObservableList<Document> items = documentTable.getItems();
        items.clear();

        for(org.metier.beans.Document document :
                procedure.getListeDocuments()) {

            Document documentModele = new Document();
            documentModele.setNumero(document.getNumero());
            documentModele.setNom(document.getNom());
            documentModele.setUrl(document.getUrl());

            documentModele.getConsulter().setOnAction(event ->
                    visualiserDocument(documentModele)
            );

            items.add(documentModele);
        }
    }

    private void visualiserDocument(Document documentModele) {
        PdfController.setUrl(documentModele.getUrl());
        PdfController.openPdf();
    }

    /**
     * Retourner à la page précédente
     */
    public void onRetour() {
        MenuEmployeController.goProcedures();
    }

    /**
     * Méthode qui s'execute lorsqu'un
     * employé accepte un étape
     */
    public void onAccepter() {
        if (dialogRapport.showAndWait().isPresent()){
            String rapport = dialogRapport.getEditor().getText();

            setDecision(procedure.getEtapeActuelle(),
                    Etat.ACCEPTE, rapport);
            ProcedureManager.updateEtapeActuelle(procedure);

            PrintMessage.afficherSucces("Confirmation",
                    "Modification enregistrée");
            onRetour();
        }
    }

    /**
     * Méthode qui s'execute lorsqu'un
     * employé choisit la mise à jour
     */
    public void onMiseAJour() {
        MiseAJourController.openDialog();
        ArrayList<org.metier.beans.Document> documentsMiseAJour =
                MiseAJourController.getDocuments();
        if (documentsMiseAJour != null){
            String rapport =  MiseAJourController.getRapport();

            setDecision(procedure.getEtapeActuelle(),
                    Etat.MAJ, rapport);

            ProcedureManager.updateEtapeActuelleMAJ(procedure, documentsMiseAJour);
            PrintMessage.afficherSucces("Confirmation",
                    "Modification enregistrée");
            onRetour();
        }
    }

    /**
     * Méthode qui s'execute
     * lorsque l'employé décide de rejeter
     * l'étape
     */
    public void onRejeter() {
        if (dialogRapport.showAndWait().isPresent()){
            String rapport = dialogRapport.getEditor().getText();

            setDecision(procedure.getEtapeActuelle(),
                    Etat.REJETE, rapport);

            ProcedureManager.updateEtapeActuelle(procedure);

            PrintMessage.afficherSucces("Confirmation",
                    "Modification enregistrée");
            onRetour();
        }
    }

    private void setDecision(Etape etapeActuelle,
                             Etat decision, String rapport) {

        etapeActuelle.setEtat(decision.toString());
        etapeActuelle.setJour(getDay());
        etapeActuelle.setDate(getToday());

        if (decision == Etat.MAJ){
            etapeActuelle.setRapportMAJ(rapport);
        }else {
            etapeActuelle.setRapport(rapport);
        }
    }

    private String getToday() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dateTimeFormatter.format(now);
    }

    private int getDay(){

        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    }
}
