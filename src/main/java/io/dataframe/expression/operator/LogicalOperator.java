package io.dataframe.expression.operator;

/**
 * Represents the supported logical operators.
 *
 * <p>Logical operators are used by {@code LogicalExpression}
 * to combine two boolean expressions.
 *
 * @since 1.0.0
 * @author Anirban Chatterjee
 */
public enum LogicalOperator {

    AND("&&"),
    OR("||");

    private final String symbol;

    LogicalOperator(String symbol) {
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
