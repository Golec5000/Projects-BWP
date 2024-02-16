package org.application.bwp.rentalskiequcrud.database;

import org.application.bwp.rentalskiequcrud.entity.Customer;
import org.application.bwp.rentalskiequcrud.entity.Reservation;
import org.application.bwp.rentalskiequcrud.entity.SkiEqu;

import java.util.List;

public interface DatabaseService {


    //create database and tables methods
    boolean checkIfDatabaseExist();
    boolean createDatabase();
    boolean createCustomerTable();
    boolean createSkiTable();
    boolean createReservationTable();

    //insert data into tables
    boolean insertSkiData();
    boolean insertCustomerData();
    boolean insertReservationData();

    boolean insertCustomer(Customer customer);
    boolean insertSki(SkiEqu skiEqu);

    //select data from tables
    List<SkiEqu> selectAllSki();

    List<Reservation> selectAllReservation();

    List<Customer> selectAllCustomer();


    //save data from tables to file

    boolean saveSkiDataToFile();
    boolean saveCustomerDataToFile();
    boolean saveReservationDataToFile();

    //drop database
    boolean dropDatabase();

    //delete data from tables
    boolean deleteCustomer(int id);
    boolean deleteSki(int id);
    boolean deleteReservation(int id);

    //update data in tables
    boolean updateCustomer(Customer customer);
    boolean updateSki(SkiEqu updatedSkiEqu);
    boolean updateReservation(Reservation updatedReservation);
}
