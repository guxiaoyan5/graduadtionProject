package edu.Dao.College;

import edu.Dao.Class.ClassDayCSKey;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class CollegeDayCSKey implements WritableComparable<CollegeDayCSKey> {
    private int college_id;
    private int month;
    private int year;
    private int day;

    public CollegeDayCSKey() {
    }

    public CollegeDayCSKey(int college_id, int month, int year, int day) {
        this.college_id = college_id;
        this.month = month;
        this.year = year;
        this.day = day;
    }

    @Override
    public String toString() {
        return "CollegeDayCSKey{" +
                "college_id=" + college_id +
                ", month=" + month +
                ", year=" + year +
                ", day=" + day +
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

    @Override
    public int compareTo(CollegeDayCSKey o) {
        if (this.college_id > o.college_id) {
            return 1;
        } else if (this.college_id == o.college_id) {
            if (this.year > o.year) {
                return 1;
            } else if (this.year == o.year) {
                if (this.month > o.month) {
                    return 1;
                } else if (this.month == o.month) {
                    return Integer.compare(this.day, o.day);
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
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.college_id = dataInput.readInt();
        this.month = dataInput.readInt();
        this.year = dataInput.readInt();
        this.day = dataInput.readInt();
    }
}
