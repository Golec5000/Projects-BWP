package org.application.bwp.rentalskiequcrud.entity;

import org.application.bwp.rentalskiequcrud.entity.buildres.ReservationBuilder;
import org.application.bwp.rentalskiequcrud.entity.enums.Payment;
import org.application.bwp.rentalskiequcrud.entity.enums.Status;

import java.time.LocalDate;

public class Reservation implements ReservationBuilder {
    private int id;
    private int id_narty;
    private int id_klient;
    private LocalDate data_poczatkowa;
    private LocalDate data_koncowa;

    private Status status;

    private Payment platnosc;

    public int getId() {
        return id;
    }

    @Override
    public ReservationBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public int getId_narty() {
        return id_narty;
    }

    @Override
    public ReservationBuilder setId_narty(int id_narty) {
        this.id_narty = id_narty;
        return this;
    }

    public int getId_klient() {
        return id_klient;
    }

    @Override
    public ReservationBuilder setId_klient(int id_klient) {
        this.id_klient = id_klient;
        return this;
    }

    public LocalDate getData_poczatkowa() {
        return data_poczatkowa;
    }

    @Override
    public ReservationBuilder setData_poczatkowa(LocalDate data_poczatkowa) {
        this.data_poczatkowa = data_poczatkowa;
        return this;
    }

    public LocalDate getData_koncowa() {
        return data_koncowa;
    }

    @Override
    public ReservationBuilder setData_koncowa(LocalDate data_koncowa) {
        this.data_koncowa = data_koncowa;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public ReservationBuilder setStatus(Status status) {
        this.status = status;
        return this;
    }

    public Payment getPlatnosc() {
        return platnosc;
    }

    @Override
    public ReservationBuilder setPlatnosc(Payment platnosc) {
        this.platnosc = platnosc;
        return this;
    }

    @Override
    public Reservation build() {
        return this;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", id_narty=" + id_narty +
                ", id_klient=" + id_klient +
                ", data_poczatkowa=" + data_poczatkowa +
                ", data_koncowa=" + data_koncowa +
                ", status=" + status +
                ", platnosc=" + platnosc +
                '}';
    }
}
