package tech.brtrndb.airproject.flightplan.api.dto;

import java.time.Instant;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ApiErrorDTO(
        @JsonProperty("id")
        UUID id,

        @JsonProperty("timestamp")
        Instant timestamp,

        @JsonProperty("httpCode")
        int httpCode,

        @JsonProperty("message")
        String message
) {

    public ApiErrorDTO(int httpCode, String message) {
        this(UUID.randomUUID(), Instant.now(), httpCode, message);
    }

}
