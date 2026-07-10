package io.dataframe.core;

import io.dataframe.column.Column;
import io.dataframe.column.IntColumn;
import io.dataframe.exception.dataframe.InvalidColumnSizeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DataFrameTest {

    private Column ageColumn() {
        return IntColumn.of("Age", 20, 30, 40);
    }

    private Column salaryColumn(){
        return IntColumn.of("Salary", 50000, 60000, 70000);
    }

    @Test
    void shouldCreateEmptyDataFrame() {
        DataFrame dataFrame = DataFrame.of();

        assertTrue(dataFrame.isEmpty());
        assertEquals(0, dataFrame.rowCount());
        assertEquals(0, dataFrame.columnCount());
    }

    @Test
    void shouldCreateDataFrameWithSingleColumn() {
        DataFrame dataFrame = DataFrame.of(ageColumn());

        assertEquals(3, dataFrame.rowCount());
        assertEquals(1, dataFrame.columnCount());
        assertFalse(dataFrame.isEmpty());
    }

    @Test
    void shouldCreateDataFrameWithMultipleColumns() {
        DataFrame dataFrame = DataFrame.of(ageColumn(), salaryColumn());

        assertEquals(3, dataFrame.rowCount());
        assertEquals(2, dataFrame.columnCount());
    }

    @Test
    void shouldReturnFalseWhenDataFrameIsNotEmpty() {
        DataFrame dataFrame = DataFrame.of(ageColumn());

        assertFalse(dataFrame.isEmpty());
    }

    @Test
    void shouldReturnSchema(){
        DataFrame dataFrame = DataFrame.of(ageColumn());

        assertNotNull(dataFrame.schema());
        assertEquals(1, dataFrame.schema().size());
    }

    @Test
    void shouldReturnColumnByName() {
        DataFrame dataFrame = DataFrame.of(ageColumn(), salaryColumn());
        Column column = dataFrame.column("Salary");

        assertEquals("Salary", column.getName());
    }

    @Test
    void shouldReturnImmutableColumns() {
        DataFrame dataFrame = DataFrame.of(ageColumn());

        assertThrows(
                UnsupportedOperationException.class,
                () -> dataFrame.columns().add(IntColumn.of("Salary", 100))
        );
    }

    @Test
    void shouldRejectColumnsWithDifferentRowCounts() {
        Column salary = IntColumn.of("Salary", 50000);

        assertThrows(
                InvalidColumnSizeException.class,
                () -> DataFrame.of(ageColumn(), salary)
        );
    }

    @Test
    void shouldRejectNullColumnArray() {
        assertThrows(
                NullPointerException.class,
                () -> DataFrame.of((Column[]) null)
        );
    }

    @Test
    void shouldRejectNullColumn() {
        assertThrows(
                NullPointerException.class,
                () -> DataFrame.of(ageColumn(), null)
        );
    }
}
