package tech.brtrndb.airproject.flightplan.runner;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;

import tech.brtrndb.airproject.flightplan.BaseTest;
import tech.brtrndb.airproject.flightplan.FlightPlanIntegrationTest;

@Slf4j
@FlightPlanIntegrationTest
@Import({PopulateDatabaseRunner.class})
public class PopulateDatabaseRunnerTest extends BaseTest {

    @SpyBean
    private PopulateDatabaseRunner runner;

    @Test
    public void runner_is_run_on_start() {
        // Given:
        // When:
        // Then:
        Mockito.verify(this.runner, Mockito.times(1)).run(Mockito.any());
    }

}
