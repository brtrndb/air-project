package tech.brtrndb.airproject.flightplan.service;

import java.util.List;
import java.util.Optional;

import tech.brtrndb.airproject.flightplan.domain.Order;
import tech.brtrndb.airproject.flightplan.domain.ProductQuantity;
import tech.brtrndb.airproject.flightplan.error.ModelNotFoundException;

public interface OrderService {

    public List<Order> getAll();

    public Optional<Order> find(String orderId);

    public default Order get(String orderId) throws ModelNotFoundException {
        return this.find(orderId).orElseThrow(() -> new ModelNotFoundException(Order.class, orderId));
    }

    public Order create(String orderId, String customerId, ProductQuantity itemsQuantity) throws ModelNotFoundException;

}
