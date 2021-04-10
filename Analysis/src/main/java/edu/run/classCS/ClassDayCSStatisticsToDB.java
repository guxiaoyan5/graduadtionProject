package edu.run.classCS;

import edu.Dao.Class.ClassDayCSInputValue;
import edu.Dao.Class.ClassDayCSKey;
import edu.Dao.Class.ClassDayCSValue;
import edu.Infomation.Class.ClassDayCS;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class ClassDayCSStatisticsToDB {
    public static void main(String[] args) {

    }
    private static class Map extends Mapper<Object, ClassDayCSInputValue, ClassDayCSKey, ClassDayCSValue>{
        @Override
        protected void map(Object key, ClassDayCSInputValue value, Context context) throws IOException, InterruptedException {

        }
    }
    private static class Reduce extends Reducer<ClassDayCSKey,ClassDayCSValue, ClassDayCS,ClassDayCS>{
        @Override
        protected void reduce(ClassDayCSKey key, Iterable<ClassDayCSValue> values, Context context) throws IOException, InterruptedException {

        }
    }
}
