package edu.gyj.backend.result;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ConsumeResult implements Serializable {
    private int id;
    private String name;
    private List<ConsumeData> consumeData;
    private List<ConsumeThreeData> consumeThreeData;

    public ConsumeResult() {
    }

    public ConsumeResult(int id, String name) {
        this.id = id;
        this.name = name;
        this.consumeData = new ArrayList<>();
        this.consumeThreeData = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "ConsumeResult{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", consumeData=" + consumeData +
                ", consumeThreeData=" + consumeThreeData +
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

    public List<ConsumeData> getConsumeData() {
        return consumeData;
    }

    public void setConsumeData(List<ConsumeData> consumeData) {
        this.consumeData = consumeData;
    }

    public List<ConsumeThreeData> getConsumeThreeData() {
        return consumeThreeData;
    }

    public void setConsumeThreeData(List<ConsumeThreeData> consumeThreeData) {
        this.consumeThreeData = consumeThreeData;
    }
}
