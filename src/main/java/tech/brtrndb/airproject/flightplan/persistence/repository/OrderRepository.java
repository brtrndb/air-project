package tech.brtrndb.airproject.flightplan.persistence.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import tech.brtrndb.airproject.flightplan.persistence.entity.OrderEntity;

@Repository
public interface OrderRepository extends ModelRepository<OrderEntity> {

    public Optional<OrderEntity> findByOrderId(String orderId);

}
