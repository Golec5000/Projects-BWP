package org.application.bwp.rentalskiequcrud.entity;

import org.application.bwp.rentalskiequcrud.entity.buildres.SkiEquBuilder;
import org.application.bwp.rentalskiequcrud.entity.enums.Status;
import org.application.bwp.rentalskiequcrud.jsonFile.adapters.JsonFileFormatInterface;

public class SkiEqu implements SkiEquBuilder, JsonFileFormatInterface {
    private int id;
    private String rodzaj;
    private int dlugosc;
    private Status status;

    public int getId() {
        return id;
    }

    @Override
    public SkiEquBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public String getRodzaj() {
        return rodzaj;
    }

    @Override
    public SkiEquBuilder setRodzaj(String rodzaj) {
        this.rodzaj = rodzaj;
        return this;
    }

    public int getDlugosc() {
        return dlugosc;
    }

    @Override
    public SkiEquBuilder setDlugosc(int dlugosc) {
        this.dlugosc = dlugosc;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public SkiEquBuilder setStatus(Status status) {
        this.status = status;
        return this;
    }

    public SkiEqu build() {
        return this;
    }

    @Override
    public String toString() {
        return "SkiEqu{" +
                "id=" + id +
                ", rodzaj='" + rodzaj + '\'' +
                ", dlugosc=" + dlugosc +
                '}';
    }

    @Override
    public String jsonBuilder() {
        return "{ \"id\": " + id + ", \"rodzaj\": \"" + rodzaj + "\", \"dlugosc\": " + dlugosc + ", \"status\": \"" + status + "\" }";
    }
}
