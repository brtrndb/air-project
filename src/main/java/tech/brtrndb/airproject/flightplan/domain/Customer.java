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
public class Customer extends LocalizableModel {

    @Getter
    @Setter(AccessLevel.PRIVATE)
    @NonNull
    private String customerId;

    public Customer(String customerId, Position position) {
        super(position);
        this.setCustomerId(customerId);
    }

    public Customer(UUID id, CreationInfo creationInfo, String customerId, Position position) {
        super(id, creationInfo, position);
        this.setCustomerId(customerId);
    }

}
