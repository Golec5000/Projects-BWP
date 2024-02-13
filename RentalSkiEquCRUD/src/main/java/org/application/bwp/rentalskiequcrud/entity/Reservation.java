package org.application.bwp.rentalskiequcrud.entity;

import org.application.bwp.rentalskiequcrud.entity.enums.Payment;
import org.application.bwp.rentalskiequcrud.entity.enums.Status;

import java.time.LocalDate;

public class Reservation {
    private int id;
    private int id_narty;
    private int id_klient;
    private LocalDate data_poczatkowa;
    private LocalDate data_koncowa;

    private Status status;

    private Payment platnosc;

    public Reservation(int id, int id_narty, int id_klient, LocalDate data_poczatkowa, LocalDate data_koncowa, Status status, Payment platnosc) {
        this.id = id;
        this.id_narty = id_narty;
        this.id_klient = id_klient;
        this.data_poczatkowa = data_poczatkowa;
        this.data_koncowa = data_koncowa;
        this.status = status;
        this.platnosc = platnosc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_narty() {
        return id_narty;
    }

    public void setId_narty(int id_narty) {
        this.id_narty = id_narty;
    }

    public int getId_klient() {
        return id_klient;
    }

    public void setId_klient(int id_klient) {
        this.id_klient = id_klient;
    }

    public LocalDate getData_poczatkowa() {
        return data_poczatkowa;
    }

    public void setData_poczatkowa(LocalDate data_poczatkowa) {
        this.data_poczatkowa = data_poczatkowa;
    }

    public LocalDate getData_koncowa() {
        return data_koncowa;
    }

    public void setData_koncowa(LocalDate data_koncowa) {
        this.data_koncowa = data_koncowa;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Payment getPlatnosc() {
        return platnosc;
    }

    public void setPlatnosc(Payment platnosc) {
        this.platnosc = platnosc;
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
