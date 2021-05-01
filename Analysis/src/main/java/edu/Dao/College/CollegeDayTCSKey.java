package edu.Dao.College;

import edu.Dao.Class.ClassDayTCSKey;
import edu.Infomation.enumObject.ThreeMeals;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class CollegeDayTCSKey implements WritableComparable<CollegeDayTCSKey> {
    private int college_id;
    private int month;
    private int year;
    private int day;
    private String meal;

    public CollegeDayTCSKey() {
    }

    public CollegeDayTCSKey(int college_id, int month, int year, int day, String meal) {
        this.college_id = college_id;
        this.month = month;
        this.year = year;
        this.day = day;
        this.meal = meal;
    }

    @Override
    public String toString() {
        return "CollegeDayTCSKey{" +
                "college_id=" + college_id +
                ", month=" + month +
                ", year=" + year +
                ", day=" + day +
                ", meal=" + meal +
                '}';
    }

    public int getCollege_id() {
        return college_id;
    }

    public void setCollege_id(int college_id) {
        this.college_id = college_id;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    @Override
    public int compareTo(CollegeDayTCSKey o) {
        if (this.college_id > o.college_id) {
            return 1;
        } else if (this.college_id == o.college_id) {
            if (this.year > o.year) {
                return 1;
            } else if (this.year == o.year) {
                if (this.month > o.month) {
                    return 1;
                } else if (this.month == o.month) {
                    if (this.day > o.day) {
                        return 1;
                    } else if (this.day == o.day) {
                        return this.meal.compareTo(o.meal);
                    } else {
                        return -1;
                    }
                } else {
                    return -1;
                }
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.college_id);
        dataOutput.writeInt(this.month);
        dataOutput.writeInt(this.year);
        dataOutput.writeInt(this.day);
        dataOutput.writeUTF(this.meal);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.college_id = dataInput.readInt();
        this.month = dataInput.readInt();
        this.year = dataInput.readInt();
        this.day = dataInput.readInt();
        this.meal = dataInput.readUTF();
    }
}
