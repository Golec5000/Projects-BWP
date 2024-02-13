package org.application.bwp.rentalskiequcrud.entity;

public class Customer {
    private int id;

    private String nazwa;

    public Customer(int id, String nazwa) {
        this.id = id;
        this.nazwa = nazwa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + nazwa + '\'' +
                '}';
    }
}
