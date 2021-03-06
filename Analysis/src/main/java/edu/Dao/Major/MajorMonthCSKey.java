package edu.Dao.Major;

import edu.Dao.Class.ClassMonthCSKey;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class MajorMonthCSKey implements WritableComparable<MajorMonthCSKey> {
    private int major_id;
    private int month;
    private int year;

    public MajorMonthCSKey() {
    }

    public MajorMonthCSKey(int major_id, int month, int year) {
        this.major_id = major_id;
        this.month = month;
        this.year = year;
    }

    @Override
    public String toString() {
        return "MajorMonthCSKey{" +
                "major_id=" + major_id +
                ", month=" + month +
                ", year=" + year +
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

    @Override
    public int compareTo(MajorMonthCSKey o) {
        if (this.major_id > o.major_id) {
            return 1;
        } else if (this.major_id == o.major_id) {
            if (this.year > o.year) {
                return 1;
            } else if (this.year == o.year) {
                return Integer.compare(this.month, o.month);
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
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.major_id = dataInput.readInt();
        this.month = dataInput.readInt();
        this.year = dataInput.readInt();
    }
}
