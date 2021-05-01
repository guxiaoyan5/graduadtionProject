package edu.Infomation.Class;

import edu.Infomation.enumObject.ThreeMeals;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClassMonthTCS implements DBWritable, Writable {
    private int class_id;
    private String meal;
    private int month;
    private int year;
    private int consumption_count;
    private float consumption_total_money;
    private float consumption_average_money;
    private float consumption_student_average_money;
    private int student_count;
    private int consumption_low_count;
    private int consumption_high_count;
    private int student_low_count;
    private int student_high_count;

    public ClassMonthTCS() {
    }

    public ClassMonthTCS(int class_id, String meal, int month, int year, int consumption_count, float consumption_total_money, float consumption_average_money, float consumption_student_average_money, int student_count, int consumption_low_count, int consumption_high_count, int student_low_count, int student_high_count) {
        this.class_id = class_id;
        this.meal = meal;
        this.month = month;
        this.year = year;
        this.consumption_count = consumption_count;
        this.consumption_total_money = consumption_total_money;
        this.consumption_average_money = consumption_average_money;
        this.consumption_student_average_money = consumption_student_average_money;
        this.student_count = student_count;
        this.consumption_low_count = consumption_low_count;
        this.consumption_high_count = consumption_high_count;
        this.student_low_count = student_low_count;
        this.student_high_count = student_high_count;
    }

    @Override
    public String toString() {
        return "ClassMonthTCS{" +
                "class_id=" + class_id +
                ", meal=" + meal +
                ", month=" + month +
                ", year=" + year +
                ", consumption_count=" + consumption_count +
                ", consumption_total_money=" + consumption_total_money +
                ", consumption_average_money=" + consumption_average_money +
                ", consumption_student_average_money=" + consumption_student_average_money +
                ", student_count=" + student_count +
                ", consumption_low_count=" + consumption_low_count +
                ", consumption_high_count=" + consumption_high_count +
                ", student_low_count=" + student_low_count +
                ", student_high_count=" + student_high_count +
                '}';
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
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

    public float getConsumption_average_money() {
        return consumption_average_money;
    }

    public void setConsumption_average_money(float consumption_average_money) {
        this.consumption_average_money = consumption_average_money;
    }

    public float getConsumption_student_average_money() {
        return consumption_student_average_money;
    }

    public void setConsumption_student_average_money(float consumption_student_average_money) {
        this.consumption_student_average_money = consumption_student_average_money;
    }

    public int getStudent_count() {
        return student_count;
    }

    public void setStudent_count(int student_count) {
        this.student_count = student_count;
    }

    public int getConsumption_low_count() {
        return consumption_low_count;
    }

    public void setConsumption_low_count(int consumption_low_count) {
        this.consumption_low_count = consumption_low_count;
    }

    public int getConsumption_high_count() {
        return consumption_high_count;
    }

    public void setConsumption_high_count(int consumption_high_count) {
        this.consumption_high_count = consumption_high_count;
    }

    public int getStudent_low_count() {
        return student_low_count;
    }

    public void setStudent_low_count(int student_low_count) {
        this.student_low_count = student_low_count;
    }

    public int getStudent_high_count() {
        return student_high_count;
    }

    public void setStudent_high_count(int student_high_count) {
        this.student_high_count = student_high_count;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.class_id);
        dataOutput.writeUTF(this.meal);
        dataOutput.writeInt(this.month);
        dataOutput.writeInt(this.year);
        dataOutput.writeInt(this.consumption_count);
        dataOutput.writeFloat(this.consumption_total_money);
        dataOutput.writeFloat(this.consumption_average_money);
        dataOutput.writeFloat(this.consumption_student_average_money);
        dataOutput.writeInt(this.student_count);
        dataOutput.writeInt(this.consumption_low_count);
        dataOutput.writeInt(this.consumption_high_count);
        dataOutput.writeInt(this.student_low_count);
        dataOutput.writeInt(this.student_high_count);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.class_id = dataInput.readInt();
        this.meal = dataInput.readUTF();
        this.month = dataInput.readInt();
        this.year = dataInput.readInt();
        this.consumption_count = dataInput.readInt();
        this.consumption_total_money = dataInput.readFloat();
        this.consumption_average_money = dataInput.readFloat();
        this.consumption_student_average_money = dataInput.readFloat();
        this.student_count = dataInput.readInt();
        this.consumption_low_count = dataInput.readInt();
        this.consumption_high_count = dataInput.readInt();
        this.student_low_count = dataInput.readInt();
        this.student_high_count = dataInput.readInt();
    }

    @Override
    public void write(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setInt(1, this.class_id);
        preparedStatement.setString(2,this.meal);
        preparedStatement.setInt(3, this.month);
        preparedStatement.setInt(4, this.year);
        preparedStatement.setInt(5, this.consumption_count);
        preparedStatement.setFloat(6, this.consumption_total_money);
        preparedStatement.setFloat(7, this.consumption_average_money);
        preparedStatement.setFloat(8, this.consumption_student_average_money);
        preparedStatement.setInt(9, this.student_count);
        preparedStatement.setInt(10,this.consumption_low_count);
        preparedStatement.setInt(11,this.consumption_high_count);
        preparedStatement.setInt(12,this.student_low_count);
        preparedStatement.setInt(13,this.student_high_count);
    }

    @Override
    public void readFields(ResultSet resultSet) throws SQLException {
        this.class_id = resultSet.getInt(1);
        this.meal = resultSet.getString(2);
        this.month = resultSet.getInt(3);
        this.year = resultSet.getInt(4);
        this.consumption_count = resultSet.getInt(5);
        this.consumption_total_money = resultSet.getFloat(6);
        this.consumption_average_money = resultSet.getFloat(7);
        this.consumption_student_average_money = resultSet.getFloat(8);
        this.student_count = resultSet.getInt(9);
        this.consumption_low_count = resultSet.getInt(10);
        this.consumption_high_count = resultSet.getInt(11);
        this.student_low_count = resultSet.getInt(12);
        this.student_high_count = resultSet.getInt(13);
    }
}
