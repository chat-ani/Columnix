package io.dataframe.column;

import io.dataframe.exception.column.InvalidColumnException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BooleanColumnTest {

    @Test
    void shouldCreateBooleanColumn() {

        BooleanColumn column = BooleanColumn.of("IsActive", true, false, true);

        assertNotNull(column);
    }

    @Test
    void shouldRejectNullName() {

        InvalidColumnException exception = assertThrows(
                InvalidColumnException.class,
                () -> BooleanColumn.of(null, true, false)
        );

        assertEquals("Column name cannot be null.", exception.getMessage());
    }

    @Test
    void shouldRejectBlankName() {

        InvalidColumnException exception = assertThrows(
                InvalidColumnException.class,
                () -> BooleanColumn.of("   ", true)
        );

        assertEquals("Column name cannot be blank or empty.", exception.getMessage());
    }

    @Test
    void shouldRejectEmptyName() {

        InvalidColumnException exception = assertThrows(
                InvalidColumnException.class,
                () -> BooleanColumn.of("", true)
        );

        assertEquals("Column name cannot be blank or empty.", exception.getMessage());
    }

    @Test
    void shouldReturnName() {

        BooleanColumn column = BooleanColumn.of("IsActive", true);

        assertEquals("IsActive", column.name());
    }

    @Test
    void shouldReturnDataType() {

        BooleanColumn column = BooleanColumn.of("IsActive", true);

        assertEquals(DataType.BOOLEAN, column.type());
    }

    @Test
    void shouldReturnSize() {

        BooleanColumn column = BooleanColumn.of("IsActive", true, false, true);

        assertEquals(3, column.size());
    }

    @Test
    void shouldReturnValue() {

        BooleanColumn column = BooleanColumn.of("IsActive", true, false, true);

        assertFalse(column.get(1));
    }

    @Test
    void shouldRejectInvalidIndex() {

        BooleanColumn column = BooleanColumn.of("IsActive", true);

        assertThrows(IndexOutOfBoundsException.class,
                () -> column.get(2));
    }

    @Test
    void shouldReturnDefensiveCopyOfValues() {

        BooleanColumn column = BooleanColumn.of("IsActive", true, false);

        boolean[] values = column.values();

        values[0] = false;

        assertTrue(column.get(0));
    }

    @Test
    void shouldNotImplementEqualsForDifferentValues() {

        BooleanColumn first = BooleanColumn.of("IsActive", true, false);
        BooleanColumn second = BooleanColumn.of("IsActive", false, false);

        assertNotEquals(first, second);
    }

    @Test
    void shouldDefensivelyCopyInputArray() {

        boolean[] values = {true, false};

        BooleanColumn column = BooleanColumn.of("IsActive", values);

        values[0] = false;

        assertTrue(column.get(0));
    }

    @Test
    void shouldImplementEquals() {

        BooleanColumn first = BooleanColumn.of("IsActive", true, false);
        BooleanColumn second = BooleanColumn.of("IsActive", true, false);

        assertEquals(first, second);
    }

    @Test
    void shouldImplementHashCode() {

        BooleanColumn first = BooleanColumn.of("IsActive", true, false);
        BooleanColumn second = BooleanColumn.of("IsActive", true, false);

        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void shouldImplementToString() {

        BooleanColumn column = BooleanColumn.of("IsActive", true, false);

        String expected = "BooleanColumn[name=IsActive, values=[true, false]]";

        assertEquals(expected, column.toString());
    }
}