package edu.Dao.College;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class CollegeMonthCSKey implements WritableComparable<CollegeMonthCSKey> {
    private int college_id;
    private int month;
    private int year;

    public CollegeMonthCSKey() {
    }

    public CollegeMonthCSKey(int college_id, int month, int year) {
        this.college_id = college_id;
        this.month = month;
        this.year = year;
    }

    @Override
    public String toString() {
        return "CollegeMonthCSKey{" +
                "college_id=" + college_id +
                ", month=" + month +
                ", year=" + year +
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

    @Override
    public int compareTo(CollegeMonthCSKey o) {
        if(this.college_id>o.college_id){
            return 1;
        }else if(this.college_id == o.college_id){
            if(this.year>o.year){
                return 1;
            }else if (this.year==o.year){
                return Integer.compare(this.month,o.month);
            }else {
                return -1;
            }
        }else {
            return -1;
        }
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.college_id);
        dataOutput.writeInt(this.month);
        dataOutput.writeInt(this.year);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.college_id = dataInput.readInt();
        this.month = dataInput.readInt();
        this.year = dataInput.readInt();
    }
}
