package tech.brtrndb.airproject.flightplan.persistence.repository;

import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import tech.brtrndb.airproject.flightplan.FlightPlanIntegrationTest;
import tech.brtrndb.airproject.flightplan.fixture.CustomerFixtures;
import tech.brtrndb.airproject.flightplan.persistence.entity.CustomerEntity;

@Slf4j
@FlightPlanIntegrationTest
public class CustomerRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    private CustomerEntity save() {
        CustomerEntity entity = CustomerFixtures.randomCustomerEntity();
        entity = this.repository.saveAndFlush(entity);
        return entity;
    }

    @Test
    public void save_customer() {
        // Given:
        CustomerEntity entity = CustomerFixtures.randomCustomerEntity();

        // When:
        CustomerEntity created = this.repository.saveAndFlush(entity);
        CustomerEntity got = this.repository.getById(created.getId());

        // Then:
        Assertions.assertThat(created).isNotNull();
        Assertions.assertThat(created.getId()).isNotNull();
        Assertions.assertThat(created.getCreationInfo())
                .isNotNull()
                .satisfies(creationInfo -> {
                    Assertions.assertThat(creationInfo.getCreatedAt()).isNotNull();
                    Assertions.assertThat(creationInfo.getUpdatedAt()).isEqualTo(creationInfo.getUpdatedAt());
                });
        Assertions.assertThat(created.getPosition())
                .isNotNull()
                .satisfies(position -> {
                    Assertions.assertThat(position.getPosX()).isNotNull();
                    Assertions.assertThat(position.getPosY()).isNotNull();
                });
        Assertions.assertThat(got.getCustomerId()).isEqualTo(entity.getCustomerId());
    }

    @Test
    public void find_customer_by_customerId() {
        // Given:
        CustomerEntity entity = this.save();
        String customerId = entity.getCustomerId();

        // When:
        Optional<CustomerEntity> optional = this.repository.findByCustomerId(customerId);

        // Then:
        Assertions.assertThat(optional)
                .isNotEmpty()
                .contains(entity);
    }

    @Test
    public void dont_find_customer_by_customerId_given_customerId_non_existent() {
        // Given:
        CustomerEntity entity = this.save();
        String nonExistentCustomerId = CustomerFixtures.randomCustomerId();

        // When:
        Optional<CustomerEntity> optional = this.repository.findByCustomerId(nonExistentCustomerId);

        // Then:
        Assertions.assertThat(optional)
                .isEmpty();
    }

}
