package edu.Infomation.store;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StoreCS implements Writable, DBWritable {
    private int store_id;
    private int consumption_count;
    private float consumption_total_money;
    private float consumption_average_money;
    private float consumption_student_average_money;
    private int student_count;

    public StoreCS() {
    }

    public StoreCS(int store_id, int consumption_count, float consumption_total_money, float consumption_average_money, float consumption_student_average_money, int student_count) {
        this.store_id = store_id;
        this.consumption_count = consumption_count;
        this.consumption_total_money = consumption_total_money;
        this.consumption_average_money = consumption_average_money;
        this.consumption_student_average_money = consumption_student_average_money;
        this.student_count = student_count;
    }

    @Override
    public String toString() {
        return "StoreCS{" +
                "store_id=" + store_id +
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
        dataOutput.writeInt(this.consumption_count);
        dataOutput.writeFloat(this.consumption_total_money);
        dataOutput.writeFloat(this.consumption_average_money);
        dataOutput.writeFloat(this.consumption_student_average_money);
        dataOutput.writeInt(this.student_count);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.store_id=dataInput.readInt();
        this.consumption_count=dataInput.readInt();
        this.consumption_total_money=dataInput.readFloat();
        this.consumption_average_money=dataInput.readFloat();
        this.consumption_student_average_money=dataInput.readFloat();
        this.student_count=dataInput.readInt();
    }

    @Override
    public void write(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setInt(1,this.store_id);
        preparedStatement.setInt(2,this.consumption_count);
        preparedStatement.setFloat(3,this.consumption_total_money);
        preparedStatement.setFloat(4,this.consumption_average_money);
        preparedStatement.setFloat(5,this.consumption_student_average_money);
        preparedStatement.setInt(6,this.student_count);
    }

    @Override
    public void readFields(ResultSet resultSet) throws SQLException {
        this.store_id=resultSet.getInt(1);
        this.consumption_count=resultSet.getInt(2);
        this.consumption_total_money=resultSet.getFloat(3);
        this.consumption_average_money=resultSet.getFloat(4);
        this.consumption_student_average_money=resultSet.getFloat(5);
        this.student_count=resultSet.getInt(6);
    }
}
