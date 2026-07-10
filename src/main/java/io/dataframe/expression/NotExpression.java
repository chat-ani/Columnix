package io.dataframe.expression;

import io.dataframe.exception.expression.InvalidExpressionException;
import io.dataframe.util.ValidationUtils;

import java.util.Objects;

/**
 * Represents the logical negation of an expression.
 *
 * <p>A {@code NotExpression} wraps a single expression and represents its
 * logical negation. It is typically used to negate comparison or logical
 * expressions when constructing filter predicates.
 *
 * <p>Example:
 *
 * <pre>{@code
 * NOT (Age > 30)
 * }</pre>
 *
 * <p>This class is immutable and thread-safe.
 *
 * @since 1.0.0
 * @author Anirban Chatterjee
 */
public final class NotExpression implements Expression {

    private final Expression expression;

    /**
     * Creates a new {@code NotExpression}.
     *
     * @param expression the expression to negate
     */
    private NotExpression(Expression expression) {
        this.expression = ValidationUtils.requireNonNull(
                expression,
                () -> new InvalidExpressionException("Expression cannot be null.")
        );
    }

    /**
     * Creates a new {@code NotExpression}.
     *
     * @param expression the expression to negate
     * @return a new {@code NotExpression}
     */
    public static NotExpression of(Expression expression) {
        return new NotExpression(expression);
    }

    /**
     * Returns the wrapped expression.
     *
     * @return the wrapped expression
     */
    public Expression expression() {
        return expression;
    }

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }

        if (!(object instanceof NotExpression other)) {
            return false;
        }

        return expression.equals(other.expression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expression);
    }

    @Override
    public String toString() {
        return "NotExpression[expression=" + expression + "]";
    }
}
