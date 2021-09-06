package tech.brtrndb.airproject.flightplan.service.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import tech.brtrndb.airproject.flightplan.domain.Position;
import tech.brtrndb.airproject.flightplan.persistence.entity.PositionEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PositionMapper {

    public static Position toModel(PositionEntity entity) {
        return Position.of(entity.getPosX(), entity.getPosY());
    }

    public static PositionEntity toEntity(Position model) {
        return PositionEntity.of(model.x(), model.y());
    }

}
