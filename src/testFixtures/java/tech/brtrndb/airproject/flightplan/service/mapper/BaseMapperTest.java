package tech.brtrndb.airproject.flightplan.service.mapper;

import java.util.function.Function;
import java.util.function.Supplier;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import tech.brtrndb.airproject.flightplan.BaseTest;

@Slf4j
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class BaseMapperTest<A, B> extends BaseTest {

    private final Function<A, B> mapperToEntity;
    private final Function<B, A> mapperToModel;
    private final Supplier<A> modelSupplier;
    private final Supplier<B> entitySupplier;

    @Test
    public void map_model_to_entity() {
        // Given:
        A a = this.modelSupplier.get();
        B b = this.entitySupplier.get();

        // When:
        B mapped = this.mapperToEntity.apply(a);

        // Then:
        Assertions.assertThat(mapped).isEqualTo(b);
    }

    @Test
    public void map_entity_to_model() {
        // Given:
        A a = this.modelSupplier.get();
        B b = this.entitySupplier.get();

        // When:
        A mapped = this.mapperToModel.apply(b);

        // Then:
        Assertions.assertThat(mapped).isEqualTo(a);
    }

}
