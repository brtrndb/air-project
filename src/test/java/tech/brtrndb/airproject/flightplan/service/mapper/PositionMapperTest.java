package tech.brtrndb.airproject.flightplan.service.mapper;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import tech.brtrndb.airproject.flightplan.domain.Position;
import tech.brtrndb.airproject.flightplan.fixture.PositionFixtures;
import tech.brtrndb.airproject.flightplan.persistence.entity.PositionEntity;

@Slf4j
public class PositionMapperTest extends BaseMapperTest<Position, PositionEntity> {

    private static final Map.Entry<PositionEntity, Position> matching = PositionFixtures.randomMatching();

    public PositionMapperTest() {
        super(
                PositionMapper::toEntity,
                PositionMapper::toModel,
                matching::getValue,
                matching::getKey
        );
    }

}
