package tech.brtrndb.airproject.flightplan.service.mapper;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import tech.brtrndb.airproject.flightplan.domain.Product;
import tech.brtrndb.airproject.flightplan.fixture.ProductFixtures;
import tech.brtrndb.airproject.flightplan.persistence.entity.ProductEntity;

@Slf4j
public class ProductMapperTest extends BaseMapperTest<Product, ProductEntity> {

    private static final Map.Entry<ProductEntity, Product> matching = ProductFixtures.randomMatching();

    public ProductMapperTest() {
        super(
                ProductMapper::toEntity,
                ProductMapper::toModel,
                matching::getValue,
                matching::getKey
        );
    }

}
