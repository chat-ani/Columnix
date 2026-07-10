package io.dataframe.exception.schema;

import io.dataframe.exception.DataFrameException;

/**
 * Thrown when a requested column does not exist.
 *
 * @since 1.0.0
 * @author Anirban Chatterjee
 */
public final class ColumnNotFoundException extends DataFrameException {

    public ColumnNotFoundException(String message) {
        super(message);
    }

    public ColumnNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}