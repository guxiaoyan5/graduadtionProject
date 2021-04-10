package edu.Infomation.StudentStore;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentStoreCS implements Writable, DBWritable {
    private String sid;
    private int store_id;
    private int consumption_count;
    private float consumption_total_money;
    private float consumption_average_money;

    public StudentStoreCS() {
    }

    public StudentStoreCS(String sid, int store_id, int consumption_count, float consumption_total_money, float consumption_average_money) {
        this.sid = sid;
        this.store_id = store_id;
        this.consumption_count = consumption_count;
        this.consumption_total_money = consumption_total_money;
        this.consumption_average_money = consumption_average_money;
    }

    @Override
    public String toString() {
        return "StudentStoreCS{" +
                "sid='" + sid + '\'' +
                ", store_id=" + store_id +
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

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
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
        dataOutput.writeInt(this.store_id);
        dataOutput.writeInt(this.consumption_count);
        dataOutput.writeFloat(this.consumption_total_money);
        dataOutput.writeFloat(this.consumption_average_money);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.sid = dataInput.readUTF();
        this.store_id = dataInput.readInt();
        this.consumption_count = dataInput.readInt();
        this.consumption_total_money = dataInput.readFloat();
        this.consumption_average_money = dataInput.readFloat();
    }

    @Override
    public void write(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, this.sid);
        preparedStatement.setInt(2, this.store_id);
        preparedStatement.setInt(3, this.consumption_count);
        preparedStatement.setFloat(4, this.consumption_total_money);
        preparedStatement.setFloat(5, this.consumption_average_money);
    }

    @Override
    public void readFields(ResultSet resultSet) throws SQLException {
        this.sid = resultSet.getString(1);
        this.store_id = resultSet.getInt(2);
        this.consumption_count = resultSet.getInt(3);
        this.consumption_total_money = resultSet.getFloat(4);
        this.consumption_average_money = resultSet.getFloat(5);
    }
}
