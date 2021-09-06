package tech.brtrndb.airproject.flightplan.persistence.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

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
@MappedSuperclass
public abstract class LocalizableModelEntity extends ModelEntity {

    @NonNull
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "pos_x", column = @Column(name = "pos_x")),
            @AttributeOverride(name = "pos_y", column = @Column(name = "pos_y"))
    })
    private PositionEntity position;

}
