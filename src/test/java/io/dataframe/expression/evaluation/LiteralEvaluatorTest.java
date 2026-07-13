package io.dataframe.expression.evaluation;

import io.dataframe.column.IntColumn;
import io.dataframe.core.DataFrame;
import io.dataframe.expression.LiteralExpression;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LiteralEvaluatorTest {

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
}
