package org.application.bwp.airqualityapp.entity.airCondition.molecules;

import java.time.LocalDateTime;

public class MoleculeValue {

    private LocalDateTime date;
    private double value;

    public MoleculeValue(LocalDateTime date, double value) {
        this.date = date;
        this.value = value;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "MoleculeValue{" +
                "date=" + date +
                ", value=" + value +
                '}';
    }
}
