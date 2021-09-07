package tech.brtrndb.airproject.flightplan.service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tech.brtrndb.airproject.flightplan.domain.Position;
import tech.brtrndb.airproject.flightplan.domain.Product;
import tech.brtrndb.airproject.flightplan.domain.ProductQuantity;
import tech.brtrndb.airproject.flightplan.domain.Store;
import tech.brtrndb.airproject.flightplan.error.ModelNotFoundException;
import tech.brtrndb.airproject.flightplan.persistence.entity.StoreEntity;
import tech.brtrndb.airproject.flightplan.persistence.repository.StockRepository;
import tech.brtrndb.airproject.flightplan.persistence.repository.StoreRepository;
import tech.brtrndb.airproject.flightplan.service.mapper.StoreMapper;
import tech.brtrndb.airproject.flightplan.util.ValidationUtils;

@Slf4j
@Service
@Transactional
public class StoreServiceImpl implements StoreService {

    private static final String STORE_ID_IS_NULL = "Store id is null";
    private static final String STORE_ID_IS_EMPTY = "Store id is blank or empty";
    private static final String STORE_POSITION_IS_NULL = "Store position is null";
    private static final String PRODUCT_IS_NULL = "Product is null";
    private static final String PRODUCT_ID_IS_NULL = "Product id is null";
    private static final String PRODUCT_ID_IS_EMPTY = "Product id is empty";

    private final StoreRepository repository;
    private final StockRepository stockRepository;
    private final ProductService productService;

    public StoreServiceImpl(StoreRepository repository, StockRepository stockRepository, ProductService productService) {
        this.repository = repository;
        this.stockRepository = stockRepository;
        this.productService = productService;
    }

    @Override
    public List<Store> getAll() {
        List<StoreEntity> entities = this.repository.findAll();
        List<Store> models = StoreMapper.toModels(entities);

        log.trace("Got {} store(s).", models.size());

        return models;

    }

    @Override
    public Optional<Store> find(String storeId) {
        ValidationUtils.requireNonNullBlankEmptyString(storeId, STORE_ID_IS_NULL, STORE_ID_IS_EMPTY);

        Optional<StoreEntity> optional = this.repository.findByStoreId(storeId);

        Optional<Store> optionalModel = optional.map(StoreMapper::toModel);

        optionalModel.ifPresentOrElse(
                d -> log.trace("Store [{}] found.", d.getStoreId()),
                () -> log.trace("Store [{}] not found.", storeId)
        );

        return optionalModel;
    }

    @Override
    public Store create(String storeId, Position position) {
        ValidationUtils.requireNonNullBlankEmptyString(storeId, STORE_ID_IS_NULL, STORE_ID_IS_EMPTY);
        Objects.requireNonNull(position, STORE_POSITION_IS_NULL);

        Store model = new Store(storeId, position);
        StoreEntity entity = StoreMapper.toNewEntity(model);
        entity = this.repository.saveAndFlush(entity);
        model = StoreMapper.toModel(entity);

        log.trace("Store [{}] created at ({}, {}).", model.getStoreId(), model.getPosX(), model.getPosY());

        return model;
    }

    @Override
    public List<Store> findAllWithProduct(String productId) throws ModelNotFoundException {
        ValidationUtils.requireNonNullBlankEmptyString(productId, PRODUCT_ID_IS_NULL, PRODUCT_ID_IS_EMPTY);

        List<StoreEntity> entities = this.repository.findAllByProductId(productId);

        return StoreMapper.toModels(entities);
    }

    @Override
    public Store setStocks(String storeId, ProductQuantity stocks) throws ModelNotFoundException {
        ValidationUtils.requireNonNullBlankEmptyString(storeId, STORE_ID_IS_NULL, STORE_ID_IS_EMPTY);

        Store store = this.get(storeId);

        for (Map.Entry<String, Integer> stock : stocks) {
            String stockProductId = stock.getKey();
            int stockQuantity = stock.getValue();
            Product product = this.productService.get(stockProductId);

            log.trace("Setting stocks for product [{}] in store [{}] to {}.", product.getProductId(), storeId, stockQuantity);

            store.getProductStocks().setProductQuantity(product.getProductId(), stockQuantity);
        }

        StoreEntity e = StoreMapper.toEntity(store);
        e = this.repository.saveAndFlush(e);

        store = StoreMapper.toModel(e);

        log.trace("Store [{}] now has {} different product(s) in stock.", store.getStoreId(), store.getProductStocks().getAllProductQuantity().size());

        return store;
    }

    @Override
    public Store addOrRemoveProductFromStock(String storeId, Product product, int quantity) throws ModelNotFoundException {
        ValidationUtils.requireNonNullBlankEmptyString(storeId, STORE_ID_IS_NULL, STORE_ID_IS_EMPTY);

        Store store = this.get(storeId);

        int currentQuantity = store.getProductStocks().getProductQuantity(product);
        store.getProductStocks().setProductQuantity(product, currentQuantity + quantity);

        StoreEntity entity = StoreMapper.toEntity(store);
        entity = this.repository.saveAndFlush(entity);

        store = StoreMapper.toModel(entity);

        return store;
    }

}
