package tech.brtrndb.airproject.flightplan;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
@FlightPlanIntegrationTest
public class FlightPlanApplicationTest extends BaseTest {

    @Test
    @DisplayName("Check context loading.")
    public void load_context() {
        log.info("Context loaded !");
    }

}
