package edu.run.student;

import edu.Dao.Student.*;
import edu.Infomation.Consume;
import edu.Infomation.Student.StudentDayTCS;
import edu.Infomation.Student.StudentTCS;
import edu.Infomation.enumObject.ThreeMeals;
import edu.util.StaticConstant;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBInputFormat;
import org.apache.hadoop.mapreduce.lib.db.DBOutputFormat;

import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;

public class StudentDayTCSStatisticsToDB {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration configuration = new Configuration();
        configuration.set("mapreduce.framework.name", "yarn");
        DBConfiguration.configureDB(configuration, StaticConstant.jdbcDriver, StaticConstant.jdbcUrl, StaticConstant.jdbcUser, StaticConstant.jdbcPassword);
        Job job = Job.getInstance(configuration, "student day Three meals consume");
        job.setJarByClass(StudentDayTCSStatisticsToDB.class);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);

        job.setMapOutputKeyClass(StudentDayTCSKey.class);
        job.setMapOutputValueClass(StudentDayTCSValue.class);

        job.setOutputKeyClass(StudentDayTCS.class);
        job.setOutputValueClass(StudentDayTCS.class);

        job.setInputFormatClass(DBInputFormat.class);
        job.setOutputFormatClass(DBOutputFormat.class);

        DBOutputFormat.setOutput(job, "student_day_three_meals_statistics",
                "sid", "day", "consumption_category", "consumption_count", "consumption_total_money",
                "consumption_average_money");
        DBInputFormat.setInput(job, Consume.class, "consume", null, null,
                "sid", "execution_time", "money", "store_id ", "mode");
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }

    static class Map extends Mapper<Object, Consume, StudentDayTCSKey, StudentDayTCSValue> {
        @Override
        protected void map(Object key, Consume value, Context context) throws IOException, InterruptedException {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(value.getExecution_time());
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
            context.write(new StudentDayTCSKey(value.getSid(), year, month, day, meal), new StudentDayTCSValue(value.getMoney()));
        }
    }

    static class Reduce extends Reducer<StudentDayTCSKey, StudentDayTCSValue, StudentDayTCS, StudentDayTCS> {
        @Override
        protected void reduce(StudentDayTCSKey key, Iterable<StudentDayTCSValue> values, Context context) throws IOException, InterruptedException {
            int count = 0;
            float totalMoney = 0;
            for (StudentDayTCSValue value : values) {
                count += 1;
                totalMoney += value.getMoney();
            }
            float average = totalMoney / count;
            String date = key.getYear() + "-" + key.getMonth() + "-" + key.getDay();
            context.write(new StudentDayTCS(key.getSid(), Date.valueOf(date), key.getMeal(), count, totalMoney, average), new StudentDayTCS(key.getSid(), Date.valueOf(date), key.getMeal(), count, totalMoney, average));
        }
    }
}
