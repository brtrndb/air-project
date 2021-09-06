package tech.brtrndb.airproject.flightplan.fixture;

import java.util.Map;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import tech.brtrndb.airproject.flightplan.domain.CreationInfo;
import tech.brtrndb.airproject.flightplan.domain.Customer;
import tech.brtrndb.airproject.flightplan.domain.Position;
import tech.brtrndb.airproject.flightplan.persistence.entity.CreationInfoEntity;
import tech.brtrndb.airproject.flightplan.persistence.entity.CustomerEntity;
import tech.brtrndb.airproject.flightplan.persistence.entity.PositionEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CustomerFixtures {

    public static String randomCustomerId() {
        return DataFixtures.randomString("CUSTOMER");
    }

    public static CustomerEntity randomCustomerEntity() {
        return DataFixtures.build(() -> {
            CustomerEntity entity = new CustomerEntity();
            entity.setCustomerId(randomCustomerId());
            entity.setPosition(PositionFixtures.randomPositionEntity());

            return entity;
        });
    }

    public static Customer randomCustomer() {
        return DataFixtures.build(() -> new Customer(
                randomCustomerId(),
                PositionFixtures.randomPosition()
        ));
    }

    public static Map.Entry<CustomerEntity, Customer> randomMatching() {
        UUID id = CreationInfoFixtures.randomId();
        Map.Entry<CreationInfoEntity, CreationInfo> matchingCreationInfo = CreationInfoFixtures.randomMatching();
        String customerId = randomCustomerId();
        Map.Entry<PositionEntity, Position> matchingPosition = PositionFixtures.randomMatching();

        CustomerEntity entity = new CustomerEntity();
        entity.setId(id);
        entity.setCreationInfo(matchingCreationInfo.getKey());
        entity.setCustomerId(customerId);
        entity.setPosition(matchingPosition.getKey());

        Customer model = new Customer(id, matchingCreationInfo.getValue(), customerId, matchingPosition.getValue());

        return Map.entry(entity, model);
    }

}
