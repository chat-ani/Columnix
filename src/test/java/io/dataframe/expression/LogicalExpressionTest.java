package io.dataframe.expression;

import io.dataframe.exception.expression.InvalidExpressionException;
import io.dataframe.expression.operator.ComparisonOperator;
import io.dataframe.expression.operator.LogicalOperator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogicalExpressionTest {

    @Test
    void shouldCreateLogicalExpression() {

        ComparisonExpression left = ComparisonExpression.of(ColumnExpression.of("Age"), LiteralExpression.of(30), ComparisonOperator.GREATER_THAN);
        ComparisonExpression right = ComparisonExpression.of(ColumnExpression.of("Salary"), LiteralExpression.of(50000), ComparisonOperator.GREATER_THAN);

        LogicalExpression expression = LogicalExpression.of(left, right, LogicalOperator.AND);

        assertNotNull(expression);
    }

    @Test
    void shouldRejectNullLeftExpression() {

        InvalidExpressionException exception = assertThrows(
                InvalidExpressionException.class,
                () -> LogicalExpression.of(
                        null,
                        LiteralExpression.of(true),
                        LogicalOperator.AND
                )
        );

        assertEquals("Left expression cannot be null.", exception.getMessage());
    }

    @Test
    void shouldRejectNullRightExpression() {

        InvalidExpressionException exception = assertThrows(
                InvalidExpressionException.class,
                () -> LogicalExpression.of(
                        LiteralExpression.of(true),
                        null,
                        LogicalOperator.AND
                )
        );

        assertEquals("Right expression cannot be null.", exception.getMessage());
    }

    @Test
    void shouldRejectNullLogicalOperator() {

        InvalidExpressionException exception = assertThrows(
                InvalidExpressionException.class,
                () -> LogicalExpression.of(
                        LiteralExpression.of(true),
                        LiteralExpression.of(false),
                        null
                )
        );

        assertEquals("Logical operator cannot be null.", exception.getMessage());
    }

    @Test
    void shouldReturnLeftExpression() {

        Expression left = LiteralExpression.of(true);

        LogicalExpression expression = LogicalExpression.of(left, LiteralExpression.of(false), LogicalOperator.AND);

        assertEquals(left, expression.left());
    }

    @Test
    void shouldReturnRightExpression() {

        Expression right = LiteralExpression.of(false);

        LogicalExpression expression = LogicalExpression.of(LiteralExpression.of(true), right, LogicalOperator.OR);

        assertEquals(right, expression.right());
    }

    @Test
    void shouldReturnLogicalOperator() {

        LogicalExpression expression = LogicalExpression.of(LiteralExpression.of(true), LiteralExpression.of(false), LogicalOperator.OR);

        assertEquals(LogicalOperator.OR, expression.operator());
    }

    @Test
    void shouldImplementEquals() {

        LogicalExpression expression1 = LogicalExpression.of(LiteralExpression.of(true), LiteralExpression.of(false), LogicalOperator.AND);
        LogicalExpression expression2 = LogicalExpression.of(LiteralExpression.of(true), LiteralExpression.of(false), LogicalOperator.AND);

        assertEquals(expression1, expression2);
    }

    @Test
    void shouldImplementHashCode() {

        LogicalExpression expression1 = LogicalExpression.of(LiteralExpression.of(true), LiteralExpression.of(false), LogicalOperator.OR);
        LogicalExpression expression2 = LogicalExpression.of(LiteralExpression.of(true), LiteralExpression.of(false), LogicalOperator.OR);

        assertEquals(expression1.hashCode(), expression2.hashCode());
    }

    @Test
    void shouldImplementToString() {

        LogicalExpression expression = LogicalExpression.of(LiteralExpression.of(true), LiteralExpression.of(false), LogicalOperator.AND);

        String expected = "LogicalExpression[left=LiteralExpression[value=true], operator=&&, right=LiteralExpression[value=false]]";

        assertEquals(expected, expression.toString());
    }
}
