package org.application.bwp.rentalskiequcrud.entity.buildres;

import org.application.bwp.rentalskiequcrud.entity.SkiEqu;
import org.application.bwp.rentalskiequcrud.entity.enums.Status;

public interface SkiEquBuilder {
    SkiEquBuilder setId(int id);
    SkiEquBuilder setRodzaj(String rodzaj);
    SkiEquBuilder setDlugosc(int dlugosc);
    SkiEquBuilder setStatus(Status status);
    SkiEqu build();
}
