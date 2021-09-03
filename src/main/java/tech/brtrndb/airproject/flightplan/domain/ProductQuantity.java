package tech.brtrndb.airproject.flightplan.domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import tech.brtrndb.airproject.flightplan.util.ValidationUtils;

@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class ProductQuantity implements Iterable<Map.Entry<String, Integer>> {

    /**
     * Map of (productId -> productQuantity).
     */
    @NonNull
    private final Map<String, Integer> productQuantity = new HashMap<>();

    public ProductQuantity(Product product, int quantity) {
        this(Map.of(product.getProductId(), quantity));
    }

    public ProductQuantity(Map<String, Integer> productQuantity) {
        this();
        if (!productQuantity.isEmpty())
            productQuantity.forEach(this::setProductQuantity);
    }

    public void setProductQuantity(Product product, int quantity) {
        this.setProductQuantity(product.getProductId(), quantity);
    }

    public void setProductQuantity(String productId, int quantity) throws IllegalArgumentException {
        ValidationUtils.requireNonNullBlankEmptyString(productId, "Product id is null", "Product id is empty");

        if (quantity < 0)
            throw new IllegalArgumentException("Product quantity cannot be negative");

        this.productQuantity.put(productId, quantity);
    }

    public int getProductQuantity(Product product) {
        return this.getProductQuantity(product.getProductId());
    }

    public int getProductQuantity(String productId) {
        return this.productQuantity.getOrDefault(productId, 0);
    }

    public Map<String, Integer> getAllProductQuantity() {
        return Collections.unmodifiableMap(this.productQuantity);
    }

    @NotNull
    @Override
    public Iterator<Map.Entry<String, Integer>> iterator() {
        return this.getAllProductQuantity().entrySet().iterator();
    }

}
