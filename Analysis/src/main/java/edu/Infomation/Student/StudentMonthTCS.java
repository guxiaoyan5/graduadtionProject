package edu.Infomation.Student;

import edu.Infomation.enumObject.ThreeMeals;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentMonthTCS implements Writable, DBWritable {
    private String sid;
    private int month;
    private int year;
    private ThreeMeals meal;
    private int consumption_count;
    private float consumption_total_money;
    private float consumption_average_money;

    public StudentMonthTCS() {
    }

    public StudentMonthTCS(String sid, int month, int year, ThreeMeals meal, int consumption_count, float consumption_total_money, float consumption_average_money) {
        this.sid = sid;
        this.month = month;
        this.year = year;
        this.meal = meal;
        this.consumption_count = consumption_count;
        this.consumption_total_money = consumption_total_money;
        this.consumption_average_money = consumption_average_money;
    }

    @Override
    public String toString() {
        return "StudentMonthTCS{" +
                "sid='" + sid + '\'' +
                ", month=" + month +
                ", year=" + year +
                ", meal=" + meal +
                ", consumption_count=" + consumption_count +
                ", consumption_total_money=" + consumption_total_money +
                ", consumption_average_money=" + consumption_average_money +
                '}';
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
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

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(this.sid);
        dataOutput.writeInt(this.month);
        dataOutput.writeInt(this.year);
        dataOutput.writeUTF(this.meal.getMeal());
        dataOutput.writeInt(this.consumption_count);
        dataOutput.writeFloat(this.consumption_total_money);
        dataOutput.writeFloat(this.consumption_average_money);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.sid = dataInput.readUTF();
        this.month = dataInput.readInt();
        this.year = dataInput.readInt();
        this.meal = ThreeMeals.valueOf(dataInput.readUTF());
        this.consumption_count = dataInput.readInt();
        this.consumption_total_money = dataInput.readFloat();
        this.consumption_average_money = dataInput.readFloat();
    }

    @Override
    public void write(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, this.sid);
        preparedStatement.setInt(2, this.month);
        preparedStatement.setInt(3, this.year);
        preparedStatement.setString(4, this.meal.getMeal());
        preparedStatement.setInt(5, this.consumption_count);
        preparedStatement.setFloat(6, this.consumption_total_money);
        preparedStatement.setFloat(7, this.consumption_average_money);
    }

    @Override
    public void readFields(ResultSet resultSet) throws SQLException {
        this.sid = resultSet.getString(1);
        this.month = resultSet.getInt(2);
        this.year = resultSet.getInt(3);
        this.meal = ThreeMeals.valueOf(resultSet.getString(4));
        this.consumption_count = resultSet.getInt(5);
        this.consumption_total_money = resultSet.getFloat(6);
        this.consumption_average_money = resultSet.getFloat(7);
    }
}
