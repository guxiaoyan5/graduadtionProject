package edu.Dao.MajorMonthCS;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class MajorMonthCSValue implements Writable {
    private int class_id;
    private int consumption_count;
    private float consumption_total_money;
    private int student_count;

    public MajorMonthCSValue() {
    }

    public MajorMonthCSValue(int class_id, int consumption_count, float consumption_total_money, int student_count) {
        this.class_id = class_id;
        this.consumption_count = consumption_count;
        this.consumption_total_money = consumption_total_money;
        this.student_count = student_count;
    }

    @Override
    public String toString() {
        return "MajorMonthCSValue{" +
                "class_id=" + class_id +
                ", consumption_count=" + consumption_count +
                ", consumption_total_money=" + consumption_total_money +
                ", student_count=" + student_count +
                '}';
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
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

    public int getStudent_count() {
        return student_count;
    }

    public void setStudent_count(int student_count) {
        this.student_count = student_count;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.class_id);
        dataOutput.writeInt(this.consumption_count);
        dataOutput.writeFloat(this.consumption_total_money);
        dataOutput.writeInt(this.student_count);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.class_id = dataInput.readInt();
        this.consumption_count = dataInput.readInt();
        this.consumption_total_money = dataInput.readFloat();
        this.student_count = dataInput.readInt();
    }
}
