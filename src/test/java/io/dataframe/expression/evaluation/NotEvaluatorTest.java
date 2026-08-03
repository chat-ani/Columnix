package io.dataframe.expression.evaluation;

import io.dataframe.column.IntColumn;
import io.dataframe.core.DataFrame;
import io.dataframe.expression.*;
import io.dataframe.expression.operator.ComparisonOperator;
import io.dataframe.expression.operator.LogicalOperator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NotEvaluatorTest {

    @Test
    void shouldEvaluateNotExpression() {

        DataFrame dataFrame = DataFrame.of(
                IntColumn.of("Age", 25, 30, 35)
        );

        NotExpression expression = NotExpression.of(
                ComparisonExpression.of(
                        ColumnExpression.of("Age"),
                        LiteralExpression.of(20),
                        ComparisonOperator.GREATER_THAN
                )
        );

        boolean result = (Boolean) ExpressionEvaluator.evaluate(
                expression,
                dataFrame, 1
        );

        assertFalse(result);
    }

    @Test
    void shouldEvaluateNestedNotExpression() {

        DataFrame dataFrame = DataFrame.of(
                IntColumn.of("Age", 25, 30, 35)
        );

        NotExpression expression = NotExpression.of(
                NotExpression.of(
                    ComparisonExpression.of(
                            ColumnExpression.of("Age"),
                            LiteralExpression.of(20),
                            ComparisonOperator.GREATER_THAN
                    )
                )
        );

        boolean result = (Boolean) ExpressionEvaluator.evaluate(
                expression,
                dataFrame, 1
        );

        assertTrue(result);
    }

    @Test
    void shouldRejectNonBooleanOperand() {

        DataFrame dataFrame = DataFrame.of();

        NotExpression expression = NotExpression.of(
                LiteralExpression.of(10)
        );

        IndexOutOfBoundsException exception = assertThrows(
                IndexOutOfBoundsException.class,
                () -> ExpressionEvaluator.evaluate(
                        expression,
                        dataFrame,
                        0
                )
        );

        assertEquals(
                "Row index out of bounds: 0",
                exception.getMessage()
        );
    }

    @Test
    void shouldEvaluateNotFalseExpression() {

        DataFrame dataFrame = DataFrame.of(
                IntColumn.of("Age", 25, 30, 35)
        );

        NotExpression expression = NotExpression.of(
                ComparisonExpression.of(
                        ColumnExpression.of("Age"),
                        LiteralExpression.of(100),
                        ComparisonOperator.GREATER_THAN
                )
        );

        boolean result = (Boolean) ExpressionEvaluator.evaluate(
                expression,
                dataFrame, 1
        );

        assertTrue(result);
    }

    @Test
    void shouldEvaluateNotLogicalExpression() {

        DataFrame dataFrame = DataFrame.of(
                IntColumn.of("Age", 25, 30, 35)
        );

        NotExpression expression = NotExpression.of(
                LogicalExpression.of(
                        ComparisonExpression.of(
                                ColumnExpression.of("Age"),
                                LiteralExpression.of(20),
                                ComparisonOperator.GREATER_THAN
                        ),
                        ComparisonExpression.of(
                                ColumnExpression.of("Age"),
                                LiteralExpression.of(40),
                                ComparisonOperator.LESS_THAN
                        ),
                        LogicalOperator.AND
                )
        );

        boolean result = (Boolean) ExpressionEvaluator.evaluate(
                expression,
                dataFrame, 1
        );

        assertFalse(result);
    }
}
