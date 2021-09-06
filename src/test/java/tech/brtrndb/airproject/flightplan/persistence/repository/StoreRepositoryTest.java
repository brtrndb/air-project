package tech.brtrndb.airproject.flightplan.persistence.repository;

import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import tech.brtrndb.airproject.flightplan.FlightPlanIntegrationTest;
import tech.brtrndb.airproject.flightplan.fixture.ProductFixtures;
import tech.brtrndb.airproject.flightplan.fixture.StockFixtures;
import tech.brtrndb.airproject.flightplan.fixture.StoreFixtures;
import tech.brtrndb.airproject.flightplan.persistence.entity.ProductEntity;
import tech.brtrndb.airproject.flightplan.persistence.entity.StockEntity;
import tech.brtrndb.airproject.flightplan.persistence.entity.StoreEntity;

@Slf4j
@FlightPlanIntegrationTest
public class StoreRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private StoreRepository repository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StockRepository stockRepository;

    private StoreEntity save() {
        StoreEntity entity = StoreFixtures.randomStoreEntity();
        entity = this.repository.saveAndFlush(entity);
        return entity;
    }

    private ProductEntity saveProduct() {
        ProductEntity entity = ProductFixtures.randomProductEntity();
        entity = this.productRepository.saveAndFlush(entity);
        return entity;
    }

    private StockEntity saveStock(String storeId, String productId) {
        StockEntity entity = StockFixtures.randomStockEntity(storeId, productId);
        entity = this.stockRepository.saveAndFlush(entity);
        return entity;
    }

    @Test
    public void save_store_with_stocks() {
        // Given:
        StoreEntity entity = this.save();
        ProductEntity product = this.saveProduct();
        StockEntity stock = this.saveStock(entity.getStoreId(), product.getProductId());

        // When:
        entity.getProductStocks().put(product.getProductId(), stock);
        entity = this.repository.saveAndFlush(entity);
        StoreEntity got = this.repository.getById(entity.getId());

        // Then:
        Assertions.assertThat(got.getProductStocks())
                .hasSize(1)
                .containsEntry(product.getProductId(), stock);
    }

    @Test
    public void save_store_without_stocks() {
        // Given:
        StoreEntity entity = StoreFixtures.randomStoreEntity();

        // When:
        StoreEntity created = this.repository.saveAndFlush(entity);
        StoreEntity got = this.repository.getById(entity.getId());

        // Then:
        Assertions.assertThat(created).isNotNull();
        Assertions.assertThat(created.getId()).isNotNull();
        Assertions.assertThat(created.getCreationInfo())
                .isNotNull()
                .satisfies(creationInfo -> {
                    Assertions.assertThat(creationInfo.getCreatedAt()).isNotNull();
                    Assertions.assertThat(creationInfo.getUpdatedAt()).isEqualTo(creationInfo.getUpdatedAt());
                });
        Assertions.assertThat(created.getPosition())
                .isNotNull()
                .satisfies(position -> {
                    Assertions.assertThat(position.getPosX()).isNotNull();
                    Assertions.assertThat(position.getPosY()).isNotNull();
                });
        Assertions.assertThat(got.getProductStocks())
                .isNotNull()
                .isEmpty();
    }

    @Test
    public void find_store_by_storeId() {
        // Given:
        StoreEntity entity = this.save();
        String storeId = entity.getStoreId();

        // When:
        Optional<StoreEntity> optional = this.repository.findByStoreId(storeId);

        // Then:
        Assertions.assertThat(optional)
                .isNotEmpty()
                .contains(entity);
    }

    @Test
    public void dont_find_store_by_storeId_given_storeId_non_existent() {
        // Given:
        StoreEntity entity = this.save();
        String nonExistentStoreId = StoreFixtures.randomStoreId();

        // When:
        Optional<StoreEntity> optional = this.repository.findByStoreId(nonExistentStoreId);

        // Then:
        Assertions.assertThat(optional)
                .isEmpty();
    }

    @Test
    public void update_store_stock() {
        // Given:
        StoreEntity entity = this.save();
        ProductEntity product = this.saveProduct();
        StockEntity stock = this.saveStock(entity.getStoreId(), product.getProductId());
        entity.getProductStocks().put(product.getProductId(), stock);
        entity = this.repository.saveAndFlush(entity);
        int newQuantity = stock.getQuantity() + 1;

        // When:
        StoreEntity got = this.repository.getById(entity.getId());
        StockEntity stockEntity = got.getProductStocks().get(product.getProductId());
        stockEntity.setQuantity(newQuantity);
        stockEntity = this.stockRepository.saveAndFlush(stockEntity);
        StoreEntity updated = this.repository.getById(entity.getId());

        // Then:
        Assertions.assertThat(updated.getProductStocks())
                .hasSize(1)
                .containsEntry(product.getProductId(), stock);
    }

}
