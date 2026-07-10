package io.dataframe.column;

import java.util.Arrays;

/**
 * A concrete implementation of {@link Column} specifically for storing primitive 32-bit integers ({@code int}).
 * <p>
 * This class ensures internal data encapsulation by making deep defensive copies of the underlying
 * integer arrays upon construction.
 * </p>
 *
 * @since 1.0.0
 *  @author Anirban Chatterjee
 */
public final class IntColumn extends Column {

    /**
     * The internal primitive array storing the integer elements of this column.
     */
    private final int[] data;

    /**
     * Constructs a new {@code IntColumn} with the specified name and integer array elements.
     * <p>
     * A defensive copy of the provided array is created to prevent external modifications
     * from altering the column's internal state.
     * </p>
     *
     * @param name text identifier for the column; must not be null or blank
     * @param data the array of integers to populate the column
     */
    IntColumn(String name, int[] data) {
        super(name, DataType.INT);
        this.data = Arrays.copyOf(data, data.length);
    }

    /**
     * Static factory method to create an immutable {@code IntColumn} containing the specified values.
     * <p>
     * Usage example:
     * <pre>{@code
     * IntColumn idColumn = IntColumn.of("id", 101, 102, 103);
     * }</pre>
     *
     * @param name   text identifier for the column; must not be null or blank
     * @param values the varargs sequence or array of primitive integers to include
     * @return a new {@code IntColumn} instance populated with the given values
     * @throws IllegalArgumentException if the provided name is null or blank
     */
    public static IntColumn of(String name, int... values) {
        return new IntColumn(name, values);
    }

    /**
     * Retrieves the primitive integer value stored at the specified index.
     *
     * @param index the position of the element to retrieve (0-indexed)
     * @return the {@code int} value at the given index
     * @throws IndexOutOfBoundsException if the index is out of range ({@code index < 0 || index >= size()})
     */
    public int get(int index) {
        return data[index];
    }

    /**
     * Returns the total number of integer elements stored in this column.
     *
     * @return the number of rows/elements in the column
     */
    @Override
    public int size() {
        return data.length;
    }
}