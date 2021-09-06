package tech.brtrndb.airproject.flightplan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@PropertySources({
        @PropertySource(value = "classpath:/config/application.yml", ignoreResourceNotFound = false)
})
@EnableTransactionManagement
public class FlightPlanApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlightPlanApplication.class, args);
    }

}
