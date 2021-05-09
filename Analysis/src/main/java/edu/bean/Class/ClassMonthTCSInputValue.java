package edu.bean.Class;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.*;

public class ClassMonthTCSInputValue implements Writable, DBWritable {
    private String sid;
    private int class_id;
    private Timestamp day;
    private float money;

    public ClassMonthTCSInputValue() {
    }

    public ClassMonthTCSInputValue(String sid, int class_id, Timestamp day, float money) {
        this.sid = sid;
        this.class_id = class_id;
        this.day = day;
        this.money = money;
    }

    @Override
    public String toString() {
        return "ClassMonthCSInputValue{" +
                "sid='" + sid + '\'' +
                ", class_id=" + class_id +
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

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public Timestamp getDay() {
        return day;
    }

    public void setDay(Timestamp day) {
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
        dataOutput.writeInt(this.class_id);
        Text.writeString(dataOutput, String.valueOf(day));
        dataOutput.writeFloat(this.money);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.sid = dataInput.readUTF();
        this.class_id = dataInput.readInt();
        this.day = Timestamp.valueOf(Text.readString(dataInput));
        this.money = dataInput.readFloat();
    }

    @Override
    public void write(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, this.sid);
        preparedStatement.setInt(2, this.class_id);
        preparedStatement.setTimestamp(3,this.day);
        preparedStatement.setFloat(4, this.money);
    }

    @Override
    public void readFields(ResultSet resultSet) throws SQLException {
        this.sid = resultSet.getString(1);
        this.class_id = resultSet.getInt(2);
        this.day = resultSet.getTimestamp(3);
        this.money = resultSet.getFloat(4);
    }
}
