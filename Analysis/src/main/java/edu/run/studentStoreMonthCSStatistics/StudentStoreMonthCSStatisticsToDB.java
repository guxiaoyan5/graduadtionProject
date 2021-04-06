package edu.run.studentStoreMonthCSStatistics;

import edu.Dao.StudentDayCS.StudentDayCSKey;
import edu.Dao.StudentStoreMonthCS.StudentStoreMonthCSKey;
import edu.Infomation.Consume;
import edu.Infomation.StudentDayCS;
import edu.Infomation.StudentStoreMonthCS;
import edu.run.studentDayCSStatistics.StudentDayCSStatisticsToDB;
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

/**
 * 中间步骤，已检验
 */
public class StudentStoreMonthCSStatisticsToDB {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration configuration = new Configuration();
        configuration.set("mapreduce.framework.name", "yarn");
        DBConfiguration.configureDB(configuration, StaticConstant.jdbcDriver, StaticConstant.jdbcUrl, StaticConstant.jdbcUser, StaticConstant.jdbcPassword);
        Job job = Job.getInstance(configuration, "student store month consume");
        //job.addArchiveToClassPath(new Path("hdfs://master:9000/user/util/mysql-connector-java-5.1.49.jar"));
        job.setJarByClass(StudentStoreMonthCSStatisticsToDB.class);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);

        job.setMapOutputKeyClass(StudentStoreMonthCSKey.class);
        job.setMapOutputValueClass(FloatWritable.class);

        job.setOutputKeyClass(StudentStoreMonthCS.class);
        job.setOutputValueClass(StudentStoreMonthCS.class);

        job.setInputFormatClass(DBInputFormat.class);
        job.setOutputFormatClass(DBOutputFormat.class);

        DBOutputFormat.setOutput(job, "student_store_month_statistics", new String[]{"sid","store_id", "month","year", "consumption_count", "consumption_total_money", "consumption_average_money"});
        DBInputFormat.setInput(job, Consume.class, "consume", null, null, new String[]{"sid", "execution_time", "money", "store_id ", "mode"});
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }
}

class Map extends Mapper<Object, Consume, StudentStoreMonthCSKey, FloatWritable> {
    @Override
    protected void map(Object key, Consume value, Context context) throws IOException, InterruptedException {
        String new_value = value.getExecution_time().toString().split(" ")[0];
        int year = Integer.parseInt(new_value.split("-")[0]);
        int month = Integer.parseInt(new_value.split("-")[1]);
        context.write(new StudentStoreMonthCSKey(value.getSid(), value.getStore_id(), month, year), new FloatWritable(value.getMoney()));
    }
}

class Reduce extends Reducer<StudentStoreMonthCSKey, FloatWritable, StudentStoreMonthCS, StudentStoreMonthCS> {
    @Override
    protected void reduce(StudentStoreMonthCSKey key, Iterable<FloatWritable> values, Context context) throws IOException, InterruptedException {
        int count = 0;
        float sum = 0;
        for (FloatWritable value : values) {
            count += 1;
            sum += value.get();
        }
        float average = sum / count;
        context.write(new StudentStoreMonthCS(key.getSid(), key.getStore_id(), key.getMonth(), key.getYear(), count, sum, average), new StudentStoreMonthCS(key.getSid(), key.getStore_id(), key.getMonth(), key.getYear(), count, sum, average));
    }
}