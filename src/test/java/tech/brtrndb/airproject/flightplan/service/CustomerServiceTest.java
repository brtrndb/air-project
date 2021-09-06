package tech.brtrndb.airproject.flightplan.service;

import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import tech.brtrndb.airproject.flightplan.FlightPlanIntegrationTest;
import tech.brtrndb.airproject.flightplan.domain.Customer;
import tech.brtrndb.airproject.flightplan.domain.Position;
import tech.brtrndb.airproject.flightplan.error.ModelNotFoundException;
import tech.brtrndb.airproject.flightplan.fixture.CustomerFixtures;
import tech.brtrndb.airproject.flightplan.fixture.PositionFixtures;

@Slf4j
@FlightPlanIntegrationTest
public class CustomerServiceTest extends BaseServiceTest {

    @Autowired
    private CustomerService service;

    private Customer create() {
        String customerId = CustomerFixtures.randomCustomerId();
        Position customerPosition = PositionFixtures.randomPosition();

        return this.service.create(customerId, customerPosition);
    }

    @Test
    public void should_get_all_customers() {
        // Given:
        Customer customer0 = this.create();
        Customer customer1 = this.create();
        Customer customer2 = this.create();
        List<Customer> all = List.of(customer0, customer1, customer2);

        // When:
        List<Customer> got = this.service.getAll();

        // Then:
        Assertions.assertThat(got)
                .hasSameSizeAs(all)
                .containsExactlyInAnyOrderElementsOf(all);
    }

    @Test
    public void should_find_customer_by_id() {
        // Given:
        Customer customer = this.create();

        // When:
        Optional<Customer> optional = this.service.find(customer.getCustomerId());

        // Then:
        Assertions.assertThat(optional)
                .isNotEmpty()
                .contains(customer);
    }

    @Test
    public void should_not_find_customer_by_id() {
        // Given:
        String nonExistentId = CustomerFixtures.randomCustomerId();

        // When:
        Optional<Customer> optional = this.service.find(nonExistentId);

        // Then:
        Assertions.assertThat(optional).isEmpty();
    }

    @Test
    public void should_get_customer_by_id() {
        // Given:
        Customer customer = this.create();

        // When:
        Customer got = this.service.get(customer.getCustomerId());

        // Then:
        Assertions.assertThat(got).isEqualTo(customer);
    }

    @Test
    public void should_not_get_customer_by_id() {
        // Given:
        String nonExistentId = CustomerFixtures.randomCustomerId();

        // When:
        // Then:
        Assertions.assertThatExceptionOfType(ModelNotFoundException.class)
                .isThrownBy(() -> this.service.get(nonExistentId));
    }

    @Test
    public void should_create_customer_at_position() {
        // Given:
        String customerId = CustomerFixtures.randomCustomerId();
        Position position = PositionFixtures.randomPosition();

        // When:
        Customer customer = this.service.create(customerId, position);

        // Then:
        Assertions.assertThat(customer.getCustomerId()).isEqualTo(customerId);
        Assertions.assertThat(customer.getPosition()).isEqualTo(position);
    }

}
