package tech.brtrndb.airproject.flightplan.service;

import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import tech.brtrndb.airproject.flightplan.FlightPlanIntegrationTest;
import tech.brtrndb.airproject.flightplan.domain.Position;
import tech.brtrndb.airproject.flightplan.domain.Product;
import tech.brtrndb.airproject.flightplan.domain.ProductQuantity;
import tech.brtrndb.airproject.flightplan.domain.Store;
import tech.brtrndb.airproject.flightplan.error.ModelNotFoundException;
import tech.brtrndb.airproject.flightplan.fixture.PositionFixtures;
import tech.brtrndb.airproject.flightplan.fixture.ProductFixtures;
import tech.brtrndb.airproject.flightplan.fixture.StoreFixtures;

@Slf4j
@FlightPlanIntegrationTest
public class StoreServiceTest extends BaseServiceTest {

    @Autowired
    private StoreService service;

    @Autowired
    private ProductService productService;

    private Store create() {
        String storeId = StoreFixtures.randomStoreId();
        Position storePosition = PositionFixtures.randomPosition();

        return this.service.create(storeId, storePosition);
    }

    private Product createProduct() {
        String productId = ProductFixtures.randomProductId();
        String name = ProductFixtures.randomName();

        return this.productService.create(productId, name);
    }

    @Test
    public void should_get_all_stores() {
        // Given:
        Store store0 = this.create();
        Store store1 = this.create();
        Store store2 = this.create();
        List<Store> all = List.of(store0, store1, store2);

        // When:
        List<Store> got = this.service.getAll();

        // Then:
        Assertions.assertThat(got)
                .hasSameSizeAs(all)
                .containsExactlyInAnyOrderElementsOf(all);
    }

    @Test
    public void should_find_store_by_id() {
        // Given:
        Store store = this.create();

        // When:
        Optional<Store> optional = this.service.find(store.getStoreId());

        // Then:
        Assertions.assertThat(optional)
                .isNotEmpty()
                .contains(store);
    }

    @Test
    public void should_not_find_store_by_id() {
        // Given:
        String nonExistentId = StoreFixtures.randomStoreId();

        // When:
        Optional<Store> optional = this.service.find(nonExistentId);

        // Then:
        Assertions.assertThat(optional).isEmpty();
    }

    @Test
    public void should_get_store_by_id() {
        // Given:
        Store store = this.create();

        // When:
        Store got = this.service.get(store.getStoreId());

        // Then:
        Assertions.assertThat(got).isEqualTo(store);
    }

    @Test
    public void should_not_get_store_by_id() {
        // Given:
        String nonExistentId = StoreFixtures.randomStoreId();

        // When:
        // Then:
        Assertions.assertThatExceptionOfType(ModelNotFoundException.class)
                .isThrownBy(() -> this.service.get(nonExistentId));
    }

    @Test
    public void should_create_store_at_position() {
        // Given:
        String storeId = StoreFixtures.randomStoreId();
        Position position = PositionFixtures.randomPosition();

        // When:
        Store store = this.service.create(storeId, position);

        // Then:
        Assertions.assertThat(store.getStoreId()).isEqualTo(storeId);
        Assertions.assertThat(store.getPosition()).isEqualTo(position);
    }

    @Test
    public void should_find_all_stores_with_product() {
        // Given:
        Store store0 = this.create();
        Store store1 = this.create();
        Store store2 = this.create();
        Product product = this.createProduct();
        store0 = this.service.setStocks(store0.getStoreId(), new ProductQuantity(product, 1));
        store1 = this.service.setStocks(store1.getStoreId(), new ProductQuantity(product, 1));
        List<Store> expectedStores = List.of(store0, store1);

        // When:
        List<Store> found = this.service.findAllWithProduct(product);

        // Then:
        Assertions.assertThat(found)
                .hasSameSizeAs(expectedStores)
                .containsExactlyInAnyOrderElementsOf(expectedStores);
    }

    @Test
    public void should_set_store_stock() {
        // Given:
        Store store = this.create();
        Product product = this.createProduct();
        int quantity = 1;

        // When:
        Store withStock = this.service.setStocks(store.getStoreId(), new ProductQuantity(product, quantity));

        // Then:
        Assertions.assertThat(withStock.getProductStocks().getProductQuantity(product.getProductId())).isEqualTo(quantity);
    }

}
