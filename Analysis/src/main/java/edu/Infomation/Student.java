package edu.Infomation;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Student implements Writable, DBWritable {
    String id;
    String name;
    String className;
    String major;
    String college;
    String sex;

    public Student() {
    }

    public Student(String id, String name, String className, String major, String college, String sex) {
        this.id = id;
        this.name = name;
        this.className = className;
        this.major = major;
        this.college = college;
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", className='" + className + '\'' +
                ", major='" + major + '\'' +
                ", college='" + college + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(this.id);
        dataOutput.writeUTF(this.name);
        dataOutput.writeUTF(this.className);
        dataOutput.writeUTF(this.major);
        dataOutput.writeUTF(this.college);
        dataOutput.writeUTF(this.sex);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.id = dataInput.readUTF();
        this.name = dataInput.readUTF();
        this.className = dataInput.readUTF();
        this.major = dataInput.readUTF();
        this.college = dataInput.readUTF();
        this.sex = dataInput.readUTF();
    }

    @Override
    public void write(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, this.id);
        preparedStatement.setString(2, this.name);
        preparedStatement.setString(3, this.className);
        preparedStatement.setString(4, this.major);
        preparedStatement.setString(5, this.college);
        preparedStatement.setString(6, this.sex);
    }

    @Override
    public void readFields(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getString(1);
        this.name = resultSet.getString(2);
        this.className = resultSet.getString(3);
        this.major = resultSet.getString(4);
        this.college = resultSet.getString(5);
        this.sex = resultSet.getString(6);
    }
}