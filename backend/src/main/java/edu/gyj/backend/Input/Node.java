package edu.gyj.backend.Input;

import java.io.Serializable;

public class Node implements Serializable {
    private int value;
    private String label;
    private int level;

    public Node() {
    }

    public Node(int value, String label, int level) {
        this.value = value;
        this.label = label;
        this.level = level;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                ", label='" + label + '\'' +
                ", level=" + level +
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
