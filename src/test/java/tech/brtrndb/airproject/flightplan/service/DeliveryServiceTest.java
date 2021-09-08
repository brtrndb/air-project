package tech.brtrndb.airproject.flightplan.service;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import tech.brtrndb.airproject.flightplan.FlightPlanIntegrationTest;
import tech.brtrndb.airproject.flightplan.domain.Customer;
import tech.brtrndb.airproject.flightplan.domain.Delivery;
import tech.brtrndb.airproject.flightplan.domain.Drone;
import tech.brtrndb.airproject.flightplan.domain.Order;
import tech.brtrndb.airproject.flightplan.domain.Position;
import tech.brtrndb.airproject.flightplan.domain.Product;
import tech.brtrndb.airproject.flightplan.domain.ProductQuantity;
import tech.brtrndb.airproject.flightplan.domain.Store;
import tech.brtrndb.airproject.flightplan.fixture.CustomerFixtures;
import tech.brtrndb.airproject.flightplan.fixture.DroneFixtures;
import tech.brtrndb.airproject.flightplan.fixture.OrderFixtures;
import tech.brtrndb.airproject.flightplan.fixture.ProductFixtures;
import tech.brtrndb.airproject.flightplan.fixture.StoreFixtures;
import tech.brtrndb.airproject.flightplan.util.GeoUtils;

@Slf4j
@FlightPlanIntegrationTest
public class DeliveryServiceTest extends BaseServiceTest {

    @Autowired
    private DeliveryService service;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private DroneService droneService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private StoreService storeService;

    @Test
    public void should_generate_deliveries_list_empty() {
        // Given:
        // When:
        List<Delivery> deliveries = this.service.generateDeliveries();

        // Then:
        Assertions.assertThat(deliveries).isEmpty();
    }

    @Test
    public void should_generate_delivery_plan() {
        // Given:
        // Create a customer.
        Position customerPosition = Position.of(0d, 0d);
        Customer customer = this.customerService.create(CustomerFixtures.randomCustomerId(), customerPosition);
        // Create a product.
        Product product = this.productService.create(ProductFixtures.randomProductId(), ProductFixtures.randomName());
        // Create a drone.
        Position dronePosition = Position.of(5d, 0d);
        Drone drone = this.droneService.deploy(DroneFixtures.randomDroneId(), dronePosition);
        // Create a store.
        Position storePosition = Position.of(-5d, 0d);
        int initialProductStockQuantity = 10;
        Store store = this.storeService.create(StoreFixtures.randomStoreId(), storePosition);
        store = this.storeService.setStocks(store.getStoreId(), product, initialProductStockQuantity);
        // Create an order.
        int nbItem = 5;
        Order order = this.orderService.create(OrderFixtures.randomOrderId(), customer.getCustomerId(), new ProductQuantity(product, nbItem));
        List<Position> expectedDronePath = List.of(
                dronePosition,
                storePosition,
                customerPosition,
                storePosition,
                customerPosition,
                storePosition,
                customerPosition,
                storePosition,
                customerPosition,
                storePosition,
                customerPosition
        );

        // When:
        List<Delivery> deliveries = this.service.generateDeliveries();
        Store storeAfterDeliveries = this.storeService.get(store.getStoreId());
        Drone droneAfterDeliveries = this.droneService.get(drone.getDroneId());

        log.error("{}", storeAfterDeliveries);
        log.error("{}", droneAfterDeliveries);

        // Then:
        Assertions.assertThat(deliveries).hasSize(nbItem);
        Assertions.assertThat(storeAfterDeliveries.getProductStocks().getProductQuantity(product.getProductId())).isEqualTo(initialProductStockQuantity - nbItem);
        Assertions.assertThat(droneAfterDeliveries.getFuel()).isEqualTo(drone.getFuel() - GeoUtils.distance(expectedDronePath));
    }

}
