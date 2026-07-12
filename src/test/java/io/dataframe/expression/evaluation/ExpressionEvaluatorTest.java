package io.dataframe.expression.evaluation;

import io.dataframe.column.BooleanColumn;
import io.dataframe.column.IntColumn;
import io.dataframe.column.StringColumn;
import io.dataframe.core.DataFrame;
import io.dataframe.exception.schema.ColumnNotFoundException;
import io.dataframe.expression.ColumnExpression;
import io.dataframe.expression.LiteralExpression;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

//    @Test
//    void shouldRejectUnsupportedExpression() {
//
//        DataFrame dataFrame = DataFrame.of(
//                IntColumn.of("Age", 25)
//        );
//
//        LiteralExpression<Integer> expression = LiteralExpression.of(10);
//
//        UnsupportedOperationException exception = assertThrows(
//                UnsupportedOperationException.class,
//                () -> ExpressionEvaluator.evaluate(
//                        expression,
//                        dataFrame,
//                        0
//                )
//        );
//
//        assertEquals(
//                "Expression evaluation has not been implemented yet.",
//                exception.getMessage()
//        );
//    }

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
}