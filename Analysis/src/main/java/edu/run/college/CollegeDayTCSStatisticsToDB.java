package edu.run.college;

import edu.Dao.Class.ClassDayCSInputValue;
import edu.Dao.Class.ClassDayTCSKey;
import edu.Dao.Class.ClassDayTCSValue;
import edu.Dao.College.CollegeDayCSInputValue;
import edu.Dao.College.CollegeDayTCSInputValue;
import edu.Dao.College.CollegeDayTCSKey;
import edu.Dao.College.CollegeDayTCSValue;
import edu.Infomation.Class.ClassDayTCS;
import edu.Infomation.College.CollegeDayTCS;
import edu.run.classCS.ClassDayTCSStatisticsToDB;
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

public class CollegeDayTCSStatisticsToDB {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration configuration = new Configuration();
        configuration.set("mapreduce.framework.name", "yarn");
        DBConfiguration.configureDB(configuration, StaticConstant.jdbcDriver, StaticConstant.jdbcUrl, StaticConstant.jdbcUser, StaticConstant.jdbcPassword);
        Job job = Job.getInstance(configuration, "college day three meals consume");
        job.setJarByClass(CollegeDayTCSStatisticsToDB.class);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);

        job.setMapOutputKeyClass(CollegeDayTCSKey.class);
        job.setMapOutputValueClass(CollegeDayTCSValue.class);
        job.setOutputKeyClass(CollegeDayTCS.class);
        job.setOutputValueClass(CollegeDayTCS.class);

        job.setInputFormatClass(DBInputFormat.class);
        job.setOutputKeyClass(DBOutputFormat.class);

        DBOutputFormat.setOutput(job, "college_day_three_meals_statistics",
                "class_id", "consumption_category", "day", "consumption_count", "consumption_total_money",
                "consumption_average_money", "consumption_student_average_money", "student_count",
                "consumption_low_count", "consumption_high_count", "student_low_count", "student_high_count"
        );
        DBInputFormat.setInput(job, CollegeDayCSInputValue.class,
                "select student.id,college_id,day,consumption_category,money,consumption_total_money" +
                        " from student,consume,student_day_three_meals_statistics where student.id=consume.sid " +
                        "and student.id=student_day_three_meals_statistics.sid and to_days(execution_time)=to_days(day)",
                "select count(1) from consume");
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }
    static class Map extends Mapper<Object, CollegeDayTCSInputValue, CollegeDayTCSKey, CollegeDayTCSValue>{
        @Override
        protected void map(Object key, CollegeDayTCSInputValue value, Context context) throws IOException, InterruptedException {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(value.getDay());
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            context.write(new CollegeDayTCSKey(value.getCollege_id(), month, year, day, value.getMeal()), new CollegeDayTCSValue(value.getSid(), value.getMoney(), value.getStudentTotalMoney()));
        }
    }
    static class Reduce extends Reducer<CollegeDayTCSKey,CollegeDayTCSValue, CollegeDayTCS,CollegeDayTCS>{
        @Override
        protected void reduce(CollegeDayTCSKey key, Iterable<CollegeDayTCSValue> values, Context context) throws IOException, InterruptedException {
            ArrayList<CollegeDayTCSValue> newValues = new ArrayList<>();
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
            for (CollegeDayTCSValue value : values) {
                count += 1;
                totalMoney += value.getMoney();
                sid.add(value.getSid());
                CollegeDayTCSValue newValue = WritableUtils.clone(value, context.getConfiguration());
                newValues.add(newValue);
            }
            studentCount = sid.size();
            float average = totalMoney / count;
            float studentAverage = totalMoney / studentCount;

            for (CollegeDayTCSValue value : newValues) {
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
            context.write(new CollegeDayTCS(key.getCollege_id(), key.getMeal(), Date.valueOf(date), count, totalMoney, average, studentAverage, studentCount, lowCount, highCount, studentLowCount, studentHighCount),
                    new CollegeDayTCS(key.getCollege_id(), key.getMeal(), Date.valueOf(date), count, totalMoney, average, studentAverage, studentCount, lowCount, highCount, studentLowCount, studentHighCount));
        }
    }
}
