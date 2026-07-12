package io.dataframe.column;

import io.dataframe.exception.column.InvalidColumnException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringColumnTest {

    @Test
    void shouldCreateStringColumn() {

        StringColumn column = StringColumn.of(
                "Name",
                "Alice",
                "Bob",
                "Charlie"
        );

        assertNotNull(column);
    }

    @Test
    void shouldRejectNullName() {

        InvalidColumnException exception = assertThrows(
                InvalidColumnException.class,
                () -> StringColumn.of(null, "Alice")
        );

        assertEquals(
                "Column name cannot be null.",
                exception.getMessage()
        );
    }

    @Test
    void shouldRejectBlankName() {

        InvalidColumnException exception = assertThrows(
                InvalidColumnException.class,
                () -> StringColumn.of("   ", "Alice")
        );

        assertEquals(
                "Column name cannot be blank or empty.",
                exception.getMessage()
        );
    }

    @Test
    void shouldRejectEmptyName() {

        InvalidColumnException exception = assertThrows(
                InvalidColumnException.class,
                () -> StringColumn.of("", "Alice")
        );

        assertEquals(
                "Column name cannot be blank or empty.",
                exception.getMessage()
        );
    }

    @Test
    void shouldRejectNullValuesArray() {

        InvalidColumnException exception = assertThrows(
                InvalidColumnException.class,
                () -> StringColumn.of("Name", (String[]) null)
        );

        assertEquals(
                "Column values cannot be null.",
                exception.getMessage()
        );
    }

    @Test
    void shouldRejectNullElement() {

        InvalidColumnException exception = assertThrows(
                InvalidColumnException.class,
                () -> StringColumn.of(
                        "Name",
                        "Alice",
                        null,
                        "Bob"
                )
        );

        assertEquals(
                "Column values cannot contain null elements.",
                exception.getMessage()
        );
    }

    @Test
    void shouldAllowEmptyString() {

        StringColumn column = StringColumn.of(
                "Name",
                "",
                "Alice"
        );

        assertEquals("", column.get(0));
    }

    @Test
    void shouldAllowBlankString() {

        StringColumn column = StringColumn.of(
                "Name",
                "   ",
                "Alice"
        );

        assertEquals("   ", column.get(0));
    }

    @Test
    void shouldPreserveWhitespace() {

        StringColumn column = StringColumn.of(
                "Name",
                " Alice "
        );

        assertEquals(" Alice ", column.get(0));
    }

    @Test
    void shouldReturnName() {

        StringColumn column = StringColumn.of("Name", "Alice");

        assertEquals("Name", column.name());
    }

    @Test
    void shouldReturnDataType() {

        StringColumn column = StringColumn.of("Name", "Alice");

        assertEquals(DataType.STRING, column.type());
    }

    @Test
    void shouldReturnSize() {

        StringColumn column = StringColumn.of(
                "Name",
                "Alice",
                "Bob",
                "Charlie"
        );

        assertEquals(3, column.size());
    }

    @Test
    void shouldReturnValue() {

        StringColumn column = StringColumn.of(
                "Name",
                "Alice",
                "Bob"
        );

        assertEquals("Bob", column.get(1));
    }

    @Test
    void shouldRejectInvalidIndex() {

        StringColumn column = StringColumn.of(
                "Name",
                "Alice"
        );

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> column.get(2)
        );
    }

    @Test
    void shouldReturnDefensiveCopyOfValues() {

        StringColumn column = StringColumn.of(
                "Name",
                "Alice",
                "Bob"
        );

        String[] values = column.values();

        values[0] = "Modified";

        assertEquals("Alice", column.get(0));
    }

    @Test
    void shouldDefensivelyCopyInputArray() {

        String[] values = {"Alice", "Bob"};

        StringColumn column = StringColumn.of(
                "Name",
                values
        );

        values[0] = "Modified";

        assertEquals("Alice", column.get(0));
    }

    @Test
    void shouldNotImplementEqualsForDifferentValues() {

        StringColumn first = StringColumn.of(
                "Name",
                "Alice",
                "Bob"
        );

        StringColumn second = StringColumn.of(
                "Name",
                "Alice",
                "Charlie"
        );

        assertNotEquals(first, second);
    }

    @Test
    void shouldImplementEquals() {

        StringColumn first = StringColumn.of(
                "Name",
                "Alice",
                "Bob"
        );

        StringColumn second = StringColumn.of(
                "Name",
                "Alice",
                "Bob"
        );

        assertEquals(first, second);
    }

    @Test
    void shouldImplementHashCode() {

        StringColumn first = StringColumn.of(
                "Name",
                "Alice",
                "Bob"
        );

        StringColumn second = StringColumn.of(
                "Name",
                "Alice",
                "Bob"
        );

        assertEquals(
                first.hashCode(),
                second.hashCode()
        );
    }

    @Test
    void shouldImplementToString() {

        StringColumn column = StringColumn.of(
                "Name",
                "Alice",
                "Bob"
        );

        String expected =
                "StringColumn[name=Name, values=[Alice, Bob]]";

        assertEquals(expected, column.toString());
    }
}
