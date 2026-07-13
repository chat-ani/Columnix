package io.dataframe.expression.evaluation;

import io.dataframe.column.BooleanColumn;
import io.dataframe.column.IntColumn;
import io.dataframe.column.StringColumn;
import io.dataframe.core.DataFrame;
import io.dataframe.exception.schema.ColumnNotFoundException;
import io.dataframe.expression.ColumnExpression;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ColumnEvaluatorTest {

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
