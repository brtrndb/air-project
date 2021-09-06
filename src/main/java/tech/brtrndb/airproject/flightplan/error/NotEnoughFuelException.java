package tech.brtrndb.airproject.flightplan.error;

import java.io.Serial;

import lombok.Getter;

@Getter
public class NotEnoughFuelException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 8084486393841158539L;

    private final String droneId;
    private final double capacity;
    private final double requested;

    public NotEnoughFuelException(String droneId, double capacity, double requested) {
        super();
        this.droneId = droneId;
        this.capacity = capacity;
        this.requested = requested;
    }

    @Override
    public String getMessage() {
        return "Drone [%s] can rum distance of %s, available capacity is %s".formatted(this.droneId, this.requested, this.capacity);
    }

}
