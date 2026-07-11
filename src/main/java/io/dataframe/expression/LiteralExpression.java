package io.dataframe.expression;

import io.dataframe.exception.expression.InvalidExpressionException;
import io.dataframe.util.ValidationUtils;

import java.util.Objects;

/**
 * Represents a literal value within an expression tree.
 *
 * <p>A {@code LiteralExpression} encapsulates a constant value that participates
 * in query expressions. Examples include numbers, strings, booleans, and other
 * immutable values.
 *
 * <p>This class is immutable and thread-safe.
 *
 * @param <T> the getDataType of the literal value
 *
 * @since 1.0.0
 * @author Anirban Chatterjee
 */
public final class LiteralExpression<T> implements Expression {

    private final T value;

    /**
     * Creates a new {@code LiteralExpression}.
     *
     * @param value the literal value
     */
    private LiteralExpression(T value) {
        this.value = ValidationUtils.requireNonNull(value, () -> new InvalidExpressionException("Literal value cannot be null."));
    }

    /**
     * Creates a {@code LiteralExpression} from the supplied value.
     *
     * @param value the literal value
     * @param <T> the getDataType of the literal value
     * @return a new {@code LiteralExpression}
     */
    public static <T> LiteralExpression<T> of(T value) {
        return new LiteralExpression<>(value);
    }

    /**
     * Returns the literal value.
     *
     * @return the literal value
     */
    public T value() {
        return value;
    }

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }

        if (!(object instanceof LiteralExpression<?> other)) {
            return false;
        }

        return Objects.equals(value, other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "LiteralExpression[value=" + value + "]";
    }
}