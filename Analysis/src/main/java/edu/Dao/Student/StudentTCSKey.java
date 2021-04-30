package edu.Dao.Student;

import edu.Infomation.enumObject.ThreeMeals;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.Date;

public class StudentTCSKey implements WritableComparable<StudentTCSKey> {
    private String Sid;
    private ThreeMeals meal;

    public StudentTCSKey() {
    }

    public StudentTCSKey(String sid, ThreeMeals meal) {
        Sid = sid;
        this.meal = meal;
    }

    @Override
    public String toString() {
        return "StudentTCSKey{" +
                "Sid='" + Sid + '\'' +
                ", meal=" + meal +
                '}';
    }

    public String getSid() {
        return Sid;
    }

    public void setSid(String sid) {
        Sid = sid;
    }

    public ThreeMeals getMeal() {
        return meal;
    }

    public void setMeal(ThreeMeals meal) {
        this.meal = meal;
    }

    @Override
    public int compareTo(StudentTCSKey o) {
        if(this.Sid.compareTo(o.Sid)<0){
            return -1;
        }else if(this.Sid.compareTo(o.Sid) == 0){
            return this.meal.compareTo(o.meal);
        }else {
            return 1;
        }
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(this.Sid);
        dataOutput.writeUTF(this.meal.getMeal());
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.Sid = dataInput.readUTF();
        this.meal = ThreeMeals.valueOf(dataInput.readUTF());
    }
}
