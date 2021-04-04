package edu.Dao.StoreMonthCS;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class StoreMonthCSValue implements Writable {
    private String sid;
    private int consumption_count;
    private float consumption_total_money;

    public StoreMonthCSValue() {
    }

    public StoreMonthCSValue(String sid, int consumption_count, float consumption_total_money) {
        this.sid = sid;
        this.consumption_count = consumption_count;
        this.consumption_total_money = consumption_total_money;
    }

    @Override
    public String toString() {
        return "StoreMonthCSValue{" +
                "sid='" + sid + '\'' +
                ", consumption_count=" + consumption_count +
                ", consumption_total_money=" + consumption_total_money +
                '}';
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
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

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(this.sid);
        dataOutput.writeInt(this.consumption_count);
        dataOutput.writeFloat(this.consumption_total_money);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.sid = dataInput.readUTF();
        this.consumption_count = dataInput.readInt();
        this.consumption_total_money = dataInput.readFloat();
    }
}
