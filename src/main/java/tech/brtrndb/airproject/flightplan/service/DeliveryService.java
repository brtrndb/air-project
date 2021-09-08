package tech.brtrndb.airproject.flightplan.service;

import java.util.List;

import tech.brtrndb.airproject.flightplan.domain.Delivery;
import tech.brtrndb.airproject.flightplan.domain.Order;
import tech.brtrndb.airproject.flightplan.error.DeliveryCannotBeSatisfiedException;

public interface DeliveryService {

    public List<Delivery> generateDeliveries() throws DeliveryCannotBeSatisfiedException;

    public List<Delivery> generateDeliveries(Order order) throws DeliveryCannotBeSatisfiedException;

}
