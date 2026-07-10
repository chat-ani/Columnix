package io.dataframe.expression;

import io.dataframe.exception.expression.InvalidExpressionException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ColumnExpressionTest {

    @Test
    void shouldCreateColumnExpression() {
        ColumnExpression expression = ColumnExpression.of("Age");

        assertNotNull(expression);
    }

    @Test
    void shouldRejectNullColumnName() {
        InvalidExpressionException exception = assertThrows(InvalidExpressionException.class, () -> ColumnExpression.of(null));

        assertEquals("Column name cannot be null.", exception.getMessage()
        );
    }

    @Test
    void shouldRejectBlankColumnName() {
        InvalidExpressionException exception = assertThrows(InvalidExpressionException.class, () -> ColumnExpression.of("   "));

        assertEquals("Column name cannot be blank or Empty.", exception.getMessage());
    }

    @Test
    void shouldReturnColumnName() {
        ColumnExpression expression = ColumnExpression.of("Salary");

        assertEquals("Salary", expression.name());
    }

    @Test
    void shouldImplementEquals() {
        ColumnExpression first = ColumnExpression.of("Age");
        ColumnExpression second = ColumnExpression.of("Age");
        ColumnExpression third = ColumnExpression.of("Salary");

        assertEquals(first, second);
        assertNotEquals(first, third);
        assertNotEquals(null, first);
        assertNotEquals("Age", first.toString());
    }

    @Test
    void shouldImplementHashCode() {
        ColumnExpression first = ColumnExpression.of("Age");
        ColumnExpression second = ColumnExpression.of("Age");

        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void shouldImplementToString() {
        ColumnExpression expression = ColumnExpression.of("Age");

        assertEquals("ColumnExpression[columnName=Age]", expression.toString());
    }

    @Test
    void shouldBeEqualToItself() {
        ColumnExpression expression = ColumnExpression.of("Age");

        assertEquals(expression, expression);
    }

    @Test
    void shouldRejectEmptyColumnName() {
        InvalidExpressionException exception = assertThrows(InvalidExpressionException.class, () -> ColumnExpression.of(""));

        assertEquals("Column name cannot be blank or Empty.", exception.getMessage());
    }
}
