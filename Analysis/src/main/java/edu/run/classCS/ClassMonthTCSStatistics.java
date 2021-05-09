package edu.run.classCS;


import edu.Dao.Class.ClassMonthTCSKey;
import edu.Infomation.Class.ClassMonthTCS;
import edu.Infomation.enumObject.ThreeMeals;
import edu.bean.Class.ClassMonthTCSInputValue;
import edu.bean.Class.ClassMonthTCSValue;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;

public class ClassMonthTCSStatistics {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration configuration = new Configuration();
        configuration.set("mapreduce.framework.name", "yarn");
        DBConfiguration.configureDB(configuration, StaticConstant.jdbcDriver, StaticConstant.jdbcUrl, StaticConstant.jdbcUser, StaticConstant.jdbcPassword);
        Job job = Job.getInstance(configuration, "class month three meals consume");
        job.setJarByClass(ClassMonthTCSStatistics.class);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);

        job.setMapOutputKeyClass(ClassMonthTCSKey.class);
        job.setMapOutputValueClass(ClassMonthTCSValue.class);
        job.setOutputKeyClass(ClassMonthTCS.class);
        job.setOutputValueClass(ClassMonthTCS.class);

        job.setInputFormatClass(DBInputFormat.class);
        job.setOutputKeyClass(DBOutputFormat.class);

        DBOutputFormat.setOutput(job, args[0]+"_month_three_meals_statistics",
                args[0]+"_id", "consumption_category", "month", "year", "consumption_count", "consumption_total_money",
                "consumption_average_money", "consumption_student_average_money", "student_count",
                "consumption_low_count", "consumption_high_count", "student_low_count", "student_high_count"
        );
        DBInputFormat.setInput(job, ClassMonthTCSInputValue.class,
                "select student.id,"+args[0]+"_id,execution_time,money" +
                        " from student,consume" +
                        " where student.id=consume.sid",
                "select count(1) from consume");
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }

    static class Map extends Mapper<Object, ClassMonthTCSInputValue, ClassMonthTCSKey, ClassMonthTCSValue> {
        @Override
        protected void map(Object key, ClassMonthTCSInputValue value, Context context) throws IOException, InterruptedException {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(value.getDay());
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH) + 1;
            int year = calendar.get(Calendar.YEAR);
            String meal;
            if (hour <= 9) {
                meal = ThreeMeals.BREAKFAST;
            } else if (hour <= 15) {
                meal = ThreeMeals.LUNCH;
            } else {
                meal = ThreeMeals.DINNER;
            }
            context.write(new ClassMonthTCSKey(value.getClass_id(), month, year, meal), new ClassMonthTCSValue(value.getSid(), value.getMoney()));
        }
    }

    static class Reduce extends Reducer<ClassMonthTCSKey, ClassMonthTCSValue, ClassMonthTCS, ClassMonthTCS> {
        @Override
        protected void reduce(ClassMonthTCSKey key, Iterable<ClassMonthTCSValue> values, Context context) throws IOException, InterruptedException {
            ArrayList<ClassMonthTCSValue> newValues = new ArrayList<>();
            HashMap<String, Float> student = new HashMap<>();
            HashSet<String> sid = new HashSet<>();
            int count = 0;
            int lowCount = 0;
            int highCount = 0;
            int studentCount = 0;
            int studentLowCount = 0;
            int studentHighCount = 0;
            float totalMoney = 0;
            for (ClassMonthTCSValue value : values) {
                count += 1;
                totalMoney += value.getMoney();
                sid.add(value.getSid());
                ClassMonthTCSValue newValue = WritableUtils.clone(value, context.getConfiguration());
                newValues.add(newValue);
            }
            studentCount = sid.size();
            float average = totalMoney / count;
            float studentAverage = totalMoney / studentCount;
            for (ClassMonthTCSValue value : newValues) {
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
            context.write(new ClassMonthTCS(key.getClass_id(), key.getMeal(), key.getMonth(), key.getYear(), count, totalMoney, average, studentAverage, studentCount, lowCount, highCount, studentLowCount, studentHighCount),
                    new ClassMonthTCS(key.getClass_id(), key.getMeal(), key.getMonth(), key.getYear(), count, totalMoney, average, studentAverage, studentCount, lowCount, highCount, studentLowCount, studentHighCount));
        }
    }
}
