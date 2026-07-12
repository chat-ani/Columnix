package io.dataframe.column;

import io.dataframe.exception.column.InvalidColumnException;
import io.dataframe.util.ValidationUtils;

/**
 * Represents an immutable column of values within a DataFrame.
 *
 * <p>Every column has a unique name, a fixed data type, and a constant number
 * of elements. Concrete subclasses provide storage optimized for specific
 * value types.
 *
 * <p>This class is immutable and thread-safe.
 *
 * @since 1.0.0
 * @author Anirban Chatterjee
 */
public abstract class Column {

    private final String name;
    private final DataType type;

    protected Column(String name, DataType type) {

        this.name = ValidationUtils.requireNonNull(
                name,
                () -> new InvalidColumnException("Column name cannot be null.")
        );

        ValidationUtils.requireNonBlank(
                this.name,
                () -> new InvalidColumnException("Column name cannot be blank or empty.")
        );

        this.type = ValidationUtils.requireNonNull(
                type,
                () -> new InvalidColumnException("Column type cannot be null.")
        );
    }

    /**
     * Returns the column name.
     *
     * @return the column name
     */
    public final String name() {
        return name;
    }

    /**
     * Returns the data type of this column.
     *
     * @return the column data type
     */
    public final DataType type() {
        return type;
    }

    /**
     * Returns the number of values stored in this column.
     *
     * @return the column size
     */
    public abstract int size();

    /**
     * Returns the value stored at the specified row index.
     *
     * <p>This method provides type-independent access to column values and is
     * primarily intended for internal framework components such as expression
     * evaluation. Concrete column implementations may also expose type-specific
     * accessors (for example, {@code get(int)}) to avoid boxing overhead.
     *
     * @param rowIndex the zero-based row index
     * @return the value at the specified row as an {@link Object}
     * @throws IndexOutOfBoundsException if {@code rowIndex < 0 || rowIndex >= size()}
     */
    public abstract Object value(int rowIndex);
}