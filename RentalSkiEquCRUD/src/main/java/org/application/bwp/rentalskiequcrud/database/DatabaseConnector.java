package org.application.bwp.rentalskiequcrud.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

    public Connection getConnection(String database) throws SQLException {
        return DriverManager.getConnection(URL + "/" + database, USER, PASSWORD);
    }

    public String getDB_NAME() {
        return DB_NAME;
    }
}
