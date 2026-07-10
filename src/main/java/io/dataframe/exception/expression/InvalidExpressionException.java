package io.dataframe.exception.expression;

/**
 * Throws when ColumnExpression get a invalid expression
 *
 * @since 1.0.0
 * @author Anirban Chatterjee
 */
public final class InvalidExpressionException extends ExpressionException{

    public InvalidExpressionException(String message) {
        super(message);
    }

    public InvalidExpressionException(String message, Throwable cause) {
        super(message, cause);
    }
}
