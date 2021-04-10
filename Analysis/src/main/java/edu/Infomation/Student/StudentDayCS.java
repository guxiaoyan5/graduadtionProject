package edu.Infomation.Student;

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

public class StudentDayCS implements Writable, DBWritable {
    private String sid;
    private Date day;
    private int count;
    private float totalMoney;
    private float averageMoney;
    //private int consumption_low_count;
    //private int consumption_high_count;


    public StudentDayCS() {
    }

    public StudentDayCS(String sid, Date day, int count, float totalMoney, float averageMoney) {
        this.sid = sid;
        this.day = day;
        this.count = count;
        this.totalMoney = totalMoney;
        this.averageMoney = averageMoney;
    }

    @Override
    public String toString() {
        return "StudentDayCS{" +
                "sid='" + sid + '\'' +
                ", day=" + day +
                ", count=" + count +
                ", totalMoney=" + totalMoney +
                ", averageMoney=" + averageMoney +
                '}';
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public float getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(float totalMoney) {
        this.totalMoney = totalMoney;
    }

    public float getAverageMoney() {
        return averageMoney;
    }

    public void setAverageMoney(float averageMoney) {
        this.averageMoney = averageMoney;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(this.sid);
        Text.writeString(dataOutput, this.day.toString());
        dataOutput.writeInt(this.count);
        dataOutput.writeFloat(this.totalMoney);
        dataOutput.writeFloat(this.averageMoney);
       // dataOutput.writeInt(this.consumption_low_count);
       // dataOutput.writeInt(this.consumption_high_count);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.sid = dataInput.readUTF();
        this.day = Date.valueOf(Text.readString(dataInput));
        this.count = dataInput.readInt();
        this.totalMoney = dataInput.readFloat();
        this.averageMoney = dataInput.readFloat();
       // this.consumption_low_count = dataInput.readInt();
       // this.consumption_high_count = dataInput.readInt();
    }

    @Override
    public void write(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, this.sid);
        preparedStatement.setDate(2, this.day);
        preparedStatement.setInt(3, this.count);
        preparedStatement.setFloat(4, this.totalMoney);
        preparedStatement.setFloat(5, this.averageMoney);
       // preparedStatement.setInt(6,this.consumption_low_count);
       // preparedStatement.setInt(7,this.consumption_high_count);
    }

    @Override
    public void readFields(ResultSet resultSet) throws SQLException {
        this.sid = resultSet.getString(1);
        this.day = resultSet.getDate(2);
        this.count = resultSet.getInt(3);
        this.totalMoney = resultSet.getFloat(4);
        this.averageMoney = resultSet.getFloat(5);
       // this.consumption_low_count = resultSet.getInt(6);
       // this.consumption_high_count = resultSet.getInt(7);
    }
}
