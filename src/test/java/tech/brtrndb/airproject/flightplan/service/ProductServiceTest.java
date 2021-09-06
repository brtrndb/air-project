package tech.brtrndb.airproject.flightplan.service;

import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import tech.brtrndb.airproject.flightplan.FlightPlanIntegrationTest;
import tech.brtrndb.airproject.flightplan.domain.Product;
import tech.brtrndb.airproject.flightplan.error.ModelNotFoundException;
import tech.brtrndb.airproject.flightplan.fixture.ProductFixtures;

@Slf4j
@FlightPlanIntegrationTest
public class ProductServiceTest extends BaseServiceTest {

    @Autowired
    private ProductService service;

    private Product create() {
        String productId = ProductFixtures.randomProductId();
        String name = ProductFixtures.randomName();

        return this.service.create(productId, name);
    }

    @Test
    public void should_get_all_products() {
        // Given:
        Product product0 = this.create();
        Product product1 = this.create();
        Product product2 = this.create();
        List<Product> all = List.of(product0, product1, product2);

        // When:
        List<Product> got = this.service.getAll();

        // Then:
        Assertions.assertThat(got)
                .hasSameSizeAs(all)
                .containsExactlyInAnyOrderElementsOf(all);
    }

    @Test
    public void should_find_product_by_id() {
        // Given:
        Product product = this.create();

        // When:
        Optional<Product> optional = this.service.find(product.getProductId());

        // Then:
        Assertions.assertThat(optional)
                .isNotEmpty()
                .contains(product);
    }

    @Test
    public void should_not_find_product_by_id() {
        // Given:
        String nonExistentId = ProductFixtures.randomProductId();

        // When:
        Optional<Product> optional = this.service.find(nonExistentId);

        // Then:
        Assertions.assertThat(optional).isEmpty();
    }

    @Test
    public void should_get_product_by_id() {
        // Given:
        Product product = this.create();

        // When:
        Product got = this.service.get(product.getProductId());

        // Then:
        Assertions.assertThat(got).isEqualTo(product);
    }

    @Test
    public void should_not_get_product_by_id() {
        // Given:
        String nonExistentId = ProductFixtures.randomProductId();

        // When:
        // Then:
        Assertions.assertThatExceptionOfType(ModelNotFoundException.class)
                .isThrownBy(() -> this.service.get(nonExistentId));
    }

    @Test
    public void should_create_product() {
        // Given:
        String productId = ProductFixtures.randomProductId();
        String name = ProductFixtures.randomName();

        // When:
        Product product = this.service.create(productId, name);

        // Then:
        Assertions.assertThat(product.getProductId()).isEqualTo(productId);
        Assertions.assertThat(product.getName()).isEqualTo(name);
    }

}
