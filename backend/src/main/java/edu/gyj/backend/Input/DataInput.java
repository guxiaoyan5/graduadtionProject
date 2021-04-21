package edu.gyj.backend.Input;

import java.io.Serializable;

public class DataInput implements Serializable {
    private int level;
    private int value;

    public DataInput() {
    }

    public DataInput(int level, int value) {
        this.level = level;
        this.value = value;
    }

    @Override
    public String toString() {
        return "DataInput{" +
                "level=" + level +
                ", value=" + value +
                '}';
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
