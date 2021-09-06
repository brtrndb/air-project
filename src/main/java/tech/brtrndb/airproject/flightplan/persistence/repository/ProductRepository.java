package tech.brtrndb.airproject.flightplan.persistence.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import tech.brtrndb.airproject.flightplan.persistence.entity.ProductEntity;

@Repository
public interface ProductRepository extends ModelRepository<ProductEntity> {

    public Optional<ProductEntity> findByProductId(String productId);

}
