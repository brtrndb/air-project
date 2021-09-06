package tech.brtrndb.airproject.flightplan.persistence.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import tech.brtrndb.airproject.flightplan.persistence.entity.StockEntity;

@Repository
public interface StockRepository extends ModelRepository<StockEntity> {

    public Optional<StockEntity> findByStoreIdAndProductId(String storeId, String productId);

}
