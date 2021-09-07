package tech.brtrndb.airproject.flightplan.config;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = ApplicationProperties.PREFIX, ignoreUnknownFields = false)
public class ApplicationProperties {

    public static final String PREFIX = "app";

    @NonNull
    @NotNull
    private CsvFiles csvFiles;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CsvFiles {

        @NonNull
        @NotEmpty
        @NotBlank
        private String drones;

        @NonNull
        @NotEmpty
        @NotBlank
        private String stores;

        @NonNull
        @NotEmpty
        @NotBlank
        private String products;

        @NonNull
        @NotEmpty
        @NotBlank
        private String customers;

        @NonNull
        @NotEmpty
        @NotBlank
        private String orders;

    }

}
