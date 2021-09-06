package tech.brtrndb.airproject.flightplan.persistence.entity;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class ModelEntity {

    @NonNull
    @Id
    @Type(type = "pg-uuid")
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", nullable = false, updatable = false)
    protected UUID id;

    @NonNull
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "created_at", column = @Column(name = "created_at")),
            @AttributeOverride(name = "updated_at", column = @Column(name = "updated_at"))
    })
    protected CreationInfoEntity creationInfo;

    @PrePersist
    protected void onCreate() {
        // Creation instant should be truncated to microseconds because Postgres does not persist nanoseconds.
        Instant createdAt = Instant.now().truncatedTo(ChronoUnit.MICROS);
        this.creationInfo = CreationInfoEntity.of(createdAt, createdAt);
    }

    @PreUpdate
    protected void onUpdate() {
        // Update instant should be truncated to microseconds because Postgres does not persist nanoseconds.
        Instant updatedAt = Instant.now().truncatedTo(ChronoUnit.MICROS);
        this.creationInfo = CreationInfoEntity.of(this.creationInfo.getCreatedAt(), updatedAt);
    }

}
