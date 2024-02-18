package org.application.bwp.rentalskiequcrud.main.menager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class ManagerApp extends Application {

    @Override
    public void start(Stage stage) {
        Parent root;

        try {

            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/application/bwp/rentalskiequcrud/guiPages/manager/manager-main-page.fxml")));
            String css = Objects.requireNonNull(this.getClass().getResource("/org/application/bwp/rentalskiequcrud/cssStyle/basic-style.css")).toExternalForm();

            Scene scene = new Scene(root);
            scene.getStylesheets().add(css);
            stage.setTitle("CRUD ManagerApp");
            stage.setScene(scene);
            stage.show();

        } catch (IOException | NullPointerException e) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Błąd");
            alert.setHeaderText("Błąd ładowania pliku fxml");
            alert.setContentText("Sprawdź pliki aplikacji " + e.getMessage());
            alert.showAndWait();
            System.out.println(Arrays.toString(e.getStackTrace()));
        }


    }

    public static void main(String[] args) {
        launch();
    }
}