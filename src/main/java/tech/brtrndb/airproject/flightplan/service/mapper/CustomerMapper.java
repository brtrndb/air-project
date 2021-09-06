package tech.brtrndb.airproject.flightplan.service.mapper;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import tech.brtrndb.airproject.flightplan.domain.Customer;
import tech.brtrndb.airproject.flightplan.persistence.entity.CustomerEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CustomerMapper {

    public static Customer toModel(CustomerEntity entity) {
        return new Customer(
                entity.getId(),
                CreationInfoMapper.toModel(entity.getCreationInfo()),
                entity.getCustomerId(),
                PositionMapper.toModel(entity.getPosition())
        );
    }

    public static CustomerEntity toEntity(Customer model) {
        return toEntity(model, false);
    }

    public static CustomerEntity toNewEntity(Customer model) {
        return toEntity(model, true);
    }

    private static CustomerEntity toEntity(Customer model, boolean ignoreCreationFields) {
        CustomerEntity entity = new CustomerEntity();

        if (!ignoreCreationFields) {
            entity.setId(model.getId());
            entity.setCreationInfo(CreationInfoMapper.toEntity(model.getCreationInfo()));
        }
        entity.setCustomerId(model.getCustomerId());
        entity.setPosition(PositionMapper.toEntity(model.getPosition()));

        return entity;
    }

    public static List<Customer> toModels(List<CustomerEntity> entities) {
        return entities.stream()
                .map(CustomerMapper::toModel)
                .collect(Collectors.toList());
    }

}
