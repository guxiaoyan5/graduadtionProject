package edu.run.student;

import edu.Dao.Student.StudentDayCSKey;
import edu.Dao.Student.StudentDayCSValue;
import edu.Infomation.Consume;
import edu.Infomation.Student.StudentDayCS;
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

/*
未检验
 */
public class StudentDayCSStatisticsToDB {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration configuration = new Configuration();
        configuration.set("mapreduce.framework.name", "yarn");
        DBConfiguration.configureDB(configuration, StaticConstant.jdbcDriver, StaticConstant.jdbcUrl, StaticConstant.jdbcUser, StaticConstant.jdbcPassword);
        Job job = Job.getInstance(configuration, "student day consume");
        //job.addArchiveToClassPath(new Path("hdfs://master:9000/user/util/mysql-connector-java-5.1.49.jar"));
        job.setJarByClass(StudentDayCSStatisticsToDB.class);
        job.setMapperClass(StudentDayMap.class);
        job.setReducerClass(StudentDayReduce.class);

        job.setMapOutputKeyClass(StudentDayCSKey.class);
        job.setMapOutputValueClass(StudentDayCSValue.class);

        job.setOutputKeyClass(StudentDayCS.class);
        job.setOutputValueClass(StudentDayCS.class);

        job.setInputFormatClass(DBInputFormat.class);
        job.setOutputFormatClass(DBOutputFormat.class);

        DBOutputFormat.setOutput(job, "student_day_consumption_statistics",
                "sid", "day", "consumption_count", "consumption_total_money",
                "consumption_average_money");
        DBInputFormat.setInput(job, Consume.class, "consume", null, null,
                "sid", "execution_time", "money", "store_id ", "mode");
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);

    }
    static class StudentDayMap extends Mapper<Object, Consume, StudentDayCSKey, StudentDayCSValue> {
        @Override
        protected void map(Object key, Consume value, Context context) throws IOException, InterruptedException {
            String new_value = value.getExecution_time().toString().split(" ")[0];
            int year = Integer.parseInt(new_value.split("-")[0]);
            int month = Integer.parseInt(new_value.split("-")[1]);
            int day = Integer.parseInt(new_value.split("-")[2]);
            context.write(new StudentDayCSKey(value.getSid(), month, year, day), new StudentDayCSValue(value.getMoney()));
        }
    }

    static class StudentDayReduce extends Reducer<StudentDayCSKey, StudentDayCSValue, StudentDayCS, StudentDayCS> {
        @Override
        protected void reduce(StudentDayCSKey key, Iterable<StudentDayCSValue> values, Context context) throws IOException, InterruptedException {
            int count = 0;
            float totalMoney = 0;
            for (StudentDayCSValue value : values) {
                count += 1;
                totalMoney += value.getMoney();
            }
            float average = totalMoney / count;
            String date = key.getYear() + "-" + key.getMonth() + "-" + key.getDay();
            context.write(new StudentDayCS(key.getSid(), Date.valueOf(date), count, totalMoney, average), new StudentDayCS(key.getSid(), Date.valueOf(date), count, totalMoney, average));
        }
    }
}

