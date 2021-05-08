package edu.Dao.mealsTime;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class MealsTimeValue implements Writable {
    private int count;

    public MealsTimeValue() {
    }

    public MealsTimeValue(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "MealsTimeValue{" +
                "count=" + count +
                '}';
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.count);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.count = dataInput.readInt();
    }
}
