package io.dataframe.expression;

import io.dataframe.exception.expression.InvalidExpressionException;
import io.dataframe.expression.operator.LogicalOperator;
import io.dataframe.util.ValidationUtils;

import java.util.Objects;

/**
 * Represents a logical operation between two expressions.
 *
 * <p>A {@code LogicalExpression} combines two expressions using a
 * {@link LogicalOperator}. Typical examples include combining multiple
 * comparison expressions using logical AND or OR.
 *
 * <p>This class is immutable and thread-safe.
 *
 * @author Anirban Chatterjee
 * @since 1.0.0
 */
public final class LogicalExpression implements Expression {

    private final Expression left;
    private final Expression right;
    private final LogicalOperator operator;

    /**
     * Creates a new {@code LogicalExpression}.
     *
     * @param left     the left expression
     * @param right    the right expression
     * @param operator the logical operator
     */
    public LogicalExpression(Expression left, Expression right, LogicalOperator operator) {

        this.left = ValidationUtils.requireNonNull(left, () -> new InvalidExpressionException("Left expression cannot be null."));

        this.right = ValidationUtils.requireNonNull(right, () -> new InvalidExpressionException("Right expression cannot be null."));

        this.operator = ValidationUtils.requireNonNull(operator, () -> new InvalidExpressionException("Logical operator cannot be null."));
    }

    /**
     * Creates a new {@code LogicalExpression}.
     *
     * @param left     the left expression
     * @param right    the right expression
     * @param operator the logical operator
     * @return a new logical expression
     */
    public static LogicalExpression of(
            Expression left,
            Expression right,
            LogicalOperator operator) {

        return new LogicalExpression(left, right, operator);
    }

    /**
     * Returns the left operand.
     *
     * @return the left expression
     */
    public Expression left() {
        return left;
    }

    /**
     * Returns the right operand.
     *
     * @return the right expression
     */
    public Expression right() {
        return right;
    }

    /**
     * Returns the logical operator.
     *
     * @return the logical operator
     */
    public LogicalOperator operator() {
        return operator;
    }

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }

        if (!(object instanceof LogicalExpression other)) {
            return false;
        }

        return left.equals(other.left) && right.equals(other.right) && operator == other.operator;
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right, operator);
    }

    @Override
    public String toString() {
        return "LogicalExpression[left="
                + left
                + ", operator="
                + operator
                + ", right="
                + right
                + "]";
    }
}