package org.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.Main;
import org.metier.beansManager.LoginManager;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuChefController implements Initializable {

    public Button accueil;
    public Label date;
    public Button logOut;
    public Button performances;
    public Button compte;
    public Button archives;
    public Button procedures;
    public ImageView demandeImage;
    public Button demandes;
    public ImageView Laccueil;
    public Label nomComplet;
    public static BorderPane main;
    public static Stage stage;
    public ImageView procedureImage;
    public ImageView archiveImage;
    public ImageView performanceImage;
    public ImageView compteImage;
    public Label division;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("E , dd MMM yyyy HH:mm");
        String formattedDate = myDateObj.format(myFormatObj);
        date.setText(formattedDate);
        division.setText("Division ---> "+ LoginManager.nomCompletChef().getNomDiv());
        nomComplet.setText(LoginManager.nomCompletChef().getNomComplet());
        activeBtn(accueil,Laccueil);
    }
    public static void menu()
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/views/menuChef.fxml"));
            main = loader.load();

            FXMLLoader loader2  = new FXMLLoader();
            loader2.setLocation(Main.class.getResource("/views/accueilChef.fxml"));
            main.setCenter(loader2.load());

            Scene scene = new Scene(main);
            stage= new Stage();
            stage.setTitle("");
            stage.setScene(scene);
            stage.initStyle(StageStyle.DECORATED);
            stage.show();
            Main.MainStage.close();

        } catch (IOException e) {
            Logger.getLogger(MenuChefController.class.getName()).log(Level.SEVERE, null,e);
        }

    }


    public void OnAccueil(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/views/accueilChef.fxml"));
        try {
            main.setCenter(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        activeBtn(accueil,Laccueil);
    }

    public void OnDemandes(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/views/demandes.fxml"));
        try {
            main.setCenter(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        activeBtn(demandes,demandeImage);
    }

    public void onProcedures(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/views/proceduresLancees.fxml"));
        try {
            main.setCenter(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        activeBtn(procedures,procedureImage);
    }

    public void onArchives(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/views/archives.fxml"));
        try {
            main.setCenter(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        activeBtn(archives,archiveImage);
    }

    public void onPerformances(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/views/performances.fxml"));
        try {
            main.setCenter(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        activeBtn(performances,performanceImage);
    }

    public void onCompte(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/views/compte.fxml"));
        try {
            main.setCenter(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        activeBtn(compte,compteImage);
    }

    public void onLogOut(ActionEvent actionEvent) {
        Main.MainStage.show();
        stage.close();
    }


    private void activeBtn(Button btn , ImageView img) {

        Button[] btns = {accueil, performances, compte, archives, procedures ,demandes};
        ImageView[] imgs = {Laccueil ,performanceImage ,compteImage ,archiveImage ,procedureImage ,demandeImage};

        for ( Button b : btns) {
            b.setStyle("-fx-background-color:#073763");
        }

        for(ImageView i : imgs) {
            i.setStyle("-fx-fill : #969494");
        }
        btn.setStyle("-fx-background-color: #FF9900;");
        img.setStyle("-fx-fill : #FFFFFF");
    }


}
