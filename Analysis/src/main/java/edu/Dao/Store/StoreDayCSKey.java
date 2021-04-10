package edu.Dao.Store;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class StoreDayCSKey implements WritableComparable<StoreDayCSKey> {
    private int store_id;
    private int month;
    private int year;
    private int day;

    public StoreDayCSKey() {
    }

    public StoreDayCSKey(int store_id, int month, int year, int day) {
        this.store_id = store_id;
        this.month = month;
        this.year = year;
        this.day = day;
    }

    @Override
    public String toString() {
        return "StoreDayKey{" +
                "store_id=" + store_id +
                ", month=" + month +
                ", year=" + year +
                ", day=" + day +
                '}';
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

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.store_id);
        dataOutput.writeInt(this.year);
        dataOutput.writeInt(this.month);
        dataOutput.writeInt(this.day);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.store_id = dataInput.readInt();
        this.year = dataInput.readInt();
        this.month = dataInput.readInt();
        this.day = dataInput.readInt();
    }

    @Override
    public int compareTo(StoreDayCSKey o) {
        if(this.store_id > o.store_id){
            return 1;
        }else if(this.store_id == o.store_id){
            if(this.year > o.year){
                return 1;
            }else if(this.year == o.year){
                if(this.month > o.month){
                    return 1;
                }else if(this.month == o.month){
                    return Integer.compare(this.day,o.day);
                }else{
                    return -1;
                }
            }else {
                return -1;
            }
        }else {
            return -1;
        }
    }
}
