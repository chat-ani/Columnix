package io.dataframe.expression;

import io.dataframe.exception.expression.InvalidExpressionException;
import io.dataframe.expression.operator.ComparisonOperator;
import io.dataframe.util.ValidationUtils;

import java.util.Objects;

/**
 * Represents a comparison between two expressions.
 *
 * <p>A {@code ComparisonExpression} combines two expressions using a
 * {@link ComparisonOperator}. It forms the basic building block for
 * filtering and query predicates.
 *
 * <p>Examples:
 *
 * <pre>{@code
 * Age > 30
 * Salary <= 50000
 * Name == "John"
 * }</pre>
 *
 * <p>This class is immutable and thread-safe.
 *
 * @author Anirban Chatterjee
 * @since 1.0.0
 */
public final class ComparisonExpression implements Expression {

    private final Expression left;
    private final Expression right;
    private final ComparisonOperator operator;

    /**
     * Creates a new comparison expression.
     *
     * @param left     the left operand
     * @param right    the right operand
     * @param operator the comparison operator
     */
    private ComparisonExpression(Expression left, Expression right, ComparisonOperator operator) {

        this.left = ValidationUtils.requireNonNull(left, () -> new InvalidExpressionException("Left expression cannot be null."));

        this.right = ValidationUtils.requireNonNull(right, () -> new InvalidExpressionException("Right expression cannot be null."));

        this.operator = ValidationUtils.requireNonNull(operator, () -> new InvalidExpressionException("Comparison operator cannot be null."));
    }

    /**
     * Creates a comparison expression.
     *
     * @param left     the left operand
     * @param right    the right operand
     * @param operator the comparison operator
     * @return a new comparison expression
     */
    public static ComparisonExpression of(Expression left, Expression right, ComparisonOperator operator) {
        return new ComparisonExpression(left, right, operator);
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
     * Returns the comparison operator.
     *
     * @return the comparison operator
     */
    public ComparisonOperator operator() {
        return operator;
    }

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }

        if (!(object instanceof ComparisonExpression other)) {
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
        return "ComparisonExpression[left="
                + left
                + ", operator="
                + operator
                + ", right="
                + right
                + "]";
    }
}
