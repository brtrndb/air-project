package tech.brtrndb.airproject.flightplan.service.mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import tech.brtrndb.airproject.flightplan.domain.Order;
import tech.brtrndb.airproject.flightplan.domain.ProductQuantity;
import tech.brtrndb.airproject.flightplan.persistence.entity.OrderEntity;
import tech.brtrndb.airproject.flightplan.persistence.entity.OrderItemEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OrderMapper {

    public static Order toModel(OrderEntity entity) {
        Map<String, Integer> items = entity.getOrderItems()
                .stream()
                .collect(Collectors.toMap(OrderItemEntity::getProductId, OrderItemEntity::getQuantity));

        return new Order(
                entity.getId(),
                CreationInfoMapper.toModel(entity.getCreationInfo()),
                entity.getOrderId(),
                entity.getCustomerId(),
                new ProductQuantity(items)
        );
    }

    public static OrderEntity toEntity(Order model) {
        return toEntity(model, false);
    }

    public static OrderEntity toNewEntity(Order model) {
        return toEntity(model, true);
    }

    private static OrderEntity toEntity(Order model, boolean ignoreCreationFields) {
        OrderEntity entity = new OrderEntity();
        Set<OrderItemEntity> items = model.getItemsQuantity()
                .getAllProductQuantity()
                .entrySet()
                .stream()
                .map(e -> new OrderItemEntity(model.getOrderId(), e.getKey(), e.getValue()))
                .collect(Collectors.toSet());

        if (!ignoreCreationFields) {
            entity.setId(model.getId());
            entity.setCreationInfo(CreationInfoMapper.toEntity(model.getCreationInfo()));
        }
        entity.setOrderId(model.getOrderId());
        entity.setCustomerId(model.getCustomerId());
        entity.setOrderItems(items);

        return entity;

    }

    public static List<Order> toModels(List<OrderEntity> entities) {
        return entities.stream()
                .map(OrderMapper::toModel)
                .collect(Collectors.toList());
    }

}
