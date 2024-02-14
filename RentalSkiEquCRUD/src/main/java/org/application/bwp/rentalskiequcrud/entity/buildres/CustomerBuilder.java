package org.application.bwp.rentalskiequcrud.entity.buildres;

import org.application.bwp.rentalskiequcrud.entity.Customer;

public interface CustomerBuilder {
    CustomerBuilder setId(int id);
    CustomerBuilder setNazwa(String nazwa);
    Customer build();
    Customer setPassword(String password);
}
