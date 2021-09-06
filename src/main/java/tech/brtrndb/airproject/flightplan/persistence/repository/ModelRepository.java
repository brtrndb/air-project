package tech.brtrndb.airproject.flightplan.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import tech.brtrndb.airproject.flightplan.persistence.entity.ModelEntity;

@NoRepositoryBean
public interface ModelRepository<E extends ModelEntity> extends JpaRepository<E, UUID> {
}
