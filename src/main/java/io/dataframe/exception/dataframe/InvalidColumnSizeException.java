package io.dataframe.exception.dataframe;

import io.dataframe.exception.DataFrameException;

/**
 * Thrown when columns with different row counts are used to create a DataFrame.
 *
 * <p>A valid DataFrame requires every column to contain the same number of rows.
 *
 * @since 1.0.0
 * @author Anirban Chatterjee
 */
public final class InvalidColumnSizeException extends DataFrameException {

    /**
     * Creates a new exception with the specified detail message.
     *
     * @param message the detail message
     */
    public InvalidColumnSizeException(String message) {
        super(message);
    }

    /**
     * Creates a new exception with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the underlying cause
     */
    public InvalidColumnSizeException(String message, Throwable cause) {
        super(message, cause);
    }
}