package tech.brtrndb.airproject.flightplan.runner;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.info.BuildProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnBean({BuildProperties.class})
public class BuildPropertiesRunner implements ApplicationRunner {

    private static final int SEPARATOR_LENGTH = 70;
    private static final String SEPARATOR = "-".repeat(SEPARATOR_LENGTH);

    private final Environment environment;
    private final BuildProperties buildProperties;

    public BuildPropertiesRunner(Environment environment, BuildProperties buildProperties) {
        this.environment = environment;
        this.buildProperties = buildProperties;
    }

    @Override
    public void run(ApplicationArguments args) {
        List<String> profiles = List.of(this.environment.getActiveProfiles());
        log.info(SEPARATOR);
        log.info("Application {} is fully configured and running !", this.buildProperties.getName());
        log.info("Build name: {}.", this.buildProperties.getName());
        log.info("Build artefact: {}.", this.buildProperties.getArtifact());
        log.info("Build version: {}.", this.buildProperties.getVersion());
        log.info("Build time: {}.", this.buildProperties.getTime());
        log.info("Active profile(s): {}.", profiles);
        log.info(SEPARATOR);
    }

}
