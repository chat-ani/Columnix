package io.dataframe.core;

import io.dataframe.column.Column;
import io.dataframe.column.IntColumn;
import io.dataframe.exception.schema.DuplicateColumnException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SchemaTest {

    private Column ageColumn() {
        return IntColumn.of("Age", 20, 30);
    }

    private Column salaryColumn() {
        return IntColumn.of("Salary", 50000, 60000);
    }

    private Column idColumn() {
        return IntColumn.of("Id", 1, 2);
    }

    @Test
    void shouldCreateEmptySchema() {
        Schema schema = Schema.of();

        assertTrue(schema.isEmpty());
        assertEquals(0, schema.size());
    }

    @Test
    void shouldCreateSchema() {
        Column age = ageColumn();
        Schema schema = Schema.of(age);

        assertFalse(schema.isEmpty());
        assertEquals(1, schema.size());
    }

    @Test
    void shouldPreserveColumnOrder() {
        Schema schema = Schema.of(ageColumn(), salaryColumn(), idColumn());
        List<Column> columns = schema.columns();

        assertEquals("Age", columns.get(0).name());
        assertEquals("Salary", columns.get(1).name());
        assertEquals("Id", columns.get(2).name());
    }

    @Test
    void shouldRejectDuplicateColumnNames() {
        Column age = IntColumn.of("Age", 40, 50);

        DuplicateColumnException exception = assertThrows(
                DuplicateColumnException.class,
                () -> Schema.of(ageColumn(), age)
        );

        assertEquals(
                "Duplicate column name 'Age' found while creating the schema.",
                exception.getMessage()
        );    }

    @Test
    void shouldRejectNullColumns() {

        assertThrows(
                NullPointerException.class,
                () -> Schema.of(ageColumn(), null)
        );
    }

    @Test
    void shouldReturnColumnByName() {
        Schema schema = Schema.of(ageColumn(), salaryColumn());
        Column column = schema.column("Salary");

        assertEquals("Salary", column.name());
    }

    @Test
    void shouldReturnTrueWhenColumnExists() {
        Schema schema = Schema.of(ageColumn(), salaryColumn());

        assertTrue(schema.contains("Age"));
        assertTrue(schema.contains("Salary"));
    }

    @Test
    void shouldReturnFalseWhenColumnDoesNotExist() {
        Schema schema = Schema.of(ageColumn());

        assertFalse(schema.contains("Department"));
    }

    @Test
    void shouldReturnImmutableColumnList() {
        Schema schema = Schema.of(ageColumn());
        List<Column> columns = schema.columns();

        assertThrows(
                UnsupportedOperationException.class,
                () -> columns.add(IntColumn.of("Salary", 100))
        );
    }
}
