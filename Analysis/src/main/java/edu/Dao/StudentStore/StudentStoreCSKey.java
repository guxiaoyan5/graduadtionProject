package edu.Dao.StudentStore;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class StudentStoreCSKey implements WritableComparable<StudentStoreCSKey> {
    private String sid;
    private int store_id;

    public StudentStoreCSKey() {
    }

    public StudentStoreCSKey(String sid, int store_id) {
        this.sid = sid;
        this.store_id = store_id;
    }

    @Override
    public String toString() {
        return "StudentStoreCSKey{" +
                "sid='" + sid + '\'' +
                ", store_id=" + store_id +
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

    @Override
    public int compareTo(StudentStoreCSKey o) {
        if (this.sid.compareTo(o.sid) > 0) {
            return 1;
        } else if (this.sid.compareTo(o.sid) == 0) {
            return Integer.compare(this.store_id, o.store_id);
        } else {
            return -1;
        }
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(this.sid);
        dataOutput.writeInt(this.store_id);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.sid = dataInput.readUTF();
        this.store_id = dataInput.readInt();
    }
}
