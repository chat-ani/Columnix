package io.dataframe.expression;

import io.dataframe.exception.expression.InvalidExpressionException;
import io.dataframe.expression.operator.ComparisonOperator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NotExpressionTest {

    @Test
    void shouldCreateNotExpression() {

        ComparisonExpression comparison = ComparisonExpression.of(ColumnExpression.of("Age"), LiteralExpression.of(30), ComparisonOperator.GREATER_THAN);

        NotExpression expression = NotExpression.of(comparison);

        assertNotNull(expression);
    }

    @Test
    void shouldRejectNullExpression() {

        InvalidExpressionException exception = assertThrows(InvalidExpressionException.class, () -> NotExpression.of(null));

        assertEquals("Expression cannot be null.", exception.getMessage());
    }

    @Test
    void shouldReturnExpression() {

        ComparisonExpression comparison = ComparisonExpression.of(ColumnExpression.of("Age"), LiteralExpression.of(30), ComparisonOperator.GREATER_THAN);

        NotExpression expression = NotExpression.of(comparison);

        assertEquals(comparison, expression.expression());
    }

    @Test
    void shouldImplementEquals() {

        ComparisonExpression comparison = ComparisonExpression.of(ColumnExpression.of("Age"), LiteralExpression.of(30), ComparisonOperator.GREATER_THAN);

        NotExpression expression1 = NotExpression.of(comparison);
        NotExpression expression2 = NotExpression.of(comparison);

        assertEquals(expression1, expression2);
    }

    @Test
    void shouldImplementHashCode() {

        ComparisonExpression comparison = ComparisonExpression.of(ColumnExpression.of("Age"), LiteralExpression.of(30), ComparisonOperator.GREATER_THAN);

        NotExpression expression1 = NotExpression.of(comparison);
        NotExpression expression2 = NotExpression.of(comparison);

        assertEquals(expression1.hashCode(), expression2.hashCode());
    }

    @Test
    void shouldImplementToString() {

        ComparisonExpression comparison = ComparisonExpression.of(ColumnExpression.of("Age"), LiteralExpression.of(30), ComparisonOperator.GREATER_THAN);

        NotExpression expression = NotExpression.of(comparison);

        String expected = "NotExpression[expression=ComparisonExpression[left=ColumnExpression[columnName=Age], operator=>, right=LiteralExpression[value=30]]]";

        assertEquals(expected, expression.toString());
    }
}
