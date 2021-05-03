package edu.gyj.backend.Input;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class QueryDataInput implements Serializable {
    private List<Node> id;
    private boolean choice;
    private List<Date> date;

    public QueryDataInput() {
    }

    public QueryDataInput(List<Node> id) {
        this.id = id;
    }

    public QueryDataInput(List<Node> id, boolean choice, List<Date> date) {
        this.id = id;
        this.choice = choice;
        this.date = date;
    }

    @Override
    public String toString() {
        return "QueryDataInput{" +
                "id=" + id +
                ", choice=" + choice +
                ", date=" + date +
                '}';
    }

    public List<Node> getId() {
        return id;
    }

    public void setId(List<Node> id) {
        this.id = id;
    }

    public boolean isChoice() {
        return choice;
    }

    public void setChoice(boolean choice) {
        this.choice = choice;
    }

    public List<Date> getDate() {
        return date;
    }

    public void setDate(List<Date> date) {
        this.date = date;
    }
}
