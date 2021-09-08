package tech.brtrndb.airproject.flightplan.error;

import java.io.Serial;

public class DeliveryCannotBeSatisfiedException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -1386895810499766481L;

    public DeliveryCannotBeSatisfiedException(String message) {
        super(message);
    }

}
