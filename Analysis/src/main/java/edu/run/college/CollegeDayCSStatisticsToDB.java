package edu.run.college;

import edu.Dao.Class.ClassDayCSInputValue;
import edu.Dao.Class.ClassDayCSKey;
import edu.Dao.Class.ClassDayCSValue;
import edu.Dao.College.CollegeDayCSInputValue;
import edu.Dao.College.CollegeDayCSKey;
import edu.Dao.College.CollegeDayCSValue;
import edu.Infomation.Class.ClassDayCS;
import edu.Infomation.College.CollegeDayCS;
import edu.run.classCS.ClassDayCSStatisticsToDB;
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

public class CollegeDayCSStatisticsToDB {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration configuration = new Configuration();
        configuration.set("mapreduce.framework.name", "yarn");
        DBConfiguration.configureDB(configuration, StaticConstant.jdbcDriver, StaticConstant.jdbcUrl, StaticConstant.jdbcUser, StaticConstant.jdbcPassword);
        Job job = Job.getInstance(configuration, "college day consume");
        job.setJarByClass(CollegeDayCSStatisticsToDB.class);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);

        job.setMapOutputKeyClass(CollegeDayCSKey.class);
        job.setMapOutputValueClass(CollegeDayCSValue.class);
        job.setOutputKeyClass(CollegeDayCS.class);
        job.setOutputValueClass(CollegeDayCS.class);

        job.setInputFormatClass(DBInputFormat.class);
        job.setOutputKeyClass(DBOutputFormat.class);

        DBOutputFormat.setOutput(job, "college_day_consumption_statistics",
                "college_id", "day", "consumption_count", "consumption_total_money",
                "consumption_average_money", "consumption_student_average_money", "student_count",
                "consumption_low_count", "consumption_high_count", "student_low_count", "student_high_count"
        );
        DBInputFormat.setInput(job, CollegeDayCSInputValue.class,
                "select student.id,college_id,execution_time,money,consumption_total_money from student,consume,student_day_consumption_statistics where student.id=consume.sid and student.id=student_day_consumption_statistics.sid and to_days(execution_time)=to_days(day)",
                "select count(1) from consume");
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }
    private static class Map extends Mapper<Object, CollegeDayCSInputValue, CollegeDayCSKey, CollegeDayCSValue> {
        @Override
        protected void map(Object key, CollegeDayCSInputValue value, Context context) throws IOException, InterruptedException {
            String new_value = value.getDay().toString().split(" ")[0];
            int year = Integer.parseInt(new_value.split("-")[0]);
            int month = Integer.parseInt(new_value.split("-")[1]);
            int day = Integer.parseInt(new_value.split("-")[2]);
            context.write(new CollegeDayCSKey(value.getCollege_id(),month,year,day),new CollegeDayCSValue(value.getSid(), value.getMoney(), value.getStudentTotalMoney()));
        }
    }
    private static class Reduce extends Reducer<CollegeDayCSKey,CollegeDayCSValue, CollegeDayCS,CollegeDayCS> {
        @Override
        protected void reduce(CollegeDayCSKey key, Iterable<CollegeDayCSValue> values, Context context) throws IOException, InterruptedException {
            ArrayList<CollegeDayCSValue> newValues = new ArrayList<>();
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
            for (CollegeDayCSValue value : values) {
                count += 1;
                totalMoney += value.getMoney();
                sid.add(value.getSid());
                CollegeDayCSValue newValue = WritableUtils.clone(value,context.getConfiguration());
                newValues.add(newValue);
            }
            studentCount = sid.size();
            float average = totalMoney / count;
            float studentAverage = totalMoney / studentCount;
            for (CollegeDayCSValue value : newValues) {
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
            context.write(new CollegeDayCS(key.getCollege_id(), Date.valueOf(date), count, totalMoney, average, studentAverage, studentCount, lowCount, highCount, studentLowCount, studentHighCount),
                    new CollegeDayCS(key.getCollege_id(), Date.valueOf(date), count, totalMoney, average, studentAverage, studentCount, lowCount, highCount, studentLowCount, studentHighCount));
        }
    }
}
