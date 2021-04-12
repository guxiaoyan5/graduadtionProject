package edu.run.classCS;

import edu.Dao.Class.*;
import edu.Infomation.Class.ClassDayCS;
import edu.Infomation.Class.ClassMonthCS;
import edu.util.StaticConstant;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.WritableUtils;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBInputFormat;
import org.apache.hadoop.mapreduce.lib.db.DBOutputFormat;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;

public class ClassDayCSStatisticsToDB {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration configuration = new Configuration();
        configuration.set("mapreduce.framework.name", "yarn");
        DBConfiguration.configureDB(configuration, StaticConstant.jdbcDriver, StaticConstant.jdbcUrl, StaticConstant.jdbcUser, StaticConstant.jdbcPassword);
        Job job = Job.getInstance(configuration, "class day consume");
        job.setJarByClass(ClassDayCSStatisticsToDB.class);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);

        job.setMapOutputKeyClass(ClassDayCSKey.class);
        job.setMapOutputValueClass(ClassDayCSValue.class);
        job.setOutputKeyClass(ClassDayCS.class);
        job.setOutputValueClass(ClassDayCS.class);

        job.setInputFormatClass(DBInputFormat.class);
        job.setOutputKeyClass(DBOutputFormat.class);

        DBOutputFormat.setOutput(job, "class_day_consumption_statistics",
                "class_id", "day", "consumption_count", "consumption_total_money",
                "consumption_average_money", "consumption_student_average_money", "student_count",
                "consumption_low_count", "consumption_high_count", "student_low_count", "student_high_count"
        );
        DBInputFormat.setInput(job, ClassDayCSInputValue.class,
                "select student.id,class_id,execution_time,money,consumption_total_money from student,consume,student_day_consumption_statistics where student.id=consume.sid and student.id=student_day_consumption_statistics.sid and to_days(execution_time)=to_days(day)",
                "select count(1) from consume");
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }
    private static class Map extends Mapper<Object, ClassDayCSInputValue, ClassDayCSKey, ClassDayCSValue>{
        @Override
        protected void map(Object key, ClassDayCSInputValue value, Context context) throws IOException, InterruptedException {
            String new_value = value.getDay().toString().split(" ")[0];
            int year = Integer.parseInt(new_value.split("-")[0]);
            int month = Integer.parseInt(new_value.split("-")[1]);
            int day = Integer.parseInt(new_value.split("-")[2]);
            context.write(new ClassDayCSKey(value.getClass_id(),month,year,day),new ClassDayCSValue(value.getSid(), value.getMoney(), value.getStudentTotalMoney()));
        }
    }
    private static class Reduce extends Reducer<ClassDayCSKey,ClassDayCSValue, ClassDayCS,ClassDayCS>{
        @Override
        protected void reduce(ClassDayCSKey key, Iterable<ClassDayCSValue> values, Context context) throws IOException, InterruptedException {
            ArrayList<ClassDayCSValue> newValues = new ArrayList<>();
            HashSet<String> sidLow = new HashSet<>();
            HashSet<String> highLow = new HashSet<>();
            HashSet<String> sid = new HashSet<>();
            int count = 0;
            int lowCount = 0;
            int highCount = 0;
            int studentCount = 0;
            int studentLowCount = 0;
            int studentHighCount = 0;
            float totalMoney = 0;
            for (ClassDayCSValue value : values) {
                count += 1;
                totalMoney += value.getMoney();
                sid.add(value.getSid());
                ClassDayCSValue newValue = WritableUtils.clone(value,context.getConfiguration());
                newValues.add(newValue);
            }
            studentCount = sid.size();
            float average = totalMoney / count;
            float studentAverage = totalMoney / studentCount;

            for (ClassDayCSValue value : newValues) {
                if (value.getMoney() < average) {
                    lowCount += 1;
                } else {
                    highCount += 1;
                }
                if (value.getStudentTotalMoney() < studentAverage) {
                    sidLow.add(value.getSid());
                } else {
                    highLow.add(value.getSid());
                }
            }

            studentLowCount = sidLow.size();
            studentHighCount = highLow.size();
            String date = key.getYear() + "-" + key.getMonth() + "-" + key.getDay();
            context.write(new ClassDayCS(key.getClass_id(), Date.valueOf(date), count, totalMoney, average, studentAverage, studentCount, lowCount, highCount, studentLowCount, studentHighCount),
                    new ClassDayCS(key.getClass_id(), Date.valueOf(date), count, totalMoney, average, studentAverage, studentCount, lowCount, highCount, studentLowCount, studentHighCount));
        }
    }
}
