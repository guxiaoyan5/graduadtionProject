package edu.gyj.backend.Input;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

public class DayInput implements Serializable {
    private int[] ids;
    private int id;
    private Date day;

    public DayInput() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DayInput(int id, Date day) {
        this.id = id;
        this.day = day;
    }

    public DayInput(int[] ids, Date day) {
        this.ids = ids;
        this.day = day;
    }

    @Override
    public String toString() {
        return "DayInput{" +
                "ids=" + Arrays.toString(ids) +
                ", day=" + day +
                '}';
    }

    public int[] getIds() {
        return ids;
    }

    public void setIds(int[] ids) {
        this.ids = ids;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }
}
