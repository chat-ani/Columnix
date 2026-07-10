package io.dataframe.exception.schema;

import io.dataframe.exception.DataFrameException;

/**
 * Thrown when duplicate column names are encountered while creating a schema.
 *
 * @since 1.0.0
 * @author Anirban Chatterjee
 */
public final class DuplicateColumnException extends DataFrameException {

    public DuplicateColumnException(String message) {
        super(message);
    }

    public DuplicateColumnException(String message, Throwable cause) {
        super(message, cause);
    }
}