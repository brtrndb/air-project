package tech.brtrndb.airproject.flightplan.error;

import java.io.Serial;

public class CsvDataLoaderException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1238398200455708313L;

    private final String filename;

    public CsvDataLoaderException(String filename, Throwable cause) {
        super(cause);
        this.filename = filename;
    }

    @Override
    public String getMessage() {
        return "Could not load CSV file %s".formatted(this.filename);
    }
}
