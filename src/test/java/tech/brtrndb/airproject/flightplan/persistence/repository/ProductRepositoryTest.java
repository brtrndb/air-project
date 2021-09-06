package tech.brtrndb.airproject.flightplan.persistence.repository;

import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import tech.brtrndb.airproject.flightplan.FlightPlanIntegrationTest;
import tech.brtrndb.airproject.flightplan.fixture.ProductFixtures;
import tech.brtrndb.airproject.flightplan.persistence.entity.ProductEntity;

@Slf4j
@FlightPlanIntegrationTest
public class ProductRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private ProductRepository repository;

    private ProductEntity save() {
        ProductEntity entity = ProductFixtures.randomProductEntity();
        entity = this.repository.saveAndFlush(entity);
        return entity;
    }

    @Test
    public void save_product() {
        // Given:
        ProductEntity entity = ProductFixtures.randomProductEntity();

        // When:
        ProductEntity created = this.repository.saveAndFlush(entity);
        ProductEntity got = this.repository.getById(created.getId());

        // Then:
        Assertions.assertThat(created).isNotNull();
        Assertions.assertThat(created.getId()).isNotNull();
        Assertions.assertThat(created.getCreationInfo())
                .isNotNull()
                .satisfies(creationInfo -> {
                    Assertions.assertThat(creationInfo.getCreatedAt()).isNotNull();
                    Assertions.assertThat(creationInfo.getUpdatedAt()).isEqualTo(creationInfo.getUpdatedAt());
                });
        Assertions.assertThat(got.getProductId()).isEqualTo(entity.getProductId());
    }

    @Test
    public void find_product_by_productId() {
        // Given:
        ProductEntity entity = this.save();
        String productId = entity.getProductId();

        // When:
        Optional<ProductEntity> optional = this.repository.findByProductId(productId);

        // Then:
        Assertions.assertThat(optional)
                .isNotEmpty()
                .contains(entity);
    }

    @Test
    public void dont_find_product_by_productId_given_productId_non_existent() {
        // Given:
        ProductEntity entity = this.save();
        String nonExistentProductId = ProductFixtures.randomProductId();

        // When:
        Optional<ProductEntity> optional = this.repository.findByProductId(nonExistentProductId);

        // Then:
        Assertions.assertThat(optional)
                .isEmpty();
    }

}
