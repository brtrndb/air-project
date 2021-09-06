package tech.brtrndb.airproject.flightplan.util;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import tech.brtrndb.airproject.flightplan.BaseTest;
import tech.brtrndb.airproject.flightplan.domain.Customer;
import tech.brtrndb.airproject.flightplan.domain.Localizable;
import tech.brtrndb.airproject.flightplan.domain.Position;
import tech.brtrndb.airproject.flightplan.fixture.CustomerFixtures;
import tech.brtrndb.airproject.flightplan.fixture.PositionFixtures;

@Slf4j
public class GeoUtilsTest extends BaseTest {

    @ParameterizedTest
    @MethodSource("generate_positions_and_distance")
    public void compute_distance_between_two_positions(Position a, Position b, double expectedDistance) {
        // Given:
        // When:
        double distance = GeoUtils.distance(a, b);

        // Then:
        Assertions.assertThat(distance).isEqualTo(expectedDistance);
    }

    @Test
    public void compute_distance_of_path() {
        // Given:
        List<Position> positions = List.of(
                Position.of(0d, 0d),
                Position.of(1d, 0d),
                Position.of(1d, 1d),
                Position.of(0d, 1d),
                Position.of(0d, 0d),
                Position.of(-2d, 0d)
        );
        double expectedDistance = 6d;

        // When:
        double distance = GeoUtils.distance(positions);

        // Then:
        Assertions.assertThat(distance).isEqualTo(expectedDistance);
    }

    @Test
    public void no_distance_for_path_empty() {
        // Given:
        List<Position> positions = Collections.emptyList();

        // When:
        double distance = GeoUtils.distance(positions);

        // Then:
        Assertions.assertThat(distance).isEqualTo(0d);
    }

    @Test
    public void no_distance_for_path_of_single_value() {
        // Given:
        List<Position> positions = Collections.singletonList(PositionFixtures.ORIGIN);

        // When:
        double distance = GeoUtils.distance(positions);

        // Then:
        Assertions.assertThat(distance).isEqualTo(0d);
    }

    @Test
    public void get_closest_position() {
        // Given:
        Position position = PositionFixtures.ORIGIN;
        Position expectedClosest = Position.of(1d, 1d);
        List<Position> positions = List.of(
                Position.of(0d, 10d),
                expectedClosest,
                Position.of(10d, 10d)
        );

        // When:
        Position closest = GeoUtils.getClosestPosition(position, positions);

        // Then:
        Assertions.assertThat(closest).isEqualTo(expectedClosest);
    }

    @Test
    public void get_closest_position_when_single_position_in_list() {
        // Given:
        Position position = PositionFixtures.ORIGIN;
        Position single = PositionFixtures.randomPosition();
        List<Position> positions = Collections.singletonList(single);

        // When:
        Position closest = GeoUtils.getClosestPosition(position, positions);

        // Then:
        Assertions.assertThat(closest).isEqualTo(single);
    }

    @Test
    public void no_closest_position_when_positions_list_empty() {
        // Given:
        Position position = PositionFixtures.ORIGIN;
        List<Position> positions = Collections.emptyList();

        // When:
        // Then:
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> GeoUtils.getClosestPosition(position, positions));
    }

    @Test
    public void get_closest_localizable() {
        // Given:
        Position position = PositionFixtures.ORIGIN;
        Localizable expectedClosest = new Customer(CustomerFixtures.randomCustomerId(), PositionFixtures.ORIGIN);
        List<Localizable> positions = List.of(
                expectedClosest,
                new Customer(CustomerFixtures.randomCustomerId(), Position.of(0d, 10d)),
                new Customer(CustomerFixtures.randomCustomerId(), Position.of(10d, 10d))
        );

        // When:
        Localizable closest = GeoUtils.getClosestLocalizable(position, positions);

        // Then:
        Assertions.assertThat(closest).isEqualTo(expectedClosest);
    }

    @Test
    public void get_closest_localizable_when_single_localizable_in_list() {
        // Given:
        Position position = PositionFixtures.ORIGIN;
        Localizable single = new Customer(CustomerFixtures.randomCustomerId(), PositionFixtures.ORIGIN);
        List<Localizable> localizables = Collections.singletonList(single);

        // When:
        Localizable closest = GeoUtils.getClosestLocalizable(position, localizables);

        // Then:
        Assertions.assertThat(closest).isEqualTo(single);
    }

    @Test
    public void no_closest_localizable_when_localizables_list_empty() {
        // Given:
        Position position = PositionFixtures.ORIGIN;
        List<Localizable> localizables = Collections.emptyList();

        // When:
        // Then:
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> GeoUtils.getClosestLocalizable(position, localizables));
    }

    @Test
    public void sort_positions_from_closest_to_farthest() {
        // Given:
        Position origin = PositionFixtures.ORIGIN;
        Position p1 = Position.of(0, 1);
        Position p2 = Position.of(0, 2);
        Position p3 = Position.of(0, 3);
        Position p4 = Position.of(0, 4);
        List<Position> sorted = List.of(p1, p2, p3, p4);
        List<Position> unsorted = Stream.of(p2, p4, p3, p1).collect(Collectors.toList());

        // When:
        unsorted.sort(GeoUtils.closestPositionSorter(origin));

        // Then:
        Assertions.assertThat(unsorted)
                .isEqualTo(sorted);
    }

    public static Stream<Arguments> generate_positions_and_distance() {
        return Stream.of(
                Arguments.of(Position.of(0d, 0d), Position.of(0d, 0d), 0d),
                Arguments.of(Position.of(0d, 0d), Position.of(2d, 0d), 2d),
                Arguments.of(Position.of(0d, 0d), Position.of(0d, 3d), 3d),
                Arguments.of(Position.of(0d, 0d), Position.of(1d, 1d), Math.sqrt(2d)),
                Arguments.of(Position.of(0d, 0d), Position.of(-1d, -1d), Math.sqrt(2d)),
                Arguments.of(Position.of(3d, 4d), Position.of(7d, 1d), 5d)
        );
    }

}
