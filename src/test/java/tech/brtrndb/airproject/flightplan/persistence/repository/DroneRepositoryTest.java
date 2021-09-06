package tech.brtrndb.airproject.flightplan.persistence.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import tech.brtrndb.airproject.flightplan.FlightPlanIntegrationTest;
import tech.brtrndb.airproject.flightplan.fixture.DroneFixtures;
import tech.brtrndb.airproject.flightplan.persistence.entity.DroneEntity;
import tech.brtrndb.airproject.flightplan.fixture.DataFixtures;

@Slf4j
@FlightPlanIntegrationTest
public class DroneRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private DroneRepository repository;

    private DroneEntity save() {
        DroneEntity entity = DroneFixtures.randomDroneEntity();
        entity = this.repository.saveAndFlush(entity);
        return entity;
    }

    private List<DroneEntity> save(int nb) {
        return DataFixtures.generate(nb, this::save);
    }

    @Test
    public void save_drone() {
        // Given:
        DroneEntity entity = DroneFixtures.randomDroneEntity();

        // When:
        DroneEntity created = this.repository.saveAndFlush(entity);
        DroneEntity got = this.repository.getById(created.getId());

        // Then:
        Assertions.assertThat(created).isNotNull();
        Assertions.assertThat(created.getId()).isNotNull();
        Assertions.assertThat(created.getCreationInfo())
                .isNotNull()
                .satisfies(creationInfo -> {
                    Assertions.assertThat(creationInfo.getCreatedAt()).isNotNull();
                    Assertions.assertThat(creationInfo.getUpdatedAt()).isEqualTo(creationInfo.getUpdatedAt());
                });
        Assertions.assertThat(created.getPosition())
                .isNotNull()
                .satisfies(position -> {
                    Assertions.assertThat(position.getPosX()).isNotNull();
                    Assertions.assertThat(position.getPosY()).isNotNull();
                });
        Assertions.assertThat(got.getDroneId()).isEqualTo(entity.getDroneId());
        Assertions.assertThat(got.getFuel()).isEqualTo(entity.getFuel());
    }

    @Test
    public void find_drone_by_droneId() {
        // Given:
        DroneEntity entity = this.save();
        String droneId = entity.getDroneId();

        // When:
        Optional<DroneEntity> optional = this.repository.findByDroneId(droneId);

        // Then:
        Assertions.assertThat(optional)
                .isNotEmpty()
                .contains(entity);
    }

    @Test
    public void dont_find_drone_by_droneId_given_droneId_non_existent() {
        // Given:
        DroneEntity entity = this.save();
        String nonExistentDroneId = DroneFixtures.randomDroneId();

        // When:
        Optional<DroneEntity> optional = this.repository.findByDroneId(nonExistentDroneId);

        // Then:
        Assertions.assertThat(optional)
                .isEmpty();
    }

    @Test
    public void find_all_by_fuel_greater_or_equal() {
        // Given:
        List<DroneEntity> entitiesLower = this.save(3);
        List<DroneEntity> entitiesEqual = this.save(3);
        List<DroneEntity> entitiesGreater = this.save(3);
        entitiesLower.forEach(e -> e.setFuel(10d));
        entitiesEqual.forEach(e -> e.setFuel(20d));
        entitiesGreater.forEach(e -> e.setFuel(30d));
        double fuelRequested = 20d;
        List<DroneEntity> expected = Stream.of(entitiesEqual, entitiesGreater).flatMap(Collection::stream).toList();

        // When:
        List<DroneEntity> got = this.repository.findAllByFuelGreaterThanEqual(fuelRequested);

        // Then:
        Assertions.assertThat(got)
                .isNotNull()
                .hasSameSizeAs(expected)
                .containsExactlyInAnyOrderElementsOf(expected);
    }

}
