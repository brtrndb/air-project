package tech.brtrndb.airproject.flightplan.fixture;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import tech.brtrndb.airproject.flightplan.domain.CreationInfo;
import tech.brtrndb.airproject.flightplan.persistence.entity.CreationInfoEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CreationInfoFixtures {

    public static UUID randomId() {
        return UUID.randomUUID();
    }

    public static CreationInfoEntity randomCreationInfoEntity() {
        return CreationInfoEntity.of(Instant.now(), Instant.now());
    }

    public static CreationInfo randomCreationInfo() {
        return CreationInfo.of(Instant.now(), Instant.now());
    }

    public static Map.Entry<CreationInfoEntity, CreationInfo> randomMatching() {
        Instant createdAt = Instant.now();
        Instant updatedAt = createdAt.plus(1, ChronoUnit.MINUTES);

        CreationInfoEntity entity = CreationInfoEntity.of(createdAt, updatedAt);
        CreationInfo model = CreationInfo.of(createdAt, updatedAt);

        return Map.entry(entity, model);
    }

}
