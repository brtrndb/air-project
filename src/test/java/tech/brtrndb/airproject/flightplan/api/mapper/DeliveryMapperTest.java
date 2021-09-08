package tech.brtrndb.airproject.flightplan.api.mapper;

import java.util.Set;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import tech.brtrndb.airproject.flightplan.BaseTest;
import tech.brtrndb.airproject.flightplan.api.dto.DeliveryDTO;
import tech.brtrndb.airproject.flightplan.domain.Customer;
import tech.brtrndb.airproject.flightplan.domain.Delivery;
import tech.brtrndb.airproject.flightplan.domain.Drone;
import tech.brtrndb.airproject.flightplan.domain.Order;
import tech.brtrndb.airproject.flightplan.domain.Product;
import tech.brtrndb.airproject.flightplan.domain.Store;
import tech.brtrndb.airproject.flightplan.fixture.CustomerFixtures;
import tech.brtrndb.airproject.flightplan.fixture.DroneFixtures;
import tech.brtrndb.airproject.flightplan.fixture.OrderFixtures;
import tech.brtrndb.airproject.flightplan.fixture.ProductFixtures;
import tech.brtrndb.airproject.flightplan.fixture.StoreFixtures;

@Slf4j
public class DeliveryMapperTest extends BaseTest {

    @Test
    public void map_model_to_dto() {
        // Given:
        Drone drone = DroneFixtures.randomDrone();
        Store store = StoreFixtures.randomStore();
        Customer customer = CustomerFixtures.randomCustomer();
        Product product = ProductFixtures.randomProduct();
        Order order = OrderFixtures.randomOrder(customer.getCustomerId(), Set.of(product.getProductId()));
        Delivery model = new Delivery(order, drone, store, product, customer);

        // When:
        DeliveryDTO dto = DeliveryMapper.toDTO(model);

        // Then:
        Assertions.assertThat(dto.orderId()).isEqualTo(model.order().getOrderId());
        Assertions.assertThat(dto.droneId()).isEqualTo(model.drone().getDroneId());
        Assertions.assertThat(dto.storeId()).isEqualTo(model.store().getStoreId());
        Assertions.assertThat(dto.productId()).isEqualTo(model.product().getProductId());
        Assertions.assertThat(dto.customerId()).isEqualTo(model.customer().getCustomerId());
    }

}
