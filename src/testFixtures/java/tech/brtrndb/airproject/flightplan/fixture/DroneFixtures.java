package tech.brtrndb.airproject.flightplan.fixture;

import java.util.Map;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import tech.brtrndb.airproject.flightplan.domain.CreationInfo;
import tech.brtrndb.airproject.flightplan.domain.Drone;
import tech.brtrndb.airproject.flightplan.domain.Position;
import tech.brtrndb.airproject.flightplan.persistence.entity.CreationInfoEntity;
import tech.brtrndb.airproject.flightplan.persistence.entity.DroneEntity;
import tech.brtrndb.airproject.flightplan.persistence.entity.PositionEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DroneFixtures {

    public static String randomDroneId() {
        return DataFixtures.randomString("DRONE");
    }

    public static double randomFuel() {
        return DataFixtures.randomDouble(0d, Drone.INITIAL_FUEL_CAPACITY);
    }

    public static DroneEntity randomDroneEntity() {
        return DataFixtures.build(() -> {
            DroneEntity entity = new DroneEntity();
            entity.setDroneId(randomDroneId());
            entity.setPosition(PositionFixtures.randomPositionEntity());
            entity.setFuel(randomFuel());

            return entity;
        });
    }

    public static Drone randomDrone() {
        return DataFixtures.build(() -> new Drone(
                randomDroneId(),
                PositionFixtures.randomPosition(),
                randomFuel()
        ));
    }

    public static Map.Entry<DroneEntity, Drone> randomMatching() {
        UUID id = CreationInfoFixtures.randomId();
        Map.Entry<CreationInfoEntity, CreationInfo> matchingCreationInfo = CreationInfoFixtures.randomMatching();
        String droneId = randomDroneId();
        Map.Entry<PositionEntity, Position> matchingPosition = PositionFixtures.randomMatching();
        double fuel = randomFuel();

        DroneEntity entity = new DroneEntity();
        entity.setId(id);
        entity.setCreationInfo(matchingCreationInfo.getKey());
        entity.setDroneId(droneId);
        entity.setPosition(matchingPosition.getKey());
        entity.setFuel(fuel);

        Drone model = new Drone(id, matchingCreationInfo.getValue(), droneId, matchingPosition.getValue(), fuel);

        return Map.entry(entity, model);
    }

}
