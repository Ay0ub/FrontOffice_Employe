package org.controllers;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.Main;
import org.metier.beansManager.LoginManager;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class MenuEmployeController implements Initializable {
    public Label nomComplet;

    public Button accueil;
    public ImageView Laccueil;
    public Button procedures;
    public Button performances;
    public Button compte;

    public Button logOut;
    public Label date;
    public static BorderPane main;
    public static Stage stage;
    public ImageView procedureImage;
    public ImageView performanceImage;
    public ImageView compteImage;
    public BorderPane contenu;
    public Label nomDivision;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nomComplet.setText(LoginManager.getEmploye().getNomComplet());
        nomDivision.setText("Division "+LoginManager.getEmploye().getDivision());
        date.setText(dtf.format(now));
        activeBtn(accueil,Laccueil);
        OnAccueil();
    }
    public static void menu()
    {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource(
                    "/views/menuEmploye.fxml"));
            main = loader.load();

            Scene scene = new Scene(main);
            stage= new Stage();
            stage.setTitle(
                    "Gestion des procédures administratives-Espace Employé");
            stage.setScene(scene);
            stage.setResizable(true);
            stage.show();
            Main.MainStage.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void OnAccueil() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/views/accueilEmploye.fxml"));
        try {
            contenu.setCenter(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        activeBtn(accueil,Laccueil);
    }

    public void onProcedures() {
        goProcedures();
        activeBtn(procedures,procedureImage);
    }

    public static void goProcedures(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource(
                "/views/proceduresLanceesEmploye.fxml"));
        try {
            setContenu(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void onPerformances() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/views/performancesEmploye.fxml"));
        try {
            setContenu(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        activeBtn(performances,performanceImage);
    }

    public void onCompte() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource(
                "/views/compte.fxml"));
        try {
            setContenu(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        activeBtn(compte,compteImage);
    }

    public void onLogOut() {
        Main.MainStage.show();
        stage.close();
    }

    private void activeBtn(Button btn , ImageView img) {

        Button[] btns = {accueil, performances, compte, procedures};
        ImageView[] imgs = {Laccueil ,performanceImage ,compteImage,procedureImage };

        for ( Button b : btns) {
            b.setStyle("-fx-background-color:#073763");
        }

        for(ImageView i : imgs) {
            i.setStyle("-fx-fill : #969494");
        }
        btn.setStyle("-fx-background-color: #FF9900;");
        img.setStyle("-fx-fill : #FFFFFF");
    }

    public static void setContenu(Node node){
        BorderPane contenu = (BorderPane) main.getCenter();
        contenu.setCenter(node);

    }
    
}
