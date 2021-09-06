package tech.brtrndb.airproject.flightplan.persistence.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import tech.brtrndb.airproject.flightplan.persistence.entity.CustomerEntity;

@Repository
public interface CustomerRepository extends ModelRepository<CustomerEntity> {

    public Optional<CustomerEntity> findByCustomerId(String customerId);

}
