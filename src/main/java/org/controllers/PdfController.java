package org.controllers;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Pagination;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.Main;
import org.modele.PdfModel;
import org.utils.PrintMessage;

public class PdfController implements Initializable {

    public Button retour;
    @FXML
    private Pagination pagination;

    private PdfModel model;
    private static String url;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        model = new PdfModel(Paths.get(url));
        pagination.setPageCount(model.numPages());
        pagination.setPageFactory(index ->{
            ImageView im =  new ImageView(model.getImage(index));
            im.setFitHeight(580);
            im.setFitWidth(600);
            return im;
        });
        pagination.setMaxSize(40,40);

    }

    public void onRetour(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource(
                "/views/consulterDemande.fxml"));
        try {
            MenuChefController.main.setCenter(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        model.getDocument().close();
    }

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        PdfController.url = url;
    }

    public static void openPdf(){

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/views/pdfView.fxml"));

            Stage dialog = new Stage();

            dialog.setScene(new Scene(loader.load()));
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.showAndWait();

        } catch (Exception e) {
            PrintMessage.afficherErreur("Impossible d'ouvrir le fichier",
                    "Document non disponible");
        }
    }

}
