package tech.brtrndb.airproject.flightplan.util;

import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import tech.brtrndb.airproject.flightplan.BaseTest;
import tech.brtrndb.airproject.flightplan.fixture.DataFixtures;

@Slf4j
public class ValidationUtilsTest extends BaseTest {

    @Test
    public void should_throw_npe_when_null_string() {
        // Given:
        String str = null;
        String nullMsg = "null";
        String emptyMsg = "empty";

        // When:
        // Then:
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> ValidationUtils.requireNonNullBlankEmptyString(str, nullMsg, emptyMsg))
                .withMessage(nullMsg);
    }

    @ParameterizedTest
    @MethodSource("streamOfModelWithInvalidProperty")
    public void should_throw_illegal_parameter_when_empty_or_blank_string(String empty) {
        // Given:
        String nullMsg = "null";
        String emptyMsg = "empty";

        // When:
        // Then:
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> ValidationUtils.requireNonNullBlankEmptyString(empty, nullMsg, emptyMsg))
                .withMessage(emptyMsg);
    }

    public static Stream<Arguments> streamOfModelWithInvalidProperty() {
        return DataFixtures.listOfEmptyOrBlankString()
                .stream()
                .map(Arguments::of);
    }

}
