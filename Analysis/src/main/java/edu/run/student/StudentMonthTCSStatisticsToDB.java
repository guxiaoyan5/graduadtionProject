package edu.run.student;

import edu.Dao.Student.*;
import edu.Infomation.Consume;
import edu.Infomation.Student.StudentMonthCS;
import edu.Infomation.Student.StudentMonthTCS;
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
import java.util.Calendar;

public class StudentMonthTCSStatisticsToDB {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration configuration = new Configuration();
        configuration.set("mapreduce.framework.name", "yarn");
        DBConfiguration.configureDB(configuration, StaticConstant.jdbcDriver, StaticConstant.jdbcUrl, StaticConstant.jdbcUser, StaticConstant.jdbcPassword);
        Job job = Job.getInstance(configuration, "student month three meals consume");

        job.setJarByClass(StudentMonthTCSStatisticsToDB.class);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);

        job.setMapOutputKeyClass(StudentMonthTCSKey.class);
        job.setMapOutputValueClass(StudentMonthTCSValue.class);

        job.setOutputKeyClass(StudentMonthTCS.class);
        job.setOutputValueClass(StudentMonthTCS.class);

        job.setInputFormatClass(DBInputFormat.class);
        job.setOutputFormatClass(DBOutputFormat.class);

        DBOutputFormat.setOutput(job, "student_month_three_meals_statistics",
                "sid", "month", "year", "consumption_category", "consumption_count", "consumption_total_money", "consumption_average_money");
        DBInputFormat.setInput(job, Consume.class, "consume", null, null,
                "sid", "execution_time", "money", "store_id ", "mode");
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }

    static class Map extends Mapper<Object, Consume, StudentMonthTCSKey, StudentMonthTCSValue> {
        @Override
        protected void map(Object key, Consume value, Context context) throws IOException, InterruptedException {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(value.getExecution_time());
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int month = calendar.get(Calendar.MONTH) + 1;
            int year = calendar.get(Calendar.YEAR);
            ThreeMeals meal = null;
            if (hour >= 5 && hour <= 9) {
                meal = ThreeMeals.BREAKFAST;
            } else if (hour <= 15) {
                meal = ThreeMeals.LUNCH;
            } else if (hour < 23) {
                meal = ThreeMeals.DINNER;
            }
            context.write(new StudentMonthTCSKey(value.getSid(), month, year, meal), new StudentMonthTCSValue(value.getMoney()));
        }
    }

    static class Reduce extends Reducer<StudentMonthTCSKey, StudentMonthTCSValue, StudentMonthTCS, StudentMonthTCS> {
        @Override
        protected void reduce(StudentMonthTCSKey key, Iterable<StudentMonthTCSValue> values, Context context) throws IOException, InterruptedException {
            int count = 0;
            float totalMoney = 0;
            for (StudentMonthTCSValue value : values) {
                count += 1;
                totalMoney += value.getMoney();
            }
            float average = totalMoney / count;
            context.write(new StudentMonthTCS(key.getSid(), key.getMonth(), key.getYear(), key.getMeal(), count, totalMoney, average), new StudentMonthTCS(key.getSid(), key.getMonth(), key.getYear(), key.getMeal(), count, totalMoney, average));
        }
    }
}
