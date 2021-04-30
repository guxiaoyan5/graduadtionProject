package edu.Dao.Class;

import edu.Infomation.enumObject.ThreeMeals;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ClassDayTCSKey implements WritableComparable<ClassDayTCSKey> {
    private int class_id;
    private int month;
    private int year;
    private int day;
    private ThreeMeals meal;

    public ClassDayTCSKey() {
    }

    public ClassDayTCSKey(int class_id, int month, int year, int day, ThreeMeals meal) {
        this.class_id = class_id;
        this.month = month;
        this.year = year;
        this.day = day;
        this.meal = meal;
    }

    @Override
    public String toString() {
        return "ClassDayTCSKey{" +
                "class_id=" + class_id +
                ", month=" + month +
                ", year=" + year +
                ", day=" + day +
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

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public ThreeMeals getMeal() {
        return meal;
    }

    public void setMeal(ThreeMeals meal) {
        this.meal = meal;
    }

    @Override
    public int compareTo(ClassDayTCSKey o) {
        if (this.class_id > o.class_id) {
            return 1;
        } else if (this.class_id == o.class_id) {
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
        dataOutput.writeInt(this.class_id);
        dataOutput.writeInt(this.month);
        dataOutput.writeInt(this.year);
        dataOutput.writeInt(this.day);
        dataOutput.writeUTF(this.meal.getMeal());
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.class_id = dataInput.readInt();
        this.month = dataInput.readInt();
        this.year = dataInput.readInt();
        this.day = dataInput.readInt();
        this.meal = ThreeMeals.valueOf(dataInput.readUTF());
    }
}