package edu.Infomation.Major;

import edu.Infomation.enumObject.ThreeMeals;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MajorTCS implements Writable, DBWritable {
    private int major_id;
    private ThreeMeals meal;
    private int consumption_count;
    private float consumption_total_money;
    private float consumption_average_money;
    private float consumption_student_average_money;
    private int student_count;
    private int consumption_low_count;
    private int consumption_high_count;
    private int student_low_count;
    private int student_high_count;

    public MajorTCS() {
    }

    public MajorTCS(int major_id, ThreeMeals meal, int consumption_count, float consumption_total_money, float consumption_average_money, float consumption_student_average_money, int student_count, int consumption_low_count, int consumption_high_count, int student_low_count, int student_high_count) {
        this.major_id = major_id;
        this.meal = meal;
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
        return "MajorTCS{" +
                "major_id=" + major_id +
                ", meal=" + meal +
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
        dataOutput.writeInt(this.major_id);
        dataOutput.writeUTF(this.meal.getMeal());
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
        this.major_id = dataInput.readInt();
        this.meal = ThreeMeals.valueOf(dataInput.readUTF());
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
        preparedStatement.setInt(1, this.major_id);
        preparedStatement.setString(2, this.meal.getMeal());
        preparedStatement.setInt(3, this.consumption_count);
        preparedStatement.setFloat(4, this.consumption_total_money);
        preparedStatement.setFloat(5, this.consumption_average_money);
        preparedStatement.setFloat(6, this.consumption_student_average_money);
        preparedStatement.setInt(7, this.student_count);
        preparedStatement.setInt(8, this.consumption_low_count);
        preparedStatement.setInt(9, this.consumption_high_count);
        preparedStatement.setInt(10, this.student_low_count);
        preparedStatement.setInt(11, this.student_high_count);
    }

    @Override
    public void readFields(ResultSet resultSet) throws SQLException {
        this.major_id = resultSet.getInt(1);
        this.meal = ThreeMeals.valueOf(resultSet.getString(2));
        this.consumption_count = resultSet.getInt(3);
        this.consumption_total_money = resultSet.getFloat(4);
        this.consumption_average_money = resultSet.getFloat(5);
        this.consumption_student_average_money = resultSet.getFloat(6);
        this.student_count = resultSet.getInt(7);
        this.consumption_low_count = resultSet.getInt(8);
        this.consumption_high_count = resultSet.getInt(9);
        this.student_low_count = resultSet.getInt(10);
        this.student_high_count = resultSet.getInt(11);
    }
}
