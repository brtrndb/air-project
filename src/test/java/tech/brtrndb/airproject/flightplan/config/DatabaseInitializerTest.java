package tech.brtrndb.airproject.flightplan.config;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import tech.brtrndb.airproject.flightplan.BaseTest;
import tech.brtrndb.airproject.flightplan.FlightPlanIntegrationTest;
import tech.brtrndb.airproject.flightplan.domain.Customer;
import tech.brtrndb.airproject.flightplan.domain.Drone;
import tech.brtrndb.airproject.flightplan.domain.Order;
import tech.brtrndb.airproject.flightplan.domain.Product;
import tech.brtrndb.airproject.flightplan.domain.Store;
import tech.brtrndb.airproject.flightplan.service.CustomerService;
import tech.brtrndb.airproject.flightplan.service.DroneService;
import tech.brtrndb.airproject.flightplan.service.OrderService;
import tech.brtrndb.airproject.flightplan.service.ProductService;
import tech.brtrndb.airproject.flightplan.service.StoreService;

@Slf4j
@FlightPlanIntegrationTest
public class DatabaseInitializerTest extends BaseTest {

    @Autowired
    private DatabaseInitializer initializer;

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
    public void should_load_csv_into_database() {
        // Given:
        // When:
        this.initializer.initDatabase();
        List<Customer> customers = this.customerService.getAll();
        List<Drone> drones = this.droneService.getAll();
        List<Order> orders = this.orderService.getAll();
        List<Product> products = this.productService.getAll();
        List<Store> stores = this.storeService.getAll();

        // Then:
        Assertions.assertThat(customers).isNotEmpty();
        Assertions.assertThat(drones).isNotEmpty();
        Assertions.assertThat(orders).isNotEmpty();
        Assertions.assertThat(products).isNotEmpty();
        Assertions.assertThat(stores).isNotEmpty();
    }

}
