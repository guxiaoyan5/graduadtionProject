package edu.Dao.Major;

import edu.Dao.Class.ClassCSKey;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class MajorCSKey implements WritableComparable<MajorCSKey> {
    private int major_id;

    public MajorCSKey() {

    }

    public MajorCSKey(int major_id) {
        this.major_id = major_id;
    }

    public int getMajor_id() {
        return major_id;
    }

    @Override
    public String toString() {
        return "MajorCSKey{" +
                "major_id=" + major_id +
                '}';
    }

    public void setMajor_id(int major_id) {
        this.major_id = major_id;
    }

    @Override
    public int compareTo(MajorCSKey o) {
        return Integer.compare(this.major_id,o.major_id);
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.major_id);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.major_id = dataInput.readInt();
    }
}
