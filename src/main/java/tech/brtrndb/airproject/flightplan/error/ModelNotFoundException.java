package tech.brtrndb.airproject.flightplan.error;

import java.io.Serial;

public class ModelNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 5749683194109718567L;

    private final Class<?> modelClass;
    private final String modelId;

    public ModelNotFoundException(Class<?> modelClass, String modelId) {
        super();
        this.modelClass = modelClass;
        this.modelId = modelId;
    }

    @Override
    public String getMessage() {
        return "%s [%s] not found".formatted(this.modelClass.getSimpleName(), this.modelId);
    }

}
