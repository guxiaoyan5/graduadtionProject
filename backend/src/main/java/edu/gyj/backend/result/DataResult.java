package edu.gyj.backend.result;

import java.io.Serializable;

public class DataResult implements Serializable {
    private int value;
    private String label;
    private boolean leaf;

    public DataResult() {
    }

    public DataResult(int value, String label, boolean leaf) {
        this.value = value;
        this.label = label;
        this.leaf = leaf;
    }

    @Override
    public String toString() {
        return "DataResult{" +
                "value=" + value +
                ", label='" + label + '\'' +
                ", leaf=" + leaf +
                '}';
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }
}
