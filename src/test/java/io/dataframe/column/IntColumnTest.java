package io.dataframe.column;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IntColumnTest {

    @Test
    void shouldCreateIntColumn() {
        IntColumn column = IntColumn.of("Age", 20, 30, 40);
        assertEquals(3, column.size());
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        assertThrows(IllegalArgumentException.class, () -> IntColumn.of(null, 1, 2, 3));
    }

    @Test
    void shouldThrowExceptionWhenNameIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> IntColumn.of(" ", 1, 2, 3));
    }

    @Test
    void shouldDefensivelyCopyArray() {
        int[] values = {20, 30, 40};
        IntColumn column = IntColumn.of("Age", values);
        values[0] = 999;
        assertEquals(20, column.get(0));
    }
}
