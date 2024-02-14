package org.application.bwp.rentalskiequcrud.entity;

import org.application.bwp.rentalskiequcrud.entity.buildres.CustomerBuilder;
import org.application.bwp.rentalskiequcrud.jsonFile.adapters.JsonFileFormatInterface;

public class Customer implements CustomerBuilder , JsonFileFormatInterface {
    private int id;

    private String nazwa;
    private String password;

    public int getId() {
        return id;
    }

    @Override
    public CustomerBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public String getNazwa() {
        return nazwa;
    }

    @Override
    public CustomerBuilder setNazwa(String nazwa) {
        this.nazwa = nazwa;
        return this;
    }

    public String getPassword() {
        return password;
    }
    @Override
    public Customer setPassword(String password) {
        this.password = password;
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
                ", nazwa='" + nazwa + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public String jsonBuilder() {
        return "{ \"id\": " + id + ", \"nazwa\": \"" + nazwa + "\", \"password\": \"" + password + "\"}";
    }
}
