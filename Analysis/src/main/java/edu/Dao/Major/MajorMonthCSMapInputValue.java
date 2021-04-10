package edu.Dao.Major;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MajorMonthCSMapInputValue implements Writable, DBWritable {
    private int class_id;
    private int major_id;
    private int month;
    private int year;
    private int consumption_count;
    private float consumption_total_money;
    private int student_count;

    public MajorMonthCSMapInputValue() {
    }

    public MajorMonthCSMapInputValue(int class_id, int major_id, int month, int year, int consumption_count, float consumption_total_money, int student_count) {
        this.class_id = class_id;
        this.major_id = major_id;
        this.month = month;
        this.year = year;
        this.consumption_count = consumption_count;
        this.consumption_total_money = consumption_total_money;
        this.student_count = student_count;
    }

    @Override
    public String toString() {
        return "MajorMonthCSMapInputValue{" +
                "class_id=" + class_id +
                ", major_id=" + major_id +
                ", month=" + month +
                ", year=" + year +
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

    public int getMajor_id() {
        return major_id;
    }

    public void setMajor_id(int major_id) {
        this.major_id = major_id;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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
        dataOutput.writeInt(this.major_id);
        dataOutput.writeInt(this.month);
        dataOutput.writeInt(this.year);
        dataOutput.writeInt(this.consumption_count);
        dataOutput.writeFloat(this.consumption_total_money);
        dataOutput.writeInt(this.student_count);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.class_id = dataInput.readInt();
        this.major_id = dataInput.readInt();
        this.month = dataInput.readInt();
        this.year = dataInput.readInt();
        this.consumption_count = dataInput.readInt();
        this.consumption_total_money = dataInput.readFloat();
        this.student_count = dataInput.readInt();
    }

    @Override
    public void write(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setInt(1,this.class_id);
        preparedStatement.setInt(2,this.major_id);
        preparedStatement.setInt(3,this.month);
        preparedStatement.setInt(4,this.year);
        preparedStatement.setInt(5,this.consumption_count);
        preparedStatement.setFloat(6,this.consumption_total_money);
        preparedStatement.setInt(7,this.student_count);
    }

    @Override
    public void readFields(ResultSet resultSet) throws SQLException {
        this.class_id = resultSet.getInt(1);
        this.major_id = resultSet.getInt(2);
        this.month = resultSet.getInt(3);
        this.year = resultSet.getInt(4);
        this.consumption_count = resultSet.getInt(5);
        this.consumption_total_money = resultSet.getFloat(6);
        this.student_count = resultSet.getInt(7);
    }
}
