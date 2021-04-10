package edu.Dao.Major;

import edu.Dao.Class.ClassDayCSKey;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class MajorDayCSKey implements WritableComparable<MajorDayCSKey> {
    private int major_id;
    private int month;
    private int year;
    private int day;

    public MajorDayCSKey() {
    }

    public MajorDayCSKey(int major_id, int month, int year, int day) {
        this.major_id = major_id;
        this.month = month;
        this.year = year;
        this.day = day;
    }

    @Override
    public String toString() {
        return "MajorDayCSKey{" +
                "major_id=" + major_id +
                ", month=" + month +
                ", year=" + year +
                ", day=" + day +
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

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @Override
    public int compareTo(MajorDayCSKey o) {
        if (this.major_id > o.major_id) {
            return 1;
        } else if (this.major_id == o.major_id) {
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
        dataOutput.writeInt(this.major_id);
        dataOutput.writeInt(this.month);
        dataOutput.writeInt(this.year);
        dataOutput.writeInt(this.day);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.major_id = dataInput.readInt();
        this.month = dataInput.readInt();
        this.year = dataInput.readInt();
        this.day = dataInput.readInt();
    }
}
