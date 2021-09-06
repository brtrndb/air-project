package tech.brtrndb.airproject.flightplan.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import tech.brtrndb.airproject.flightplan.persistence.entity.DroneEntity;

@Repository
public interface DroneRepository extends ModelRepository<DroneEntity> {

    public Optional<DroneEntity> findByDroneId(String droneId);

    public List<DroneEntity> findAllByFuelGreaterThanEqual(Double fuel);

}
