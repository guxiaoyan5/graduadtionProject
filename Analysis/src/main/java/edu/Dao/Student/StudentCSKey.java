package edu.Dao.Student;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class StudentCSKey implements WritableComparable<StudentCSKey> {
    private String sid;

    public StudentCSKey() {
    }

    public StudentCSKey(String sid) {
        this.sid = sid;
    }

    @Override
    public String toString() {
        return "StudentCSKey{" +
                "sid='" + sid + '\'' +
                '}';
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    @Override
    public int compareTo(StudentCSKey o) {
        return this.sid.compareTo(o.sid);
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(this.sid);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.sid = dataInput.readUTF();
    }
}
