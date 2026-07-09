package io.dataframe.column;

import java.util.Arrays;

public final class IntColumn extends Column {

    private final int[] data;

    IntColumn(String name, int[] data) {
        super(name, DataType.INT);
        this.data = Arrays.copyOf(data, data.length);
    }

    public static IntColumn of(String name, int... values) {
        return new IntColumn(name, values);
    }

    public int get(int index) {
        return data[index];
    }

    @Override
    public int size() {
        return data.length;
    }
}
