package io.dataframe.exception.schema;

import io.dataframe.exception.DataFrameException;

/// Thrown when a requested column is invalid.
///
/// @since 1.0.0
/// @author Anirban Chatterjee
public class InvalidColumnException extends DataFrameException {

    public InvalidColumnException(String message) {
        super(message);
    }

    public InvalidColumnException(String message, Throwable cause) {
        super(message, cause);
    }
}