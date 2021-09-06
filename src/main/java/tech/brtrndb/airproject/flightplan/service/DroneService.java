package tech.brtrndb.airproject.flightplan.service;

import java.util.List;
import java.util.Optional;

import tech.brtrndb.airproject.flightplan.domain.Drone;
import tech.brtrndb.airproject.flightplan.domain.Localizable;
import tech.brtrndb.airproject.flightplan.domain.Position;
import tech.brtrndb.airproject.flightplan.error.ModelNotFoundException;
import tech.brtrndb.airproject.flightplan.error.NotEnoughFuelException;

public interface DroneService {

    public List<Drone> getAll();

    public Optional<Drone> find(String droneId);

    public default Drone get(String droneId) throws ModelNotFoundException {
        return this.find(droneId).orElseThrow(() -> new ModelNotFoundException(Drone.class, droneId));
    }

    public Drone deploy(String droneId, Position position);

    public Drone move(String droneId, Position target) throws ModelNotFoundException, NotEnoughFuelException;

    public default Drone move(String droneId, Localizable target) throws ModelNotFoundException, NotEnoughFuelException {
        return this.move(droneId, target.getPosition());
    }

    public Optional<Drone> findClosestTo(Position position);

    public default Optional<Drone> findClosestTo(Localizable localizable) {
        return this.findClosestTo(localizable.getPosition());
    }

}
