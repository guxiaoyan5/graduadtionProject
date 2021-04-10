package edu.run.studentStore;

import edu.Dao.StudentStore.StudentStoreDayCSKey;
import edu.Dao.StudentStore.StudentStoreMonthCSKey;
import edu.Infomation.Consume;
import edu.Infomation.StudentStore.StudentStoreCS;
import edu.Infomation.StudentStore.StudentStoreDayCS;
import edu.Infomation.StudentStore.StudentStoreMonthCS;
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
import java.sql.Date;

public class StudentStoreDayCSStatisticsToDB {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration configuration = new Configuration();
        configuration.set("mapreduce.framework.name", "yarn");
        DBConfiguration.configureDB(configuration, StaticConstant.jdbcDriver, StaticConstant.jdbcUrl, StaticConstant.jdbcUser, StaticConstant.jdbcPassword);
        Job job = Job.getInstance(configuration, "student store day consume");
        job.setJarByClass(StudentStoreDayCSStatisticsToDB.class);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);

        job.setMapOutputKeyClass(StudentStoreDayCSKey.class);
        job.setMapOutputValueClass(FloatWritable.class);

        job.setOutputKeyClass(StudentStoreDayCS.class);
        job.setOutputValueClass(StudentStoreDayCS.class);

        job.setInputFormatClass(DBInputFormat.class);
        job.setOutputFormatClass(DBOutputFormat.class);

        DBOutputFormat.setOutput(job, "student_store_day_statistics",
                "sid","store_id", "day", "consumption_count", "consumption_total_money", "consumption_average_money");
        DBInputFormat.setInput(job, Consume.class, "consume", null, null,
                "sid", "execution_time", "money", "store_id ", "mode");
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }
    private static class Map extends Mapper<Object, Consume, StudentStoreDayCSKey, FloatWritable> {
        @Override
        protected void map(Object key, Consume value, Context context) throws IOException, InterruptedException {
            String new_value = value.getExecution_time().toString().split(" ")[0];
            int year = Integer.parseInt(new_value.split("-")[0]);
            int month = Integer.parseInt(new_value.split("-")[1]);
            int day = Integer.parseInt(new_value.split("-")[2]);
            context.write(new StudentStoreDayCSKey(value.getSid(), value.getStore_id(), month, year,day), new FloatWritable(value.getMoney()));
        }
    }

    private static class Reduce extends Reducer<StudentStoreDayCSKey, FloatWritable, StudentStoreDayCS, StudentStoreDayCS> {
        @Override
        protected void reduce(StudentStoreDayCSKey key, Iterable<FloatWritable> values, Context context) throws IOException, InterruptedException {
            int count = 0;
            float sum = 0;
            for (FloatWritable value : values) {
                count += 1;
                sum += value.get();
            }
            float average = sum / count;
            String date = key.getYear() + "-" + key.getMonth() + "-" + key.getDay();
            context.write(new StudentStoreDayCS(key.getSid(), key.getStore_id(),Date.valueOf(date), count, sum, average), new StudentStoreDayCS(key.getSid(), key.getStore_id(), Date.valueOf(date), count, sum, average));
        }
    }
}
