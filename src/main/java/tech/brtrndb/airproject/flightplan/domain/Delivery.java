package tech.brtrndb.airproject.flightplan.domain;

import lombok.NonNull;

public record Delivery(
        @NonNull Order order,
        @NonNull Drone drone,
        @NonNull Store store,
        @NonNull Product product,
        @NonNull Customer customer
) {
}
