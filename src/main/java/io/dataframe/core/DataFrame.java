package io.dataframe.core;

import io.dataframe.column.Column;
import io.dataframe.exception.dataframe.InvalidColumnSizeException;

import java.util.List;
import java.util.Objects;

/**
 * Represents an immutable, column-oriented table.
 *
 * <p>A DataFrame consists of a schema and a collection of columns where every
 * column has the same number of rows. Once created, a DataFrame cannot be
 * modified, ensuring thread safety and predictable behavior.
 *
 * <p>This class is immutable and thread-safe.
 *
 * @since 1.0.0
 * @author Anirban Chatterjee
 */
public final class DataFrame {

    private final Schema schema;
    private final int rowCount;

    /**
     * Creates a new immutable DataFrame.
     *
     * @param schema   the schema describing the DataFrame
     * @param rowCount the number of rows
     */
    private DataFrame(Schema schema, int rowCount) {
        this.schema = Objects.requireNonNull(schema, "Schema cannot be null.");
        this.rowCount = rowCount;
    }

    /**
     * Creates an immutable DataFrame from the supplied columns.
     *
     * @param columns the columns that make up the DataFrame
     * @return a new immutable DataFrame
     * @throws NullPointerException     if the column array or any column is null
     *  @throws InvalidColumnSizeException if the supplied columns have different row counts
     */
    public static DataFrame of(Column... columns) {
        Objects.requireNonNull(columns, "Columns cannot be null.");
        Schema schema = Schema.of(columns);
        int rowCount = validateAndDetermineRowCount(columns);
        return new DataFrame(schema, rowCount);
    }

    /**
     * Validates that all columns have the same number of rows and returns
     * the common row count.
     *
     * @param columns the columns to validate
     * @return the common row count
     *  @throws InvalidColumnSizeException if the supplied columns have different row counts
     */
    private static int validateAndDetermineRowCount(Column... columns) {

        if (columns.length == 0) return 0;

        // Take the size of the first column and compare every other column against it.
        int expectedRowCount = columns[0].size();
        for (Column column : columns) {
            if (column.size() != expectedRowCount) {
                throw new InvalidColumnSizeException(
                        String.format(
                                "Column '%s' contains %d rows, but expected %d rows.",
                                column.name(),
                                column.size(),
                                expectedRowCount
                        )
                );
            }
        }

        return expectedRowCount;
    }

    /**
     * Returns the total number of rows.
     *
     * @return the row count
     */
    public int rowCount() {
        return rowCount;
    }

    /**
     * Returns the total number of columns.
     *
     * @return the column count
     */
    public int columnCount() {
        return schema.size();
    }

    /**
     * Returns whether this DataFrame contains no rows.
     *
     * @return {@code true} if the DataFrame has no rows, otherwise {@code false}
     */
    public boolean isEmpty() {
        return rowCount == 0;
    }

    /**
     * Returns the schema of this DataFrame.
     *
     * @return the schema
     */
    public Schema schema() {
        return schema;
    }

    /**
     * Returns the column with the specified name.
     *
     * @param columnName the name of the column
     * @return the matching column
     */
    public Column column(String columnName) {
        return schema.column(columnName);
    }

    /**
     * Returns all columns in this DataFrame.
     *
     * @return an immutable list of columns
     */
    public List<Column> columns() {
        return schema.columns();
    }
}
