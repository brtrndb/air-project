package tech.brtrndb.airproject.flightplan.persistence.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
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
@Entity(name = "store")
@Table(schema = "svc_flightplan", name = "store")
public class StoreEntity extends LocalizableModelEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 4966105359066380570L;

    @NonNull
    @Column(name = "store_id", nullable = false, updatable = false, unique = true)
    private String storeId;

    @NonNull
    @OneToMany(targetEntity = StockEntity.class, fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    @JoinColumn(name = "store_id", referencedColumnName = "store_id")
    @MapKeyColumn(name = "product_id")
    @ToString.Exclude
    private Map<String, StockEntity> productStocks = new HashMap<>();

}
