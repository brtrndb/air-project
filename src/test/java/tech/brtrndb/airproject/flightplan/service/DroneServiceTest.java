package tech.brtrndb.airproject.flightplan.service;

import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import tech.brtrndb.airproject.flightplan.FlightPlanIntegrationTest;
import tech.brtrndb.airproject.flightplan.domain.Drone;
import tech.brtrndb.airproject.flightplan.domain.Position;
import tech.brtrndb.airproject.flightplan.error.ModelNotFoundException;
import tech.brtrndb.airproject.flightplan.error.NotEnoughFuelException;
import tech.brtrndb.airproject.flightplan.fixture.DroneFixtures;
import tech.brtrndb.airproject.flightplan.fixture.PositionFixtures;
import tech.brtrndb.airproject.flightplan.util.GeoUtils;

@Slf4j
@FlightPlanIntegrationTest
public class DroneServiceTest extends BaseServiceTest {

    @Autowired
    private DroneService service;

    private Drone create() {
        String droneId = DroneFixtures.randomDroneId();
        Position dronePosition = PositionFixtures.randomPosition();

        return this.service.deploy(droneId, dronePosition);
    }

    @Test
    public void should_get_all_drones() {
        // Given:
        Drone drone0 = this.create();
        Drone drone1 = this.create();
        Drone drone2 = this.create();
        List<Drone> all = List.of(drone0, drone1, drone2);

        // When:
        List<Drone> got = this.service.getAll();

        // Then:
        Assertions.assertThat(got)
                .hasSameSizeAs(all)
                .containsExactlyInAnyOrderElementsOf(all);
    }

    @Test
    public void should_find_drone_by_id() {
        // Given:
        Drone drone = this.create();

        // When:
        Optional<Drone> optional = this.service.find(drone.getDroneId());

        // Then:
        Assertions.assertThat(optional)
                .isNotEmpty()
                .contains(drone);
    }

    @Test
    public void should_not_find_drone_by_id() {
        // Given:
        String nonExistentId = DroneFixtures.randomDroneId();

        // When:
        Optional<Drone> optional = this.service.find(nonExistentId);

        // Then:
        Assertions.assertThat(optional).isEmpty();
    }

    @Test
    public void should_get_drone_by_id() {
        // Given:
        Drone drone = this.create();

        // When:
        Drone got = this.service.get(drone.getDroneId());

        // Then:
        Assertions.assertThat(got).isEqualTo(drone);
    }

    @Test
    public void should_not_get_drone_by_id() {
        // Given:
        String nonExistentId = DroneFixtures.randomDroneId();

        // When:
        // Then:
        Assertions.assertThatExceptionOfType(ModelNotFoundException.class)
                .isThrownBy(() -> this.service.get(nonExistentId));
    }

    @Test
    public void should_deploy_drone_at_position() {
        // Given:
        String droneId = DroneFixtures.randomDroneId();
        Position position = PositionFixtures.randomPosition();

        // When:
        Drone drone = this.service.deploy(droneId, position);

        // Then:
        Assertions.assertThat(drone.getDroneId()).isEqualTo(droneId);
        Assertions.assertThat(drone.getPosition()).isEqualTo(position);
    }

    @Test
    public void should_move_drone_to_position() {
        // Given:
        Drone drone = this.service.deploy(DroneFixtures.randomDroneId(), PositionFixtures.ORIGIN);
        Position newPosition = PositionFixtures.X0Y1;
        double distance = GeoUtils.distance(drone.getPosition(), newPosition);
        double expectedFuel = drone.getFuel() - distance;

        // When:
        Drone moved = this.service.move(drone.getDroneId(), newPosition);

        // Then:
        Assertions.assertThat(moved.getPosition()).isEqualTo(newPosition);
        Assertions.assertThat(moved.getFuel()).isEqualTo(expectedFuel);
    }

    @Test
    public void should_not_move_drone_to_position_if_not_enough_fuel() {
        // Given:
        Drone drone = this.service.deploy(DroneFixtures.randomDroneId(), PositionFixtures.ORIGIN);
        Position veryFar = PositionFixtures.VERY_FAR;

        // When:
        // Then:
        Assertions.assertThatExceptionOfType(NotEnoughFuelException.class)
                .isThrownBy(() -> this.service.move(drone.getDroneId(), veryFar));
    }

    @Test
    public void should_find_closest_drone_to_position() {
        // Given:
        Drone drone0 = this.service.deploy(DroneFixtures.randomDroneId(), PositionFixtures.ORIGIN);
        Drone drone1 = this.service.deploy(DroneFixtures.randomDroneId(), PositionFixtures.VERY_FAR);
        Drone drone2 = this.service.deploy(DroneFixtures.randomDroneId(), Position.of(10d, 10d));
        Position position = PositionFixtures.X0Y1;

        // When:
        Optional<Drone> optionalDrone = this.service.findClosestTo(position);

        // Then:
        Assertions.assertThat(optionalDrone)
                .isNotEmpty()
                .contains(drone0);
    }

    @Test
    public void should_not_find_closest_drone_to_position_if_no_drones() {
        // Given:
        Position position = PositionFixtures.X0Y1;

        // When:
        Optional<Drone> optionalDrone = this.service.findClosestTo(position);

        // Then:
        Assertions.assertThat(optionalDrone)
                .isEmpty();
    }

}
