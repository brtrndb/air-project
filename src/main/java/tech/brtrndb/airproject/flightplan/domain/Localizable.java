package tech.brtrndb.airproject.flightplan.domain;

/**
 * Represents an object with a {@link Position}.
 */
public interface Localizable {

    public static final Position ORIGIN = Position.of(0d, 0d);

    public Position getPosition();

    public default double getPosX() {
        return this.getPosition().x();
    }

    public default double getPosY() {
        return this.getPosition().y();
    }

}
