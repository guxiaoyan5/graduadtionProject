package edu.gyj.backend.result;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ConsumeDayResult implements Serializable {
    private int id;
    private String name;
    private List<ConsumeDayData> consumeDayData;

    public ConsumeDayResult() {
    }

    public ConsumeDayResult(int id, String name) {
        this.id = id;
        this.name = name;
        this.consumeDayData = new ArrayList<>();
    }

    public ConsumeDayResult(int id, String name, List<ConsumeDayData> consumeDayData) {
        this.id = id;
        this.name = name;
        this.consumeDayData = consumeDayData;
    }

    @Override
    public String toString() {
        return "ConsumeDayResult{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", consumeDayData=" + consumeDayData +
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

    public List<ConsumeDayData> getConsumeDayData() {
        return consumeDayData;
    }

    public void setConsumeDayData(List<ConsumeDayData> consumeDayData) {
        this.consumeDayData = consumeDayData;
    }
}
