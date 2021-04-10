package edu.run.major;

/*
已检验
*/

import edu.Dao.Class.ClassMonthCSInputValue;
import edu.Dao.Class.ClassMonthCSKey;
import edu.Dao.Class.ClassMonthCSValue;
import edu.Dao.Major.MajorMonthCSInputValue;
import edu.Dao.Major.MajorMonthCSKey;
import edu.Dao.Major.MajorMonthCSValue;
import edu.Infomation.Class.ClassMonthCS;
import edu.Infomation.Major.MajorMonthCS;
import edu.run.classCS.ClassMonthCSStatisticsToDB;
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
import java.util.HashSet;

public class MajorMonthCSStatisticsToDB {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration configuration = new Configuration();
        configuration.set("mapreduce.framework.name", "yarn");
        DBConfiguration.configureDB(configuration, StaticConstant.jdbcDriver, StaticConstant.jdbcUrl, StaticConstant.jdbcUser, StaticConstant.jdbcPassword);
        Job job = Job.getInstance(configuration, "major month consume");
        job.setJarByClass(MajorMonthCSStatisticsToDB.class);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);

        job.setMapOutputKeyClass(MajorMonthCSKey.class);
        job.setMapOutputValueClass(MajorMonthCSValue.class);
        job.setOutputKeyClass(MajorMonthCS.class);
        job.setOutputValueClass(MajorMonthCS.class);

        job.setInputFormatClass(DBInputFormat.class);
        job.setOutputKeyClass(DBOutputFormat.class);

        DBOutputFormat.setOutput(job, "major_month_consumption_statistics",
                "major_id", "month", "year", "consumption_count", "consumption_total_money",
                "consumption_average_money", "consumption_student_average_money", "student_count",
                "consumption_low_count", "consumption_high_count", "student_low_count", "student_high_count"
        );
        DBInputFormat.setInput(job, MajorMonthCSInputValue.class,
                "select student.id,major_id,execution_time,money,consumption_total_money from student,consume,student_month_consumption_statistics where student.id=consume.sid and student.id=student_month_consumption_statistics.sid",
                "select count(1) from consume");
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }

    private static class Map extends Mapper<Object, MajorMonthCSInputValue, MajorMonthCSKey, MajorMonthCSValue> {
        @Override
        protected void map(Object key, MajorMonthCSInputValue value, Context context) throws IOException, InterruptedException {
            String new_value = value.getDay().toString().split(" ")[0];
            int year = Integer.parseInt(new_value.split("-")[0]);
            int month = Integer.parseInt(new_value.split("-")[1]);
            context.write(new MajorMonthCSKey(value.getMajor_id(), month, year), new MajorMonthCSValue(value.getSid(), value.getMoney(), value.getStudentTotalMoney()));
        }
    }

    private static class Reduce extends Reducer<MajorMonthCSKey,MajorMonthCSValue, MajorMonthCS, MajorMonthCS> {
        @Override
        protected void reduce(MajorMonthCSKey key, Iterable<MajorMonthCSValue> values, Context context) throws IOException, InterruptedException {
            ArrayList<MajorMonthCSValue> newValues = new ArrayList<>();
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
            for (MajorMonthCSValue value : values) {
                count += 1;
                totalMoney += value.getMoney();
                sid.add(value.getSid());
                MajorMonthCSValue newValue = WritableUtils.clone(value,context.getConfiguration());
                newValues.add(newValue);
            }
            studentCount = sid.size();
            float average = totalMoney / count;
            float studentAverage = totalMoney / studentCount;
            for (MajorMonthCSValue value : newValues) {
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
            context.write(new MajorMonthCS(key.getMajor_id(), key.getMonth(), key.getYear(), count, totalMoney, average, studentAverage, studentCount, lowCount, highCount, studentLowCount, studentHighCount),
                    new MajorMonthCS(key.getMajor_id(), key.getMonth(), key.getYear(), count, totalMoney, average, studentAverage, studentCount, lowCount, highCount, studentLowCount, studentHighCount));
        }
    }
}
