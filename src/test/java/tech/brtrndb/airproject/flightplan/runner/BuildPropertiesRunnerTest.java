package tech.brtrndb.airproject.flightplan.runner;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;

import tech.brtrndb.airproject.flightplan.BaseTest;
import tech.brtrndb.airproject.flightplan.FlightPlanIntegrationTest;
import tech.brtrndb.airproject.flightplan.config.BuildPropertiesTestConfiguration;

@Slf4j
@FlightPlanIntegrationTest
@Import({BuildPropertiesTestConfiguration.class})
public class BuildPropertiesRunnerTest extends BaseTest {

    @SpyBean
    private BuildPropertiesRunner buildPropertiesRunner;

    @Test
    @DisplayName("Should build properties be logged on application start.")
    public void log_application_properties_on_start() {
        // Given:
        // When:
        // Then:
        Mockito.verify(this.buildPropertiesRunner, Mockito.times(1)).run(Mockito.any());
    }

}
