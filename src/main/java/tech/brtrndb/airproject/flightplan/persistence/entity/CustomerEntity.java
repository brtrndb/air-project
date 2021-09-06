package tech.brtrndb.airproject.flightplan.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Entity(name = "customer")
@Table(schema = "svc_flightplan", name = "customer")
public class CustomerEntity extends LocalizableModelEntity {

    @NonNull
    @Column(name = "customer_id", nullable = false, updatable = false)
    private String customerId;

}
