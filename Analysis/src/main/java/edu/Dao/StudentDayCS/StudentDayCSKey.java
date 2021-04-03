package edu.Dao.StudentDayCS;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class StudentDayCSKey implements WritableComparable<StudentDayCSKey> {
    private String sid;

    private int month;
    private int year;
    private int day;


    public StudentDayCSKey() {
    }

    @Override
    public String toString() {
        return "StudentDayCSMiddle{" +
                "sid='" + sid + '\'' +
                ", month=" + month +
                ", year=" + year +
                ", day=" + day +
                '}';
    }

    public StudentDayCSKey(String sid, int month, int year, int day) {
        this.sid = sid;

        this.month = month;
        this.year = year;
        this.day = day;
    }


    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
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
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(this.sid);

        dataOutput.writeInt(this.day);
        dataOutput.writeInt(this.month);
        dataOutput.writeInt(this.year);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.sid = dataInput.readUTF();

        this.day = dataInput.readInt();
        this.month = dataInput.readInt();
        this.year = dataInput.readInt();
    }


    @Override
    public int compareTo(StudentDayCSKey o) {
        if (this.sid.compareTo(o.sid) < 0) {
            return -1;
        } else if (this.sid.compareTo(o.sid) == 0) {
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
            return 1;
        }
    }
}