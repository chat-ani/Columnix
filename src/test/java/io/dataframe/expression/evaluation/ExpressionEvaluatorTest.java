package io.dataframe.expression.evaluation;

import io.dataframe.column.IntColumn;
import io.dataframe.core.DataFrame;
import io.dataframe.expression.LiteralExpression;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ExpressionEvaluatorTest {

    @Test
    void shouldRejectNullExpression() {

        DataFrame dataFrame = DataFrame.of();

        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> ExpressionEvaluator.evaluate(
                        null,
                        dataFrame,
                        0
                )
        );

        assertEquals(
                "Expression cannot be null.",
                exception.getMessage()
        );
    }

    @Test
    void shouldRejectNullDataFrame() {

        LiteralExpression<Integer> expression = LiteralExpression.of(10);

        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> ExpressionEvaluator.evaluate(
                        expression,
                        null,
                        0
                )
        );

        assertEquals(
                "DataFrame cannot be null.",
                exception.getMessage()
        );
    }

    @Test
    void shouldRejectNegativeRowIndex() {

        DataFrame dataFrame = DataFrame.of();

        LiteralExpression<Integer> expression = LiteralExpression.of(10);

        IndexOutOfBoundsException exception = assertThrows(
                IndexOutOfBoundsException.class,
                () -> ExpressionEvaluator.evaluate(
                        expression,
                        dataFrame,
                        -1
                )
        );

        assertEquals(
                "Row index out of bounds: -1",
                exception.getMessage()
        );
    }

    @Test
    void shouldRejectRowIndexGreaterThanRowCount() {

        DataFrame dataFrame = DataFrame.of();

        LiteralExpression<Integer> expression = LiteralExpression.of(10);

        IndexOutOfBoundsException exception = assertThrows(
                IndexOutOfBoundsException.class,
                () -> ExpressionEvaluator.evaluate(
                        expression,
                        dataFrame,
                        1
                )
        );

        assertEquals(
                "Row index out of bounds: 1",
                exception.getMessage()
        );
    }

    @Test
    void shouldRejectUnsupportedExpression() {

        DataFrame dataFrame = DataFrame.of(
                IntColumn.of("Age", 25)
        );

        LiteralExpression<Integer> expression = LiteralExpression.of(10);

        UnsupportedOperationException exception = assertThrows(
                UnsupportedOperationException.class,
                () -> ExpressionEvaluator.evaluate(
                        expression,
                        dataFrame,
                        0
                )
        );

        assertEquals(
                "Expression evaluation has not been implemented yet.",
                exception.getMessage()
        );
    }
}