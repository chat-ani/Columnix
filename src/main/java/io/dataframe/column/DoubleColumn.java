package io.dataframe.column;

import io.dataframe.exception.column.InvalidColumnException;
import io.dataframe.util.ValidationUtils;

import java.util.Arrays;
import java.util.Objects;

/**
 * An immutable column storing primitive {@code double} values.
 *
 * <p>A {@code DoubleColumn} stores values in a primitive {@code double[]} array,
 * avoiding boxing overhead while providing efficient random access.
 *
 * <p>This class is immutable and thread-safe.
 *
 * @since 1.0.0
 * @author Anirban Chatterjee
 */
public final class DoubleColumn extends Column {

    /**
     * The values stored in this column.
     */
    private final double[] values;

    /**
     * Creates a new {@code DoubleColumn}.
     *
     * @param name the column name
     * @param values the column values
     */
    private DoubleColumn(String name, double... values) {

        super(name, DataType.DOUBLE);

        this.values = ValidationUtils.requireNonNull(
                values,
                () -> new InvalidColumnException("Column values cannot be null.")
        ).clone();
    }

    /**
     * Creates a new immutable {@code DoubleColumn}.
     *
     * @param name the column name
     * @param values the column values
     * @return a new {@code DoubleColumn}
     */
    public static DoubleColumn of(String name, double... values) {
        return new DoubleColumn(name, values);
    }

    /**
     * Returns the value at the specified row index.
     *
     * @param index the row index
     * @return the value at the specified index
     * @throws IndexOutOfBoundsException
     *         if the index is out of range
     *         ({@code index < 0 || index >= size()})
     */
    public double get(int index) {
        return values[index];
    }

    /**
     * Returns a defensive copy of the underlying values.
     *
     * @return a copy of the values
     */
    public double[] values() {
        return values.clone();
    }

    @Override
    public int size() {
        return values.length;
    }

    /**
     * @param rowIndex
     * @return index value of array values
     */
    @Override
    public Object value(int rowIndex) {
        return values[rowIndex];
    }

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }

        if (!(object instanceof DoubleColumn other)) {
            return false;
        }

        return name().equals(other.name())
                && Arrays.equals(values, other.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name(), Arrays.hashCode(values));
    }

    @Override
    public String toString() {
        return "DoubleColumn[name="
                + name()
                + ", values="
                + Arrays.toString(values)
                + "]";
    }
}