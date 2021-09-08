package tech.brtrndb.airproject.flightplan.service;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tech.brtrndb.airproject.flightplan.domain.Customer;
import tech.brtrndb.airproject.flightplan.domain.Delivery;
import tech.brtrndb.airproject.flightplan.domain.Drone;
import tech.brtrndb.airproject.flightplan.domain.Order;
import tech.brtrndb.airproject.flightplan.domain.Position;
import tech.brtrndb.airproject.flightplan.domain.Product;
import tech.brtrndb.airproject.flightplan.domain.ProductQuantity;
import tech.brtrndb.airproject.flightplan.domain.Store;
import tech.brtrndb.airproject.flightplan.error.DeliveryCannotBeSatisfiedException;
import tech.brtrndb.airproject.flightplan.util.GeoUtils;

@Slf4j
@Service
@Transactional
public class DeliveryServiceImpl implements DeliveryService {

    private static final Comparator<Delivery> DELIVERY_COMPARATOR = Comparator.<Delivery, String>comparing(d -> d.customer().getCustomerId())
            .thenComparing(d -> d.product().getProductId())
            .thenComparing(d -> d.drone().getDroneId())
            .thenComparing(d -> d.store().getStoreId());

    private final OrderService orderService;
    private final StoreService storeService;
    private final ProductService productService;
    private final CustomerService customerService;
    private final DroneService droneService;

    public DeliveryServiceImpl(OrderService orderService, StoreService storeService, ProductService productService, CustomerService customerService, DroneService droneService) {
        this.orderService = orderService;
        this.storeService = storeService;
        this.productService = productService;
        this.customerService = customerService;
        this.droneService = droneService;
    }

    @Override
    public List<Delivery> generateDeliveries() throws DeliveryCannotBeSatisfiedException {
        List<Order> orders = this.orderService.getAll();

        return orders.stream()
                .map(this::generateDeliveries)
                .flatMap(Collection::stream)
                .sorted(DELIVERY_COMPARATOR)
                .toList();
    }

    @Override
    public List<Delivery> generateDeliveries(Order order) throws DeliveryCannotBeSatisfiedException {
        String customerId = order.getCustomerId();
        Customer customer = this.customerService.get(customerId);
        ProductQuantity productQuantity = order.getItemsQuantity();

        log.info("Generating deliveries for order [{}].", order.getOrderId());

        return productQuantity.getAllProductQuantity()
                .entrySet()
                .stream()
                .map(stock -> this.generateDeliveries(order, customer, stock.getKey(), stock.getValue()))
                .flatMap(Collection::stream)
                .sorted(DELIVERY_COMPARATOR)
                .toList();
    }

    private List<Delivery> generateDeliveries(Order order, Customer customer, String productId, int quantity) throws DeliveryCannotBeSatisfiedException {
        Product product = this.productService.get(productId);

        return IntStream.range(0, quantity)
                .mapToObj(i -> {
                    Store store = this.getClosestStoreToCustomerWithProduct(customer.getPosition(), product);
                    Drone drone = this.getClosestDroneToStore(store, customer);

                    log.debug("Start delivery for product [{}] {}/{} to customer [{}] in order [{}].", product.getProductId(), i + 1, quantity, customer.getCustomerId(), order.getOrderId());
                    Delivery delivery = this.process(order, drone, store, product, customer);
                    log.debug("End delivery for product [{}] {}/{} to customer [{}] in order [{}].", product.getProductId(), i + 1, quantity, customer.getCustomerId(), order.getOrderId());

                    return delivery;
                })
                .toList();
    }

    private Store getClosestStoreToCustomerWithProduct(Position customerPosition, Product product) throws DeliveryCannotBeSatisfiedException {
        List<Store> storesWithRequestedProduct = this.storeService.findAllWithProduct(product);

        if (storesWithRequestedProduct.isEmpty()) {
            throw new DeliveryCannotBeSatisfiedException("Product [%s] could not be found in stores".formatted(product.getProductId()));
        }

        return GeoUtils.getClosestLocalizable(customerPosition, storesWithRequestedProduct);
    }

    private Drone getClosestDroneToStore(Store store, Customer customer) throws DeliveryCannotBeSatisfiedException {
        List<Drone> drones = this.droneService.getAll();

        if (drones.isEmpty()) {
            throw new DeliveryCannotBeSatisfiedException("No drone available for delivery");
        }

        drones = drones.stream()
                .filter(drone -> {
                    List<Position> path = List.of(drone.getPosition(), store.getPosition(), customer.getPosition());
                    double distance = GeoUtils.distance(path);
                    return drone.hasEnoughFuel(distance);
                })
                .sorted(Comparator.comparingDouble(Drone::getFuel))
                .toList();

        if (drones.isEmpty()) {
            throw new DeliveryCannotBeSatisfiedException("No drone has enough fuel to go to store [%s] and then to customer [%s]".formatted(store.getStoreId(), customer.getCustomerId()));
        }

        return drones.get(0);
    }

    private Delivery process(Order order, Drone drone, Store store, Product product, Customer customer) {
        // Drone goes to store.
        log.debug("Moving Drone [{}] from {} to Store [{}] at {}.", drone.getDroneId(), drone.getPosition(), store.getStoreId(), store.getPosition());
        Drone moved1 = this.droneService.move(drone.getDroneId(), store);
        log.debug("Drone [{}] moved from {} to {} remaining fuel: {}.", moved1.getDroneId(), drone.getPosition(), moved1.getPosition(), moved1.getFuel());

        // Drone pickup product.
        log.debug("Drone [{}] picks from Store [{}] Product [{}] (quantity: {}).", drone.getDroneId(), store.getStoreId(), product.getProductId(), store.getProductStocks().getProductQuantity(product.getProductId()));
        Store st = this.storeService.addOrRemoveProductFromStock(store.getStoreId(), product, -1);
        log.debug("Store [{}] has Product [{}] remaining {}.", st.getStoreId(), product.getProductId(), st.getProductStocks().getProductQuantity(product.getProductId()));

        // Drone goes to customer.
        log.debug("Moving Drone [{}] from {} to Customer [{}] at {}.", moved1.getDroneId(), moved1.getPosition(), customer.getCustomerId(), customer.getPosition());
        Drone moved2 = this.droneService.move(drone.getDroneId(), customer);
        log.debug("Drone [{}] moved from {} to {} remaining fuel: {}.", moved1.getDroneId(), moved1.getPosition(), moved2.getPosition(), moved2.getFuel());

        return new Delivery(order, drone, store, product, customer);
    }

}
