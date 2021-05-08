package edu.Infomation.MealsTime;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MealsTime implements Writable, DBWritable {
    private int hour;
    private int minute;
    private int count;

    public MealsTime() {
    }

    public MealsTime(int hour, int minute, int count) {
        this.hour = hour;
        this.minute = minute;
        this.count = count;
    }

    @Override
    public String toString() {
        return "MealsTime{" +
                "hour=" + hour +
                ", minute=" + minute +
                ", count=" + count +
                '}';
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.hour);
        dataOutput.writeInt(this.minute);
        dataOutput.writeInt(this.count);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.hour = dataInput.readInt();
        this.minute = dataInput.readInt();
        this.count = dataInput.readInt();
    }

    @Override
    public void write(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setInt(1, this.hour);
        preparedStatement.setInt(2, this.minute);
        preparedStatement.setInt(3, this.count);
    }

    @Override
    public void readFields(ResultSet resultSet) throws SQLException {
        this.hour = resultSet.getInt(1);
        this.minute = resultSet.getInt(2);
        this.count = resultSet.getInt(3);
    }
}
