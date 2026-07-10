package io.dataframe.expression.operator;

/**
 * Represents the supported comparison operators.
 *
 * <p>Comparison operators are used by {@code ComparisonExpression}
 * to compare two expressions.
 *
 * @since 1.0.0
 * @author Anirban Chatterjee
 */
public enum ComparisonOperator {

    EQUALS("=="),
    NOT_EQUALS("!="),
    GREATER_THAN(">"),
    GREATER_THAN_OR_EQUAL(">="),
    LESS_THAN("<"),
    LESS_THAN_OR_EQUAL("<=");

    private final String symbol;

    ComparisonOperator(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Returns the symbolic representation of this operator.
     *
     * @return the operator symbol
     */
    public String symbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
