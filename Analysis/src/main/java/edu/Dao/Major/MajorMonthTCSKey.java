package edu.Dao.Major;

import edu.Dao.Class.ClassMonthTCSKey;
import edu.Infomation.enumObject.ThreeMeals;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class MajorMonthTCSKey implements WritableComparable<MajorMonthTCSKey> {
    private int major_id;
    private int month;
    private int year;
    private String meal;

    public MajorMonthTCSKey() {
    }

    public MajorMonthTCSKey(int major_id, int month, int year, String meal) {
        this.major_id = major_id;
        this.month = month;
        this.year = year;
        this.meal = meal;
    }

    @Override
    public String toString() {
        return "MajorMonthTCSKey{" +
                "major_id=" + major_id +
                ", month=" + month +
                ", year=" + year +
                ", meal=" + meal +
                '}';
    }

    public int getMajor_id() {
        return major_id;
    }

    public void setMajor_id(int major_id) {
        this.major_id = major_id;
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
    public int compareTo(MajorMonthTCSKey o) {
        if (this.major_id > o.major_id) {
            return 1;
        } else if (this.major_id == o.major_id) {
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
        dataOutput.writeInt(this.major_id);
        dataOutput.writeInt(this.month);
        dataOutput.writeInt(this.year);
        dataOutput.writeUTF(this.meal);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.major_id = dataInput.readInt();
        this.month = dataInput.readInt();
        this.year = dataInput.readInt();
        this.meal = dataInput.readUTF();
    }
}
