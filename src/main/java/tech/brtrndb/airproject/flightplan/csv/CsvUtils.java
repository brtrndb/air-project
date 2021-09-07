package tech.brtrndb.airproject.flightplan.csv;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CsvUtils {

    private static final CsvMapper CSV_MAPPER_DE = new CsvMapper();

    public static <T> List<T> readCsvResource(String csvFile, Class<T> csvClass, char separator) throws IOException {
        Resource resource = new ClassPathResource(csvFile);

        try (InputStream is = resource.getInputStream()) {
            return CsvUtils.read(csvClass, is, separator);
        }
    }

    public static <T> List<T> read(Class<T> clazz, InputStream is, char separator) throws IOException {
        CsvSchema schema = CSV_MAPPER_DE.schemaFor(clazz)
                .withHeader()
                .withColumnSeparator(separator)
                .withColumnReordering(true)
                .withNullValue("");
        ObjectReader reader = CSV_MAPPER_DE.readerFor(clazz).with(schema);
        return reader.<T>readValues(is).readAll();
    }

}
