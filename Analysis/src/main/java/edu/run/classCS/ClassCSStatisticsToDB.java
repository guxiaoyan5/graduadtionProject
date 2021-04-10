package edu.run.classCS;

import edu.Dao.Class.ClassCSInputValue;
import edu.Dao.Class.ClassCSKey;
import edu.Dao.Class.ClassCSValue;
import edu.Infomation.Class.ClassCS;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class ClassCSStatisticsToDB {
    public static void main(String[] args) {

    }
    private static class Map extends Mapper<Object, ClassCSInputValue, ClassCSKey, ClassCSValue>{
        @Override
        protected void map(Object key, ClassCSInputValue value, Context context) throws IOException, InterruptedException {

        }
    }
    private static class Reduce extends Reducer<ClassCSKey,ClassCSKey, ClassCS,ClassCS>{
        @Override
        protected void reduce(ClassCSKey key, Iterable<ClassCSKey> values, Context context) throws IOException, InterruptedException {

        }
    }
}
