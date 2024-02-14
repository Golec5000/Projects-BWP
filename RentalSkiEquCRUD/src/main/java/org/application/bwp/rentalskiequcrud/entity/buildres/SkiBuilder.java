package org.application.bwp.rentalskiequcrud.entity.buildres;

import org.application.bwp.rentalskiequcrud.entity.Ski;
import org.application.bwp.rentalskiequcrud.entity.enums.Status;

public interface SkiBuilder {
    SkiBuilder setId(int id);
    SkiBuilder setRodzaj(String rodzaj);
    SkiBuilder setDlugosc(int dlugosc);
    SkiBuilder setStatus(Status status);
    Ski build();
}
