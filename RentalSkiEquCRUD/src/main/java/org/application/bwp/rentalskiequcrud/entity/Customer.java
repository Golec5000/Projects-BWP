package org.application.bwp.rentalskiequcrud.entity;

import org.application.bwp.rentalskiequcrud.entity.buildres.CustomerBuilder;

public class Customer implements CustomerBuilder {
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
}
