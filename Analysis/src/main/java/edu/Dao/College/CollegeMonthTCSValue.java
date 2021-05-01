package edu.Dao.College;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class CollegeMonthTCSValue implements Writable {
    private String sid;
    private float money;
    private float studentTotalMoney;

    public CollegeMonthTCSValue() {
    }

    public CollegeMonthTCSValue(String sid, float money, float studentTotalMoney) {
        this.sid = sid;
        this.money = money;
        this.studentTotalMoney = studentTotalMoney;
    }

    @Override
    public String toString() {
        return "CollegeMonthTCSValue{" +
                "sid='" + sid + '\'' +
                ", money=" + money +
                ", studentTotalMoney=" + studentTotalMoney +
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

    public float getStudentTotalMoney() {
        return studentTotalMoney;
    }

    public void setStudentTotalMoney(float studentTotalMoney) {
        this.studentTotalMoney = studentTotalMoney;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(this.sid);
        dataOutput.writeFloat(this.money);
        dataOutput.writeFloat(this.studentTotalMoney);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.sid = dataInput.readUTF();
        this.money = dataInput.readFloat();
        this.studentTotalMoney = dataInput.readFloat();
    }
}
