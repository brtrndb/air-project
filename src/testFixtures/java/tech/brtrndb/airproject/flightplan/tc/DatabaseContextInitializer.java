package tech.brtrndb.airproject.flightplan.tc;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.junit.jupiter.Container;

@Slf4j
@NoArgsConstructor
public class DatabaseContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Container
    public static final DatabaseContainer POSTGRESQL_CONTAINER = DatabaseContainer.getInstance();

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        TestPropertyValues.of(
                        "spring.datasource.url=" + POSTGRESQL_CONTAINER.getJdbcUrl(),
                        "spring.datasource.username=" + POSTGRESQL_CONTAINER.getUsername(),
                        "spring.datasource.password=" + POSTGRESQL_CONTAINER.getPassword())
                .applyTo(applicationContext.getEnvironment());
//        POSTGRESQL_CONTAINER.withLog();
    }

}
