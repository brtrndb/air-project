package tech.brtrndb.airproject.flightplan.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tech.brtrndb.airproject.flightplan.domain.Customer;
import tech.brtrndb.airproject.flightplan.domain.Position;
import tech.brtrndb.airproject.flightplan.persistence.entity.CustomerEntity;
import tech.brtrndb.airproject.flightplan.persistence.repository.CustomerRepository;
import tech.brtrndb.airproject.flightplan.service.mapper.CustomerMapper;
import tech.brtrndb.airproject.flightplan.util.ValidationUtils;

@Slf4j
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private static final String CUSTOMER_ID_IS_NULL = "Customer id is null";
    private static final String CUSTOMER_ID_IS_EMPTY = "Customer id is blank or empty";
    private static final String CUSTOMER_POSITION_IS_NULL = "Customer position is null";

    private final CustomerRepository repository;

    public CustomerServiceImpl(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Customer> getAll() {
        List<CustomerEntity> entities = this.repository.findAll();
        List<Customer> models = CustomerMapper.toModels(entities);

        log.trace("Got {} customer(s).", models.size());

        return models;
    }

    @Override
    public Optional<Customer> find(String customerId) {
        ValidationUtils.requireNonNullBlankEmptyString(customerId, CUSTOMER_ID_IS_NULL, CUSTOMER_ID_IS_EMPTY);

        Optional<CustomerEntity> optional = this.repository.findByCustomerId(customerId);

        Optional<Customer> optionalModel = optional.map(CustomerMapper::toModel);

        optionalModel.ifPresentOrElse(
                d -> log.trace("Customer [{}] found.", d.getCustomerId()),
                () -> log.trace("Customer [{}] not found.", customerId)
        );

        return optionalModel;
    }

    @Override
    public Customer create(String customerId, Position position) {
        ValidationUtils.requireNonNullBlankEmptyString(customerId, CUSTOMER_ID_IS_NULL, CUSTOMER_ID_IS_EMPTY);
        Objects.requireNonNull(position, CUSTOMER_POSITION_IS_NULL);

        Customer model = new Customer(customerId, position);
        CustomerEntity entity = CustomerMapper.toNewEntity(model);
        entity = this.repository.saveAndFlush(entity);
        model = CustomerMapper.toModel(entity);

        log.trace("Customer [{}] created at ({}, {}).", model.getCustomerId(), model.getPosX(), model.getPosY());

        return model;
    }

}
