package tech.brtrndb.airproject.flightplan.domain;

import java.time.Instant;

import lombok.NonNull;

public record CreationInfo(
        @NonNull Instant createdAt,
        @NonNull Instant updatedAt
) {

    public static final CreationInfo EPOCH = new CreationInfo(Instant.EPOCH, Instant.EPOCH);

    public static CreationInfo of(Instant createdAt, Instant updatedAt) {
        return new CreationInfo(createdAt, updatedAt);
    }

}
