package org.application.bwp.airqualityapp.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage){

        Parent root = null;

        try {

            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/application/bwp/airqualityapp/mainPage/main-page-v2.fxml")));

        } catch (IOException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setHeaderText("Błąd ładowania pliku fxml");
            alert.setContentText("Sprawdź pliki aplikacji");
            alert.showAndWait();

        }

        Scene scene = new Scene(root);
        stage.setTitle("Weather and Air Quality App");
        stage.setScene(scene);
        stage.show();

    }
}
