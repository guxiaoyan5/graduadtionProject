package edu.Infomation.Student;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentMonthCS implements Writable, DBWritable {

    private String sid;
    private int month;
    private int year;
    private int consumption_count;
    private float consumption_total_money;
    private float consumption_average_money;
   // private int consumption_low_count;
   // private int consumption_high_count;


    public StudentMonthCS() {
    }

    public StudentMonthCS(String sid, int month, int year, int consumption_count, float consumption_total_money, float consumption_average_money) {
        this.sid = sid;
        this.month = month;
        this.year = year;
        this.consumption_count = consumption_count;
        this.consumption_total_money = consumption_total_money;
        this.consumption_average_money = consumption_average_money;
    }

    @Override
    public String toString() {
        return "StudentMonthCS{" +
                "sid='" + sid + '\'' +
                ", month=" + month +
                ", year=" + year +
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
        dataOutput.writeInt(this.consumption_count);
        dataOutput.writeFloat(this.consumption_total_money);
        dataOutput.writeFloat(this.consumption_average_money);
       // dataOutput.writeInt(this.consumption_low_count);
       // dataOutput.writeInt(this.consumption_high_count);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.sid = dataInput.readUTF();
        this.month = dataInput.readInt();
        this.year = dataInput.readInt();
        this.consumption_count = dataInput.readInt();
        this.consumption_total_money = dataInput.readFloat();
        this.consumption_average_money = dataInput.readFloat();
       // this.consumption_low_count = dataInput.readInt();
       // this.consumption_high_count = dataInput.readInt();
    }

    @Override
    public void write(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1,this.sid);
        preparedStatement.setInt(2,this.month);
        preparedStatement.setInt(3,this.year);
        preparedStatement.setInt(4,this.consumption_count);
        preparedStatement.setFloat(5,this.consumption_total_money);
        preparedStatement.setFloat(6,this.consumption_average_money);
       // preparedStatement.setInt(7,this.consumption_low_count);
       // preparedStatement.setInt(8,this.consumption_high_count);
    }

    @Override
    public void readFields(ResultSet resultSet) throws SQLException {
        this.sid = resultSet.getString(1);
        this.month = resultSet.getInt(2);
        this.year = resultSet.getInt(3);
        this.consumption_count = resultSet.getInt(4);
        this.consumption_total_money = resultSet.getFloat(5);
        this.consumption_average_money = resultSet.getFloat(6);
       //this.consumption_low_count = resultSet.getInt(7);
       // this.consumption_high_count = resultSet.getInt(8);
    }
}
