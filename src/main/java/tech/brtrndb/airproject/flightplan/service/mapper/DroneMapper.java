package tech.brtrndb.airproject.flightplan.service.mapper;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import tech.brtrndb.airproject.flightplan.domain.Drone;
import tech.brtrndb.airproject.flightplan.persistence.entity.DroneEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DroneMapper {

    public static Drone toModel(DroneEntity entity) {
        return new Drone(
                entity.getId(),
                CreationInfoMapper.toModel(entity.getCreationInfo()),
                entity.getDroneId(),
                PositionMapper.toModel(entity.getPosition()),
                entity.getFuel()
        );
    }

    public static DroneEntity toEntity(Drone model) {
        return toEntity(model, false);
    }

    public static DroneEntity toNewEntity(Drone model) {
        return toEntity(model, true);
    }

    private static DroneEntity toEntity(Drone model, boolean ignoreCreationFields) {
        DroneEntity entity = new DroneEntity();

        if (!ignoreCreationFields) {
            entity.setId(model.getId());
            entity.setCreationInfo(CreationInfoMapper.toEntity(model.getCreationInfo()));
        }
        entity.setDroneId(model.getDroneId());
        entity.setPosition(PositionMapper.toEntity(model.getPosition()));
        entity.setFuel(model.getFuel());

        return entity;
    }

    public static List<Drone> toModels(List<DroneEntity> entities) {
        return entities.stream()
                .map(DroneMapper::toModel)
                .collect(Collectors.toList());
    }

}
