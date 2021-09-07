package tech.brtrndb.airproject.flightplan.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import tech.brtrndb.airproject.flightplan.config.DatabaseInitializer;

@Slf4j
@Component
@Profile("!test") // TODO: Try to find a vay to avoid this.
public class PopulateDatabaseRunner implements ApplicationRunner {

    private final DatabaseInitializer databaseInitializer;

    public PopulateDatabaseRunner(DatabaseInitializer databaseInitializer) {
        this.databaseInitializer = databaseInitializer;
    }

    @Override
    public void run(ApplicationArguments args) {
        this.databaseInitializer.initDatabase();
    }

}
