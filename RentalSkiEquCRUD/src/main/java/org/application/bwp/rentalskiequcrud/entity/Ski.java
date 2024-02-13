package org.application.bwp.rentalskiequcrud.entity;

import org.application.bwp.rentalskiequcrud.entity.buildres.SkiBuilder;

public class Ski implements SkiBuilder {
    private int id;
    private String rodzaj;
    private int dlugosc;

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
}
