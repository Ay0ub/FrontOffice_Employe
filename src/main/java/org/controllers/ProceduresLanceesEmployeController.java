package org.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.Main;
import org.metier.beansManager.LoginManager;
import org.metier.beansManager.ProcedureManager;
import org.modele.Procedure;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProceduresLanceesEmployeController implements Initializable {
    public TableColumn<Procedure,String> cin;
    public TableColumn<Procedure,String> nomProc;
    public TableColumn<Procedure,String> date;
    public TableColumn<Procedure,String> etape;
    public TableColumn<Procedure, Button> boutonConsulter;
    public TableView<Procedure> proceduresLanceesTable;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDataOnTable();
    }



    public void loadDataOnTable()
    {
        setCellValueFactory();

        ObservableList<Procedure> items = proceduresLanceesTable.getItems();
        items.clear();

        ArrayList<org.metier.beans.Procedure> procedures =
                ProcedureManager.getAllProcedureByEmploye(LoginManager.getEmploye());

        for(org.metier.beans.Procedure procedure : procedures)
        {
            Procedure procedureModele = new Procedure();
            procedureModele.setNumero(procedure.getNumero());
            procedureModele.setNom(procedure.getNom());
            procedureModele.setCin(procedure.getCin());
            procedureModele.setDate(procedure.getDate());
            procedureModele.setEtapeActuelle(procedure.getEtapeActuelle());
            procedureModele.setListeDocuments(procedure.getListeDocuments());

            procedureModele.getConsulter().setOnAction(event ->
                consulterDemande(procedureModele)
            );
            items.add(procedureModele);
        }


    }

    private void setCellValueFactory(){
        cin.setCellValueFactory(new PropertyValueFactory<>("cin"));
        nomProc.setCellValueFactory(new PropertyValueFactory<>("nom"));
        etape.setCellValueFactory(new PropertyValueFactory<>("etapeActuelle"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        boutonConsulter.setCellValueFactory(new PropertyValueFactory<>("consulter"));
    }

    private void consulterDemande(Procedure procedureModele)
    {
        ConsulterDemEmpController.setProcedure(procedureModele);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource(
                "/views/consulterDemandeEmploye.fxml"));
        try {
            MenuEmployeController.setContenu(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
