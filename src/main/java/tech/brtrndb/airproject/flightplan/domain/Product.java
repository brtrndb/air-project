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
public class Product extends Model {

    @Getter
    @Setter(AccessLevel.PRIVATE)
    @NonNull
    private String productId;

    @Getter
    @Setter(AccessLevel.PRIVATE)
    @NonNull
    private String name;

    public Product(String productId, String name) {
        super();
        this.setProductId(productId);
        this.setName(name);
    }

    public Product(UUID id, CreationInfo creationInfo, String productId, String name) {
        super(id, creationInfo);
        this.setProductId(productId);
        this.setName(name);
    }

}
