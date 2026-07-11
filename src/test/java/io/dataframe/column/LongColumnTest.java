package io.dataframe.column;

import io.dataframe.exception.column.InvalidColumnException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LongColumnTest {

    @Test
    void shouldCreateLongColumn() {

        LongColumn column = LongColumn.of("Salary", 100L, 200L, 300L);

        assertNotNull(column);
    }

    @Test
    void shouldRejectNullName() {

        InvalidColumnException exception = assertThrows(InvalidColumnException.class, () -> LongColumn.of(null, 100L, 200L));

        assertEquals("Column name cannot be null.", exception.getMessage());
    }

    @Test
    void shouldRejectBlankName() {

        InvalidColumnException exception = assertThrows(InvalidColumnException.class, () -> LongColumn.of("   ", 100L));

        assertEquals("Column name cannot be blank or empty.", exception.getMessage());
    }

    @Test
    void shouldRejectEmptyName() {

        InvalidColumnException exception = assertThrows(InvalidColumnException.class, () -> LongColumn.of("", 100L));

        assertEquals("Column name cannot be blank or empty.", exception.getMessage());
    }

    @Test
    void shouldReturnName() {

        LongColumn column = LongColumn.of("Salary", 100L);

        assertEquals("Salary", column.name());
    }

    @Test
    void shouldReturnDataType() {

        LongColumn column = LongColumn.of("Salary", 100L);

        assertEquals(DataType.LONG, column.type());
    }

    @Test
    void shouldReturnSize() {

        LongColumn column = LongColumn.of("Salary", 100L, 200L, 300L);

        assertEquals(3, column.size());
    }

    @Test
    void shouldReturnValue() {

        LongColumn column = LongColumn.of("Salary", 100L, 200L, 300L);

        assertEquals(200L, column.get(1));
    }

    @Test
    void shouldRejectInvalidIndex() {

        LongColumn column = LongColumn.of("Salary", 100L);

        assertThrows(IndexOutOfBoundsException.class, () -> column.get(2));
    }

    @Test
    void shouldReturnDefensiveCopyOfValues() {

        LongColumn column = LongColumn.of("Salary", 100L, 200L);

        long[] values = column.values();

        values[0] = 999L;

        assertEquals(100L, column.get(0));
    }

    @Test
    void shouldNotImplementEqualsForDifferentValues() {

        LongColumn first = LongColumn.of("Salary", 100L, 200L);
        LongColumn second = LongColumn.of("Salary", 100L, 300L);

        assertNotEquals(first, second);
    }

    @Test
    void shouldDefensivelyCopyInputArray() {

        long[] values = {100L, 200L};

        LongColumn column = LongColumn.of("Salary", values);

        values[0] = 999L;

        assertEquals(100L, column.get(0));
    }
    @Test
    void shouldImplementEquals() {

        LongColumn first = LongColumn.of("Salary", 100L, 200L);
        LongColumn second = LongColumn.of("Salary", 100L, 200L);

        assertEquals(first, second);
    }

    @Test
    void shouldImplementHashCode() {

        LongColumn first = LongColumn.of("Salary", 100L, 200L);
        LongColumn second = LongColumn.of("Salary", 100L, 200L);

        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void shouldImplementToString() {

        LongColumn column = LongColumn.of("Salary", 100L, 200L);

        String expected = "LongColumn[name=Salary, values=[100, 200]]";

        assertEquals(expected, column.toString());
    }
}