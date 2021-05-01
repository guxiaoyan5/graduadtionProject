package edu.Dao.Major;

import edu.Dao.Class.ClassTCSKey;
import edu.Infomation.enumObject.ThreeMeals;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class MajorTCSKey implements WritableComparable<MajorTCSKey> {
    private int major_id;
    private ThreeMeals meal;

    public MajorTCSKey() {
    }

    public MajorTCSKey(int major_id, ThreeMeals meal) {
        this.major_id = major_id;
        this.meal = meal;
    }

    @Override
    public String toString() {
        return "MajorTCSKey{" +
                "major_id=" + major_id +
                ", meal=" + meal +
                '}';
    }

    public int getMajor_id() {
        return major_id;
    }

    public void setMajor_id(int major_id) {
        this.major_id = major_id;
    }

    public ThreeMeals getMeal() {
        return meal;
    }

    public void setMeal(ThreeMeals meal) {
        this.meal = meal;
    }

    @Override
    public int compareTo(MajorTCSKey o) {
        if (this.major_id > o.major_id) {
            return 1;
        } else if (this.major_id == o.major_id) {
            return this.meal.compareTo(o.meal);
        } else {
            return -1;
        }
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.major_id);
        dataOutput.writeUTF(this.meal.getMeal());
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.major_id = dataInput.readInt();
        this.meal = ThreeMeals.valueOf(dataInput.readUTF());
    }
}
