package edu.Dao.Student;

import edu.Infomation.enumObject.ThreeMeals;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class StudentMonthTCSKey implements WritableComparable<StudentMonthTCSKey> {
    private String sid;
    private int month;
    private int year;
    private String meal;

    public StudentMonthTCSKey() {
    }

    public StudentMonthTCSKey(String sid, int month, int year, String meal) {
        this.sid = sid;
        this.month = month;
        this.year = year;
        this.meal = meal;
    }

    @Override
    public String toString() {
        return "StudentMonthTCSKey{" +
                "sid='" + sid + '\'' +
                ", month=" + month +
                ", year=" + year +
                ", meal=" + meal +
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

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    @Override
    public int compareTo(StudentMonthTCSKey o) {
        if (this.sid.compareTo(o.sid) < 0) {
            return -1;
        } else if (this.sid.compareTo(o.sid) == 0) {
            if (this.year < o.year) {
                return -1;
            } else if (this.year == o.year) {
                if (this.month < o.month) {
                    return -1;
                } else if (this.month == o.month) {
                    return this.meal.compareTo(o.meal);
                } else {
                    return 1;
                }
            } else {
                return 1;
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
        dataOutput.writeUTF(this.meal);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.sid = dataInput.readUTF();
        this.month = dataInput.readInt();
        this.year = dataInput.readInt();
        this.meal = dataInput.readUTF();
    }
}
