package edu.Dao.Student;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class StudentDayCSValue implements Writable {
    private float money;

    public StudentDayCSValue() {
    }

    public StudentDayCSValue(float money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "StudentDayCSValue{" +
                "money=" + money +
                '}';
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeFloat(this.money);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.money = dataInput.readFloat();
    }
}
