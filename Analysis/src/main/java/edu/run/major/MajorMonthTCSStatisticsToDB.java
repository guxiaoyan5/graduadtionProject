package edu.run.major;

import edu.Dao.Class.ClassMonthTCSInputValue;
import edu.Dao.Class.ClassMonthTCSKey;
import edu.Dao.Class.ClassMonthTCSValue;
import edu.Dao.Major.MajorMonthCSInputValue;
import edu.Dao.Major.MajorMonthTCSInputValue;
import edu.Dao.Major.MajorMonthTCSKey;
import edu.Dao.Major.MajorMonthTCSValue;
import edu.Infomation.Class.ClassMonthTCS;
import edu.Infomation.Major.MajorMonthTCS;
import edu.run.classCS.ClassMonthTCSStatisticsToDB;
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
import java.util.HashSet;

public class MajorMonthTCSStatisticsToDB {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration configuration = new Configuration();
        configuration.set("mapreduce.framework.name", "yarn");
        DBConfiguration.configureDB(configuration, StaticConstant.jdbcDriver, StaticConstant.jdbcUrl, StaticConstant.jdbcUser, StaticConstant.jdbcPassword);
        Job job = Job.getInstance(configuration, "major month three meals consume");
        job.setJarByClass(MajorMonthTCSStatisticsToDB.class);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);

        job.setMapOutputKeyClass(MajorMonthTCSKey.class);
        job.setMapOutputValueClass(MajorMonthTCSValue.class);
        job.setOutputKeyClass(MajorMonthTCS.class);
        job.setOutputValueClass(MajorMonthTCS.class);

        job.setInputFormatClass(DBInputFormat.class);
        job.setOutputKeyClass(DBOutputFormat.class);

        DBOutputFormat.setOutput(job, "major_month_three_meals_statistics",
                "major_id", "consumption_category", "month", "year", "consumption_count", "consumption_total_money",
                "consumption_average_money", "consumption_student_average_money", "student_count",
                "consumption_low_count", "consumption_high_count", "student_low_count", "student_high_count"
        );
        DBInputFormat.setInput(job, MajorMonthTCSInputValue.class,
                "select student.id,major_id,day,consumption_category,money,consumption_total_money" +
                        " from student,consume,student_month_three_meals_statistics " +
                        "where student.id=consume.sid and student.id=student_month_three_meals_statistics.sid and year(execution_time) = year and month(execution_time) = month",
                "select count(1) from consume");
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }
    static class Map extends Mapper<Object, MajorMonthTCSInputValue, MajorMonthTCSKey, MajorMonthTCSValue>{
        @Override
        protected void map(Object key, MajorMonthTCSInputValue value, Context context) throws IOException, InterruptedException {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(value.getDay());
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            context.write(new MajorMonthTCSKey(value.getMajor_id(), month, year, value.getMeal()), new MajorMonthTCSValue(value.getSid(), value.getMoney(), value.getStudentTotalMoney()));
        }
    }
    static class Reduce extends Reducer<MajorMonthTCSKey,MajorMonthTCSValue, MajorMonthTCS,MajorMonthTCS>{
        @Override
        protected void reduce(MajorMonthTCSKey key, Iterable<MajorMonthTCSValue> values, Context context) throws IOException, InterruptedException {
            ArrayList<MajorMonthTCSValue> newValues = new ArrayList<>();
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
            for (MajorMonthTCSValue value : values) {
                count += 1;
                totalMoney += value.getMoney();
                sid.add(value.getSid());
                MajorMonthTCSValue newValue = WritableUtils.clone(value, context.getConfiguration());
                newValues.add(newValue);
            }
            studentCount = sid.size();
            float average = totalMoney / count;
            float studentAverage = totalMoney / studentCount;
            for (MajorMonthTCSValue value : newValues) {
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
            context.write(new MajorMonthTCS(key.getMajor_id(), key.getMeal(), key.getMonth(), key.getYear(), count, totalMoney, average, studentAverage, studentCount, lowCount, highCount, studentLowCount, studentHighCount),
                    new MajorMonthTCS(key.getMajor_id(), key.getMeal(), key.getMonth(), key.getYear(), count, totalMoney, average, studentAverage, studentCount, lowCount, highCount, studentLowCount, studentHighCount));
        }
    }
}
