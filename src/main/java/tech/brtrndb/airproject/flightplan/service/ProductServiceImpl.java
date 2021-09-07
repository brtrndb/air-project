package tech.brtrndb.airproject.flightplan.service;

import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tech.brtrndb.airproject.flightplan.domain.Product;
import tech.brtrndb.airproject.flightplan.error.ModelNotFoundException;
import tech.brtrndb.airproject.flightplan.persistence.entity.ProductEntity;
import tech.brtrndb.airproject.flightplan.persistence.repository.ProductRepository;
import tech.brtrndb.airproject.flightplan.service.mapper.ProductMapper;
import tech.brtrndb.airproject.flightplan.util.ValidationUtils;

@Slf4j
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private static final String PRODUCT_ID_IS_NULL = "Product id is null";
    private static final String PRODUCT_ID_IS_EMPTY = "Product id is blank or empty";
    private static final String PRODUCT_NAME_IS_NULL = "Product name is null";
    private static final String PRODUCT_NAME_IS_EMPTY = "Product name is blank or empty";

    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Product> getAll() {
        List<ProductEntity> entities = this.repository.findAll();
        List<Product> models = ProductMapper.toModels(entities);

        log.trace("Got {} product(s).", models.size());

        return models;
    }

    @Override
    public Optional<Product> find(String productId) {
        ValidationUtils.requireNonNullBlankEmptyString(productId, PRODUCT_ID_IS_NULL, PRODUCT_ID_IS_EMPTY);

        Optional<ProductEntity> optional = this.repository.findByProductId(productId);

        Optional<Product> optionalModel = optional.map(ProductMapper::toModel);

        optionalModel.ifPresentOrElse(
                d -> log.trace("Product [{}] found.", d.getProductId()),
                () -> log.trace("Product [{}] not found.", productId)
        );

        return optionalModel;
    }

    @Override
    public Product create(String productId, String name) {
        ValidationUtils.requireNonNullBlankEmptyString(productId, PRODUCT_ID_IS_NULL, PRODUCT_ID_IS_EMPTY);
        ValidationUtils.requireNonNullBlankEmptyString(name, PRODUCT_NAME_IS_NULL, PRODUCT_NAME_IS_EMPTY);

        Product model = new Product(productId, name);
        ProductEntity entity = ProductMapper.toNewEntity(model);
        entity = this.repository.saveAndFlush(entity);
        model = ProductMapper.toModel(entity);

        log.trace("Product [{}] created as \"{}\".", model.getProductId(), model.getName());

        return model;
    }

    @Override
    public Product getOrCreate(String productId, String name) {
        Optional<Product> optionalProduct = this.find(productId);

        Product product;
        if (optionalProduct.isEmpty()) {
            product = this.create(productId, name);
        } else {
            product = optionalProduct.get();
        }

        return product;
    }

    @Override
    public List<Product> getAll(List<String> productIds) throws ModelNotFoundException {
        return productIds.stream()
                .map(this::get)
                .toList();
    }

}
