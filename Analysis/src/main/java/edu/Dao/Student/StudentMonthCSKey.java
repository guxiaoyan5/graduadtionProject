package edu.Dao.Student;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class StudentMonthCSKey implements WritableComparable<StudentMonthCSKey> {
    private String sid;
    private int month;
    private int year;

    public StudentMonthCSKey() {
    }

    public StudentMonthCSKey(String sid, int month, int year) {
        this.sid = sid;
        this.month = month;
        this.year = year;
    }

    @Override
    public String toString() {
        return "StudentMonthCSKey{" +
                "sid='" + sid + '\'' +
                ", month=" + month +
                ", year=" + year +
                '}';
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

    @Override
    public int compareTo(StudentMonthCSKey o) {
        if (this.sid.compareTo(o.sid) < 0) {
            return -1;
        } else if (this.sid.compareTo(o.sid) == 0) {
            if (this.year > o.year) {
                return 1;
            } else if (this.year == o.year) {
                return Integer.compare(this.month, o.month);
            } else {
                return -1;
            }
        } else {
            return 1;
        }

    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(this.sid);
        dataOutput.writeInt(this.month);
        dataOutput.writeInt(this.year);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.sid = dataInput.readUTF();
        this.month = dataInput.readInt();
        this.year = dataInput.readInt();
    }
}
