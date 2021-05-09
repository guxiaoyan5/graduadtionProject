package edu.bean.Class;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ClassDayTCSValue implements Writable {
    private String sid;
    private float money;

    public ClassDayTCSValue() {
    }

    public ClassDayTCSValue(String sid, float money) {
        this.sid = sid;
        this.money = money;
    }

    @Override
    public String toString() {
        return "ClassDayCSValue{" +
                "sid='" + sid + '\'' +
                ", money=" + money +
                '}';
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
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
        dataOutput.writeFloat(this.money);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.sid = dataInput.readUTF();
        this.money = dataInput.readFloat();
    }
}
