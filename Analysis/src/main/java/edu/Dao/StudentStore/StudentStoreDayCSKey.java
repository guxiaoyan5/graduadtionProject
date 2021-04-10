package edu.Dao.StudentStore;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class StudentStoreDayCSKey implements WritableComparable<StudentStoreDayCSKey> {
    private String sid;
    private int store_id;
    private int month;
    private int year;
    private int day;

    public StudentStoreDayCSKey() {
    }

    @Override
    public String toString() {
        return "StudentStoreDayCSKey{" +
                "sid='" + sid + '\'' +
                ", store_id=" + store_id +
                ", month=" + month +
                ", year=" + year +
                ", day=" + day +
                '}';
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
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

    public StudentStoreDayCSKey(String sid, int store_id, int month, int year, int day) {
        this.sid = sid;
        this.store_id = store_id;
        this.month = month;
        this.year = year;
        this.day = day;
    }

    @Override
    public int compareTo(StudentStoreDayCSKey o) {
        if (this.sid.compareTo(o.sid) > 0) {
            return 1;
        } else if (this.sid.compareTo(o.sid) == 0) {
            if (this.store_id > o.store_id) {
                return 1;
            } else if (this.store_id == o.store_id) {
                if (this.year > o.year) {
                    return 1;
                } else if (this.year == o.year) {
                    if(this.month > o.month){
                        return 1;
                    }else if(this.month == o.month){
                        return Integer.compare(this.day,o.day);
                    }else {
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
        dataOutput.writeUTF(this.sid);
        dataOutput.writeInt(this.store_id);
        dataOutput.writeInt(this.month);
        dataOutput.writeInt(this.year);
        dataOutput.writeInt(this.day);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.sid = dataInput.readUTF();
        this.store_id = dataInput.readInt();
        this.month = dataInput.readInt();
        this.year = dataInput.readInt();
        this.day = dataInput.readInt();
    }
}
