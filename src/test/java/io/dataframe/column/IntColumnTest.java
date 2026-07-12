package io.dataframe.column;

import io.dataframe.exception.column.InvalidColumnException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntColumnTest {

    @Test
    void shouldCreateIntColumn() {

        IntColumn column = IntColumn.of("Salary", 100, 200, 300);

        assertNotNull(column);
    }

    @Test
    void shouldRejectNullName() {

        InvalidColumnException exception = assertThrows(InvalidColumnException.class, () -> IntColumn.of(null, 100, 200));

        assertEquals("Column name cannot be null.", exception.getMessage());
    }

    @Test
    void shouldRejectBlankName() {

        InvalidColumnException exception = assertThrows(InvalidColumnException.class, () -> IntColumn.of("   ", 100));

        assertEquals("Column name cannot be blank or empty.", exception.getMessage());
    }

    @Test
    void shouldRejectEmptyName() {

        InvalidColumnException exception = assertThrows(InvalidColumnException.class, () -> IntColumn.of("", 100));

        assertEquals("Column name cannot be blank or empty.", exception.getMessage());
    }

    @Test
    void shouldReturnName() {

        IntColumn column = IntColumn.of("Salary", 100);

        assertEquals("Salary", column.name());
    }

    @Test
    void shouldReturnDataType() {

        IntColumn column = IntColumn.of("Salary", 100);

        assertEquals(DataType.INT, column.type());
    }

    @Test
    void shouldReturnSize() {

        IntColumn column = IntColumn.of("Salary", 100, 200, 300);

        assertEquals(3, column.size());
    }

    @Test
    void shouldReturnValue() {

        IntColumn column = IntColumn.of("Salary", 100, 200, 300);

        assertEquals(200, column.get(1));
    }

    @Test
    void shouldRejectInvalidIndex() {

        IntColumn column = IntColumn.of("Salary", 100);

        assertThrows(IndexOutOfBoundsException.class, () -> column.get(2));
    }

    @Test
    void shouldReturnDefensiveCopyOfValues() {

        IntColumn column = IntColumn.of("Salary", 100, 200);

        int[] values = column.values();

        values[0] = 999;

        assertEquals(100, column.get(0));
    }

    @Test
    void shouldNotImplementEqualsForDifferentValues() {

        IntColumn first = IntColumn.of("Salary", 100, 200);
        IntColumn second = IntColumn.of("Salary", 100, 300);

        assertNotEquals(first, second);
    }

    @Test
    void shouldDefensivelyCopyInputArray() {

        int[] values = {100, 200};

        IntColumn column = IntColumn.of("Salary", values);

        values[0] = 999;

        assertEquals(100, column.get(0));
    }
    @Test
    void shouldImplementEquals() {

        IntColumn first = IntColumn.of("Salary", 100, 200);
        IntColumn second = IntColumn.of("Salary", 100, 200);

        assertEquals(first, second);
    }

    @Test
    void shouldImplementHashCode() {

        IntColumn first = IntColumn.of("Salary", 100, 200);
        IntColumn second = IntColumn.of("Salary", 100, 200);

        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void shouldImplementToString() {

        IntColumn column = IntColumn.of("Salary", 100, 200);

        String expected = "IntColumn[name=Salary, values=[100, 200]]";

        assertEquals(expected, column.toString());
    }
}