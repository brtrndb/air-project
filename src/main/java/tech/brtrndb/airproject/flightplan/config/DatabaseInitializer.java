package tech.brtrndb.airproject.flightplan.config;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import tech.brtrndb.airproject.flightplan.csv.CsvDataLoader;
import tech.brtrndb.airproject.flightplan.csv.CustomerCSV;
import tech.brtrndb.airproject.flightplan.csv.DroneCSV;
import tech.brtrndb.airproject.flightplan.csv.OrderCSV;
import tech.brtrndb.airproject.flightplan.csv.ProductCSV;
import tech.brtrndb.airproject.flightplan.csv.StoreCSV;
import tech.brtrndb.airproject.flightplan.domain.Customer;
import tech.brtrndb.airproject.flightplan.domain.Drone;
import tech.brtrndb.airproject.flightplan.domain.Order;
import tech.brtrndb.airproject.flightplan.domain.Position;
import tech.brtrndb.airproject.flightplan.domain.Product;
import tech.brtrndb.airproject.flightplan.domain.ProductQuantity;
import tech.brtrndb.airproject.flightplan.domain.Store;
import tech.brtrndb.airproject.flightplan.service.CustomerService;
import tech.brtrndb.airproject.flightplan.service.DroneService;
import tech.brtrndb.airproject.flightplan.service.OrderService;
import tech.brtrndb.airproject.flightplan.service.ProductService;
import tech.brtrndb.airproject.flightplan.service.StoreService;

@Slf4j
@Component
public class DatabaseInitializer {

    private final ApplicationProperties.CsvFiles csvFiles;
    private final CustomerService customerService;
    private final DroneService droneService;
    private final OrderService orderService;
    private final ProductService productService;
    private final StoreService storeService;

    public DatabaseInitializer(ApplicationProperties applicationProperties, CustomerService customerService, DroneService droneService, OrderService orderService, ProductService productService, StoreService storeService) {
        this.csvFiles = applicationProperties.getCsvFiles();
        this.customerService = customerService;
        this.droneService = droneService;
        this.orderService = orderService;
        this.productService = productService;
        this.storeService = storeService;
    }

    public void initDatabase() {
        log.debug("Loading all CSV files.");

        List<StoreCSV> stores = CsvDataLoader.loadStoresFromFile(this.csvFiles.getStores());
        List<ProductCSV> products = CsvDataLoader.loadProductsFromFile(this.csvFiles.getProducts());
        List<CustomerCSV> customers = CsvDataLoader.loadCustomersFromFile(this.csvFiles.getCustomers());
        List<OrderCSV> orders = CsvDataLoader.loadOrdersFromFile(this.csvFiles.getOrders());
        List<DroneCSV> drones = CsvDataLoader.loadDronesFromFile(this.csvFiles.getDrones());

        log.info("All CSV files loaded.");

        this.populate(customers, drones, orders, products, stores);
    }

    private void populate(List<CustomerCSV> customersCSV, List<DroneCSV> dronesCSV, List<OrderCSV> ordersCSV, List<ProductCSV> productsCSV, List<StoreCSV> storesCSV) {
        log.debug("Populating database with initialisation state.");

        List<Store> stores = this.populateStores(storesCSV);
        List<Product> products = this.populateProductsWithStocks(productsCSV);
        List<Customer> customers = this.populateCustomers(customersCSV);
        List<Drone> drones = this.populateDrones(dronesCSV);
        List<Order> orders = this.populateOrders(ordersCSV);

        log.info("Database populated with {} store(s), {} product(s), {} customer(s), {} drone(s), {} order(s)", stores.size(), products.size(), customers.size(), drones.size(), orders.size());
    }

    private List<Store> populateStores(List<StoreCSV> storesCSV) {
        List<Store> stores = storesCSV.stream()
                .map(store -> this.storeService.create(store.getStoreId(), Position.of(store.getX(), store.getY())))
                .toList();

        log.trace("All stores saved in database.");

        return stores;
    }

    private List<Product> populateProductsWithStocks(List<ProductCSV> productsCSV) {
        List<Product> products = productsCSV.stream()
                .map(product -> this.productService.create(product.getProductId(), product.getProductName()))
                .toList();

        Map<String, Product> productById = products.stream().collect(Collectors.toMap(Product::getProductId, Function.identity()));

        productsCSV.forEach(product -> product.getStockQuantity().forEach((key, value) -> this.storeService.setStocks(key, productById.get(product.getProductId()), value)));

        log.trace("All products saved in database.");

        return products;
    }

    private List<Customer> populateCustomers(List<CustomerCSV> customersCSV) {
        List<Customer> customers = customersCSV.stream()
                .map(customer -> this.customerService.create(customer.getCustomerId(), Position.of(customer.getX(), customer.getY())))
                .toList();

        log.trace("All customers saved in database.");

        return customers;
    }

    private List<Drone> populateDrones(List<DroneCSV> dronesCSV) {
        List<Drone> drones = dronesCSV.stream()
                .map(drone -> this.droneService.deploy(drone.getDroneId(), Position.of(drone.getX(), drone.getY())))
                .toList();

        log.trace("All drones saved in database.");

        return drones;
    }

    private List<Order> populateOrders(List<OrderCSV> ordersCSV) {
        List<Order> orders = ordersCSV.stream()
                .map(order -> this.orderService.create(order.getOrderId(), order.getCustomerId(), new ProductQuantity(order.getItemsQuantity())))
                .toList();

        log.trace("All orders saved in database.");

        return orders;
    }

}
