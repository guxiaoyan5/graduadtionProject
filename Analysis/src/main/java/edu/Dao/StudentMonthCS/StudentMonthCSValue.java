package edu.Dao.StudentMonthCS;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class StudentMonthCSValue implements Writable {
    private int consumption_count;
    private float consumption_total_money;

    public StudentMonthCSValue() {
    }

    public StudentMonthCSValue(int consumption_count, float consumption_total_money) {
        this.consumption_count = consumption_count;
        this.consumption_total_money = consumption_total_money;
    }

    public int getConsumption_count() {
        return consumption_count;
    }

    public void setConsumption_count(int consumption_count) {
        this.consumption_count = consumption_count;
    }

    public float getConsumption_total_money() {
        return consumption_total_money;
    }

    public void setConsumption_total_money(float consumption_total_money) {
        this.consumption_total_money = consumption_total_money;
    }

    @Override
    public String toString() {
        return "StudentMonthCSValue{" +
                "consumption_count=" + consumption_count +
                ", consumption_total_money=" + consumption_total_money +
                '}';
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.consumption_count);
        dataOutput.writeFloat(this.consumption_total_money);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.consumption_count = dataInput.readInt();
        this.consumption_total_money = dataInput.readFloat();
    }
}
