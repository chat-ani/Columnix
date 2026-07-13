package io.dataframe.expression.evaluation;

import io.dataframe.column.Column;
import io.dataframe.core.DataFrame;
import io.dataframe.expression.*;

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
final class ExpressionEvaluator extends ExpressionEvaluatorHelper {

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

        if (expression instanceof LogicalExpression logicalExpression) {

            Object left = evaluate(logicalExpression.left(), dataFrame, rowIndex);

            Object right = evaluate(logicalExpression.right(), dataFrame, rowIndex);

            requireBooleanOperands(left, right);

            return switch (logicalExpression.operator()) {
                case AND -> (Boolean) left && (Boolean) right;
                case OR  -> (Boolean) left || (Boolean) right;
            };
        }

        throw new UnsupportedOperationException("Unsupported expression type: " + expression.getClass().getSimpleName());
    }
}