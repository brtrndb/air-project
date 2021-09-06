package tech.brtrndb.airproject.flightplan.persistence.entity;

import javax.persistence.Column;
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
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "product")
@Table(schema = "svc_flightplan", name = "product")
public class ProductEntity extends ModelEntity {

    @NonNull
    @Column(name = "product_id", nullable = false, updatable = false)
    private String productId;

    @NonNull
    @Column(name = "name", nullable = false)
    private String name;

}
