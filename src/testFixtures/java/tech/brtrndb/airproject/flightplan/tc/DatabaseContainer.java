package tech.brtrndb.airproject.flightplan.tc;

import java.util.Objects;

import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;

@Slf4j
public class DatabaseContainer extends PostgreSQLContainer<DatabaseContainer> {

    private static final String IMAGE_VERSION = "postgres:latest";
    private static final String DB_USERNAME = "postgres_user";
    private static final String DB_PASSWORD = "password_password";
    private static final String DB_NAME = "test_db";

    private static DatabaseContainer container;

    public DatabaseContainer() {
        super(IMAGE_VERSION);
    }

    public void withLog() {
        Slf4jLogConsumer logConsumer = new Slf4jLogConsumer(log).withSeparateOutputStreams();
        this.followOutput(logConsumer);
    }

    public static DatabaseContainer getInstance() {
        if (Objects.isNull(container)) {
            container = new DatabaseContainer()
                    .withUsername(DB_USERNAME)
                    .withPassword(DB_PASSWORD)
                    .withDatabaseName(DB_NAME)
                    .withCreateContainerCmdModifier(createContainerCmd -> createContainerCmd.withCmd("postgres", "-c", "log_statement=all"));
        }
        return container;
    }

}
