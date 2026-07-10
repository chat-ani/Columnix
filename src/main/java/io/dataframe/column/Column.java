package io.dataframe.column;

/**
 * Represents an abstract base class for a data column within a DataFrame.
 * <p>
 * Every column is strongly typed with a {@link DataType} and possesses an immutable,
 * non-empty name. Subclasses must implement the specific storage and behavior
 * according to their underlying data types.
 * </p>
 *
 * @since 1.0.0
 * @author Anirban Chatterjee
 */
public abstract class Column {

    /**
     * The unique, non-blank name of the column.
     */
    private final String name;

    /**
     * The data type of the elements contained within this column.
     */
    private final DataType dataType;

    /**
     * Initializes the essential attributes of a column.
     * <p>
     * This constructor is intended for invocation by subclass constructors to
     * ensure validation rules for the column's name and data type are strictly enforced.
     * </p>
     *
     * @param name     the name of the column; must not be null or blank
     * @param dataType the data type of the column; must not be null
     * @throws IllegalArgumentException if {@code name} is null or blank, or if {@code dataType} is null
     */
    protected Column(String name, DataType dataType) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Column name cannot be null or blank.");
        }
        if (dataType == null) {
            throw new IllegalArgumentException("DataType cannot be null.");
        }
        this.name = name;
        this.dataType = dataType;
    }

    /**
     * Returns the name of this column.
     *
     * @return the column name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the data type of the elements stored in this column.
     *
     * @return the {@link DataType} of this column
     */
    public DataType getDataType() {
        return dataType;
    }

    /**
     * Returns the total number of elements currently stored in this column.
     *
     * @return the size of the column as an integer
     */
    public abstract int size();

}