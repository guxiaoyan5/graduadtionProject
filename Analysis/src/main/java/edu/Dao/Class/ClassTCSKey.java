package edu.Dao.Class;

import edu.Infomation.enumObject.ThreeMeals;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ClassTCSKey implements WritableComparable<ClassTCSKey> {
    private int class_id;
    private ThreeMeals meal;

    public ClassTCSKey() {
    }

    public ClassTCSKey(int class_id, ThreeMeals meal) {
        this.class_id = class_id;
        this.meal = meal;
    }

    @Override
    public String toString() {
        return "ClassTCSKey{" +
                "class_id=" + class_id +
                ", meal=" + meal +
                '}';
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public ThreeMeals getMeal() {
        return meal;
    }

    public void setMeal(ThreeMeals meal) {
        this.meal = meal;
    }

    @Override
    public int compareTo(ClassTCSKey o) {
        if (this.class_id > o.class_id) {
            return 1;
        } else if (this.class_id == o.class_id) {
            return this.meal.compareTo(o.meal);
        } else {
            return -1;
        }
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.class_id);
        dataOutput.writeUTF(this.meal.getMeal());
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.class_id = dataInput.readInt();
        this.meal = ThreeMeals.valueOf(dataInput.readUTF());
    }
}
