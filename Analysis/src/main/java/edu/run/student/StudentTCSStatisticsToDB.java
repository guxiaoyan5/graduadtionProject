package edu.run.student;


import edu.Dao.Student.StudentTCSKey;
import edu.Dao.Student.StudentTCSValue;
import edu.Infomation.Consume;
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

public class StudentTCSStatisticsToDB {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration configuration = new Configuration();
        configuration.set("mapreduce.framework.name", "yarn");
        DBConfiguration.configureDB(configuration, StaticConstant.jdbcDriver, StaticConstant.jdbcUrl, StaticConstant.jdbcUser, StaticConstant.jdbcPassword);
        Job job = Job.getInstance(configuration, "student Three consume");
        job.setJarByClass(StudentTCSStatisticsToDB.class);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);

        job.setMapOutputKeyClass(StudentTCSKey.class);
        job.setMapOutputValueClass(StudentTCSValue.class);

        job.setOutputKeyClass(StudentTCS.class);
        job.setOutputValueClass(StudentTCS.class);

        job.setInputFormatClass(DBInputFormat.class);
        job.setOutputFormatClass(DBOutputFormat.class);

        DBOutputFormat.setOutput(job, "student_three_meals_statistics",
                "sid", "consumption_category", "consumption_count", "consumption_total_money",
                "consumption_average_money");
        DBInputFormat.setInput(job, Consume.class, "consume", null, null,
                "sid", "execution_time", "money", "store_id ", "mode");
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }

    static class Map extends Mapper<Object, Consume, StudentTCSKey, StudentTCSValue> {
        @Override
        protected void map(Object key, Consume value, Context context) throws IOException, InterruptedException {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(value.getExecution_time());
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            String meal ;
            if (hour <= 9) {
                meal = ThreeMeals.BREAKFAST;
            } else if (hour <= 15) {
                meal = ThreeMeals.LUNCH;
            } else  {
                meal = ThreeMeals.DINNER;
            }
            context.write(new StudentTCSKey(value.getSid(), meal), new StudentTCSValue(value.getMoney()));
        }
    }

    static class Reduce extends Reducer<StudentTCSKey, StudentTCSValue, StudentTCS, StudentTCS> {
        @Override
        protected void reduce(StudentTCSKey key, Iterable<StudentTCSValue> values, Context context) throws IOException, InterruptedException {
            int count = 0;
            float totalMoney = 0, averageMoney = 0;
            for (StudentTCSValue value : values) {
                count += 1;
                totalMoney += value.getMoney();
            }
            averageMoney = totalMoney / count;
            context.write(new StudentTCS(key.getSid(), key.getMeal(), count, totalMoney, averageMoney), new StudentTCS(key.getSid(), key.getMeal(), count, totalMoney, averageMoney));
        }
    }
}
