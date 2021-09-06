package tech.brtrndb.airproject.flightplan.service.mapper;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import tech.brtrndb.airproject.flightplan.domain.Customer;
import tech.brtrndb.airproject.flightplan.fixture.CustomerFixtures;
import tech.brtrndb.airproject.flightplan.persistence.entity.CustomerEntity;

@Slf4j
public class CustomerMapperTest extends BaseMapperTest<Customer, CustomerEntity> {

    private static final Map.Entry<CustomerEntity, Customer> matching = CustomerFixtures.randomMatching();

    public CustomerMapperTest() {
        super(
                CustomerMapper::toEntity,
                CustomerMapper::toModel,
                matching::getValue,
                matching::getKey
        );
    }

}
