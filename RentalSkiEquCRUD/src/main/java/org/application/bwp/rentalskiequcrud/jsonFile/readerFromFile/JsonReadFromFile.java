package org.application.bwp.rentalskiequcrud.jsonFile.readerFromFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.application.bwp.rentalskiequcrud.entity.Customer;
import org.application.bwp.rentalskiequcrud.entity.Reservation;
import org.application.bwp.rentalskiequcrud.entity.Ski;
import org.application.bwp.rentalskiequcrud.jsonFile.adapters.LocalDateAdapter;

import java.io.FileReader;
import java.io.Reader;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class JsonReadFromFile {

    private static final String FILE_PATH_SKI = "src/main/resources/org/application/bwp/rentalskiequcrud/sqlSaves/ski.json";
    private static final String FILE_PATH_CUSTOMER = "src/main/resources/org/application/bwp/rentalskiequcrud/sqlSaves/customers.json";
    private static final String FILE_PATH_RENTAL = "src/main/resources/org/application/bwp/rentalskiequcrud/sqlSaves/reservations.json";

    private static JsonReadFromFile instance;

    public static JsonReadFromFile getInstance() {
        if (instance == null) return new JsonReadFromFile();
        return instance;
    }

    public List<Ski> readSki() {

        try (Reader reader = new FileReader(FILE_PATH_SKI)) {
            return new Gson().fromJson(reader, new TypeToken<List<Ski>>() {
            }.getType());
        } catch (Exception e) {
            System.err.println("Failed to read ski data from " + FILE_PATH_SKI);
            return Collections.emptyList();
        }

    }

    public List<Customer> readCustomer() {

        try (Reader reader = new FileReader(FILE_PATH_CUSTOMER)) {
            return new Gson().fromJson(reader, new TypeToken<List<Customer>>() {
            }.getType());
        } catch (Exception e) {
            System.err.println("Failed to read customer data from " + FILE_PATH_CUSTOMER);
            return Collections.emptyList();
        }

    }

    public List<Reservation> readRental() {

        try (Reader reader = new FileReader(FILE_PATH_RENTAL)) {

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .create();
            return gson.fromJson(reader, new TypeToken<List<Reservation>>() {
            }.getType());
        } catch (Exception e) {
            System.err.println("Failed to read rental data from " + FILE_PATH_RENTAL);
            return Collections.emptyList();
        }

    }
}