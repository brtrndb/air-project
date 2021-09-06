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
@Entity(name = "drone")
@Table(schema = "svc_flightplan", name = "drone")
public class DroneEntity extends LocalizableModelEntity {

    @NonNull
    @Column(name = "drone_id", nullable = false, updatable = false, unique = true)
    private String droneId;

    @NonNull
    @Column(name = "fuel", nullable = false)
    private Double fuel;

}
