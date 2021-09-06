package tech.brtrndb.airproject.flightplan.service.mapper;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import tech.brtrndb.airproject.flightplan.domain.Order;
import tech.brtrndb.airproject.flightplan.fixture.OrderFixtures;
import tech.brtrndb.airproject.flightplan.persistence.entity.OrderEntity;

@Slf4j
public class OrderMapperTest extends BaseMapperTest<Order, OrderEntity> {

    private static final Map.Entry<OrderEntity, Order> matching = OrderFixtures.randomMatching();

    public OrderMapperTest() {
        super(
                OrderMapper::toEntity,
                OrderMapper::toModel,
                matching::getValue,
                matching::getKey
        );
    }

}
