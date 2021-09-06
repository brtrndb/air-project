package tech.brtrndb.airproject.flightplan.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Embeddable
public class PositionEntity {

    @Column(name = "pos_x", nullable = false)
    private double posX;

    @Column(name = "pos_y", nullable = false)
    private double posY;

}
