package io.dataframe.expression;

import io.dataframe.exception.expression.InvalidExpressionException;
import io.dataframe.util.ValidationUtils;

import java.util.Objects;

/**
 * Represents a reference to a column within an expression tree.
 *
 * <p>A {@code ColumnExpression} does not contain column data. Instead, it
 * symbolically identifies a column by name and serves as a building block
 * for constructing query expressions.
 *
 * <p>This class is immutable and thread-safe.
 *
 * @since 1.0.0
 * @author Anirban Chatterjee
 */
public final class ColumnExpression implements Expression {

    private final String columnName;

    /**
     * Creates a new {@code ColumnExpression}.
     *
     * @param columnName the referenced column name
     */
    private ColumnExpression(String columnName) {
        this.columnName = ValidationUtils.requireNonNull(columnName, () -> new InvalidExpressionException("Column name cannot be null."));

        ValidationUtils.requireNonBlank(this.columnName, () -> new InvalidExpressionException("Column name cannot be blank or Empty."));
    }

    /**
     * Creates a {@code ColumnExpression} for the supplied column name.
     *
     * @param columnName the referenced column name
     * @return a new {@code ColumnExpression}
     */
    public static ColumnExpression of(String columnName) {
        return new ColumnExpression(columnName);
    }

    /**
     * Returns the referenced column name.
     *
     * @return the referenced column name
     */
    public String name() {
        return columnName;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof ColumnExpression other)) {
            return false;
        }

        return columnName.equals(other.columnName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(columnName);
    }

    @Override
    public String toString() {
        return "ColumnExpression[columnName=" + columnName + "]";
    }
}