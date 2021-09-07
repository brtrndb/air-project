package tech.brtrndb.airproject.flightplan.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tech.brtrndb.airproject.flightplan.domain.Drone;
import tech.brtrndb.airproject.flightplan.domain.Position;
import tech.brtrndb.airproject.flightplan.error.ModelNotFoundException;
import tech.brtrndb.airproject.flightplan.error.NotEnoughFuelException;
import tech.brtrndb.airproject.flightplan.persistence.entity.DroneEntity;
import tech.brtrndb.airproject.flightplan.persistence.repository.DroneRepository;
import tech.brtrndb.airproject.flightplan.service.mapper.DroneMapper;
import tech.brtrndb.airproject.flightplan.util.GeoUtils;
import tech.brtrndb.airproject.flightplan.util.ValidationUtils;

@Slf4j
@Service
@Transactional
public class DroneServiceImpl implements DroneService {

    private static final String DRONE_ID_IS_NULL = "Drone id is null";
    private static final String DRONE_ID_IS_EMPTY = "Drone id is blank or empty";
    private static final String DRONE_POSITION_IS_NULL = "Drone position is null";
    private static final String POSITION_IS_NULL = "Position is null";

    private final DroneRepository repository;

    public DroneServiceImpl(DroneRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Drone> getAll() {
        List<DroneEntity> entities = this.repository.findAll();
        List<Drone> models = DroneMapper.toModels(entities);

        log.trace("Got {} drone(s).", models.size());

        return models;
    }

    @Override
    public Optional<Drone> find(String droneId) {
        ValidationUtils.requireNonNullBlankEmptyString(droneId, DRONE_ID_IS_NULL, DRONE_ID_IS_EMPTY);

        Optional<DroneEntity> optional = this.repository.findByDroneId(droneId);

        Optional<Drone> optionalModel = optional.map(DroneMapper::toModel);

        optionalModel.ifPresentOrElse(
                d -> log.trace("Drone [{}] found.", d.getDroneId()),
                () -> log.trace("Drone [{}] not found.", droneId)
        );

        return optionalModel;
    }

    @Override
    public Drone deploy(String droneId, Position position) {
        ValidationUtils.requireNonNullBlankEmptyString(droneId, DRONE_ID_IS_NULL, DRONE_ID_IS_EMPTY);
        Objects.requireNonNull(position, DRONE_POSITION_IS_NULL);

        Drone model = new Drone(droneId, position);
        DroneEntity entity = DroneMapper.toNewEntity(model);
        entity = this.repository.saveAndFlush(entity);
        model = DroneMapper.toModel(entity);

        log.trace("Drone [{}] deployed at ({}, {}).", model.getDroneId(), model.getPosX(), model.getPosY());

        return model;
    }

    @Override
    public Drone move(String droneId, Position target) throws ModelNotFoundException, NotEnoughFuelException {
        ValidationUtils.requireNonNullBlankEmptyString(droneId, DRONE_ID_IS_NULL, DRONE_ID_IS_EMPTY);
        Objects.requireNonNull(target, POSITION_IS_NULL);

        Drone drone = this.get(droneId);
        Position currentPosition = drone.getPosition();
        double remainingFuel = drone.getFuel();
        double requiredFuel = GeoUtils.distance(currentPosition, target);

        if (!drone.hasEnoughFuel(requiredFuel)) {
            throw new NotEnoughFuelException(droneId, drone.getFuel(), requiredFuel);
        }

        drone.setPosition(target);
        drone.setFuel(remainingFuel - requiredFuel);

        DroneEntity entity = DroneMapper.toEntity(drone);
        entity = this.repository.saveAndFlush(entity);
        drone = DroneMapper.toModel(entity);

        log.trace("Drone [{}] moved from {} to {} and consumed {} fuel (remaining {}).", droneId, currentPosition, target, requiredFuel, drone.getFuel());

        return drone;
    }

    @Override
    public Optional<Drone> findClosestTo(Position position) {
        Objects.requireNonNull(position, POSITION_IS_NULL);

        List<Drone> drones = this.getAll();

        if (drones.isEmpty()) {
            log.trace("No drone available.");
            return Optional.empty();
        }

        drones.sort(GeoUtils.closestLocalizableSorter(position));

        Drone closest = drones.get(0);

        log.trace("Closest drone to {} is [{}].", position, closest.getDroneId());

        return Optional.of(drones.get(0));
    }

}
