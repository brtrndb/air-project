package tech.brtrndb.airproject.flightplan.fixture;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import tech.brtrndb.airproject.flightplan.domain.CreationInfo;
import tech.brtrndb.airproject.flightplan.domain.Order;
import tech.brtrndb.airproject.flightplan.domain.ProductQuantity;
import tech.brtrndb.airproject.flightplan.persistence.entity.CreationInfoEntity;
import tech.brtrndb.airproject.flightplan.persistence.entity.OrderEntity;
import tech.brtrndb.airproject.flightplan.persistence.entity.OrderItemEntity;
import tech.brtrndb.airproject.flightplan.persistence.entity.ProductEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OrderFixtures {

    public static String randomOrderId() {
        return DataFixtures.randomString("ORDER");
    }

    public static int randomQuantity() {
        return DataFixtures.randomInt(0, 5);
    }

    public static Set<OrderItemEntity> randomOrderItemsEntity(int nb, String orderId) {
        return Stream.generate(() -> new OrderItemEntity(orderId, ProductFixtures.randomProductId(), randomQuantity()))
                .limit(nb)
                .collect(Collectors.toSet());
    }

    public static OrderEntity randomOrderEntity(String customerId, Set<ProductEntity> products) {
        return DataFixtures.build(() -> {
            String orderId = randomOrderId();
            Set<OrderItemEntity> orderItems = products.stream()
                    .map(p -> new OrderItemEntity(orderId, p.getProductId(), randomQuantity()))
                    .collect(Collectors.toSet());

            OrderEntity entity = new OrderEntity();
            entity.setOrderId(orderId);
            entity.setCustomerId(customerId);
            entity.setOrderItems(orderItems);

            return entity;
        });
    }

    public static Order randomOrder(String customerId, Set<String> productIds) {
        return DataFixtures.build(() -> {
            Map<String, Integer> items = productIds.stream()
                    .collect(Collectors.toMap(Function.identity(), p -> OrderFixtures.randomQuantity()));

            return new Order(
                    randomOrderId(),
                    customerId,
                    new ProductQuantity(items)
            );
        });
    }

    public static Map.Entry<OrderEntity, Order> randomMatching() {
        UUID id = CreationInfoFixtures.randomId();
        Map.Entry<CreationInfoEntity, CreationInfo> matchingCreationInfo = CreationInfoFixtures.randomMatching();
        String orderId = randomOrderId();
        String customerId = CustomerFixtures.randomCustomerId();
        String productId = ProductFixtures.randomProductId();
        int quantity = randomQuantity();

        ProductQuantity orderItemsModel = new ProductQuantity(Map.of(productId, quantity));
        Set<OrderItemEntity> orderItemsEntity = Collections.singleton(new OrderItemEntity(orderId, productId, quantity));

        OrderEntity entity = new OrderEntity();
        entity.setId(id);
        entity.setCreationInfo(matchingCreationInfo.getKey());
        entity.setOrderId(orderId);
        entity.setCustomerId(customerId);
        entity.setOrderItems(orderItemsEntity);

        Order model = new Order(id, matchingCreationInfo.getValue(), orderId, customerId, orderItemsModel);

        return Map.entry(entity, model);
    }

}
