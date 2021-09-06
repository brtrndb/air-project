package tech.brtrndb.airproject.flightplan.fixture;

import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import tech.brtrndb.airproject.flightplan.domain.CreationInfo;
import tech.brtrndb.airproject.flightplan.domain.Position;
import tech.brtrndb.airproject.flightplan.domain.ProductQuantity;
import tech.brtrndb.airproject.flightplan.domain.Store;
import tech.brtrndb.airproject.flightplan.persistence.entity.CreationInfoEntity;
import tech.brtrndb.airproject.flightplan.persistence.entity.PositionEntity;
import tech.brtrndb.airproject.flightplan.persistence.entity.StockEntity;
import tech.brtrndb.airproject.flightplan.persistence.entity.StoreEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StoreFixtures {

    public static String randomStoreId() {
        return DataFixtures.randomString("STORE");
    }

    public static Map<String, StockEntity> randomProductStocks(int nb, String storeId) {
        return StockFixtures.randomStockEntities(nb, storeId)
                .stream()
                .collect(Collectors.toMap(StockEntity::getProductId, Function.identity()));
    }

    public static StoreEntity randomStoreEntity() {
        return DataFixtures.build(() -> {
            StoreEntity entity = new StoreEntity();
            entity.setStoreId(randomStoreId());
            entity.setPosition(PositionFixtures.randomPositionEntity());

            return entity;
        });
    }

    public static Store randomStore() {
        return DataFixtures.build(() -> new Store(
                randomStoreId(),
                PositionFixtures.randomPosition()
        ));
    }

    public static Map.Entry<StoreEntity, Store> randomMatching() {
        UUID id = CreationInfoFixtures.randomId();
        Map.Entry<CreationInfoEntity, CreationInfo> matchingCreationInfo = CreationInfoFixtures.randomMatching();
        String storeId = randomStoreId();
        Map.Entry<PositionEntity, Position> matchingPosition = PositionFixtures.randomMatching();
        String productId = ProductFixtures.randomProductId();
        int quantity = StockFixtures.randomQuantity();

        ProductQuantity productStocksModel = new ProductQuantity(Map.of(productId, quantity));
        Map<String, StockEntity> productStocksEntity = Map.of(productId, new StockEntity(storeId, productId, quantity));

        StoreEntity entity = new StoreEntity();
        entity.setId(id);
        entity.setCreationInfo(matchingCreationInfo.getKey());
        entity.setStoreId(storeId);
        entity.setPosition(matchingPosition.getKey());
        entity.setProductStocks(productStocksEntity);

        Store model = new Store(id, matchingCreationInfo.getValue(), storeId, matchingPosition.getValue(), productStocksModel);

        return Map.entry(entity, model);
    }

}
