package edu.Dao.College;

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

public class CollegeDayCSInputValue implements DBWritable, Writable {
    private String sid;
    private int college_id;
    private Date day;
    private float money;
    private float studentTotalMoney;

    public CollegeDayCSInputValue() {
    }

    public CollegeDayCSInputValue(String sid, int college_id, Date day, float money, float studentTotalMoney) {
        this.sid = sid;
        this.college_id = college_id;
        this.day = day;
        this.money = money;
        this.studentTotalMoney = studentTotalMoney;
    }

    @Override
    public String toString() {
        return "CollegeDayCSInputValue{" +
                "sid='" + sid + '\'' +
                ", college_id=" + college_id +
                ", day=" + day +
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

    public int getCollege_id() {
        return college_id;
    }

    public void setCollege_id(int college_id) {
        this.college_id = college_id;
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

    public float getStudentTotalMoney() {
        return studentTotalMoney;
    }

    public void setStudentTotalMoney(float studentTotalMoney) {
        this.studentTotalMoney = studentTotalMoney;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(this.sid);
        dataOutput.writeInt(this.college_id);
        Text.writeString(dataOutput, String.valueOf(day));
        dataOutput.writeFloat(this.money);
        dataOutput.writeFloat(this.studentTotalMoney);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.sid = dataInput.readUTF();
        this.college_id = dataInput.readInt();
        this.day = Date.valueOf(Text.readString(dataInput));
        this.money = dataInput.readFloat();
        this.studentTotalMoney = dataInput.readFloat();
    }

    @Override
    public void write(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, this.sid);
        preparedStatement.setInt(2, this.college_id);
        preparedStatement.setDate(3,this.day);
        preparedStatement.setFloat(4, this.money);
        preparedStatement.setFloat(5,this.studentTotalMoney);
    }

    @Override
    public void readFields(ResultSet resultSet) throws SQLException {
        this.sid = resultSet.getString(1);
        this.college_id = resultSet.getInt(2);
        this.day = resultSet.getDate(3);
        this.money = resultSet.getFloat(4);
        this.studentTotalMoney = resultSet.getFloat(5);
    }
}
