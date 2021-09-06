package tech.brtrndb.airproject.flightplan.persistence.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity(name = "order")
@Table(schema = "svc_flightplan", name = "order")
public class OrderEntity extends ModelEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -8009808372385413556L;

    @NonNull
    @Column(name = "order_id", nullable = false, updatable = false, unique = true)
    private String orderId;

    @NonNull
    @Column(name = "customer_id", nullable = false, updatable = false, unique = true)
    private String customerId;

    @NonNull
    @OneToMany(targetEntity = OrderItemEntity.class, cascade = {CascadeType.PERSIST}, orphanRemoval = true)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    @ToString.Exclude
    private Set<OrderItemEntity> orderItems = new HashSet<>();

}
