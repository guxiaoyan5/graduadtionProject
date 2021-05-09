package edu.run.classCS;

import edu.Dao.Class.ClassDayTCSKey;
import edu.Infomation.Class.ClassDayTCS;
import edu.Infomation.enumObject.ThreeMeals;
import edu.bean.Class.ClassDayTCSInputValue;
import edu.bean.Class.ClassDayTCSValue;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;

public class ClassDayTCSStatistics {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration configuration = new Configuration();
        configuration.set("mapreduce.framework.name", "yarn");
        DBConfiguration.configureDB(configuration, StaticConstant.jdbcDriver, StaticConstant.jdbcUrl, StaticConstant.jdbcUser, StaticConstant.jdbcPassword);
        Job job = Job.getInstance(configuration, "class day three meals consume");
        job.setJarByClass(ClassDayTCSStatistics.class);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);

        job.setMapOutputKeyClass(ClassDayTCSKey.class);
        job.setMapOutputValueClass(ClassDayTCSValue.class);
        job.setOutputKeyClass(ClassDayTCS.class);
        job.setOutputValueClass(ClassDayTCS.class);

        job.setInputFormatClass(DBInputFormat.class);
        job.setOutputKeyClass(DBOutputFormat.class);

        DBOutputFormat.setOutput(job, args[0]+"_day_three_meals_statistics",
                args[0]+"_id", "consumption_category", "day", "consumption_count", "consumption_total_money",
                "consumption_average_money", "consumption_student_average_money", "student_count",
                "consumption_low_count", "consumption_high_count", "student_low_count", "student_high_count"
        );
        DBInputFormat.setInput(job, ClassDayTCSInputValue.class,
                "select student.id,"+args[0]+"_id,execution_time,money" +
                        " from student,consume where student.id=consume.sid",
                "select count(1) from consume");
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }

    static class Map extends Mapper<Object, ClassDayTCSInputValue, ClassDayTCSKey, ClassDayTCSValue> {
        @Override
        protected void map(Object key, ClassDayTCSInputValue value, Context context) throws IOException, InterruptedException {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(value.getDay());
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH) + 1;
            int year = calendar.get(Calendar.YEAR);
            String meal ;
            if (hour <= 9) {
                meal = ThreeMeals.BREAKFAST;
            } else if (hour <= 15) {
                meal = ThreeMeals.LUNCH;
            } else {
                meal = ThreeMeals.DINNER;
            }
            context.write(new ClassDayTCSKey(value.getClass_id(), month, year, day, meal), new ClassDayTCSValue(value.getSid(), value.getMoney()));
        }
    }

    static class Reduce extends Reducer<ClassDayTCSKey, ClassDayTCSValue, ClassDayTCS, ClassDayTCS> {
        @Override
        protected void reduce(ClassDayTCSKey key, Iterable<ClassDayTCSValue> values, Context context) throws IOException, InterruptedException {
            ArrayList<ClassDayTCSValue> newValues = new ArrayList<>();
            HashSet<String> sid = new HashSet<>();
            HashMap<String, Float> student = new HashMap<>();
            int count = 0;
            int lowCount = 0;
            int highCount = 0;
            int studentCount = 0;
            int studentLowCount = 0;
            int studentHighCount = 0;
            float totalMoney = 0;
            for (ClassDayTCSValue value : values) {
                count += 1;
                totalMoney += value.getMoney();
                sid.add(value.getSid());
                ClassDayTCSValue newValue = WritableUtils.clone(value, context.getConfiguration());
                newValues.add(newValue);
            }
            studentCount = sid.size();
            float average = totalMoney / count;
            float studentAverage = totalMoney / studentCount;

            for (ClassDayTCSValue value : newValues) {
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
            String date = key.getYear() + "-" + key.getMonth() + "-" + key.getDay();
            context.write(new ClassDayTCS(key.getClass_id(), key.getMeal(), Date.valueOf(date), count, totalMoney, average, studentAverage, studentCount, lowCount, highCount, studentLowCount, studentHighCount),
                    new ClassDayTCS(key.getClass_id(), key.getMeal(), Date.valueOf(date), count, totalMoney, average, studentAverage, studentCount, lowCount, highCount, studentLowCount, studentHighCount));
        }
    }
}
