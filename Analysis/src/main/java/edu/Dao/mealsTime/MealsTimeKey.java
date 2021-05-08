package edu.Dao.mealsTime;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class MealsTimeKey implements WritableComparable<MealsTimeKey> {
    private int minutes;
    private int hour;

    public MealsTimeKey() {
    }

    public MealsTimeKey(int minutes, int hour) {
        this.minutes = minutes;
        this.hour = hour;
    }

    @Override
    public String toString() {
        return "MealsTimeKey{" +
                "minutes=" + minutes +
                ", hour=" + hour +
                '}';
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    @Override
    public int compareTo(MealsTimeKey o) {
        if (this.hour < o.hour) {
            return -1;
        } else if (this.hour == o.hour) {
            return Integer.compare(this.minutes, o.minutes);
        } else {
            return 1;
        }
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.minutes);
        dataOutput.writeInt(this.hour);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.minutes = dataInput.readInt();
        this.hour = dataInput.readInt();
    }
}
