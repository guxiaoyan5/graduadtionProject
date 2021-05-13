package edu.gyj.backend.Input;

import java.util.Arrays;
import java.util.Date;

public class MonthInput {
    private int[] ids;
    private Date year;
    private int id;
    public MonthInput() {
    }

    public MonthInput(Date year, int id) {
        this.year = year;
        this.id = id;
    }

    public MonthInput(int[] ids, Date year) {
        this.ids = ids;
        this.year = year;
    }

    @Override
    public String toString() {
        return "MonthInput{" +
                "ids=" + Arrays.toString(ids) +
                ", year=" + year +
                ", id=" + id +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    public int[] getIds() {
        return ids;
    }

    public void setIds(int[] ids) {
        this.ids = ids;
    }
}
