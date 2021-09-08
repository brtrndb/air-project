package tech.brtrndb.airproject.flightplan.config;

import java.time.Instant;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;

@Slf4j
//@TestConfiguration
public class BuildPropertiesTestConfiguration {

    @Bean
    public BuildProperties fakeBuildProperties() {
        log.warn("{} bean not found, creating fake one for tests.", BuildProperties.class.getSimpleName());
        Properties properties = new Properties();
        properties.setProperty("name", "Tests");
        properties.setProperty("artifact", "tests");
        properties.setProperty("version", "FAKE-VERSION");
        properties.setProperty("time", Instant.now().toString());
        return new BuildProperties(properties);
    }

}
