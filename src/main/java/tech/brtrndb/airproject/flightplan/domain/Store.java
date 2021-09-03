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
public class Store extends LocalizableModel {

    @Getter
    @Setter(AccessLevel.PRIVATE)
    @NonNull
    private String storeId;

    @Getter
    @Setter(AccessLevel.PRIVATE)
    @NonNull
    private ProductQuantity productStocks;

    public Store(String storeId, Position position) {
        this(storeId, position, new ProductQuantity());
    }

    public Store(String storeId, Position position, ProductQuantity productStocks) {
        super(position);
        this.setStoreId(storeId);
        this.setProductStocks(productStocks);
    }

    public Store(UUID id, CreationInfo creationInfo, String storeId, Position position, ProductQuantity productStocks) {
        super(id, creationInfo, position);
        this.setStoreId(storeId);
        this.setProductStocks(productStocks);
    }

}
