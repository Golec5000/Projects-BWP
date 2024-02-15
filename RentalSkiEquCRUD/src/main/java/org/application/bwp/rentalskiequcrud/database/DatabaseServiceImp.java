package org.application.bwp.rentalskiequcrud.database;

import org.application.bwp.rentalskiequcrud.entity.Customer;
import org.application.bwp.rentalskiequcrud.entity.Reservation;
import org.application.bwp.rentalskiequcrud.entity.SkiEqu;
import org.application.bwp.rentalskiequcrud.entity.enums.Payment;
import org.application.bwp.rentalskiequcrud.entity.enums.Status;
import org.application.bwp.rentalskiequcrud.jsonFile.readerFromFile.JsonReadFromFile;
import org.application.bwp.rentalskiequcrud.jsonFile.writerToFile.JsonWriteToFile;

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
    public boolean checkIfDatabaseExist() {
        try(Connection ignored = dataBaseConnector.getConnectionToSnowRental()) {
            return true;
        }catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean createDatabase() {

        try (Connection connection = dataBaseConnector.getConnection()) {
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
        try (Connection connection = dataBaseConnector.getConnectionToSnowRental()) {
            String sql = "CREATE TABLE IF NOT EXISTS klienci  (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "nazwa VARCHAR(255) NOT NULL UNIQUE," +
                    "password VARCHAR(255) NOT NULL" +
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
        try (Connection connection = dataBaseConnector.getConnectionToSnowRental()) {
            String sql = "CREATE TABLE IF NOT EXISTS narty  (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "rodzaj VARCHAR(255) NOT NULL," +
                    "rozmiar INT NOT NULL," +
                    "status ENUM('DOSTEPNE', 'NIEDOSTEPNE') NOT NULL" +
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
        try (Connection connection = dataBaseConnector.getConnectionToSnowRental()) {
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
        List<SkiEqu> skiEquList = jsonReadFromFile.readSki();

        if(jsonReadFromFile.isListEmpty(skiEquList)) return false;

        try (Connection connection = dataBaseConnector.getConnectionToSnowRental()) {
            String sql = "INSERT INTO narty (rodzaj, rozmiar, status) VALUES (?, ?, ?)"; // updated SQL

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                for (SkiEqu skiEqu : skiEquList) {
                    preparedStatement.setString(1, skiEqu.getRodzaj());
                    preparedStatement.setInt(2, skiEqu.getDlugosc());
                    preparedStatement.setString(3, skiEqu.getStatus().toString()); // insert status
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

        try (Connection connection = dataBaseConnector.getConnectionToSnowRental()) {
            String sql = "INSERT INTO klienci (nazwa, password) VALUES (?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                for (Customer customer : customerList) {
                    preparedStatement.setString(1, customer.getNazwa());
                    preparedStatement.setString(2, customer.getPassword());
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

        if(jsonReadFromFile.isListEmpty(reservationList)) return false;

        try (Connection connection = dataBaseConnector.getConnectionToSnowRental()) {
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
    public List<SkiEqu> selectAllSki() {
        try (Connection connection = dataBaseConnector.getConnectionToSnowRental()) {
            String sql = "SELECT * FROM narty";

            List<SkiEqu> skiEquList = new ArrayList<>();

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.execute();

                ResultSet resultSet = preparedStatement.getResultSet();

                while (resultSet.next()) {
                    SkiEqu skiEqu = new SkiEqu()
                            .setId(resultSet.getInt("id"))
                            .setRodzaj(resultSet.getString("rodzaj"))
                            .setDlugosc(resultSet.getInt("rozmiar"))
                            .setStatus(Status.valueOf(resultSet.getString("status")))
                            .build();
                    skiEquList.add(skiEqu);
                }

                return skiEquList;
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
        try (Connection connection = dataBaseConnector.getConnectionToSnowRental()) {
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
                            .setPassword(resultSet.getString("password"))
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
    public boolean saveSkiDataToFile() {
        JsonWriteToFile jsonWriteToFile = JsonWriteToFile.getInstance();
        boolean isCorrectSave = jsonWriteToFile.prepareDataAndWriteToFile(selectAllSki());

        if(!isCorrectSave) jsonWriteToFile.errorHandler();
        return isCorrectSave;
    }


    @Override
    public boolean saveCustomerDataToFile() {
        JsonWriteToFile jsonWriteToFile = JsonWriteToFile.getInstance();
        boolean isCorrectSave = jsonWriteToFile.prepareDataAndWriteToFile(selectAllCustomer());

        if(!isCorrectSave) jsonWriteToFile.errorHandler();
        return isCorrectSave;
    }

    @Override
    public boolean saveReservationDataToFile() {
        JsonWriteToFile jsonWriteToFile = JsonWriteToFile.getInstance();
        boolean isCorrectSave = jsonWriteToFile.prepareDataAndWriteToFile(selectAllReservation());

        if(!isCorrectSave) jsonWriteToFile.errorHandler();
        return isCorrectSave;
    }

    @Override
    public boolean dropDatabase() {
        try (Connection connection = dataBaseConnector.getConnection()) {
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

    @Override
    public boolean deleteCustomer(int id) {
        try (Connection connection = dataBaseConnector.getConnectionToSnowRental()) {
            String sqlMainCustomerTable = "DELETE FROM klienci WHERE id = ?";
            String sqlMainReservationTable = "DELETE FROM rezerwacja WHERE id_klient = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlMainReservationTable)) {
                preparedStatement.setInt(1, id);
                preparedStatement.execute();
            } catch (SQLException e) {
                dataBaseConnector.handleDatabaseError(e, DatabaseErrorsTypes.DELETE_ERROR);
                return false;
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlMainCustomerTable)) {
                preparedStatement.setInt(1, id);
                preparedStatement.execute();
            } catch (SQLException e) {
                dataBaseConnector.handleDatabaseError(e, DatabaseErrorsTypes.DELETE_ERROR);
                return false;
            }

            return true;

        } catch (SQLException e) {
            dataBaseConnector.handleDatabaseError(e, DatabaseErrorsTypes.CONNECTION_ERROR);
            return false;
        }
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        try (Connection connection = dataBaseConnector.getConnectionToSnowRental()) {
            String sql = "UPDATE klienci SET nazwa = ?, password = ? WHERE id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, customer.getNazwa());
                preparedStatement.setString(2, customer.getPassword());
                preparedStatement.setInt(3, customer.getId());
                preparedStatement.execute();
                return true;
            } catch (SQLException e) {
                dataBaseConnector.handleDatabaseError(e, DatabaseErrorsTypes.UPDATE_ERROR);
                return false;
            }

        } catch (SQLException e) {
            dataBaseConnector.handleDatabaseError(e, DatabaseErrorsTypes.CONNECTION_ERROR);
            return false;
        }
    }
}
