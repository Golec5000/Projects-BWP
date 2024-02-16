package org.application.bwp.rentalskiequcrud.gui.menager;

import javafx.scene.control.ComboBox;
import org.application.bwp.rentalskiequcrud.entity.enums.Payment;
import org.application.bwp.rentalskiequcrud.entity.enums.Status;

public class UIOperations {

    public void prepareSkiComboBox(ComboBox<String> comboBox) {
        comboBox.getItems().add("Select status");
        comboBox.getItems().add(Status.DOSTEPNE.toString());
        comboBox.getItems().add(Status.NIEDOSTEPNE.toString());

    }

    public void preparePaymentComboBox(ComboBox<String> comboBox) {
        comboBox.getItems().add("Select status");
        comboBox.getItems().add(Payment.OPLACONA.toString());
        comboBox.getItems().add(Payment.NIEOPLACONA.toString());
    }

    public void prepareReservationStatusComboBox(ComboBox<String> comboBox) {
        comboBox.getItems().add("Select status");
        comboBox.getItems().add(Status.ZAKONCZONA.toString());
        comboBox.getItems().add(Status.WYCOFANA.toString());
        comboBox.getItems().add(Status.ROZPOCZETA.toString());
        comboBox.getItems().add(Status.ZALOZONA.toString());
    }

}
