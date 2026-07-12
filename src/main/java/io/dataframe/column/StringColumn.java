package io.dataframe.column;

import io.dataframe.exception.column.InvalidColumnException;
import io.dataframe.util.ValidationUtils;

import java.util.Arrays;
import java.util.Objects;

/**
 * An immutable column storing {@link String} values.
 *
 * <p>A {@code StringColumn} stores values in a {@code String[]} array while
 * preserving the original text exactly as provided. Empty strings and blank
 * strings are permitted, but {@code null} values are not.
 *
 * <p>This class is immutable and thread-safe.
 *
 * @since 1.0.0
 * @author Anirban Chatterjee
 */
public final class StringColumn extends Column {

    /**
     * The values stored in this column.
     */
    private final String[] values;

    /**
     * Creates a new {@code StringColumn}.
     *
     * @param name the column name
     * @param values the column values
     */
    private StringColumn(String name, String... values) {

        super(name, DataType.STRING);

        String[] validatedValues = ValidationUtils.requireNonNull(
                values,
                () -> new InvalidColumnException("Column values cannot be null.")
        );

        ValidationUtils.requireNoNullElements(
                validatedValues,
                () -> new InvalidColumnException("Column values cannot contain null elements.")
        );


        this.values = validatedValues.clone();
    }

    /**
     * Creates a new immutable {@code StringColumn}.
     *
     * @param name the column name
     * @param values the column values
     * @return a new {@code StringColumn}
     */
    public static StringColumn of(String name, String... values) {
        return new StringColumn(name, values);
    }

    /**
     * Returns the value at the specified row index.
     *
     * @param index the row index
     * @return the value at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range ({@code index < 0 || index >= size()})
     */
    public String get(int index) {
        return values[index];
    }

    /**
     * Returns a defensive copy of the underlying values.
     *
     * @return a copy of the values
     */
    public String[] values() {
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

        if (!(object instanceof StringColumn other)) {
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
        return "StringColumn[name="
                + name()
                + ", values="
                + Arrays.toString(values)
                + "]";
    }
}
