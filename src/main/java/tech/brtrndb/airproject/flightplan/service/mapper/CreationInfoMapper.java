package tech.brtrndb.airproject.flightplan.service.mapper;

import tech.brtrndb.airproject.flightplan.domain.CreationInfo;
import tech.brtrndb.airproject.flightplan.persistence.entity.CreationInfoEntity;

public final class CreationInfoMapper {

    public static CreationInfo toModel(CreationInfoEntity entity) {
        return CreationInfo.of(entity.getCreatedAt(), entity.getUpdatedAt());
    }

    public static CreationInfoEntity toEntity(CreationInfo model) {
        return CreationInfoEntity.of(model.createdAt(), model.updatedAt());
    }

}
