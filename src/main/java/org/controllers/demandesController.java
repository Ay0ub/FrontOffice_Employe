package org.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.Main;
import org.metier.beansManager.DemandeManager;
import org.metier.beansManager.LoginManager;
import org.modele.Demande;
import org.modele.ProcedureLance;
import org.utils.CastFrom;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class demandesController implements Initializable {
    public ComboBox<ProcedureLance> optionsDivision;
    public TableColumn<Demande,String> numero;
    public TableColumn<Demande,String> nomDemande;
    public TableColumn<Demande,String> date;
    public TableColumn<Demande, Button> boutonConsulter;
    public TableView<Demande> demandes;
    public static int id;

    private ArrayList<org.metier.beans.Demande> demandes1 = new ArrayList<>();
    private ObservableList<Demande> dataDemandeView = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCellValueFactory();
        load();
        onChangeProcedure();
    }

    private void setCellValueFactory(){
        numero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        nomDemande.setCellValueFactory(new PropertyValueFactory<>("nom"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        boutonConsulter.setCellValueFactory(new PropertyValueFactory<>("consulter"));
    }

    public void load()
    {
        dataDemandeView.clear();
        demandes1.clear();
        ObservableList<ProcedureLance> dataProcedure = optionsDivision.getItems();
        demandes1 = DemandeManager.getAllDemandes(LoginManager.nomCompletChef().getNumero());
        for(org.metier.beans.Demande demande: demandes1 )
        {
            ProcedureLance procedureLance = new  ProcedureLance(demande.getNom());
            dataProcedure.add(procedureLance);
            //On fait la conversion du metier ou modéle
            Demande dmd = CastFrom.beansToModele(demande);

            dmd.getConsulter().setOnAction(event -> redirection(demande.getNumero()));
            dataDemandeView.add(dmd);//On l'ajoute dans le tableau
        }
        demandes.setItems(dataDemandeView);
    }

    private void  redirection(int id)
    {
        setId(id);
        ConsulterDemandeController.setId(id);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/views/consulterDemande.fxml"));
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
        demandesController.id = id;
    }

    public void onChangeProcedure()
    {
        this.optionsDivision.valueProperty().addListener((ov, oldProcedureLance, newProcedureLance) -> {
            //On charge les nouvelles informations
            loadProcedure(newProcedureLance);
        });
    }

    private void loadProcedure(ProcedureLance procedureLance) {

        dataDemandeView.clear();
        demandes1.clear();
        demandes1 = DemandeManager.getAllDemandes(procedureLance.getNom());
        for(org.metier.beans.Demande demande : demandes1 )
        {
            //On fait la conversion du metier ou modéle
            Demande dmd = CastFrom.beansToModele(demande);
            dmd.getConsulter().setOnAction(event -> redirection(demande.getNumero()));
            dataDemandeView.add(dmd);//On l'ajoute dans le tableau
        }
        demandes.setItems(dataDemandeView);
    }
}
