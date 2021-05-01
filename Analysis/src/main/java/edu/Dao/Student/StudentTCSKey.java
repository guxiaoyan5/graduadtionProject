package edu.Dao.Student;

import edu.Infomation.enumObject.ThreeMeals;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.Date;

public class StudentTCSKey implements WritableComparable<StudentTCSKey> {
    private String sid;
    private String meal;

    public StudentTCSKey() {
    }

    public StudentTCSKey(String sid, String meal) {
        this.sid = sid;
        this.meal = meal;
    }

    @Override
    public String toString() {
        return "StudentTCSKey{" +
                "Sid='" + this.sid + '\'' +
                ", meal=" + meal +
                '}';
    }

    public String getSid() {
        return this.sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    @Override
    public int compareTo(StudentTCSKey o) {
        if (this.sid.compareTo(o.sid) < 0) {
            return -1;
        } else if (this.sid.compareTo(o.sid) == 0) {
            return this.meal.compareTo(o.meal);
        } else {
            return 1;
        }
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(this.sid);
        dataOutput.writeUTF(this.meal);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.sid = dataInput.readUTF();
        this.meal = dataInput.readUTF();
    }
}
