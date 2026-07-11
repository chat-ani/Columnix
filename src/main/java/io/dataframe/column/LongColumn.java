package io.dataframe.column;

import io.dataframe.exception.schema.InvalidColumnException;
import io.dataframe.util.ValidationUtils;

import java.util.Arrays;
import java.util.Objects;

/**
 * An immutable column storing primitive {@code long} values.
 *
 * <p>A {@code LongColumn} stores values in a primitive {@code long[]} array,
 * avoiding boxing overhead while providing efficient random access.
 *
 * <p>This class is immutable and thread-safe.
 *
 * @since 1.0.0
 * @author Anirban Chatterjee
 */
public final class LongColumn extends Column {

    /**
     * The values stored in this column.
     */
    private final long[] values;

    /**
     * Creates a new {@code LongColumn}.
     *
     * @param name the column name
     * @param values the column values
     */
    private LongColumn(String name, long... values) {

        super(name, DataType.LONG);

        this.values = ValidationUtils.requireNonNull(
                values,
                () -> new InvalidColumnException("Column values cannot be null.")
        ).clone();
    }

    /**
     * Creates a new immutable {@code LongColumn}.
     *
     * @param name the column name
     * @param values the column values
     * @return a new {@code LongColumn}
     */
    public static LongColumn of(String name, long... values) {
        return new LongColumn(name, values);
    }

    /**
     * Returns the value at the specified row index.
     *
     * @param index the row index
     * @return the value at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range ({@code index < 0 || index >= size()})     */
    public long get(int index) {
        return values[index];
    }

    /**
     * Returns a defensive copy of the underlying values.
     *
     * @return a copy of the values
     */
    public long[] values() {
        return values.clone();
    }

    @Override
    public int size() {
        return values.length;
    }

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }

        if (!(object instanceof LongColumn other)) {
            return false;
        }

        return name().equals(other.name()) && Arrays.equals(values, other.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name(), Arrays.hashCode(values));
    }

    @Override
    public String toString() {
        return "LongColumn[name="
                + name()
                + ", values="
                + Arrays.toString(values)
                + "]";
    }
}