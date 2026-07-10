package io.dataframe.expression;

import io.dataframe.exception.expression.InvalidExpressionException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LiteralExpressionTest {

    @Test
    void shouldCreateLiteralExpression(){
        LiteralExpression<Integer> expression = LiteralExpression.of(30);

        assertNotNull(expression);
    }

    @Test
    void shouldRejectNullValue() {
        InvalidExpressionException exception = assertThrows(InvalidExpressionException.class, () -> LiteralExpression.of(null));

        assertEquals("Literal value cannot be null.", exception.getMessage()
        );
    }

    @Test
    void shouldReturnValue() {
        LiteralExpression<String> expression = LiteralExpression.of("John");

        assertEquals("John", expression.value());
    }

    @Test
    void shouldImplementEquals() {
        LiteralExpression<String> first = LiteralExpression.of("John");
        LiteralExpression<String> second = LiteralExpression.of("John");
        LiteralExpression<Integer> third = LiteralExpression.of(50000);

        assertEquals(first, second);
        assertNotEquals(first, third);
        assertNotEquals(null, first);
        assertNotEquals("Age", first.toString());
    }

    @Test
    void shouldImplementHashCode() {
        LiteralExpression<Double> first = LiteralExpression.of(3.14);
        LiteralExpression<Double> second = LiteralExpression.of(3.14);

        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void shouldImplementToString() {
        LiteralExpression<Integer> expression = LiteralExpression.of(100);

        assertEquals("LiteralExpression[value=100]", expression.toString());
    }
}
