package org.application.bwp.rentalskiequcrud.jsonFile.writerToFile;

import javafx.scene.control.Alert;
import org.application.bwp.rentalskiequcrud.database.DatabaseErrorsTypes;
import org.application.bwp.rentalskiequcrud.entity.Customer;
import org.application.bwp.rentalskiequcrud.entity.Reservation;
import org.application.bwp.rentalskiequcrud.entity.SkiEqu;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JsonWriteToFile {

    private static final String FILE_PATH_SKI = "src/main/resources/org/application/bwp/rentalskiequcrud/sqlSaves/ski.json";
    private static final String FILE_PATH_CUSTOMER = "src/main/resources/org/application/bwp/rentalskiequcrud/sqlSaves/customers.json";
    private static final String FILE_PATH_RENTAL = "src/main/resources/org/application/bwp/rentalskiequcrud/sqlSaves/reservations.json";

    private static JsonWriteToFile instance;

    private JsonWriteToFile() {
    }

    public static JsonWriteToFile getInstance() {
        if (instance == null) {
            instance = new JsonWriteToFile();
        }
        return instance;
    }

    public void errorHandler() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Błąd");
        alert.setHeaderText("Błąd odczytu z pliku" + DatabaseErrorsTypes.SAVE_DATA_TO_FILE_ERROR);
        alert.setContentText("Sprawdź pliki aplikacji ");
        alert.showAndWait();
    }

    private boolean writeToFile(String fileName, String data) {
        try (FileWriter fileWriter = new FileWriter(fileName, false)) {
            fileWriter.write(data);
            return true;
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setHeaderText("Błąd zapisu do pliku");
            alert.setContentText("Sprawdź pliki aplikacji " + e.getMessage());
            alert.showAndWait();
            return false;
        }
    }

    public boolean prepareDataAndWriteToFile(List<?> list) {
        StringBuilder data = new StringBuilder("[\n");
        for (int i = 0; i < list.size(); i++) {
            Object e = list.get(i);
            if (e instanceof SkiEqu) {
                data.append(((SkiEqu) e).jsonBuilder());
            } else if (e instanceof Customer) {
                data.append(((Customer) e).jsonBuilder());
            } else if (e instanceof Reservation) {
                data.append(((Reservation) e).jsonBuilder());
            }
            // Append comma if not the last element
            if (i < list.size() - 1) {
                data.append(",\n");
            }
        }
        data.append("\n]");

        String fileName;
        if(list.get(0) instanceof SkiEqu)
            fileName = FILE_PATH_SKI;
        else if(list.get(0) instanceof Customer)
            fileName = FILE_PATH_CUSTOMER;
        else
            fileName = FILE_PATH_RENTAL;

        return writeToFile(fileName, data.toString());
    }

}
