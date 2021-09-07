package tech.brtrndb.airproject.flightplan.util;

import java.util.Objects;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ValidationUtils {

    public static String requireNonNullBlankEmptyString(String str, String nullMsg, String emptyMsg) throws NullPointerException, IllegalArgumentException {
        Objects.requireNonNull(str, nullMsg);

        if (str.isEmpty() || str.isBlank())
            throw new IllegalArgumentException(emptyMsg);

        return str;
    }

}
