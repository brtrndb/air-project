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
public class Drone extends LocalizableModel {

    public static final double INITIAL_FUEL_CAPACITY = 100d;

    @Getter
    @Setter(AccessLevel.PRIVATE)
    @NonNull
    private String droneId;

    @Getter
    private double fuel;

    public Drone(String droneId, Position position) {
        this(droneId, position, INITIAL_FUEL_CAPACITY);
    }

    public Drone(String droneId, Position position, double fuel) {
        super(position);
        this.setDroneId(droneId);
        this.setFuel(fuel);
    }

    public Drone(UUID id, CreationInfo creationInfo, String droneId, Position position, double fuel) {
        super(id, creationInfo, position);
        this.setDroneId(droneId);
        this.setFuel(fuel);
    }

    public void setFuel(double fuel) {
        this.fuel = Math.max(fuel, 0d);
    }

    public boolean hasEnoughFuel(double requiredFuel) {
        return requiredFuel <= this.fuel;
    }

}
