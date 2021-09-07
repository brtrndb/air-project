package tech.brtrndb.airproject.flightplan.service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tech.brtrndb.airproject.flightplan.domain.Customer;
import tech.brtrndb.airproject.flightplan.domain.Order;
import tech.brtrndb.airproject.flightplan.domain.Product;
import tech.brtrndb.airproject.flightplan.domain.ProductQuantity;
import tech.brtrndb.airproject.flightplan.error.ModelNotFoundException;
import tech.brtrndb.airproject.flightplan.persistence.entity.OrderEntity;
import tech.brtrndb.airproject.flightplan.persistence.repository.OrderRepository;
import tech.brtrndb.airproject.flightplan.service.mapper.OrderMapper;
import tech.brtrndb.airproject.flightplan.util.ValidationUtils;

@Slf4j
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private static final String ORDER_ID_IS_NULL = "Order id is null";
    private static final String ORDER_ID_IS_EMPTY = "Order id is blank or empty";
    private static final String CUSTOMER_ID_IS_NULL = "Customer id is null";
    private static final String CUSTOMER_ID_IS_EMPTY = "Customer id is blank or empty";
    private static final String ORDER_PRODUCTS_IS_NULL = "Order's products is null";

    private final OrderRepository repository;
    private final ProductService productService;
    private final CustomerService customerService;

    public OrderServiceImpl(OrderRepository repository, ProductService productService, CustomerService customerService) {
        this.repository = repository;
        this.productService = productService;
        this.customerService = customerService;
    }

    @Override
    public List<Order> getAll() {
        List<OrderEntity> entities = this.repository.findAll();
        List<Order> models = OrderMapper.toModels(entities);

        log.trace("Got {} order(s).", models.size());

        return models;
    }

    @Override
    public Optional<Order> find(String orderId) {
        ValidationUtils.requireNonNullBlankEmptyString(orderId, ORDER_ID_IS_NULL, ORDER_ID_IS_EMPTY);

        Optional<OrderEntity> optional = this.repository.findByOrderId(orderId);

        Optional<Order> optionalModel = optional.map(OrderMapper::toModel);

        optionalModel.ifPresentOrElse(
                d -> log.trace("Order [{}] found.", d.getOrderId()),
                () -> log.trace("Order [{}] not found.", orderId)
        );

        return optionalModel;
    }

    @Override
    public Order create(String orderId, String customerId, ProductQuantity itemsQuantity) throws ModelNotFoundException {
        ValidationUtils.requireNonNullBlankEmptyString(orderId, ORDER_ID_IS_NULL, ORDER_ID_IS_EMPTY);
        ValidationUtils.requireNonNullBlankEmptyString(customerId, CUSTOMER_ID_IS_NULL, CUSTOMER_ID_IS_EMPTY);
        Objects.requireNonNull(itemsQuantity, ORDER_PRODUCTS_IS_NULL);

        Customer customer = this.customerService.get(customerId);

        Order model = new Order(orderId, customer.getCustomerId());

        for (Map.Entry<String, Integer> entry : itemsQuantity.getAllProductQuantity().entrySet()) {
            Product product = this.productService.get(entry.getKey());
            model.addItem(product, entry.getValue());
        }

        OrderEntity entity = OrderMapper.toNewEntity(model);
        entity = this.repository.saveAndFlush(entity);
        model = OrderMapper.toModel(entity);

        log.trace("Order [{}] created for customer [{}] with {} products.", model.getOrderId(), model.getCustomerId(), model.getProductCount());

        return model;
    }

}
