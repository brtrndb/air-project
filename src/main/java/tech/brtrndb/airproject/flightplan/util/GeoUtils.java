package tech.brtrndb.airproject.flightplan.util;

import java.util.Comparator;
import java.util.List;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import tech.brtrndb.airproject.flightplan.domain.Localizable;
import tech.brtrndb.airproject.flightplan.domain.Position;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GeoUtils {

    public static double distance(Localizable l0, Localizable l1) {
        return GeoUtils.distance(l0.getPosition(), l1.getPosition());
    }

    public static double distance(Position p0, Position p1) {
        if (p0.equals(p1))
            return 0d;

        double xBxA = p1.x() - p0.x();
        double yByA = p1.y() - p0.y();

        return Math.hypot(xBxA, yByA);
    }

    public static double distance(List<Position> path) {
        if (path.size() <= 1)
            return 0d;

        return GeoUtils.distance(path.get(0), path.get(1)) + GeoUtils.distance(path.subList(1, path.size()));
    }

    public static Position getClosestPosition(Position position, List<Position> positions) {
        if (positions.isEmpty())
            throw new IllegalArgumentException("positions list is empty");

        if (positions.size() == 1)
            return positions.get(0);

        return positions.stream()
                .sorted(closestPositionSorter(position))
                .toList()
                .get(0);
    }

    public static <T extends Localizable> T getClosestLocalizable(Position position, List<T> localizables) {
        if (localizables.isEmpty())
            throw new IllegalArgumentException("localizable list is empty");

        if (localizables.size() == 1)
            return localizables.get(0);

        return localizables.stream()
                .sorted(closestLocalizableSorter(position))
                .toList()
                .get(0);
    }

    public static Comparator<Localizable> closestLocalizableSorter(Position position) {
        return Comparator.comparingDouble(l -> distance(position, l.getPosition()));
    }

    public static Comparator<Position> closestPositionSorter(Position position) {
        return Comparator.comparingDouble(p -> distance(position, p));
    }

}
