package tech.brtrndb.airproject.flightplan.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DeliveryDTO(
        @JsonProperty("OrderId")
        String orderId,

        @JsonProperty("DroneId")
        String droneId,

        @JsonProperty("StoreId")
        String storeId,

        @JsonProperty("ProductId")
        String productId,

        @JsonProperty("CustomerId")
        String customerId
) {
}
