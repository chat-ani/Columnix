package io.dataframe.exception;

/**
 * Base class for all unchecked exceptions thrown by the DataFrame library.
 *
 * <p>All library-specific runtime exceptions extend this class, allowing
 * applications to catch a single exception type when appropriate.
 */
public abstract class DataFrameException extends RuntimeException {

    /**
     * Creates a new dataframe with the specified message.
     *
     * @param message the detail message
     */
    public DataFrameException(String message) {
        super(message);
    }

    /**
     * Creates a new dataframe with the specified message and cause.
     *
     * @param message the detail message
     * @param cause   the underlying cause
     */
    public DataFrameException(String message, Throwable cause) {
        super(message, cause);
    }
}
