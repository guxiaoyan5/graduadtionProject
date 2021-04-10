package edu.Infomation.store;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StoreDayCS implements Writable, DBWritable {
    private int store_id;
    private Date day;
    private int consumption_count;
    private float consumption_total_money;
    private float consumption_average_money;
    private float consumption_student_average_money;
    private int student_count;

    public StoreDayCS() {
    }

    public StoreDayCS(int store_id, Date day, int consumption_count, float consumption_total_money, float consumption_average_money, float consumption_student_average_money, int student_count) {
        this.store_id = store_id;
        this.day = day;
        this.consumption_count = consumption_count;
        this.consumption_total_money = consumption_total_money;
        this.consumption_average_money = consumption_average_money;
        this.consumption_student_average_money = consumption_student_average_money;
        this.student_count = student_count;
    }

    @Override
    public String toString() {
        return "StoreDayCS{" +
                "store_id=" + store_id +
                ", day=" + day +
                ", consumption_count=" + consumption_count +
                ", consumption_total_money=" + consumption_total_money +
                ", consumption_average_money=" + consumption_average_money +
                ", consumption_student_average_money=" + consumption_student_average_money +
                ", student_count=" + student_count +
                '}';
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
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

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.store_id);
        Text.writeString(dataOutput, this.day.toString());
        dataOutput.writeInt(this.consumption_count);
        dataOutput.writeFloat(this.consumption_total_money);
        dataOutput.writeFloat(this.consumption_average_money);
        dataOutput.writeFloat(this.consumption_student_average_money);
        dataOutput.writeInt(this.student_count);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.store_id = dataInput.readInt();
        this.day = Date.valueOf(Text.readString(dataInput));
        this.consumption_count = dataInput.readInt();
        this.consumption_total_money = dataInput.readFloat();
        this.consumption_average_money = dataInput.readFloat();
        this.consumption_student_average_money = dataInput.readFloat();
        this.student_count = dataInput.readInt();
    }

    @Override
    public void write(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setInt(1, this.store_id);
        preparedStatement.setDate(2, this.day);
        preparedStatement.setInt(3, this.consumption_count);
        preparedStatement.setFloat(4, this.consumption_total_money);
        preparedStatement.setFloat(5, this.consumption_average_money);
        preparedStatement.setFloat(6, this.consumption_student_average_money);
        preparedStatement.setInt(7, this.student_count);
    }

    @Override
    public void readFields(ResultSet resultSet) throws SQLException {
        this.store_id = resultSet.getInt(1);
        this.day = resultSet.getDate(2);
        this.consumption_count = resultSet.getInt(3);
        this.consumption_total_money = resultSet.getFloat(4);
        this.consumption_average_money = resultSet.getFloat(5);
        this.consumption_student_average_money = resultSet.getFloat(6);
        this.student_count = resultSet.getInt(7);
    }
}
