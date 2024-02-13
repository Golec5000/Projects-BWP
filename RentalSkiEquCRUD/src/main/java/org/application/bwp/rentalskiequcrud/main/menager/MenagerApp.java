package org.application.bwp.rentalskiequcrud.main.menager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.application.bwp.rentalskiequcrud.database.DatabaseConnector;
import org.application.bwp.rentalskiequcrud.database.DatabaseService;
import org.application.bwp.rentalskiequcrud.database.DatabaseServiceImp;
import org.application.bwp.rentalskiequcrud.jsonFile.readerFromFile.JsonReadFromFile;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class MenagerApp extends Application {

    private final DatabaseConnector dataBaseConnector = DatabaseConnector.getInstance();

    private final DatabaseService databaseService = DatabaseServiceImp.getInstance();

    private final JsonReadFromFile jsonReadFromFile = JsonReadFromFile.getInstance();

    @Override
    public void start(Stage stage){

        try (Connection ignored = dataBaseConnector.getConnection()) {

            System.out.println("Connected to the database!");

            sceneOn(stage);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void sceneOn(Stage stage) {
        Parent root;

        try {

            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/application/bwp/rentalskiequcrud/menagerGuiFile/menager-main-page.fxml")));

        } catch (IOException | NullPointerException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setHeaderText("Błąd ładowania pliku fxml");
            alert.setContentText("Sprawdź pliki aplikacji " + e.getMessage());
            alert.showAndWait();

            return;
        }

        Scene scene = new Scene(root);
        stage.setTitle("CRUD MenagerApp");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}