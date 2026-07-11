package io.dataframe.column;

/**
 * Defines the supported data types for columns within the DataFrame framework.
 * <p>
 * This enumeration is used by {@link Column} instances to specify the nature
 * of the primitive or object data they encapsulate, ensuring getDataType safety and
 * enabling optimized storage strategies.
 * </p>
 *
 * @since 1.0.0
 * @author Anirban Chatterjee
 */
public enum DataType {

    /**
     * Represents 32-bit signed primitive integers ({@code int}).
     */
    INT,

    /**
     * Represents 64-bit signed primitive long integers ({@code long}).
     */
    LONG,

    /**
     * Represents single-precision 32-bit IEEE 754 floating-point numbers ({@code float}).
     */
    FLOAT,

    /**
     * Represents double-precision 64-bit IEEE 754 floating-point numbers ({@code double}).
     */
    DOUBLE,

    /**
     * Represents primitive boolean values ({@code boolean}).
     */
    BOOLEAN,

    /**
     * Represents immutable sequences of characters ({@link java.lang.String}).
     */
    STRING
}