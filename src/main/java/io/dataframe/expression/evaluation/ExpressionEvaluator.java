package io.dataframe.expression.evaluation;

import io.dataframe.core.DataFrame;
import io.dataframe.expression.Expression;

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
     * @throws NullPointerException          if {@code expression} or {@code dataFrame} is {@code null}
     * @throws IndexOutOfBoundsException     if {@code rowIndex} is outside the DataFrame bounds
     * @throws UnsupportedOperationException if the expression type is not supported
     */
    static void evaluate(
            Expression expression,
            DataFrame dataFrame,
            int rowIndex) {

        Objects.requireNonNull(
                expression,
                "Expression cannot be null."
        );

        Objects.requireNonNull(
                dataFrame,
                "DataFrame cannot be null."
        );

        if (rowIndex < 0 || rowIndex >= dataFrame.rowCount()) {
            throw new IndexOutOfBoundsException(
                    "Row index out of bounds: " + rowIndex
            );
        }

        throw new UnsupportedOperationException(
                "Expression evaluation has not been implemented yet."
        );
    }
}