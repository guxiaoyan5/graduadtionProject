package edu.run.studentDayCSStatistics;

import edu.Dao.StudentDayCS.StudentDayCSKey;
import edu.Infomation.Consume;
import edu.Infomation.StudentDayCS;
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


public class StudentDayCSStatisticsToDB {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration configuration = new Configuration();
        configuration.set("mapreduce.framework.name", "yarn");
        DBConfiguration.configureDB(configuration, StaticConstant.jdbcDriver, StaticConstant.jdbcUrl, StaticConstant.jdbcUser, StaticConstant.jdbcPassword);
        Job job = Job.getInstance(configuration, "student day consume");
        //job.addArchiveToClassPath(new Path("hdfs://master:9000/user/util/mysql-connector-java-5.1.49.jar"));
        job.setJarByClass(StudentDayCSStatisticsToDB.class);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);

        job.setMapOutputKeyClass(StudentDayCSKey.class);
        job.setMapOutputValueClass(FloatWritable.class);

        job.setOutputKeyClass(StudentDayCS.class);
        job.setOutputValueClass(StudentDayCS.class);

        job.setInputFormatClass(DBInputFormat.class);
        job.setOutputFormatClass(DBOutputFormat.class);

        DBOutputFormat.setOutput(job, "student_day_consumption_statistics", new String[]{"sid", "day", "consumption_count", "consumption_total_money", "consumption_average_money"});
        DBInputFormat.setInput(job, Consume.class, "consume", null, null, new String[]{"sid", "execution_time", "money", "store_id ", "mode"});
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);

    }
}

class Map extends Mapper<Object, Consume, StudentDayCSKey, FloatWritable> {
    @Override
    protected void map(Object key, Consume value, Context context) throws IOException, InterruptedException {
        String new_value = value.getExecution_time().toString().split(" ")[0];
        int year = Integer.parseInt(new_value.split("-")[0]);
        int month = Integer.parseInt(new_value.split("-")[1]);
        int day = Integer.parseInt(new_value.split("-")[2]);
        context.write(new StudentDayCSKey(value.getSid(), month, year, day), new FloatWritable(value.getMoney()));
    }
}

class Reduce extends Reducer<StudentDayCSKey, FloatWritable, StudentDayCS, StudentDayCS> {
    @Override
    protected void reduce(StudentDayCSKey key, Iterable<FloatWritable> values, Context context) throws IOException, InterruptedException {
        int count = 0;
        float totalMoney = 0;
        for (FloatWritable value : values) {
            count += 1;
            totalMoney += value.get();
        }
        float average = totalMoney / count;
        String date = key.getYear() + "-" + key.getMonth() + "-" + key.getDay();
        context.write(new StudentDayCS(key.getSid(), Date.valueOf(date), count, totalMoney, average), new StudentDayCS(key.getSid(), Date.valueOf(date), count, totalMoney, average));
    }
}