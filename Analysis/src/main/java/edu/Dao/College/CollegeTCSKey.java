package edu.Dao.College;

import edu.Infomation.enumObject.ThreeMeals;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class CollegeTCSKey implements WritableComparable<CollegeTCSKey> {
    private int college_id;
    private String meal;

    public CollegeTCSKey() {
    }

    public CollegeTCSKey(int college_id, String meal) {
        this.college_id = college_id;
        this.meal = meal;
    }

    @Override
    public String toString() {
        return "CollegeTCSKey{" +
                "college_id=" + college_id +
                ", meal=" + meal +
                '}';
    }

    public int getCollege_id() {
        return college_id;
    }

    public void setCollege_id(int college_id) {
        this.college_id = college_id;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    @Override
    public int compareTo(CollegeTCSKey o) {
        if (this.college_id > o.college_id) {
            return 1;
        } else if (this.college_id == o.college_id) {
            return this.meal.compareTo(o.meal);
        } else {
            return -1;
        }
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.college_id);
        dataOutput.writeUTF(this.meal);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.college_id = dataInput.readInt();
        this.meal = dataInput.readUTF();
    }
}
