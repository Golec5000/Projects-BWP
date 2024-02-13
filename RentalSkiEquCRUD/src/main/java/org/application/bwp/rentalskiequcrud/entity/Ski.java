package org.application.bwp.rentalskiequcrud.entity;

public class Ski {
    private int id;
    private String rodzaj;
    private int dlugosc;

    public Ski(int id, String rodzaj, int dlugosc) {
        this.id = id;
        this.rodzaj = rodzaj;
        this.dlugosc = dlugosc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRodzaj() {
        return rodzaj;
    }

    public void setRodzaj(String rodzaj) {
        this.rodzaj = rodzaj;
    }

    public int getDlugosc() {
        return dlugosc;
    }

    public void setDlugosc(int dlugosc) {
        this.dlugosc = dlugosc;
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
