package tech.brtrndb.airproject.flightplan.persistence.entity;

import java.io.Serial;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode
@NoArgsConstructor
@Entity(name = "order_item")
@Table(schema = "svc_flightplan", name = "order_item")
public class OrderItemEntity {

    @EmbeddedId
    private OrderItemId orderItemId;

    @NonNull
    @Column(name = "quantity", nullable = false, updatable = false)
    private Integer quantity;

    public OrderItemEntity(String orderId, String productId, Integer quantity) {
        this.orderItemId = OrderItemId.of(orderId, productId);
        this.quantity = quantity;
    }

    public String getOrderId() {
        return this.orderItemId.getOrderId();
    }

    public String getProductId() {
        return this.orderItemId.getProductId();
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor(staticName = "of")
    @ToString
    @Embeddable
    public static class OrderItemId implements Serializable {

        @Serial
        private static final long serialVersionUID = -3496878217514846406L;

        @NonNull
        @Column(name = "order_id", nullable = false, updatable = false)
        private String orderId;

        @NonNull
        @Column(name = "product_id", nullable = false, updatable = false)
        private String productId;

    }

}
