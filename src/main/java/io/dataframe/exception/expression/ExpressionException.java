package io.dataframe.exception.expression;

import io.dataframe.exception.DataFrameException;

/**
 * Base exception for all expression-related errors in JavaDataFrame.
 *
 * <p>This exception serves as the root of the expression exception hierarchy.
 * It is thrown when an error occurs while creating, validating, or processing
 * query expressions.
 *
 * <p>Concrete subclasses should represent specific expression-related failures,
 * such as invalid expressions, getDataType mismatches, or evaluation errors.
 *
 * @since 1.0.0
 * @author Anirban Chatterjee
 */
public class ExpressionException extends DataFrameException {

    /**
     * Creates a new expression exception with the specified message.
     *
     * @param message the exception message
     */
    public ExpressionException(String message) {
        super(message);
    }

    /**
     * Creates a new expression exception with the specified message and cause.
     *
     * @param message the exception message
     * @param cause the underlying cause
     */
    public ExpressionException(String message, Throwable cause) {
        super(message, cause);
    }
}