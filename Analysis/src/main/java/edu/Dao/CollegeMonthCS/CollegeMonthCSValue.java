package edu.Dao.CollegeMonthCS;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class CollegeMonthCSValue implements Writable {
    private int major_id;
    private int consumption_count;
    private float consumption_total_money;
    private int student_count;

    public CollegeMonthCSValue() {
    }

    public CollegeMonthCSValue(int major_id, int consumption_count, float consumption_total_money, int student_count) {
        this.major_id = major_id;
        this.consumption_count = consumption_count;
        this.consumption_total_money = consumption_total_money;
        this.student_count = student_count;
    }

    @Override
    public String toString() {
        return "CollegeMonthCSValue{" +
                "major_id=" + major_id +
                ", consumption_count=" + consumption_count +
                ", consumption_total_money=" + consumption_total_money +
                ", student_count=" + student_count +
                '}';
    }

    public int getMajor_id() {
        return major_id;
    }

    public void setMajor_id(int major_id) {
        this.major_id = major_id;
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
        dataOutput.writeInt(this.major_id);
        dataOutput.writeInt(this.consumption_count);
        dataOutput.writeFloat(this.consumption_total_money);
        dataOutput.writeInt(this.student_count);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.major_id = dataInput.readInt();
        this.consumption_count = dataInput.readInt();
        this.consumption_total_money = dataInput.readFloat();
        this.student_count = dataInput.readInt();
    }
}
