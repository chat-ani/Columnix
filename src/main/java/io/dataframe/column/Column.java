package io.dataframe.column;

public abstract class Column {

    private final String name;
    private final DataType dataType;

    protected Column(String name, DataType dataType) {
        if(name == null || name.isBlank()) {
            throw new IllegalArgumentException("Column name cannot be null or black.");
        }
        if (dataType == null) {
            throw new IllegalArgumentException("DataType cannot be null.");
        }
        this.name = name;
        this.dataType = dataType;
    }

    public String getName() {
        return name;
    }

    public DataType getDataType() {
        return dataType;
    }

    public abstract int size();

}
