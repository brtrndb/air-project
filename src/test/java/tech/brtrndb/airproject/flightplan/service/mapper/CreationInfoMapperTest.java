package tech.brtrndb.airproject.flightplan.service.mapper;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import tech.brtrndb.airproject.flightplan.domain.CreationInfo;
import tech.brtrndb.airproject.flightplan.fixture.CreationInfoFixtures;
import tech.brtrndb.airproject.flightplan.persistence.entity.CreationInfoEntity;

@Slf4j
public class CreationInfoMapperTest extends BaseMapperTest<CreationInfo, CreationInfoEntity> {

    private static final Map.Entry<CreationInfoEntity, CreationInfo> matching = CreationInfoFixtures.randomMatching();

    public CreationInfoMapperTest() {
        super(
                CreationInfoMapper::toEntity,
                CreationInfoMapper::toModel,
                matching::getValue,
                matching::getKey
        );
    }

}
