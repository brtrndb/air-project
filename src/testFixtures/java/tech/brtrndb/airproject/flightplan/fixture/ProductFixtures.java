package tech.brtrndb.airproject.flightplan.fixture;

import java.util.Map;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import tech.brtrndb.airproject.flightplan.domain.CreationInfo;
import tech.brtrndb.airproject.flightplan.domain.Product;
import tech.brtrndb.airproject.flightplan.persistence.entity.CreationInfoEntity;
import tech.brtrndb.airproject.flightplan.persistence.entity.ProductEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProductFixtures {

    public static String randomProductId() {
        return DataFixtures.randomString("PRODUCT");
    }

    public static String randomName() {
        return DataFixtures.randomString("product_name");
    }

    public static ProductEntity randomProductEntity() {
        return DataFixtures.build(() -> {
            ProductEntity entity = new ProductEntity();
            entity.setProductId(randomProductId());
            entity.setName(randomName());

            return entity;
        });
    }

    public static Product randomProduct() {
        return DataFixtures.build(() -> new Product(
                randomProductId(),
                randomName()
        ));
    }

    public static Map.Entry<ProductEntity, Product> randomMatching() {
        UUID id = CreationInfoFixtures.randomId();
        Map.Entry<CreationInfoEntity, CreationInfo> matchingCreationInfo = CreationInfoFixtures.randomMatching();
        String customerId = randomProductId();
        String name = randomName();

        ProductEntity entity = new ProductEntity();
        entity.setId(id);
        entity.setCreationInfo(matchingCreationInfo.getKey());
        entity.setProductId(customerId);
        entity.setName(name);

        Product model = new Product(id, matchingCreationInfo.getValue(), customerId, name);

        return Map.entry(entity, model);
    }


}
