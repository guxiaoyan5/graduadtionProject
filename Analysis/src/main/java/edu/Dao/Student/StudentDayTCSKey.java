package edu.Dao.Student;

import edu.Infomation.enumObject.ThreeMeals;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.Date;

public class StudentDayTCSKey implements WritableComparable<StudentDayTCSKey> {
    private String sid;
    private int year;
    private int month;
    private int day;
    private String meal;

    public StudentDayTCSKey() {
    }

    public StudentDayTCSKey(String sid, int year, int month, int day, String meal) {
        this.sid = sid;
        this.year = year;
        this.month = month;
        this.day = day;
        this.meal = meal;
    }

    @Override
    public String toString() {
        return "StudentDayTCSKey{" +
                "sid='" + sid + '\'' +
                ", year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", meal=" + meal +
                '}';
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    @Override
    public int compareTo(StudentDayTCSKey o) {
        if (this.sid.compareTo(o.sid) < 0) {
            return -1;
        } else if (this.sid.compareTo(o.sid) == 0) {
            if (this.year < o.year) {
                return -1;
            } else if (this.year == o.year) {
                if (this.month < o.month) {
                    return -1;
                } else if (this.month == o.month) {
                    if (this.day < o.day) {
                        return -1;
                    } else if (this.day == o.day) {
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
        } else {
            return 1;
        }
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(this.sid);
        dataOutput.writeInt(this.year);
        dataOutput.writeInt(this.month);
        dataOutput.writeInt(this.day);
        dataOutput.writeUTF(this.meal);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.sid = dataInput.readUTF();
        this.year = dataInput.readInt();
        this.month = dataInput.readInt();
        this.day = dataInput.readInt();
        this.meal = dataInput.readUTF();
    }
}
