package edu.run.classCS;

import edu.Dao.Class.*;
import edu.Infomation.Class.ClassDayCS;
import edu.Infomation.Class.ClassDayTCS;
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
import java.util.HashSet;

public class ClassDayTCSStatisticsToDB {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration configuration = new Configuration();
        configuration.set("mapreduce.framework.name", "yarn");
        DBConfiguration.configureDB(configuration, StaticConstant.jdbcDriver, StaticConstant.jdbcUrl, StaticConstant.jdbcUser, StaticConstant.jdbcPassword);
        Job job = Job.getInstance(configuration, "class day three meals consume");
        job.setJarByClass(ClassDayTCSStatisticsToDB.class);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);

        job.setMapOutputKeyClass(ClassDayTCSKey.class);
        job.setMapOutputValueClass(ClassDayTCSValue.class);
        job.setOutputKeyClass(ClassDayTCS.class);
        job.setOutputValueClass(ClassDayTCS.class);

        job.setInputFormatClass(DBInputFormat.class);
        job.setOutputKeyClass(DBOutputFormat.class);

        DBOutputFormat.setOutput(job, "class_day_three_meals_statistics",
                "class_id", "consumption_category", "day", "consumption_count", "consumption_total_money",
                "consumption_average_money", "consumption_student_average_money", "student_count",
                "consumption_low_count", "consumption_high_count", "student_low_count", "student_high_count"
        );
        DBInputFormat.setInput(job, ClassDayCSInputValue.class,
                "select student.id,class_id,day,consumption_category,money,consumption_total_money" +
                        " from student,consume,student_day_three_meals_statistics where student.id=consume.sid " +
                        "and student.id=student_day_three_meals_statistics.sid and to_days(execution_time)=to_days(day)",
                "select count(1) from consume");
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }

    static class Map extends Mapper<Object, ClassDayTCSInputValue, ClassDayTCSKey, ClassDayTCSValue> {
        @Override
        protected void map(Object key, ClassDayTCSInputValue value, Context context) throws IOException, InterruptedException {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(value.getDay());
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            context.write(new ClassDayTCSKey(value.getClass_id(), month, year, day, value.getMeal()), new ClassDayTCSValue(value.getSid(), value.getMoney(), value.getStudentTotalMoney()));
        }
    }

    static class Reduce extends Reducer<ClassDayTCSKey, ClassDayTCSValue, ClassDayTCS, ClassDayTCS> {
        @Override
        protected void reduce(ClassDayTCSKey key, Iterable<ClassDayTCSValue> values, Context context) throws IOException, InterruptedException {
            ArrayList<ClassDayTCSValue> newValues = new ArrayList<>();
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
                if (value.getStudentTotalMoney() < studentAverage) {
                    sidLow.add(value.getSid());
                } else {
                    highLow.add(value.getSid());
                }
            }

            studentLowCount = sidLow.size();
            studentHighCount = highLow.size();
            String date = key.getYear() + "-" + key.getMonth() + "-" + key.getDay();
            context.write(new ClassDayTCS(key.getClass_id(), key.getMeal(), Date.valueOf(date), count, totalMoney, average, studentAverage, studentCount, lowCount, highCount, studentLowCount, studentHighCount),
                    new ClassDayTCS(key.getClass_id(), key.getMeal(), Date.valueOf(date), count, totalMoney, average, studentAverage, studentCount, lowCount, highCount, studentLowCount, studentHighCount));
        }
    }
}
