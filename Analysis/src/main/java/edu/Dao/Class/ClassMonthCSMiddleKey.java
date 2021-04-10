package edu.Dao.Class;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ClassMonthCSMiddleKey implements WritableComparable<ClassMonthCSMiddleKey> {
    private int class_id;
    private int month;
    private int year;

    public ClassMonthCSMiddleKey() {
    }

    public ClassMonthCSMiddleKey(int class_id, int month, int year) {
        this.class_id = class_id;
        this.month = month;
        this.year = year;
    }

    @Override
    public String toString() {
        return "ClassMonthCSKey{" +
                "class_id=" + class_id +
                ", month=" + month +
                ", year=" + year +
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

    @Override
    public int compareTo(ClassMonthCSMiddleKey o) {
        if (this.class_id > o.class_id) {
            return 1;
        } else if (this.class_id == o.class_id) {
            if(this.year>o.year){
                return 1;
            }else if(this.year==o.year){
                return Integer.compare(this.month,o.month);
            }else {
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
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.class_id = dataInput.readInt();
        this.month = dataInput.readInt();
        this.year = dataInput.readInt();
    }
}
