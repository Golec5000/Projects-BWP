package org.application.bwp.rentalskiequcrud.entity;

import org.application.bwp.rentalskiequcrud.entity.buildres.SkiBuilder;
import org.application.bwp.rentalskiequcrud.entity.enums.Status;
import org.application.bwp.rentalskiequcrud.jsonFile.adapters.JsonFileFormatInterface;

public class Ski implements SkiBuilder, JsonFileFormatInterface {
    private int id;
    private String rodzaj;
    private int dlugosc;
    private Status status;

    public int getId() {
        return id;
    }

    @Override
    public SkiBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public String getRodzaj() {
        return rodzaj;
    }

    @Override
    public SkiBuilder setRodzaj(String rodzaj) {
        this.rodzaj = rodzaj;
        return this;
    }

    public int getDlugosc() {
        return dlugosc;
    }

    @Override
    public SkiBuilder setDlugosc(int dlugosc) {
        this.dlugosc = dlugosc;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public SkiBuilder setStatus(Status status) {
        this.status = status;
        return this;
    }

    public Ski build() {
        return this;
    }

    @Override
    public String toString() {
        return "Ski{" +
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
