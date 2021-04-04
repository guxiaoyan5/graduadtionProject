package edu.Dao.ClassMonthCS;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClassMonthCSMapInputValue implements Writable, DBWritable {
    private String sid;
    private int class_id;
    private int month;
    private int year;
    private int consumption_count;
    private float consumption_total_money;

    public ClassMonthCSMapInputValue() {
    }

    public ClassMonthCSMapInputValue(String sid, int class_id, int month, int year, int consumption_count, float consumption_total_money) {
        this.sid = sid;
        this.class_id = class_id;
        this.month = month;
        this.year = year;
        this.consumption_count = consumption_count;
        this.consumption_total_money = consumption_total_money;
    }

    @Override
    public String toString() {
        return "ClassMonthCSMapInputValue{" +
                "sid='" + sid + '\'' +
                ", class_id=" + class_id +
                ", month=" + month +
                ", year=" + year +
                ", consumption_count=" + consumption_count +
                ", consumption_total_money=" + consumption_total_money +
                '}';
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
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

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(this.sid);
        dataOutput.writeInt(this.class_id);
        dataOutput.writeInt(this.month);
        dataOutput.writeInt(this.year);
        dataOutput.writeInt(this.consumption_count);
        dataOutput.writeFloat(consumption_total_money);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.sid = dataInput.readUTF();
        this.class_id = dataInput.readInt();
        this.month = dataInput.readInt();
        this.year = dataInput.readInt();
        this.consumption_count = dataInput.readInt();
        this.consumption_total_money = dataInput.readFloat();
    }

    @Override
    public void write(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1,this.sid);
        preparedStatement.setInt(2,this.class_id);
        preparedStatement.setInt(3,this.month);
        preparedStatement.setInt(4,this.year);
        preparedStatement.setInt(5,this.consumption_count);
        preparedStatement.setFloat(6,this.consumption_total_money);
    }

    @Override
    public void readFields(ResultSet resultSet) throws SQLException {
        this.sid = resultSet.getString(1);
        this.class_id = resultSet.getInt(2);
        this.month = resultSet.getInt(3);
        this.year = resultSet.getInt(4);
        this.consumption_count = resultSet.getInt(5);
        this.consumption_total_money = resultSet.getFloat(6);
    }
}
