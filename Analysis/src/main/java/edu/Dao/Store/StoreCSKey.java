package edu.Dao.Store;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class StoreCSKey implements WritableComparable<StoreCSKey> {
    private int store_id;

    public StoreCSKey() {
    }

    public StoreCSKey(int store_id) {
        this.store_id = store_id;
    }

    @Override
    public String toString() {
        return "StoreCSKey{" +
                "store_id=" + store_id +
                '}';
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    @Override
    public int compareTo(StoreCSKey o) {
        return Integer.compare(this.store_id, o.store_id);
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(store_id);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.store_id = dataInput.readInt();
    }
}
