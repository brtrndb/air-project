package tech.brtrndb.airproject.flightplan.service.mapper;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import tech.brtrndb.airproject.flightplan.domain.Drone;
import tech.brtrndb.airproject.flightplan.fixture.DroneFixtures;
import tech.brtrndb.airproject.flightplan.persistence.entity.DroneEntity;

@Slf4j
public class DroneMapperTest extends BaseMapperTest<Drone, DroneEntity> {

    private static final Map.Entry<DroneEntity, Drone> matching = DroneFixtures.randomMatching();

    public DroneMapperTest() {
        super(
                DroneMapper::toEntity,
                DroneMapper::toModel,
                matching::getValue,
                matching::getKey
        );
    }

}
