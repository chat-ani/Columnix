package io.dataframe.util;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Utility class providing common validation methods used throughout the
 * JavaDataFrame library.
 *
 * <p>This class centralizes validation logic to ensure consistent validation
 * behavior while remaining independent of any specific module.
 *
 * <p>This class cannot be instantiated.
 *
 * @since 1.0.0
 * @author Anirban Chatterjee
 */
public final class ValidationUtils {

    /**
     * Prevents instantiation.
     */
    private ValidationUtils() {
        throw new AssertionError("Utility class cannot be instantiated.");
    }

    /**
     * Ensures that the supplied value is not {@code null}.
     *
     * @param value the value to validate
     * @param exceptionSupplier supplies the exception to throw
     * @param <T> the value type
     * @param <E> the exception type
     * @return the validated value
     * @throws E if the value is {@code null}
     */
    public static <T, E extends RuntimeException> T requireNonNull(T value, Supplier<E> exceptionSupplier) {
        Objects.requireNonNull(exceptionSupplier, "Exception supplier cannot be null.");

        if (value == null) {
            throw exceptionSupplier.get();
        }

        return value;
    }

    /**
     * Ensures that the supplied string is not blank.
     *
     * <p>The value is assumed to be non-null. Use {@link #requireNonNull(Object, Supplier)}
     * if null validation is also required.
     *
     * @param value             the string to validate
     * @param exceptionSupplier supplies the exception to throw
     * @param <E>               the exception type
     * @throws E if the string is blank
     */
    public static <E extends RuntimeException> void requireNonBlank(String value, Supplier<E> exceptionSupplier) {
        Objects.requireNonNull(exceptionSupplier, "Exception supplier cannot be null.");

        if (value.isBlank()) {
            throw exceptionSupplier.get();
        }
    }

}