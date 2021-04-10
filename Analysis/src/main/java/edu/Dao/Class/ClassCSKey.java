package edu.Dao.Class;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ClassCSKey implements WritableComparable<ClassCSKey> {
    private int class_id;

    public ClassCSKey() {
    }

    public ClassCSKey(int class_id) {
        this.class_id = class_id;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    @Override
    public String toString() {
        return "ClassCSKey{" +
                "class_id=" + class_id +
                '}';
    }

    @Override
    public int compareTo(ClassCSKey o) {
        return Integer.compare(this.class_id,o.class_id);
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.class_id);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.class_id = dataInput.readInt();
    }
}
