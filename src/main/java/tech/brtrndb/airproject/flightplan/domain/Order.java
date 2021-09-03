package tech.brtrndb.airproject.flightplan.domain;

import java.util.UUID;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Order extends Model {

    @Getter
    @Setter(AccessLevel.PRIVATE)
    @NonNull
    private String orderId;

    @Getter
    @Setter(AccessLevel.PRIVATE)
    @NonNull
    private String customerId;

    @Getter
    @Setter(AccessLevel.PRIVATE)
    @NonNull
    private ProductQuantity itemsQuantity;

    public Order(String orderId, String customerId) {
        this(orderId, customerId, new ProductQuantity());
    }

    public Order(String orderId, String customerId, ProductQuantity itemsQuantity) {
        super();
        this.setOrderId(orderId);
        this.setCustomerId(customerId);
        this.setItemsQuantity(itemsQuantity);
    }

    public Order(UUID id, CreationInfo creationInfo, String orderId, String customerId, ProductQuantity itemsQuantity) {
        super(id, creationInfo);
        this.setOrderId(orderId);
        this.setCustomerId(customerId);
        this.setItemsQuantity(itemsQuantity);
    }

    public void addItem(Product product, int quantity) {
        this.itemsQuantity.setProductQuantity(product.getProductId(), quantity);
    }

    public int getProductCount() {
        return this.itemsQuantity.getAllProductQuantity().size();
    }

}
