package org.modele;

import javafx.scene.control.Button;

import java.util.Objects;

public class Procedure extends org.metier.beans.Procedure {
    private final Button consulter;

    public Procedure() {
        consulter = new Button("Consulter");
        consulter.setStyle("-fx-background-color:#FF9900");
    }

    public Button getConsulter() {
        return consulter;
    }

    @Override
    public String toString() {
        return getNom();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Procedure procedure = (Procedure) o;
        return getNumero() == procedure.getNumero();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumero());
    }
}
