package io.dataframe.expression.evaluation;

import io.dataframe.column.Column;
import io.dataframe.core.DataFrame;
import io.dataframe.expression.ColumnExpression;
import io.dataframe.expression.ComparisonExpression;
import io.dataframe.expression.Expression;
import io.dataframe.expression.LiteralExpression;

import java.util.Objects;

/**
 * Internal execution engine responsible for evaluating expression trees
 * against a specific row of a {@link io.dataframe.core.DataFrame}.
 *
 * <p>The evaluation recursively traverses an expression tree and computes
 * the resulting value for a given row. It is intentionally separated from
 * the expression hierarchy to preserve the single responsibility of
 * expression objects.
 *
 * <p>This class is stateless and cannot be instantiated.
 *
 * @since 1.0.0
 * @author Anirban Chatterjee
 */
final class ExpressionEvaluator {

    /**
     * Validates that two operands can participate in an ordered comparison.
     *
     * <p>Both operands must be of the same runtime type and implement
     * {@link Comparable}. This validation is required for relational
     * comparison operators such as {@code >}, {@code >=}, {@code <},
     * and {@code <=}.
     *
     * @param left the left operand
     * @param right the right operand
     * @throws IllegalArgumentException if the operands have different runtime
     *                                  types or do not implement
     *                                  {@link Comparable}
     */
    private static void requireComparableOperands(Object left, Object right) {

        if (left instanceof Boolean) {
            throw new IllegalArgumentException("Boolean values do not support relational comparisons.");
        }

        if (!left.getClass().equals(right.getClass())) {
            throw new IllegalArgumentException("Comparison operands must have the same type.");
        }

        if (!(left instanceof Comparable<?>)) {
            throw new IllegalArgumentException("Comparison operands must implement Comparable.");
        }
    }

    /**
     * Compares two comparable operands.
     * <p>
     * The operands are assumed to have already passed validation through
     * {@link #requireComparableOperands(Object, Object)}.
     * </p>
     *
     * @param left  the left operand
     * @param right the right operand
     * @return a negative integer, zero, or a positive integer as the left operand
     *         is less than, equal to, or greater than the right operand
     */
    @SuppressWarnings("unchecked")
    private static int compare(Object left, Object right) {

        return ((Comparable<Object>) left).compareTo(right);
    }

    /**
     * Prevents instantiation of this utility class.
     */
    private ExpressionEvaluator() {
        throw new AssertionError("Utility class cannot be instantiated.");
    }

    /**
     * Evaluates an expression against the specified row of a DataFrame.
     *
     * <p>The evaluation is performed recursively according to the concrete
     * expression type.
     *
     * @param expression the expression to evaluate
     * @param dataFrame  the DataFrame providing column values
     * @param rowIndex   the zero-based row index
     * @return Object value of type expression
     * @throws NullPointerException          if {@code expression} or {@code dataFrame} is {@code null}
     * @throws IndexOutOfBoundsException     if {@code rowIndex} is outside the DataFrame bounds
     * @throws UnsupportedOperationException if the expression type is not supported
     */
    static Object evaluate(Expression expression, DataFrame dataFrame, int rowIndex) {

        Objects.requireNonNull(expression, "Expression cannot be null.");

        Objects.requireNonNull(dataFrame, "DataFrame cannot be null.");

        if (rowIndex < 0 || rowIndex >= dataFrame.rowCount()) {
            throw new IndexOutOfBoundsException("Row index out of bounds: " + rowIndex);
        }

        if (expression instanceof LiteralExpression<?> literalExpression) {
            return literalExpression.value();
        }

        if (expression instanceof ColumnExpression columnExpression) {

            Column column = dataFrame.column(columnExpression.name());

            return column.value(rowIndex);
        }

        if (expression instanceof ComparisonExpression comparisonExpression) {

            Object left = evaluate(comparisonExpression.left(), dataFrame, rowIndex);

            Object right = evaluate(comparisonExpression.right(), dataFrame, rowIndex);

            return switch (comparisonExpression.operator()) {

                case EQUALS -> Objects.equals(left, right);

                case NOT_EQUALS -> !Objects.equals(left, right);

                case GREATER_THAN -> {
                    requireComparableOperands(left, right);
                    yield compare(left, right) > 0;
                }

                case GREATER_THAN_OR_EQUAL -> {
                    requireComparableOperands(left, right);
                    yield compare(left, right) >= 0;
                }

                case LESS_THAN -> {
                    requireComparableOperands(left, right);
                    yield compare(left, right) < 0;
                }

                case LESS_THAN_OR_EQUAL -> {
                    requireComparableOperands(left, right);
                    yield compare(left, right) <= 0;
                }
            };
        }

        throw new UnsupportedOperationException("Unsupported expression type: " + expression.getClass().getSimpleName());
    }
}