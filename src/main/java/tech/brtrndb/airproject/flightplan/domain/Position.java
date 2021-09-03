package tech.brtrndb.airproject.flightplan.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@AllArgsConstructor(staticName = "of")
public final class Position {

    private double x;
    private double y;

    public double x() {
        return this.x;
    }

    public void x(double x) {
        this.x = x;
    }

    public double y() {
        return this.y;
    }

    public void y(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "(%s, %s)".formatted(this.x, this.y);
    }

}
