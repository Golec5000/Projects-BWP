package org.application.bwp.rentalskiequcrud.database;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;

public class DatabaseConnector {

    private final String URL = "jdbc:mysql://localhost:3306";

    private final String DB_NAME = "skiandsnowboardrental";
    private final String USER = "root";
    private final String PASSWORD = "root";

    private DatabaseConnector() {
    }

    private static DatabaseConnector instance;

    public static DatabaseConnector getInstance() {
        if(instance == null) return new DatabaseConnector();
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public Connection getConnectionToSnowRental() throws SQLException {
        return DriverManager.getConnection(URL + "/" + DB_NAME, USER, PASSWORD);
    }

    public void handleDatabaseError(SQLException e,DatabaseErrorsTypes errorType) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Błąd");
        alert.setHeaderText("Błąd " + errorType);
        alert.setContentText("Sprawdź " + errorType + " " + e.getMessage());
        System.out.println("Błąd " + errorType + " " + e.getMessage());
        System.out.println(Arrays.toString(e.getStackTrace()));
        alert.showAndWait();
    }

}
