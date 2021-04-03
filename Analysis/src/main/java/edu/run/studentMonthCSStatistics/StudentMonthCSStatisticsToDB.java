package edu.run.studentMonthCSStatistics;

import edu.Dao.StudentDayCS.StudentDayCSKey;
import edu.Dao.StudentMonthCS.StudentMonthCSKey;
import edu.Dao.StudentMonthCS.StudentMonthCSValue;
import edu.Infomation.Consume;
import edu.Infomation.StudentDayCS;
import edu.Infomation.StudentMonthCS;
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

public class StudentMonthCSStatisticsToDB {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration configuration = new Configuration();
        configuration.set("mapreduce.framework.name", "yarn");
        DBConfiguration.configureDB(configuration, StaticConstant.jdbcDriver, StaticConstant.jdbcUrl, StaticConstant.jdbcUser, StaticConstant.jdbcPassword);
        Job job = Job.getInstance(configuration, "student month consume");

        job.setJarByClass(StudentMonthCSStatisticsToDB.class);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);

        job.setMapOutputKeyClass(StudentMonthCSKey.class);
        job.setMapOutputValueClass(StudentMonthCSValue.class);

        job.setOutputKeyClass(StudentMonthCS.class);
        job.setOutputValueClass(StudentMonthCS.class);

        job.setInputFormatClass(DBInputFormat.class);
        job.setOutputFormatClass(DBOutputFormat.class);

        DBOutputFormat.setOutput(job, "student_month_consumption_statistics", new String[]{"sid", "month", "year", "consumption_count", "consumption_total_money", "consumption_average_money"});
        DBInputFormat.setInput(job, StudentDayCS.class, "student_day_consumption_statistics", null, null, new String[]{"sid", "day", "consumption_count", "consumption_total_money", "consumption_average_money"});
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }
}

class Map extends Mapper<Object, StudentDayCS, StudentMonthCSKey, StudentMonthCSValue> {
    @Override
    protected void map(Object key, StudentDayCS value, Context context) throws IOException, InterruptedException {
        String new_value = value.getDay().toString().split(" ")[0];
        int year = Integer.parseInt(new_value.split("-")[0]);
        int month = Integer.parseInt(new_value.split("-")[1]);
        context.write(new StudentMonthCSKey(value.getSid(), month, year), new StudentMonthCSValue(value.getCount(), value.getTotalMoney()));
    }
}

class Reduce extends Reducer<StudentMonthCSKey, StudentMonthCSValue, StudentMonthCS, StudentMonthCS> {
    @Override
    protected void reduce(StudentMonthCSKey key, Iterable<StudentMonthCSValue> values, Context context) throws IOException, InterruptedException {
        int count = 0;
        float totalMoney = 0;
        for (StudentMonthCSValue value : values) {
            count += value.getConsumption_count();
            totalMoney += value.getConsumption_total_money();
        }
        float average = totalMoney / count;
        context.write(new StudentMonthCS(key.getSid(), key.getMonth(), key.getYear(), count, totalMoney, average), new StudentMonthCS(key.getSid(), key.getMonth(), key.getYear(), count, totalMoney, average));
    }
}
