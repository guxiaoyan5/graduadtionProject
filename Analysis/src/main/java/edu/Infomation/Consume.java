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
    private String sid;
    private Date execution_time;
    private float money;
    private int store_id;
    private String mode;

    public Consume() {
    }

    public Consume(String sid, Date execution_time, float money, int store_id,String mode) {
        this.sid = sid;
        this.execution_time = execution_time;
        this.money = money;
        this.store_id = store_id;
        this.mode = mode;
    }

    @Override
    public String toString() {
        return "Consume{" +
                "sid='" + sid + '\'' +
                ", execution_time=" + execution_time +
                ", money=" + money +
                ", storeName='" + store_id+ '\'' +
                ", mode='" + mode + '\'' +
                '}';
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
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

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(this.sid);
        Text.writeString(dataOutput, this.execution_time.toString());
        dataOutput.writeFloat(this.money);
        dataOutput.writeInt(this.store_id);
        dataOutput.writeUTF(this.mode);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.sid = dataInput.readUTF();
        this.execution_time = Date.valueOf(Text.readString(dataInput));
        this.money = dataInput.readFloat();
        this.store_id = dataInput.readInt();
        this.mode = dataInput.readUTF();
    }

    @Override
    public void write(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, this.sid);
        preparedStatement.setDate(2, this.execution_time);
        preparedStatement.setFloat(3, this.money);
        preparedStatement.setInt(4, this.store_id);
        preparedStatement.setString(5,this.mode);
    }

    @Override
    public void readFields(ResultSet resultSet) throws SQLException {
        this.sid = resultSet.getString(1);
        this.execution_time = resultSet.getDate(2);
        this.money = resultSet.getFloat(3);
        this.store_id = resultSet.getInt(4);
        this.mode = resultSet.getString(5);
    }
}
