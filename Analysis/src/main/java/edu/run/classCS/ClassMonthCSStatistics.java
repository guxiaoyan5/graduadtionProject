package edu.run.classCS;


import edu.Dao.Class.ClassMonthCSKey;
import edu.Infomation.Class.ClassMonthCS;
import edu.bean.Class.ClassMonthCSInputValue;
import edu.bean.Class.ClassMonthCSValue;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class ClassMonthCSStatistics {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration configuration = new Configuration();
        configuration.set("mapreduce.framework.name", "yarn");
        DBConfiguration.configureDB(configuration, StaticConstant.jdbcDriver, StaticConstant.jdbcUrl, StaticConstant.jdbcUser, StaticConstant.jdbcPassword);
        Job job = Job.getInstance(configuration, "class month consume");
        job.setJarByClass(ClassMonthCSStatistics.class);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);

        job.setMapOutputKeyClass(ClassMonthCSKey.class);
        job.setMapOutputValueClass(ClassMonthCSValue.class);
        job.setOutputKeyClass(ClassMonthCS.class);
        job.setOutputValueClass(ClassMonthCS.class);

        job.setInputFormatClass(DBInputFormat.class);
        job.setOutputKeyClass(DBOutputFormat.class);

        DBOutputFormat.setOutput(job, args[0]+"_month_consumption_statistics",
                args[0]+"_id", "month", "year", "consumption_count", "consumption_total_money",
                "consumption_average_money", "consumption_student_average_money", "student_count",
                "consumption_low_count", "consumption_high_count", "student_low_count", "student_high_count"
        );
        DBInputFormat.setInput(job, ClassMonthCSInputValue.class,
                "select student.id,"+args[0]+"_id,execution_time,money from student,consume where student.id=consume.sid",
                "select count(1) from consume");
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }

    private static class Map extends Mapper<Object, ClassMonthCSInputValue, ClassMonthCSKey, ClassMonthCSValue> {
        @Override
        protected void map(Object key, ClassMonthCSInputValue value, Context context) throws IOException, InterruptedException {
            String new_value = value.getDay().toString().split(" ")[0];
            int year = Integer.parseInt(new_value.split("-")[0]);
            int month = Integer.parseInt(new_value.split("-")[1]);
            context.write(new ClassMonthCSKey(value.getClass_id(), month, year), new ClassMonthCSValue(value.getSid(), value.getMoney()));
        }
    }

    private static class Reduce extends Reducer<ClassMonthCSKey, ClassMonthCSValue, ClassMonthCS, ClassMonthCS> {
        @Override
        protected void reduce(ClassMonthCSKey key, Iterable<ClassMonthCSValue> values, Context context) throws IOException, InterruptedException {
            ArrayList<ClassMonthCSValue> newValues = new ArrayList<ClassMonthCSValue>();
            HashMap<String, Float> student = new HashMap<>();
            HashSet<String> sid = new HashSet<>();
            int count = 0;
            int lowCount = 0;
            int highCount = 0;
            int studentCount = 0;
            int studentLowCount = 0;
            int studentHighCount = 0;
            float totalMoney = 0;
            for (ClassMonthCSValue value : values) {
                count += 1;
                totalMoney += value.getMoney();
                sid.add(value.getSid());
                ClassMonthCSValue newValue = WritableUtils.clone(value, context.getConfiguration());
                newValues.add(newValue);
            }
            studentCount = sid.size();
            float average = totalMoney / count;
            float studentAverage = totalMoney / studentCount;
            for (ClassMonthCSValue value : newValues) {
                if (value.getMoney() < average) {
                    lowCount += 1;
                } else {
                    highCount += 1;
                }
                student.merge(value.getSid(), value.getMoney(), Float::sum);
            }
            for (String entry : student.keySet()) {
                if (student.get(entry) < studentAverage) {
                    studentLowCount++;
                } else {
                    studentHighCount++;
                }
            }
            context.write(new ClassMonthCS(key.getClass_id(), key.getMonth(), key.getYear(), count, totalMoney, average, studentAverage, studentCount, lowCount, highCount, studentLowCount, studentHighCount),
                    new ClassMonthCS(key.getClass_id(), key.getMonth(), key.getYear(), count, totalMoney, average, studentAverage, studentCount, lowCount, highCount, studentLowCount, studentHighCount));
        }
    }
}
