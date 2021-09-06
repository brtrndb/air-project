package tech.brtrndb.airproject.flightplan.service;

import java.util.List;
import java.util.Optional;

import tech.brtrndb.airproject.flightplan.domain.Product;
import tech.brtrndb.airproject.flightplan.error.ModelNotFoundException;

public interface ProductService {

    public List<Product> getAll();

    public Optional<Product> find(String productId);

    public default Product get(String productId) throws ModelNotFoundException {
        return this.find(productId).orElseThrow(() -> new ModelNotFoundException(Product.class, productId));
    }

    public Product create(String productId, String name);

    public Product getOrCreate(String productId, String name);

    public List<Product> getAll(List<String> productIds) throws ModelNotFoundException;

}
