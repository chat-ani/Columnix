package io.dataframe.expression;

import io.dataframe.expression.operator.ComparisonOperator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ComparisonExpressionTest {

    @Test
    void shouldContainAllComparisonOperators() {
        ComparisonOperator[] operators = ComparisonOperator.values();

        assertEquals(6, operators.length);

        assertEquals(ComparisonOperator.EQUALS, operators[0]);
        assertEquals(ComparisonOperator.NOT_EQUALS, operators[1]);
        assertEquals(ComparisonOperator.GREATER_THAN, operators[2]);
        assertEquals(ComparisonOperator.GREATER_THAN_OR_EQUAL, operators[3]);
        assertEquals(ComparisonOperator.LESS_THAN, operators[4]);
        assertEquals(ComparisonOperator.LESS_THAN_OR_EQUAL, operators[5]);
    }

    @Test
    void shouldReturnCorrectSymbolForEquals() {
        assertEquals("==", ComparisonOperator.EQUALS.symbol());
    }

    @Test
    void shouldReturnCorrectSymbolForNotEquals() {
        assertEquals("!=", ComparisonOperator.NOT_EQUALS.symbol());
    }

    @Test
    void shouldReturnCorrectSymbolForGreaterThan() {
        assertEquals(">", ComparisonOperator.GREATER_THAN.symbol());
    }

    @Test
    void shouldReturnCorrectSymbolForGreaterThanOrEqual() {
        assertEquals(">=", ComparisonOperator.GREATER_THAN_OR_EQUAL.symbol());
    }

    @Test
    void shouldReturnCorrectSymbolForLessThan() {
        assertEquals("<", ComparisonOperator.LESS_THAN.symbol());
    }

    @Test
    void shouldReturnCorrectSymbolForLessThanOrEqual() {
        assertEquals("<=", ComparisonOperator.LESS_THAN_OR_EQUAL.symbol());
    }

    @Test
    void shouldReturnSymbolFromToString() {
        assertEquals("==", ComparisonOperator.EQUALS.toString());
        assertEquals("!=", ComparisonOperator.NOT_EQUALS.toString());
        assertEquals(">", ComparisonOperator.GREATER_THAN.toString());
        assertEquals(">=", ComparisonOperator.GREATER_THAN_OR_EQUAL.toString());
        assertEquals("<", ComparisonOperator.LESS_THAN.toString());
        assertEquals("<=", ComparisonOperator.LESS_THAN_OR_EQUAL.toString());
    }
}
