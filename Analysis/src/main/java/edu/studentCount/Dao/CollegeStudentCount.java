package edu.studentCount.Dao;


import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CollegeStudentCount implements Writable, DBWritable {
    String College;
    int count;

    public CollegeStudentCount(String college, int count) {
        this.College = college;
        this.count = count;
    }

    public CollegeStudentCount() {
    }

    @Override
    public String toString() {
        return "CollegeStudentCount{" +
                "College='" + College + '\'' +
                ", count=" + count +
                '}';
    }

    public String getCollege() {
        return College;
    }

    public void setCollege(String college) {
        College = college;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(this.College);
        dataOutput.writeInt(this.count);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.College = dataInput.readUTF();
        this.count = dataInput.readInt();
    }

    @Override
    public void write(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, this.College);
        preparedStatement.setInt(2, this.count);
    }

    @Override
    public void readFields(ResultSet resultSet) throws SQLException {
        this.College = resultSet.getString(1);
        this.count = resultSet.getInt(2);
    }
}
