package edu.Dao.StudentStoreMonthCS;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class StudentStoreMonthCSKey implements WritableComparable<StudentStoreMonthCSKey> {
    private String sid;
    private int store_id;
    private int month;
    private int year;

    public StudentStoreMonthCSKey() {
    }

    public StudentStoreMonthCSKey(String sid, int store_id, int month, int year) {
        this.sid = sid;
        this.store_id = store_id;
        this.month = month;
        this.year = year;
    }

    @Override
    public String toString() {
        return "StudentStoreMonthCSKey{" +
                "sid=" + sid +
                ", store_id=" + store_id +
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

    @Override
    public int compareTo(StudentStoreMonthCSKey o) {
        if (this.sid.compareTo(o.sid) > 0) {
            return 1;
        } else if (this.sid.compareTo(o.sid) == 0) {
            if (this.store_id > o.store_id) {
                return 1;
            } else if (this.store_id == o.store_id) {
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
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.sid = dataInput.readUTF();
        this.store_id = dataInput.readInt();
        this.month = dataInput.readInt();
        this.year = dataInput.readInt();
    }
}
