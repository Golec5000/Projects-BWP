package org.application.bwp.rentalskiequcrud.database;

import org.application.bwp.rentalskiequcrud.entity.Customer;
import org.application.bwp.rentalskiequcrud.entity.Reservation;
import org.application.bwp.rentalskiequcrud.entity.Ski;
import org.application.bwp.rentalskiequcrud.entity.enums.Payment;
import org.application.bwp.rentalskiequcrud.entity.enums.Status;
import org.application.bwp.rentalskiequcrud.jsonFile.readerFromFile.JsonReadFromFile;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DatabaseServiceImp implements DatabaseService {

    private static DatabaseServiceImp instance;

    public static DatabaseServiceImp getInstance() {
        if (instance == null) return new DatabaseServiceImp();
        return instance;
    }

    private DatabaseServiceImp() {
    }

    private final DatabaseConnector dataBaseConnector = DatabaseConnector.getInstance();
    private final JsonReadFromFile jsonReadFromFile = JsonReadFromFile.getInstance();

    @Override
    public boolean createDatabase() {

        try (Connection connection = DatabaseConnector.getInstance().getConnection()) {
            String sql = "CREATE DATABASE IF NOT EXISTS skiandsnowboardrental";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.execute();
                return true;
            } catch (SQLException e) {
                dataBaseConnector.handleDatabaseError(e, DatabaseErrorsTypes.CREATE_DATABASE_ERROR);
                return false;
            }

        } catch (SQLException e) {
            dataBaseConnector.handleDatabaseError(e, DatabaseErrorsTypes.CONNECTION_ERROR);
            return false;
        }

    }

    @Override
    public boolean createCustomerTable() {
        try (Connection connection = DatabaseConnector.getInstance().getConnectionToSnowRental()) {
            String sql = "CREATE TABLE IF NOT EXISTS klienci  (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "nazwa VARCHAR(255) NOT NULL" +
                    ")";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.execute();
                return true;
            } catch (SQLException e) {
                dataBaseConnector.handleDatabaseError(e, DatabaseErrorsTypes.CREATE_TABLE_ERROR);
                return false;
            }

        } catch (SQLException e) {
            dataBaseConnector.handleDatabaseError(e, DatabaseErrorsTypes.CONNECTION_ERROR);
            return false;
        }
    }

    @Override
    public boolean createSkiTable() {
        try (Connection connection = DatabaseConnector.getInstance().getConnectionToSnowRental()) {
            String sql = "CREATE TABLE IF NOT EXISTS narty  (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "rodzaj VARCHAR(255) NOT NULL," +
                    "rozmiar INT NOT NULL" +
                    ")";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.execute();
                return true;
            } catch (SQLException e) {
                dataBaseConnector.handleDatabaseError(e, DatabaseErrorsTypes.CREATE_TABLE_ERROR);
                return false;
            }

        } catch (SQLException e) {
            dataBaseConnector.handleDatabaseError(e, DatabaseErrorsTypes.CONNECTION_ERROR);
            return false;
        }
    }

    @Override
    public boolean createReservationTable() {
        try (Connection connection = DatabaseConnector.getInstance().getConnectionToSnowRental()) {
            String sql = "CREATE TABLE IF NOT EXISTS rezerwacja  (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "id_klient INT NOT NULL," +
                    "id_narty INT NOT NULL," +
                    "data_poczatkowa DATE NOT NULL," +
                    "data_koncowa DATE NOT NULL," +
                    "status ENUM('ZALOZONA', 'WYCOFANA', 'ROZPOCZETA', 'ZAKONCZONA') NOT NULL," +
                    "platnosc ENUM('NIEOPLACONA', 'OPLACONA') NOT NULL," +
                    "FOREIGN KEY (id_klient) REFERENCES klienci (id)," +
                    "FOREIGN KEY (id_narty) REFERENCES narty (id)" +
                    ")";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.execute();
                return true;
            } catch (SQLException e) {
                dataBaseConnector.handleDatabaseError(e, DatabaseErrorsTypes.CREATE_TABLE_ERROR);
                return false;
            }

        } catch (SQLException e) {
            dataBaseConnector.handleDatabaseError(e, DatabaseErrorsTypes.CONNECTION_ERROR);
            return false;
        }
    }

    @Override
    public boolean insertSkiData() {
        List<Ski> skiList = jsonReadFromFile.readSki();

        if(jsonReadFromFile.isListEmpty(skiList)) return false;

        try (Connection connection = DatabaseConnector.getInstance().getConnectionToSnowRental()) {
            String sql = "INSERT INTO narty (rodzaj, rozmiar) VALUES (?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                for (Ski ski : skiList) {
                    preparedStatement.setString(1, ski.getRodzaj());
                    preparedStatement.setInt(2, ski.getDlugosc());
                    preparedStatement.execute();
                }
                return true;
            } catch (SQLException e) {
                dataBaseConnector.handleDatabaseError(e, DatabaseErrorsTypes.INSERT_ERROR);
                return false;
            }

        } catch (SQLException e) {
            dataBaseConnector.handleDatabaseError(e, DatabaseErrorsTypes.CONNECTION_ERROR);
            return false;
        }
    }

    @Override
    public boolean insertCustomerData() {
        List<Customer> customerList = jsonReadFromFile.readCustomer();

        if(jsonReadFromFile.isListEmpty(customerList)) return false;

        try (Connection connection = DatabaseConnector.getInstance().getConnectionToSnowRental()) {
            String sql = "INSERT INTO klienci (nazwa) VALUES (?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                for (Customer customer : customerList) {
                    preparedStatement.setString(1, customer.getNazwa());
                    preparedStatement.execute();
                }
                return true;
            } catch (SQLException e) {
                dataBaseConnector.handleDatabaseError(e, DatabaseErrorsTypes.INSERT_ERROR);
                return false;
            }

        } catch (SQLException e) {
            dataBaseConnector.handleDatabaseError(e, DatabaseErrorsTypes.CONNECTION_ERROR);
            return false;
        }
    }

    @Override
    public boolean insertReservationData() {
        List<Reservation> reservationList = jsonReadFromFile.readRental();

        reservationList.forEach(System.out::println);

        if(jsonReadFromFile.isListEmpty(reservationList)) return false;

        try (Connection connection = DatabaseConnector.getInstance().getConnectionToSnowRental()) {
            String sql = "INSERT INTO rezerwacja (id_klient, id_narty, data_poczatkowa, data_koncowa, status, platnosc) VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                for (Reservation reservation : reservationList) {
                    preparedStatement.setInt(1, reservation.getId_klient());
                    preparedStatement.setInt(2, reservation.getId_narty());
                    preparedStatement.setDate(3, Date.valueOf(reservation.getData_poczatkowa()));
                    preparedStatement.setDate(4, Date.valueOf(reservation.getData_koncowa()));
                    preparedStatement.setString(5, reservation.getStatus().toString());
                    preparedStatement.setString(6, reservation.getPlatnosc().toString());
                    preparedStatement.execute();
                }
                return true;
            } catch (SQLException e) {
                dataBaseConnector.handleDatabaseError(e, DatabaseErrorsTypes.INSERT_ERROR);
                return false;
            }

        } catch (SQLException e) {
            dataBaseConnector.handleDatabaseError(e, DatabaseErrorsTypes.CONNECTION_ERROR);
            return false;
        }
    }

    @Override
    public List<Ski> selectAllSki() {
        try (Connection connection = DatabaseConnector.getInstance().getConnectionToSnowRental()) {
            String sql = "SELECT * FROM narty";

            List<Ski> skiList = new ArrayList<>();

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.execute();

                ResultSet resultSet = preparedStatement.getResultSet();

                while (resultSet.next()) {
                    Ski ski = new Ski()
                            .setId(resultSet.getInt("id"))
                            .setRodzaj(resultSet.getString("rodzaj"))
                            .setDlugosc(resultSet.getInt("rozmiar"))
                            .build();
                    skiList.add(ski);
                }

                return skiList;
            } catch (SQLException e) {
                dataBaseConnector.handleDatabaseError(e, DatabaseErrorsTypes.SELECT_ERROR);
                return Collections.emptyList();
            }

        } catch (SQLException e) {
            dataBaseConnector.handleDatabaseError(e, DatabaseErrorsTypes.CONNECTION_ERROR);
            return Collections.emptyList();
        }
    }

    @Override
    public List<Reservation> selectAllReservation() {
        try (Connection connection = DatabaseConnector.getInstance().getConnectionToSnowRental()) {
            String sql = "SELECT * FROM rezerwacja";

            List<Reservation> reservationList = new ArrayList<>();

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.execute();

                ResultSet resultSet = preparedStatement.getResultSet();

                while (resultSet.next()) {
                    Reservation reservation = new Reservation()
                            .setId(resultSet.getInt("id"))
                            .setId_klient(resultSet.getInt("id_klient"))
                            .setId_narty(resultSet.getInt("id_narty"))
                            .setData_poczatkowa(resultSet.getDate("data_poczatkowa").toLocalDate())
                            .setData_koncowa(resultSet.getDate("data_koncowa").toLocalDate())
                            .setStatus(Status.valueOf(resultSet.getString("status")))
                            .setPlatnosc(Payment.valueOf(resultSet.getString("platnosc")))
                            .build();
                    reservationList.add(reservation);
                }

                return reservationList;
            } catch (SQLException e) {
                dataBaseConnector.handleDatabaseError(e, DatabaseErrorsTypes.SELECT_ERROR);
                return Collections.emptyList();
            }

        } catch (SQLException e) {
            dataBaseConnector.handleDatabaseError(e, DatabaseErrorsTypes.CONNECTION_ERROR);
            return Collections.emptyList();
        }
    }

    @Override
    public List<Customer> selectAllCustomer() {
        try (Connection connection = DatabaseConnector.getInstance().getConnectionToSnowRental()) {
            String sql = "SELECT * FROM klienci";

            List<Customer> customerList = new ArrayList<>();

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.execute();

                ResultSet resultSet = preparedStatement.getResultSet();

                while (resultSet.next()) {
                    Customer customer = new Customer()
                            .setId(resultSet.getInt("id"))
                            .setNazwa(resultSet.getString("nazwa"))
                            .build();
                    customerList.add(customer);
                }

                return customerList;
            } catch (SQLException e) {
                dataBaseConnector.handleDatabaseError(e, DatabaseErrorsTypes.SELECT_ERROR);
                return Collections.emptyList();
            }

        } catch (SQLException e) {
            dataBaseConnector.handleDatabaseError(e, DatabaseErrorsTypes.CONNECTION_ERROR);
            return Collections.emptyList();
        }
    }

    @Override
    public boolean dropDatabase() {
        try (Connection connection = DatabaseConnector.getInstance().getConnection()) {
            String sql = "DROP DATABASE IF EXISTS skiandsnowboardrental";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.execute();
                return true;
            } catch (SQLException e) {
                dataBaseConnector.handleDatabaseError(e, DatabaseErrorsTypes.DROP_DATABASE_ERROR);
                return false;
            }

        } catch (SQLException e) {
            dataBaseConnector.handleDatabaseError(e, DatabaseErrorsTypes.CONNECTION_ERROR);
            return false;
        }
    }
}
