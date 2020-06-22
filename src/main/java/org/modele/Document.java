package org.modele;

import javafx.scene.control.Button;

public class Document extends org.metier.beans.Document {
    private Button consulter;

    public Document() {
        consulter = new Button("Consulter");
        consulter.setStyle( "-fx-background-color: #FF9900");
    }

    public Button getConsulter() {
        return consulter;
    }

    public void setConsulter(Button consulter) {
        this.consulter = consulter;
    }
}
