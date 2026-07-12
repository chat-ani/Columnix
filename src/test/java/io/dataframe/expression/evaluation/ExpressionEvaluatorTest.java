package io.dataframe.expression.evaluation;

import io.dataframe.column.BooleanColumn;
import io.dataframe.column.IntColumn;
import io.dataframe.column.StringColumn;
import io.dataframe.core.DataFrame;
import io.dataframe.exception.schema.ColumnNotFoundException;
import io.dataframe.expression.ColumnExpression;
import io.dataframe.expression.ComparisonExpression;
import io.dataframe.expression.LiteralExpression;
import io.dataframe.expression.operator.ComparisonOperator;
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

    @Test
    void shouldEvaluateLiteralExpression() {

        DataFrame dataFrame = DataFrame.of(IntColumn.of("Age", 27));

        LiteralExpression<Integer> expression = LiteralExpression.of(10);

        Object result = ExpressionEvaluator.evaluate(
                expression,
                dataFrame,
                0
        );

        assertEquals(10, result);
    }

    @Test
    void shouldEvaluateStringLiteralExpression() {

        DataFrame dataFrame = DataFrame.of(IntColumn.of("Age", 27));

        LiteralExpression<String> expression = LiteralExpression.of("Hello");

        Object result = ExpressionEvaluator.evaluate(
                expression,
                dataFrame,
                0
        );

        assertEquals("Hello", result);
    }

    @Test
    void shouldCreateIntColumnExpression() {

        DataFrame dataFrame = DataFrame.of(
                IntColumn.of("Age", 25, 30, 35)
        );

        ColumnExpression expression = ColumnExpression.of("Age");

        Object result = ExpressionEvaluator.evaluate(
                expression,
                dataFrame,
                1
        );

        assertEquals(30, result);
    }

    @Test
    void shouldCreateStringColumnExpression() {

        DataFrame dataFrame = DataFrame.of(
                StringColumn.of("Name", "Alice", "Bob", "Charlie")
        );

        ColumnExpression expression = ColumnExpression.of("Name");

        Object result = ExpressionEvaluator.evaluate(
                expression,
                dataFrame,
                2
        );

        assertEquals("Charlie", result);
    }

    @Test
    void shouldCreateBooleanColumnExpression() {

        DataFrame dataFrame = DataFrame.of(
                BooleanColumn.of("isActive", true, true, false)
        );

        ColumnExpression expression = ColumnExpression.of("isActive");

        Object result = ExpressionEvaluator.evaluate(
                expression,
                dataFrame,
                2
        );

        assertEquals(false, result);
    }

    @Test
    void shouldRejectUnknownColumn() {

        DataFrame dataFrame = DataFrame.of(
                IntColumn.of("Age", 25, 30, 35)
        );

        ColumnExpression expression = ColumnExpression.of("Name");

        ColumnNotFoundException exception = assertThrows(
                ColumnNotFoundException.class,
                () -> ExpressionEvaluator.evaluate(
                        expression,
                        dataFrame,
                        2
                )
        );

        assertEquals(
                "Column 'Name' could not be found in the schema.",
                exception.getMessage()
        );
    }

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