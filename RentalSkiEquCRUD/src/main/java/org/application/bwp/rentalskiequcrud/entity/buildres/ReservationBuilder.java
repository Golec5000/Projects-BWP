package org.application.bwp.rentalskiequcrud.entity.buildres;

import org.application.bwp.rentalskiequcrud.entity.Reservation;
import org.application.bwp.rentalskiequcrud.entity.enums.Payment;
import org.application.bwp.rentalskiequcrud.entity.enums.Status;

import java.time.LocalDate;

public interface ReservationBuilder {

    ReservationBuilder setId(int id);
    ReservationBuilder setId_narty(int id_narty);
    ReservationBuilder setId_klient(int id_klient);
    ReservationBuilder setData_poczatkowa(LocalDate data_poczatkowa);
    ReservationBuilder setData_koncowa(LocalDate data_koncowa);
    ReservationBuilder setStatus(Status status);
    ReservationBuilder setPlatnosc(Payment platnosc);
     Reservation build();
}
