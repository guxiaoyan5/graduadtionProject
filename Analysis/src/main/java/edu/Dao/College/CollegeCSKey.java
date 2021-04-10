package edu.Dao.College;

import edu.Dao.Class.ClassCSKey;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class CollegeCSKey implements WritableComparable<CollegeCSKey> {
    private int college_id;

    public CollegeCSKey(int college_id) {
        this.college_id = college_id;
    }

    public CollegeCSKey() {
    }

    @Override
    public String toString() {
        return "CollegeCSKey{" +
                "college_id=" + college_id +
                '}';
    }

    public int getCollege_id() {
        return college_id;
    }

    public void setCollege_id(int college_id) {
        this.college_id = college_id;
    }

    @Override
    public int compareTo(CollegeCSKey o) {
        return Integer.compare(this.college_id,o.college_id);
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.college_id);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.college_id = dataInput.readInt();
    }
}
