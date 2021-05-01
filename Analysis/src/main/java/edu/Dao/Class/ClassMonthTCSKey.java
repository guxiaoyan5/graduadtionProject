package edu.Dao.Class;

import edu.Infomation.enumObject.ThreeMeals;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ClassMonthTCSKey implements WritableComparable<ClassMonthTCSKey> {
    private int class_id;
    private int month;
    private int year;
    private String meal;

    public ClassMonthTCSKey() {
    }

    public ClassMonthTCSKey(int class_id, int month, int year, String meal) {
        this.class_id = class_id;
        this.month = month;
        this.year = year;
        this.meal = meal;
    }

    @Override
    public String toString() {
        return "ClassMonthTCSKey{" +
                "class_id=" + class_id +
                ", month=" + month +
                ", year=" + year +
                ", meal=" + meal +
                '}';
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
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

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    @Override
    public int compareTo(ClassMonthTCSKey o) {
        if (this.class_id > o.class_id) {
            return 1;
        } else if (this.class_id == o.class_id) {
            if (this.year > o.year) {
                return 1;
            } else if (this.year == o.year) {
                if (this.month > o.month) {
                    return 1;
                } else if (this.month == o.month) {
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
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.class_id);
        dataOutput.writeInt(this.month);
        dataOutput.writeInt(this.year);
        dataOutput.writeUTF(this.meal);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.class_id = dataInput.readInt();
        this.month = dataInput.readInt();
        this.year = dataInput.readInt();
        this.meal = dataInput.readUTF();
    }
}
