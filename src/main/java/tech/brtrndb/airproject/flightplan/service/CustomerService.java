package tech.brtrndb.airproject.flightplan.service;

import java.util.List;
import java.util.Optional;

import tech.brtrndb.airproject.flightplan.domain.Customer;
import tech.brtrndb.airproject.flightplan.domain.Position;
import tech.brtrndb.airproject.flightplan.error.ModelNotFoundException;

public interface CustomerService {

    public List<Customer> getAll();

    public Optional<Customer> find(String customerId);

    public default Customer get(String customerId) throws ModelNotFoundException {
        return this.find(customerId).orElseThrow(() -> new ModelNotFoundException(Customer.class, customerId));
    }

    public Customer create(String customerId, Position position);

}
