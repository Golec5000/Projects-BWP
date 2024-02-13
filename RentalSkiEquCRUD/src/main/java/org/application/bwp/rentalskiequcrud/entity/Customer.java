package org.application.bwp.rentalskiequcrud.entity;

import org.application.bwp.rentalskiequcrud.entity.buildres.CustomerBuilder;
import org.application.bwp.rentalskiequcrud.jsonFile.adapters.JsonFileFormatInterface;

public class Customer implements CustomerBuilder , JsonFileFormatInterface {
    private int id;

    private String nazwa;

    public int getId() {
        return id;
    }

    public CustomerBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public String getNazwa() {
        return nazwa;
    }

    public CustomerBuilder setNazwa(String nazwa) {
        this.nazwa = nazwa;
        return this;
    }

    @Override
    public Customer build() {
        return this;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + nazwa + '\'' +
                '}';
    }

    @Override
    public String jsonBuilder() {
        return "{ \"id\": " + id + ", \"nazwa\": \"" + nazwa + "\" }";
    }
}
