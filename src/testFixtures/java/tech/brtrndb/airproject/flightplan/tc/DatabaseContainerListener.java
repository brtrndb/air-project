package tech.brtrndb.airproject.flightplan.tc;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.flywaydb.core.Flyway;
import org.springframework.lang.NonNull;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

@Slf4j
public class DatabaseContainerListener implements TestExecutionListener {

    private static final List<String> DEFAULT_SCHEMAS = Collections.singletonList("svc_flightplan");

    private final List<String> schemas;

    public DatabaseContainerListener() {
        this.schemas = Stream.of(DEFAULT_SCHEMAS)
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());
    }

    private Flyway flywayConfiguration() {
        return Flyway.configure()
                .dataSource(DatabaseContainer.getInstance().getJdbcUrl(), DatabaseContainer.getInstance().getUsername(), DatabaseContainer.getInstance().getPassword())
                .locations("db/migration")
                .schemas(this.schemas.toArray(new String[0]))
                .baselineOnMigrate(true)
                .load();
    }

    @Override
    public void beforeTestClass(@NonNull TestContext testContext) {
        log.debug("Starting container...");
        DatabaseContainer.getInstance().start();
        log.info("Container started: {}.", DatabaseContainer.getInstance().getContainerId());
        log.debug("Container JDBC url: {}.", DatabaseContainer.getInstance().getJdbcUrl());
        Flyway flyway = this.flywayConfiguration();
        log.debug("Preparing Database. Running migrations...");
        flyway.migrate();
        log.info("Database is ready.");
    }

    @Override
    public void beforeTestMethod(@NonNull TestContext testContext) {
        Assertions.assertThat(DatabaseContainer.getInstance().isRunning()).isTrue();
    }

    @Override
    public void beforeTestExecution(@NonNull TestContext testContext) {
        Assertions.assertThat(DatabaseContainer.getInstance().isRunning()).isTrue();
    }

    @Override
    public void afterTestExecution(@NonNull TestContext testContext) {
        /**/
    }

    @Override
    public void afterTestMethod(@NonNull TestContext testContext) {
        /**/
    }

    @Override
    public void afterTestClass(@NonNull TestContext testContext) {
        Flyway flyway = this.flywayConfiguration();
        log.debug("Cleaning database...");
        flyway.clean();
        log.info("Database has been cleaned.");
    }


}
