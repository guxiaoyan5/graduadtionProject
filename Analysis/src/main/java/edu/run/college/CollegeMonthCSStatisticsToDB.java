package edu.run.college;


import edu.Dao.Class.ClassMonthCSInputValue;
import edu.Dao.Class.ClassMonthCSKey;
import edu.Dao.Class.ClassMonthCSValue;
import edu.Dao.College.CollegeMonthCSInputValue;
import edu.Dao.College.CollegeMonthCSKey;
import edu.Dao.College.CollegeMonthCSValue;
import edu.Infomation.Class.ClassMonthCS;
import edu.Infomation.College.CollegeMonthCS;
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

public class CollegeMonthCSStatisticsToDB {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration configuration = new Configuration();
        configuration.set("mapreduce.framework.name", "yarn");
        DBConfiguration.configureDB(configuration, StaticConstant.jdbcDriver, StaticConstant.jdbcUrl, StaticConstant.jdbcUser, StaticConstant.jdbcPassword);
        Job job = Job.getInstance(configuration, "College month consume");
        job.setJarByClass(CollegeMonthCSStatisticsToDB.class);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);

        job.setMapOutputKeyClass(CollegeMonthCSKey.class);
        job.setMapOutputValueClass(CollegeMonthCSValue.class);
        job.setOutputKeyClass(CollegeMonthCS.class);
        job.setOutputValueClass(CollegeMonthCS.class);

        job.setInputFormatClass(DBInputFormat.class);
        job.setOutputKeyClass(DBOutputFormat.class);

        DBOutputFormat.setOutput(job, "college_month_consumption_statistics",
                "college_id", "month", "year", "consumption_count", "consumption_total_money",
                "consumption_average_money", "consumption_student_average_money", "student_count",
                "consumption_low_count", "consumption_high_count", "student_low_count", "student_high_count"
        );
        DBInputFormat.setInput(job, CollegeMonthCSInputValue.class,
                "select student.id,college_id,execution_time,money,consumption_total_money from student,consume,student_month_consumption_statistics where student.id=consume.sid and student.id=student_month_consumption_statistics.sid",
                "select count(1) from consume");
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }

    private static class Map extends Mapper<Object, CollegeMonthCSInputValue, CollegeMonthCSKey, CollegeMonthCSValue> {
        @Override
        protected void map(Object key, CollegeMonthCSInputValue value, Context context) throws IOException, InterruptedException {
            String new_value = value.getDay().toString().split(" ")[0];
            int year = Integer.parseInt(new_value.split("-")[0]);
            int month = Integer.parseInt(new_value.split("-")[1]);
            context.write(new CollegeMonthCSKey(value.getCollege_id(), month, year), new CollegeMonthCSValue(value.getSid(), value.getMoney(), value.getStudentTotalMoney()));
        }
    }

    private static class Reduce extends Reducer<CollegeMonthCSKey, CollegeMonthCSValue, CollegeMonthCS, CollegeMonthCS> {
        @Override
        protected void reduce(CollegeMonthCSKey key, Iterable<CollegeMonthCSValue> values, Context context) throws IOException, InterruptedException {
            ArrayList<CollegeMonthCSValue> newValues = new ArrayList<>();
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
            for (CollegeMonthCSValue value : values) {
                count += 1;
                totalMoney += value.getMoney();
                sid.add(value.getSid());
                CollegeMonthCSValue newValue = WritableUtils.clone(value,context.getConfiguration());
                newValues.add(newValue);
            }
            studentCount = sid.size();
            float average = totalMoney / count;
            float studentAverage = totalMoney / studentCount;
            for (CollegeMonthCSValue value : newValues) {
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
            context.write(new CollegeMonthCS(key.getCollege_id(), key.getMonth(), key.getYear(), count, totalMoney, average, studentAverage, studentCount, lowCount, highCount, studentLowCount, studentHighCount),
                    new CollegeMonthCS(key.getCollege_id(), key.getMonth(), key.getYear(), count, totalMoney, average, studentAverage, studentCount, lowCount, highCount, studentLowCount, studentHighCount));
        }
    }
}


