package edu.Infomation;

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


public class Consume implements Writable, DBWritable {
    String sid;
    Date execution_time;
    float money;
    String storeName;

    public Consume() {
    }

    public Consume(String sid, Date execution_time, float money, String storeName) {
        this.sid = sid;
        this.execution_time = execution_time;
        this.money = money;
        this.storeName = storeName;
    }

    @Override
    public String toString() {
        return "Consume{" +
                "sid='" + sid + '\'' +
                ", execution_time=" + execution_time +
                ", money=" + money +
                ", storeName='" + storeName + '\'' +
                '}';
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public Date getExecution_time() {
        return execution_time;
    }

    public void setExecution_time(Date execution_time) {
        this.execution_time = execution_time;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(this.sid);
        Text.writeString(dataOutput, this.execution_time.toString());
        dataOutput.writeFloat(this.money);
        dataOutput.writeUTF(this.storeName);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.sid = dataInput.readUTF();
        this.execution_time = Date.valueOf(Text.readString(dataInput));
        this.money = dataInput.readFloat();
        this.storeName = dataInput.readUTF();
    }

    @Override
    public void write(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(2, this.sid);
        preparedStatement.setDate(3, this.execution_time);
        preparedStatement.setFloat(4, this.money);
        preparedStatement.setString(4, this.storeName);
    }

    @Override
    public void readFields(ResultSet resultSet) throws SQLException {
        this.sid = resultSet.getString(2);
        this.execution_time = resultSet.getDate(3);
        this.money = resultSet.getFloat(4);
        this.storeName = resultSet.getString(5);
    }
}
