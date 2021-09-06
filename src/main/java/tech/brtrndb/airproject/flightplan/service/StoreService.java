package tech.brtrndb.airproject.flightplan.service;

import java.util.List;
import java.util.Optional;

import tech.brtrndb.airproject.flightplan.domain.Position;
import tech.brtrndb.airproject.flightplan.domain.Product;
import tech.brtrndb.airproject.flightplan.domain.ProductQuantity;
import tech.brtrndb.airproject.flightplan.domain.Store;
import tech.brtrndb.airproject.flightplan.error.ModelNotFoundException;

public interface StoreService {

    public List<Store> getAll();

    public Optional<Store> find(String storeId);

    public default Store get(String storeId) throws ModelNotFoundException {
        return this.find(storeId).orElseThrow(() -> new ModelNotFoundException(Store.class, storeId));
    }

    public Store create(String storeId, Position position);

    public default List<Store> findAllWithProduct(Product product) throws ModelNotFoundException {
        return this.findAllWithProduct(product.getProductId());
    }

    public List<Store> findAllWithProduct(String productId) throws ModelNotFoundException;

    public Store setStocks(String storeId, ProductQuantity stocks) throws ModelNotFoundException;

    public default Store setStocks(String storeId, Product product, int quantity) throws ModelNotFoundException {
        return this.setStocks(storeId, new ProductQuantity(product, quantity));
    }

    public Store addOrRemoveProductFromStock(String storeId, Product productToRemove, int quantity) throws ModelNotFoundException;

}
