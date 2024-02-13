package org.application.bwp.rentalskiequcrud.database;

import org.application.bwp.rentalskiequcrud.entity.Customer;
import org.application.bwp.rentalskiequcrud.entity.Reservation;
import org.application.bwp.rentalskiequcrud.entity.Ski;

import java.util.List;

public interface DatabaseService {


    //create database and tables methods
    boolean createDatabase();
    boolean createCustomerTable();
    boolean createSkiTable();
    boolean createReservationTable();

    //insert data into tables
    boolean insertSkiData();
    boolean insertCustomerData();
    boolean insertReservationData();

    //select data from tables
    List<Ski> selectAllSki();

    List<Reservation> selectAllReservation();

    List<Customer> selectAllCustomer();


    //save data from tables to file

    //drop database
    boolean dropDatabase();
}
