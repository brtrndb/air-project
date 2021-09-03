package tech.brtrndb.airproject.flightplan;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class FlightPlanApplicationTest {

    @Test
    @DisplayName("Check context loading.")
    public void load_context() {
        log.info("Context loaded !");
    }

}
