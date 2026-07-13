package io.dataframe.expression.evaluation;

import io.dataframe.column.BooleanColumn;
import io.dataframe.column.IntColumn;
import io.dataframe.column.StringColumn;
import io.dataframe.core.DataFrame;
import io.dataframe.expression.ColumnExpression;
import io.dataframe.expression.ComparisonExpression;
import io.dataframe.expression.LiteralExpression;
import io.dataframe.expression.operator.ComparisonOperator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ComparisonEvaluatorTest {

    @Test
    void shouldEvaluateEqualsComparison() {

        DataFrame dataFrame = DataFrame.of(
                IntColumn.of("Age", 25, 30, 35)
        );

        ComparisonExpression expression = ComparisonExpression.of(
                ColumnExpression.of("Age"),
                LiteralExpression.of(30),
                ComparisonOperator.EQUALS
        );

        assertEquals(true, ExpressionEvaluator.evaluate(expression, dataFrame, 1));
    }

    @Test
    void shouldEvaluateNotEqualsComparison() {

        DataFrame dataFrame = DataFrame.of(
                IntColumn.of("Age", 25, 30, 35)
        );

        ComparisonExpression expression = ComparisonExpression.of(
                ColumnExpression.of("Age"),
                LiteralExpression.of(40),
                ComparisonOperator.NOT_EQUALS
        );

        assertEquals(true, ExpressionEvaluator.evaluate(expression, dataFrame, 1));
    }

    @Test
    void shouldEvaluateGreaterThanComparison() {

        DataFrame dataFrame = DataFrame.of(
                IntColumn.of("Age", 25, 30, 35)
        );

        ComparisonExpression expression = ComparisonExpression.of(
                ColumnExpression.of("Age"),
                LiteralExpression.of(20),
                ComparisonOperator.GREATER_THAN
        );

        assertEquals(true, ExpressionEvaluator.evaluate(expression, dataFrame, 0));
    }

    @Test
    void shouldEvaluateGreaterThanOrEqualComparison() {

        DataFrame dataFrame = DataFrame.of(
                IntColumn.of("Age", 25, 30, 35)
        );

        ComparisonExpression expression = ComparisonExpression.of(
                ColumnExpression.of("Age"),
                LiteralExpression.of(30),
                ComparisonOperator.GREATER_THAN_OR_EQUAL
        );

        assertEquals(true, ExpressionEvaluator.evaluate(expression, dataFrame, 1));
    }

    @Test
    void shouldEvaluateLessThanComparison() {

        DataFrame dataFrame = DataFrame.of(
                IntColumn.of("Age", 25, 30, 35)
        );

        ComparisonExpression expression = ComparisonExpression.of(
                ColumnExpression.of("Age"),
                LiteralExpression.of(40),
                ComparisonOperator.LESS_THAN
        );

        assertEquals(true, ExpressionEvaluator.evaluate(expression, dataFrame, 2));
    }

    @Test
    void shouldEvaluateLessThanOrEqualComparison() {

        DataFrame dataFrame = DataFrame.of(
                IntColumn.of("Age", 25, 30, 35)
        );

        ComparisonExpression expression = ComparisonExpression.of(
                ColumnExpression.of("Age"),
                LiteralExpression.of(30),
                ComparisonOperator.LESS_THAN_OR_EQUAL
        );

        assertEquals(true, ExpressionEvaluator.evaluate(expression, dataFrame, 1));
    }

    @Test
    void shouldEvaluateFalseComparison() {

        DataFrame dataFrame = DataFrame.of(
                IntColumn.of("Age", 25)
        );

        ComparisonExpression expression = ComparisonExpression.of(
                ColumnExpression.of("Age"),
                LiteralExpression.of(30),
                ComparisonOperator.GREATER_THAN
        );

        assertEquals(false, ExpressionEvaluator.evaluate(expression, dataFrame, 0));
    }

    @Test
    void shouldEvaluateStringEqualityComparison() {

        DataFrame dataFrame = DataFrame.of(
                StringColumn.of("Name", "Alice", "Bob")
        );

        ComparisonExpression expression = ComparisonExpression.of(
                ColumnExpression.of("Name"),
                LiteralExpression.of("Bob"),
                ComparisonOperator.EQUALS
        );

        assertEquals(true, ExpressionEvaluator.evaluate(expression, dataFrame, 1));
    }

    @Test
    void shouldEvaluateStringOrderingComparison() {

        DataFrame dataFrame = DataFrame.of(
                StringColumn.of("Name", "Alice", "Bob")
        );

        ComparisonExpression expression = ComparisonExpression.of(
                ColumnExpression.of("Name"),
                LiteralExpression.of("Charlie"),
                ComparisonOperator.LESS_THAN
        );

        assertEquals(true, ExpressionEvaluator.evaluate(expression, dataFrame, 1));
    }

    @Test
    void shouldEvaluateBooleanEqualityComparison() {

        DataFrame dataFrame = DataFrame.of(
                BooleanColumn.of("Active", true, false)
        );

        ComparisonExpression expression = ComparisonExpression.of(
                ColumnExpression.of("Active"),
                LiteralExpression.of(true),
                ComparisonOperator.EQUALS
        );

        assertEquals(true, ExpressionEvaluator.evaluate(expression, dataFrame, 0));
    }

    @Test
    void shouldEvaluateBooleanNotEqualsComparison() {

        DataFrame dataFrame = DataFrame.of(
                BooleanColumn.of("Active", true, false)
        );

        ComparisonExpression expression = ComparisonExpression.of(
                ColumnExpression.of("Active"),
                LiteralExpression.of(true),
                ComparisonOperator.NOT_EQUALS
        );

        assertEquals(true, ExpressionEvaluator.evaluate(expression, dataFrame, 1));
    }

    @Test
    void shouldRejectComparisonOfDifferentTypes() {

        DataFrame dataFrame = DataFrame.of(
                IntColumn.of("Age", 25)
        );

        ComparisonExpression expression = ComparisonExpression.of(
                ColumnExpression.of("Age"),
                LiteralExpression.of("25"),
                ComparisonOperator.GREATER_THAN
        );

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> ExpressionEvaluator.evaluate(expression, dataFrame, 0)
        );

        assertEquals(
                "Comparison operands must have the same type.",
                exception.getMessage()
        );
    }

    @Test
    void shouldRejectOrderingComparisonForBooleanValues() {

        DataFrame dataFrame = DataFrame.of(
                BooleanColumn.of("Active", true)
        );

        ComparisonExpression expression = ComparisonExpression.of(
                ColumnExpression.of("Active"),
                LiteralExpression.of(false),
                ComparisonOperator.GREATER_THAN
        );

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> ExpressionEvaluator.evaluate(expression, dataFrame, 0)
        );

        assertEquals(
                "Boolean values do not support relational comparisons.",
                exception.getMessage()
        );
    }
}
