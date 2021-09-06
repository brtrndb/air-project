package tech.brtrndb.airproject.flightplan.fixture;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Data generator for testing.
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DataFixtures {

    public static int randomInt(int start, int end) {
        return new Random()
                .ints(start, end)
                .limit(1)
                .findAny()
                .orElseThrow();

    }

    public static long randomLong(long start, long end) {
        return new Random()
                .longs(start, end)
                .limit(1)
                .findAny()
                .orElseThrow();
    }

    public static double randomDouble(double start, double end) {
        return new Random()
                .doubles(start, end)
                .limit(1)
                .findAny()
                .orElseThrow();
    }

    public static String randomString() {
        return randomString("str");
    }

    public static String randomString(String prefix) {
        return String.format("%s_%s", prefix, randomString(5));
    }

    public static String randomString(int length) {
        if (length < 0)
            throw new IllegalArgumentException("String length cannot be negative");
        if (length == 0)
            return "";

        int leftLimit = 48;     // Numeral '0'
        int rightLimit = 122;   // Letter 'z'
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= '9' || 'A' <= i) && (i <= 'Z' || 'a' <= i))
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public static Instant randomInstant() {
        return randomInstantBetween(Instant.now(), Instant.now().plus(1, ChronoUnit.YEARS));
    }

    public static Instant randomInstantBetween(Instant start, Instant end) {
        long startSeconds = start.getEpochSecond();
        long endSeconds = end.getEpochSecond();
        long random = randomLong(startSeconds, endSeconds);

        return Instant.ofEpochSecond(random);
    }

    public static <T> T build(Supplier<T> supplier) {
        T t = supplier.get();
        log.trace("{} built: {}", t.getClass().getSimpleName(), t);
        return t;
    }

    public static <T> List<T> generate(int nb, Supplier<T> supplier) {
        return Stream.generate(supplier)
                .limit(nb)
                .collect(Collectors.toList());
    }

    public static List<String> listOfEmptyOrBlankString() {
        return List.of("", " ", "  ", "\t", "\t\t", " \t", "\t ");
    }

    public static List<String> listOfNullOrEmptyOrBlankString() {
        return Stream.of(Collections.singletonList((String) null), listOfEmptyOrBlankString())
                .flatMap(Collection::stream)
                .toList();
    }

}
