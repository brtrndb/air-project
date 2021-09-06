package tech.brtrndb.airproject.flightplan.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tech.brtrndb.airproject.flightplan.persistence.entity.StoreEntity;

@Repository
public interface StoreRepository extends ModelRepository<StoreEntity> {

    public Optional<StoreEntity> findByStoreId(String storeId);

    @Query("SELECT s FROM store s JOIN s.productStocks st " +
            "WHERE st.productId = :productId " +
            "AND st.quantity > 0 ")
    public List<StoreEntity> findAllByProductId(@Param("productId") String productId);

}
