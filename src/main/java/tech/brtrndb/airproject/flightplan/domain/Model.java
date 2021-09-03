package tech.brtrndb.airproject.flightplan.domain;

import java.util.UUID;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Model {

    protected static final UUID NO_UUID = UUID.fromString("00000000-0000-0000-0000-000000000000");

    @Getter
    @Setter(AccessLevel.PRIVATE)
    @NonNull
    protected UUID id = NO_UUID;

    @Getter
    @Setter(AccessLevel.PRIVATE)
    @NonNull
    protected CreationInfo creationInfo = CreationInfo.EPOCH;

}
