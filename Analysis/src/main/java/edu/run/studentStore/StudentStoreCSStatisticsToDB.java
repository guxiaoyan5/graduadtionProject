package edu.run.studentStore;

import edu.Dao.StudentStore.StudentStoreCSKey;
import edu.Dao.StudentStore.StudentStoreDayCSKey;
import edu.Infomation.Consume;
import edu.Infomation.StudentStore.StudentStoreCS;
import edu.Infomation.StudentStore.StudentStoreDayCS;
import edu.util.StaticConstant;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBInputFormat;
import org.apache.hadoop.mapreduce.lib.db.DBOutputFormat;

import java.io.IOException;

public class StudentStoreCSStatisticsToDB {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration configuration = new Configuration();
        configuration.set("mapreduce.framework.name", "yarn");
        DBConfiguration.configureDB(configuration, StaticConstant.jdbcDriver, StaticConstant.jdbcUrl, StaticConstant.jdbcUser, StaticConstant.jdbcPassword);
        Job job = Job.getInstance(configuration, "student store consume");
        job.setJarByClass(StudentStoreCSStatisticsToDB.class);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);

        job.setMapOutputKeyClass(StudentStoreCSKey.class);
        job.setMapOutputValueClass(FloatWritable.class);

        job.setOutputKeyClass(StudentStoreCS.class);
        job.setOutputValueClass(StudentStoreDayCS.class);

        job.setInputFormatClass(DBInputFormat.class);
        job.setOutputFormatClass(DBOutputFormat.class);

        DBOutputFormat.setOutput(job, "student_store_statistics",
                "sid","store_id","consumption_count", "consumption_total_money", "consumption_average_money");
        DBInputFormat.setInput(job, Consume.class, "consume", null, null,
                "sid", "execution_time", "money", "store_id ", "mode");
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }

    private static class Map extends Mapper<Object, Consume, StudentStoreCSKey, FloatWritable> {
        @Override
        protected void map(Object key, Consume value, Context context) throws IOException, InterruptedException {
            context.write(new StudentStoreCSKey(value.getSid(), value.getStore_id()), new FloatWritable(value.getMoney()));
        }
    }

    private static class Reduce extends Reducer<StudentStoreCSKey, FloatWritable, StudentStoreCS, StudentStoreCS> {
        @Override
        protected void reduce(StudentStoreCSKey key, Iterable<FloatWritable> values, Context context) throws IOException, InterruptedException {
            int count = 0;
            float sum = 0;
            for (FloatWritable value : values) {
                count += 1;
                sum += value.get();
            }
            float average = sum / count;
            context.write(new StudentStoreCS(key.getSid(), key.getStore_id(), count, sum, average), new StudentStoreCS(key.getSid(), key.getStore_id(), count, sum, average));
        }
    }
}
