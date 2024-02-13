package org.application.bwp.rentalskiequcrud.entity.buildres;

import org.application.bwp.rentalskiequcrud.entity.Ski;

public interface SkiBuilder {
    SkiBuilder setId(int id);
    SkiBuilder setRodzaj(String rodzaj);
    SkiBuilder setDlugosc(int dlugosc);
    Ski build();
}
