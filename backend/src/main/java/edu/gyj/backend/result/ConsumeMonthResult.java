package edu.gyj.backend.result;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ConsumeMonthResult implements Serializable {
    private int id;
    private String name;
    private List<ConsumeMonthData> consumeMonthData;

    public ConsumeMonthResult(int id, String name) {
        this.id = id;
        this.name = name;
        this.consumeMonthData = new ArrayList<>();
    }

    public ConsumeMonthResult() {
    }

    @Override
    public String toString() {
        return "ConsumeMonthResult{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", consumeMonthData=" + consumeMonthData +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ConsumeMonthData> getConsumeMonthData() {
        return consumeMonthData;
    }

    public void setConsumeMonthData(List<ConsumeMonthData> consumeMonthData) {
        this.consumeMonthData = consumeMonthData;
    }

    public ConsumeMonthResult(int id, String name, List<ConsumeMonthData> consumeMonthData) {
        this.id = id;
        this.name = name;
        this.consumeMonthData = consumeMonthData;
    }
}
