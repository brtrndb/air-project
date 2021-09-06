package tech.brtrndb.airproject.flightplan.service.mapper;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import tech.brtrndb.airproject.flightplan.domain.Store;
import tech.brtrndb.airproject.flightplan.fixture.StoreFixtures;
import tech.brtrndb.airproject.flightplan.persistence.entity.StoreEntity;

@Slf4j
public class StoreMapperTest extends BaseMapperTest<Store, StoreEntity> {

    private static final Map.Entry<StoreEntity, Store> matching = StoreFixtures.randomMatching();

    public StoreMapperTest() {
        super(
                StoreMapper::toEntity,
                StoreMapper::toModel,
                matching::getValue,
                matching::getKey
        );
    }

}
