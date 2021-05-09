package edu.bean.major;

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

public class MajorDayCSInputValue implements Writable, DBWritable {
    private String sid;
    private int major_id;
    private Date day;
    private float money;

    public MajorDayCSInputValue() {
    }

    public MajorDayCSInputValue(String sid, int major_id, Date day, float money) {
        this.sid = sid;
        this.major_id = major_id;
        this.day = day;
        this.money = money;
    }

    @Override
    public String toString() {
        return "MajorDayCSInputValue{" +
                "sid='" + sid + '\'' +
                ", major_id=" + major_id +
                ", day=" + day +
                ", money=" + money +
                '}';
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public int getMajor_id() {
        return major_id;
    }

    public void setMajor_id(int major_id) {
        this.major_id = major_id;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(this.sid);
        dataOutput.writeInt(this.major_id);
        Text.writeString(dataOutput, String.valueOf(day));
        dataOutput.writeFloat(this.money);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.sid = dataInput.readUTF();
        this.major_id = dataInput.readInt();
        this.day = Date.valueOf(Text.readString(dataInput));
        this.money = dataInput.readFloat();
    }

    @Override
    public void write(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, this.sid);
        preparedStatement.setInt(2, this.major_id);
        preparedStatement.setDate(3,this.day);
        preparedStatement.setFloat(4, this.money);
    }

    @Override
    public void readFields(ResultSet resultSet) throws SQLException {
        this.sid = resultSet.getString(1);
        this.major_id = resultSet.getInt(2);
        this.day = resultSet.getDate(3);
        this.money = resultSet.getFloat(4);
    }
}
