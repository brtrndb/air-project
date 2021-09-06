package tech.brtrndb.airproject.flightplan.fixture;

import java.util.Map;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import tech.brtrndb.airproject.flightplan.domain.Position;
import tech.brtrndb.airproject.flightplan.persistence.entity.PositionEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PositionFixtures {

    public static final double MIN_X = 0d;
    public static final double MIN_Y = 0d;
    public static final double MAX_X = 100d;
    public static final double MAX_Y = 100d;
    public static final Position ORIGIN = Position.of(MIN_X, MIN_Y);
    public static final Position X0Y1 = Position.of(MIN_X, 1d);
    public static final Position VERY_FAR = Position.of(1000d, 1000d);

    public static double randomX() {
        return DataFixtures.randomDouble(MIN_X, MAX_X);
    }

    public static double randomY() {
        return DataFixtures.randomDouble(MIN_Y, MAX_Y);
    }

    public static PositionEntity randomPositionEntity() {
        return PositionEntity.of(randomX(), randomY());
    }

    public static Position randomPosition() {
        return Position.of(randomX(), randomY());
    }

    public static Map.Entry<PositionEntity, Position> randomMatching() {
        double x = PositionFixtures.randomX();
        double y = PositionFixtures.randomY();

        PositionEntity entity = PositionEntity.of(x, y);
        Position model = Position.of(x, y);

        return Map.entry(entity, model);
    }

}
