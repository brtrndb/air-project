package tech.brtrndb.airproject.flightplan.fixture;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import tech.brtrndb.airproject.flightplan.persistence.entity.StockEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StockFixtures {

    public static int randomQuantity() {
        return DataFixtures.randomInt(0, 100);
    }

    public static StockEntity randomStockEntity(String storeId) {
        return randomStockEntity(storeId, ProductFixtures.randomProductId());
    }

    public static StockEntity randomStockEntity(String storeId, String productId) {
        return DataFixtures.build(() -> new StockEntity(storeId, productId, randomQuantity()));
    }

    public static List<StockEntity> randomStockEntities(int nb, String storeId) {
        return DataFixtures.generate(nb, () -> randomStockEntity(storeId));
    }

    public static Map<String, Integer> randomStock(int nb) {
        return DataFixtures.generate(nb, ProductFixtures::randomProductId)
                .stream()
                .collect(Collectors.toMap(Function.identity(), s -> randomQuantity()));
    }

}
