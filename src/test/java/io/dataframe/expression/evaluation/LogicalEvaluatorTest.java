package io.dataframe.expression.evaluation;

import io.dataframe.column.IntColumn;
import io.dataframe.core.DataFrame;
import io.dataframe.expression.ColumnExpression;
import io.dataframe.expression.ComparisonExpression;
import io.dataframe.expression.LiteralExpression;
import io.dataframe.expression.LogicalExpression;
import io.dataframe.expression.operator.ComparisonOperator;
import io.dataframe.expression.operator.LogicalOperator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LogicalEvaluatorTest {

    @Test
    void shouldEvaluateAndExpression() {

        DataFrame dataFrame = DataFrame.of(IntColumn.of("Age", 25, 30, 35));

        LogicalExpression expression = LogicalExpression.of(
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
        );

        assertTrue(
                true,
                ExpressionEvaluator.evaluate(
                        expression,
                        dataFrame,
                        1
                ).toString()
        );
    }

    @Test
    void shouldEvaluateOrExpression() {

        DataFrame dataFrame = DataFrame.of(IntColumn.of("Age", 25, 30, 35));

        LogicalExpression expression = LogicalExpression.of(
                ComparisonExpression.of(
                        ColumnExpression.of("Age"),
                        LiteralExpression.of(20),
                        ComparisonOperator.GREATER_THAN
                ),
                ComparisonExpression.of(
                        ColumnExpression.of("Age"),
                        LiteralExpression.of(30),
                        ComparisonOperator.LESS_THAN
                ),
                LogicalOperator.OR
        );

        assertTrue(
                true,
                ExpressionEvaluator.evaluate(
                        expression,
                        dataFrame,
                        1
                ).toString()
        );
    }

    @Test
    void shouldEvaluateFalseAndExpression() {

        DataFrame dataFrame = DataFrame.of(
                IntColumn.of("Age", 25, 30, 35)
        );

        LogicalExpression expression = LogicalExpression.of(
                ComparisonExpression.of(
                        ColumnExpression.of("Age"),
                        LiteralExpression.of(20),
                        ComparisonOperator.GREATER_THAN
                ),
                ComparisonExpression.of(
                        ColumnExpression.of("Age"),
                        LiteralExpression.of(30),
                        ComparisonOperator.LESS_THAN
                ),
                LogicalOperator.AND
        );

        assertFalse(
                (Boolean) ExpressionEvaluator.evaluate(
                        expression,
                        dataFrame,
                        1
                )
        );
    }

    @Test
    void shouldEvaluateFalseOrExpression() {

        DataFrame dataFrame = DataFrame.of(
                IntColumn.of("Age", 25, 30, 35)
        );

        LogicalExpression expression = LogicalExpression.of(
                ComparisonExpression.of(
                        ColumnExpression.of("Age"),
                        LiteralExpression.of(20),
                        ComparisonOperator.LESS_THAN
                ),
                ComparisonExpression.of(
                        ColumnExpression.of("Age"),
                        LiteralExpression.of(40),
                        ComparisonOperator.GREATER_THAN
                ),
                LogicalOperator.OR
        );

        assertFalse(
                (Boolean) ExpressionEvaluator.evaluate(
                        expression,
                        dataFrame,
                        1
                )
        );
    }

    @Test
    void shouldEvaluateNestedLogicalExpression() {

        DataFrame dataFrame = DataFrame.of(
                IntColumn.of("Age", 25, 30, 35)
        );

        LogicalExpression expression = LogicalExpression.of(
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
                ),
                ComparisonExpression.of(
                        ColumnExpression.of("Age"),
                        LiteralExpression.of(100),
                        ComparisonOperator.EQUALS
                ),
                LogicalOperator.OR
        );

        boolean result = (Boolean) ExpressionEvaluator.evaluate(
                expression,
                dataFrame,
                1
        );

        assertTrue(result);
    }

    @Test
    void shouldRejectNonBooleanLeftOperand() {

        DataFrame dataFrame = DataFrame.of(
                IntColumn.of("Age", 25)
        );

        LogicalExpression expression = LogicalExpression.of(
                LiteralExpression.of(10),
                ComparisonExpression.of(
                        ColumnExpression.of("Age"),
                        LiteralExpression.of(20),
                        ComparisonOperator.GREATER_THAN
                ),
                LogicalOperator.AND
        );

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> ExpressionEvaluator.evaluate(
                        expression,
                        dataFrame,
                        0
                )
        );

        assertEquals(
                "Logical operands must be of type Boolean.",
                exception.getMessage()
        );
    }

    @Test
    void shouldRejectNonBooleanRightOperand() {

        DataFrame dataFrame = DataFrame.of(
                IntColumn.of("Age", 25)
        );

        LogicalExpression expression = LogicalExpression.of(
                ComparisonExpression.of(
                        ColumnExpression.of("Age"),
                        LiteralExpression.of(20),
                        ComparisonOperator.GREATER_THAN
                ),
                LiteralExpression.of(10),
                LogicalOperator.AND
        );

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> ExpressionEvaluator.evaluate(
                        expression,
                        dataFrame,
                        0
                )
        );

        assertEquals(
                "Logical operands must be of type Boolean.",
                exception.getMessage()
        );
    }
}
