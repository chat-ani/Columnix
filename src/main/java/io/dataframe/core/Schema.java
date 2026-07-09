package io.dataframe.core;

import io.dataframe.column.Column;

import java.util.*;

public final class Schema {

    private final List<Column> columns;
    private final Map<String, Column> columnMap;

    public Schema(List<Column> columns, Map<String, Column> columnMap) {
        this.columns = columns;
        this.columnMap = columnMap;
    }

    private static void validateColumnName(String columnName) {
        if (columnName == null || columnName.isBlank()) {
            throw new IllegalArgumentException(
                    "Column name cannot be null or blank."
            );
        }
    }

    public static Schema of(Column... columns) {
        validateColumnName(Arrays.toString(columns));

        List<Column> columnList = new ArrayList<>();
        Map<String, Column> columnMap = new LinkedHashMap<>();

        for (Column column : columns) {

            validateColumnName(String.valueOf(column));

            if (columnMap.containsKey(column.getName())) {
                throw new IllegalArgumentException(
                        "Duplicate column name: " + column.getName()
                );
            }

            columnList.add(column);
            columnMap.put(column.getName(), column);
        }

        return new Schema(
                List.copyOf(columnList),
                Map.copyOf(columnMap)
        );
    }

    public int size() {
        return columns.size();
    }

    public boolean isEmpty() {
        return columns.isEmpty();
    }

    public boolean contains(String columnName) {
        validateColumnName(columnName);
        return columnMap.containsKey(columnName);
    }

    public Column column(String columnName) {
        validateColumnName(columnName);
        Column column = columnMap.get(columnName);
        if (column == null) {
            throw new IllegalArgumentException(
                    "Column '" + columnName + "' does not exist."
            );
        }
        return column;
    }

    public List<Column> columns() {
        return columns;
    }
}
