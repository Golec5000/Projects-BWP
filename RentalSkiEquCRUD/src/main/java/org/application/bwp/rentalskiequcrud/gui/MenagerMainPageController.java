package org.application.bwp.rentalskiequcrud.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.application.bwp.rentalskiequcrud.database.DatabaseService;
import org.application.bwp.rentalskiequcrud.database.DatabaseServiceImp;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MenagerMainPageController implements Initializable {

    @FXML
    private Tab customersTab;

    @FXML
    private Button dropBbButton;

    @FXML
    private Button initDbButton;

    @FXML
    private Button loadSaveDbButton;

    @FXML
    private ImageView logoImageView;

    @FXML
    private Tab magazineTab;

    @FXML
    private Tab reservationTab;

    @FXML
    private Button saveDbButton;

    private final DatabaseService databaseService = DatabaseServiceImp.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init();
        loadLogo();

        initDb();
        loadSaveDb();
        saveDb();
        dropDb();
    }

    private void init() {

        if (databaseService.checkIfDatabaseExist()) {

            magazineTab.setDisable(false);
            reservationTab.setDisable(false);
            customersTab.setDisable(false);

            initDbButton.setDisable(true);

            magazineTab.setDisable(false);
            reservationTab.setDisable(false);
            customersTab.setDisable(false);

            return;

        }

        magazineTab.setDisable(true);
        reservationTab.setDisable(true);
        customersTab.setDisable(true);
    }

    private void initDb() {
        initDbButton.setOnAction(event -> {

            databaseService.createDatabase();

            databaseService.createSkiTable();
            databaseService.createCustomerTable();
            databaseService.createReservationTable();

            magazineTab.setDisable(false);
            reservationTab.setDisable(false);
            customersTab.setDisable(false);
        });
    }

    private void loadSaveDb() {
        loadSaveDbButton.setOnAction(event -> {
            databaseService.insertSkiData();
            databaseService.insertCustomerData();
            databaseService.insertReservationData();
        });
    }

    private void saveDb() {
        saveDbButton.setOnAction(event -> {
            databaseService.saveSkiDataToFile();
            databaseService.saveCustomerDataToFile();
            databaseService.saveReservationDataToFile();
        });
    }

    private void dropDb() {
        dropBbButton.setOnAction(event -> {
            databaseService.dropDatabase();
            init();
        });
    }

    private void loadLogo() {
        logoImageView.setImage(new Image(Objects.requireNonNull(getClass().getResource("/org/application/bwp/rentalskiequcrud/images/skiImage.jpg")).toString()));
    }
}