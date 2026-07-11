package io.dataframe.core;

import io.dataframe.column.Column;
import io.dataframe.exception.schema.ColumnNotFoundException;
import io.dataframe.exception.schema.DuplicateColumnException;
import io.dataframe.exception.schema.InvalidColumnException;
import io.dataframe.util.ValidationUtils;

import java.util.*;

/**
 * Represents an immutable schema definition for a DataFrame, consisting of an ordered
 * collection of {@link Column} objects.
 * <p>
 * This class ensures that all column names are valid (neither null nor blank)
 * and that duplicate column names are not allowed within the same schema.
 * </p>
 *
 * @author Anirban Chatterjee
 * @since 1.0.0
 */
public final class Schema {

    /**
     * An unmodifiable list of columns preserving the insertion order.
     */
    private final List<Column> columns;

    /**
     * An unmodifiable map mapping column names to their respective {@link Column} objects.
     */
    private final Map<String, Column> columnMap;

    /**
     * Constructs a new {@code Schema} with the specified list and map of columns.
     * <p>
     * <b>Note:</b> It is recommended to use the factory method {@link #of(Column...)}
     * to instantiate this class, as it handles necessary validations and deep immutability.
     * </p>
     *
     * @param columns   the ordered list of columns
     * @param columnMap the map of column names to columns
     */
    public Schema(List<Column> columns, Map<String, Column> columnMap) {
        this.columns = columns;
        this.columnMap = columnMap;
    }

    /**
     * Validates that the provided column name is neither null nor blank.
     *
     * @param columnName the name of the column to validate
     * @throws IllegalArgumentException if the column name is null or blank
     */
    private static void validateColumnName(String columnName) {

        ValidationUtils.requireNonNull(
                columnName,
                () -> new InvalidColumnException("Column name cannot be null.")
        );

        ValidationUtils.requireNonBlank(
                columnName,
                () -> new InvalidColumnException("Column name cannot be blank.")
        );
    }

    /**
     * Creates an immutable {@code Schema} from the given array of columns.
     * <p>
     * This factory method performs strict validation to ensure that no column names
     * are null/blank, and that all column names are unique.
     * </p>
     *
     * @param columns the varargs array of columns to include in the schema
     * @return a new, immutable {@code Schema} instance containing the specified columns
     * @throws DuplicateColumnException if any column name is duplicate
     */
    public static Schema of(Column... columns) {
        // Note: validating Arrays.toString(columns) checks the array representation structure,
        // but individual column string validation happens inside the loop.
        validateColumnName(Arrays.toString(columns));

        List<Column> columnList = new ArrayList<>();
        Map<String, Column> columnMap = new LinkedHashMap<>();

        for (Column column : columns) {

            validateColumnName(String.valueOf(column));

            if (columnMap.containsKey(column.name())) {
                throw new DuplicateColumnException(
                        String.format(
                                "Duplicate column name '%s' found while creating the schema.",
                                column.name()
                        )
                );
            }

            columnList.add(column);
            columnMap.put(column.name(), column);
        }

        return new Schema(
                List.copyOf(columnList),
                Map.copyOf(columnMap)
        );
    }

    /**
     * Returns the total number of columns in this schema.
     *
     * @return the number of columns
     */
    public int size() {
        return columns.size();
    }

    /**
     * Checks if the schema contains no columns.
     *
     * @return {@code true} if the schema is empty; {@code false} otherwise
     */
    public boolean isEmpty() {
        return columns.isEmpty();
    }

    /**
     * Checks if a column with the specified name exists within this schema.
     *
     * @param columnName the name of the column to look for
     * @return {@code true} if the column exists; {@code false} otherwise
     * @throws IllegalArgumentException if the provided column name is null or blank
     */
    public boolean contains(String columnName) {
        validateColumnName(columnName);
        return columnMap.containsKey(columnName);
    }

    /**
     * Retrieves the {@link Column} associated with the specified name.
     *
     * @param columnName the name of the column to retrieve
     * @return the matching {@link Column} instance
     * @throws ColumnNotFoundException if the column name is null/blank, or if no
     *                                  column with the specified name exists
     */
    public Column column(String columnName) {
        validateColumnName(columnName);
        Column column = columnMap.get(columnName);
        if (column == null) {
            throw new ColumnNotFoundException(
                    String.format(
                            "Column '%s' does not exist in the schema.",
                            columnName
                    )
            );
        }
        return column;
    }

    /**
     * Returns an unmodifiable view of the columns in this schema.
     *
     * @return an unmodifiable {@link List} of {@link Column} objects
     */
    public List<Column> columns() {
        return columns;
    }
}