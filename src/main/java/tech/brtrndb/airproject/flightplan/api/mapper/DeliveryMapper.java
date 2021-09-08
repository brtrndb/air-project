package tech.brtrndb.airproject.flightplan.api.mapper;

import java.util.List;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import tech.brtrndb.airproject.flightplan.api.dto.DeliveryDTO;
import tech.brtrndb.airproject.flightplan.domain.Delivery;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DeliveryMapper {

    public static DeliveryDTO toDTO(Delivery model) {
        return new DeliveryDTO(
                model.order().getOrderId(),
                model.drone().getDroneId(),
                model.store().getStoreId(),
                model.product().getProductId(),
                model.customer().getCustomerId()
        );
    }

    public static List<DeliveryDTO> toDTOs(List<Delivery> models) {
        return models.stream()
                .map(DeliveryMapper::toDTO)
                .toList();
    }

}
