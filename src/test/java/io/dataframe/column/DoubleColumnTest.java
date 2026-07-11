package io.dataframe.column;

import io.dataframe.exception.column.InvalidColumnException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DoubleColumnTest {

    @Test
    void shouldCreateDoubleColumn() {

        DoubleColumn column = DoubleColumn.of("Salary", 100.36, 200.19, 300.57);

        assertNotNull(column);
    }

    @Test
    void shouldRejectNullName() {

        InvalidColumnException exception = assertThrows(InvalidColumnException.class, () -> DoubleColumn.of(null, 100.36, 200.19));

        assertEquals("Column name cannot be null.", exception.getMessage());
    }

    @Test
    void shouldRejectBlankName() {

        InvalidColumnException exception = assertThrows(InvalidColumnException.class, () -> DoubleColumn.of("   ", 100.36));

        assertEquals("Column name cannot be blank or empty.", exception.getMessage());
    }

    @Test
    void shouldRejectEmptyName() {

        InvalidColumnException exception = assertThrows(InvalidColumnException.class, () -> DoubleColumn.of("", 100.36));

        assertEquals("Column name cannot be blank or empty.", exception.getMessage());
    }

    @Test
    void shouldReturnName() {

        DoubleColumn column = DoubleColumn.of("Salary", 100.36);

        assertEquals("Salary", column.name());
    }

    @Test
    void shouldReturnDataType() {

        DoubleColumn column = DoubleColumn.of("Salary", 100.36);

        assertEquals(DataType.DOUBLE, column.type());
    }

    @Test
    void shouldReturnSize() {

        DoubleColumn column = DoubleColumn.of("Salary", 100.36, 200.19, 300.57);

        assertEquals(3, column.size());
    }

    @Test
    void shouldReturnValue() {

        DoubleColumn column = DoubleColumn.of("Salary", 100.36, 200.19, 300.57);

        assertEquals(200.19, column.get(1), 1e-9);
    }

    @Test
    void shouldRejectInvalidIndex() {

        DoubleColumn column = DoubleColumn.of("Salary", 100.36);

        assertThrows(IndexOutOfBoundsException.class, () -> column.get(2));
    }

    @Test
    void shouldReturnDefensiveCopyOfValues() {

        DoubleColumn column = DoubleColumn.of("Salary", 100.36, 200.19);

        double[] values = column.values();

        values[0] = 999L;

        assertEquals(100.36, column.get(0), 1e-9);
    }

    @Test
    void shouldNotImplementEqualsForDifferentValues() {

        DoubleColumn first = DoubleColumn.of("Salary", 100.36, 200.19);
        DoubleColumn second = DoubleColumn.of("Salary", 100.36, 300.57);

        assertNotEquals(first, second);
    }

    @Test
    void shouldDefensivelyCopyInputArray() {

        double[] values = {100.36, 200.19};

        DoubleColumn column = DoubleColumn.of("Salary", values);

        values[0] = 999.00;

        assertEquals(100.36, column.get(0), 1e-9);
    }
    @Test
    void shouldImplementEquals() {

        DoubleColumn first = DoubleColumn.of("Salary", 100.36, 200.19);
        DoubleColumn second = DoubleColumn.of("Salary", 100.36, 200.19);

        assertEquals(first, second);
    }

    @Test
    void shouldImplementHashCode() {

        DoubleColumn first = DoubleColumn.of("Salary", 100.36, 200.19);
        DoubleColumn second = DoubleColumn.of("Salary", 100.36, 200.19);

        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void shouldImplementToString() {

        DoubleColumn column = DoubleColumn.of("Salary", 100.36, 200.19);

        String expected = "DoubleColumn[name=Salary, values=[100.36, 200.19]]";

        assertEquals(expected, column.toString());
    }
}
