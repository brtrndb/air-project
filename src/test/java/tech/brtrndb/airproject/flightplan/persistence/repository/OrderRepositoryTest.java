package tech.brtrndb.airproject.flightplan.persistence.repository;

import java.util.Optional;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import tech.brtrndb.airproject.flightplan.FlightPlanIntegrationTest;
import tech.brtrndb.airproject.flightplan.fixture.CustomerFixtures;
import tech.brtrndb.airproject.flightplan.fixture.OrderFixtures;
import tech.brtrndb.airproject.flightplan.fixture.ProductFixtures;
import tech.brtrndb.airproject.flightplan.persistence.entity.CustomerEntity;
import tech.brtrndb.airproject.flightplan.persistence.entity.OrderEntity;
import tech.brtrndb.airproject.flightplan.persistence.entity.ProductEntity;

@Slf4j
@FlightPlanIntegrationTest
public class OrderRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    private OrderEntity save() {
        CustomerEntity customer = this.saveCustomer();
        ProductEntity product0 = this.saveProduct();
        ProductEntity product1 = this.saveProduct();
        ProductEntity product2 = this.saveProduct();
        Set<ProductEntity> products = Set.of(product0, product1, product2);

        OrderEntity entity = OrderFixtures.randomOrderEntity(customer.getCustomerId(), products);
        entity = this.repository.saveAndFlush(entity);
        return entity;
    }

    private CustomerEntity saveCustomer() {
        CustomerEntity entity = CustomerFixtures.randomCustomerEntity();
        entity = this.customerRepository.saveAndFlush(entity);
        return entity;
    }

    private ProductEntity saveProduct() {
        ProductEntity entity = ProductFixtures.randomProductEntity();
        entity = this.productRepository.saveAndFlush(entity);
        return entity;
    }

    @Test
    public void save_order_with_items() {
        // Given:
        int nbItems = 3;
        CustomerEntity customer = this.saveCustomer();
        ProductEntity product0 = this.saveProduct();
        ProductEntity product1 = this.saveProduct();
        ProductEntity product2 = this.saveProduct();
        OrderEntity entity = OrderFixtures.randomOrderEntity(customer.getCustomerId(), Set.of(product0, product1, product2));

        // When:
        OrderEntity created = this.repository.saveAndFlush(entity);
        OrderEntity got = this.repository.getById(entity.getId());

        // Then:
        Assertions.assertThat(created).isNotNull();
        Assertions.assertThat(created.getId()).isNotNull();
        Assertions.assertThat(created.getCreationInfo())
                .isNotNull()
                .satisfies(creationInfo -> {
                    Assertions.assertThat(creationInfo.getCreatedAt()).isNotNull();
                    Assertions.assertThat(creationInfo.getUpdatedAt()).isEqualTo(creationInfo.getUpdatedAt());
                });
        Assertions.assertThat(got.getOrderItems())
                .isNotNull()
                .hasSize(nbItems);
    }

    @Test
    public void find_order_by_orderId() {
        // Given:
        OrderEntity entity = this.save();
        String orderId = entity.getOrderId();

        // When:
        Optional<OrderEntity> optional = this.repository.findByOrderId(orderId);

        // Then:
        Assertions.assertThat(optional)
                .isNotEmpty()
                .contains(entity);
    }

    @Test
    public void dont_find_order_by_orderId_given_orderId_non_existent() {
        // Given:
        OrderEntity entity = this.save();
        String nonExistentOrderId = OrderFixtures.randomOrderId();

        // When:
        Optional<OrderEntity> optional = this.repository.findByOrderId(nonExistentOrderId);

        // Then:
        Assertions.assertThat(optional)
                .isEmpty();
    }

}
