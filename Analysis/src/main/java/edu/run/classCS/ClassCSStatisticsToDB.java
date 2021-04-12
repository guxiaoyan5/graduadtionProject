package edu.run.classCS;

import edu.Dao.Class.*;
import edu.Infomation.Class.ClassCS;
import edu.Infomation.Class.ClassDayCS;
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

public class ClassCSStatisticsToDB {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration configuration = new Configuration();
        configuration.set("mapreduce.framework.name", "yarn");
        DBConfiguration.configureDB(configuration, StaticConstant.jdbcDriver, StaticConstant.jdbcUrl, StaticConstant.jdbcUser, StaticConstant.jdbcPassword);
        Job job = Job.getInstance(configuration, "class consume");
        job.setJarByClass(ClassCSStatisticsToDB.class);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);

        job.setMapOutputKeyClass(ClassCSKey.class);
        job.setMapOutputValueClass(ClassCSValue.class);
        job.setOutputKeyClass(ClassCS.class);
        job.setOutputValueClass(ClassCS.class);

        job.setInputFormatClass(DBInputFormat.class);
        job.setOutputKeyClass(DBOutputFormat.class);

        DBOutputFormat.setOutput(job, "class_consumption_statistics",
                "class_id", "consumption_count", "consumption_total_money",
                "consumption_average_money", "consumption_student_average_money", "student_count",
                "consumption_low_count", "consumption_high_count", "student_low_count", "student_high_count"
        );
        DBInputFormat.setInput(job, ClassCSInputValue.class,
                "select student.id,class_id,execution_time,money,consumption_total_money " +
                        "from student,consume,student_consumption_statistics where student.id=consume.sid " +
                        "and student.id=student_consumption_statistics.sid",
                "select count(1) from consume");
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }
    private static class Map extends Mapper<Object, ClassCSInputValue, ClassCSKey, ClassCSValue> {
        @Override
        protected void map(Object key, ClassCSInputValue value, Context context) throws IOException, InterruptedException {
            context.write(new ClassCSKey(value.getClass_id()), new ClassCSValue(value.getSid(), value.getMoney(), value.getStudentTotalMoney()));
        }
    }
    private static class Reduce extends Reducer<ClassCSKey, ClassCSValue, ClassCS, ClassCS> {
        @Override
        protected void reduce(ClassCSKey key, Iterable<ClassCSValue> values, Context context) throws IOException, InterruptedException {
            ArrayList<ClassCSValue> newValues = new ArrayList<>();
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
            for (ClassCSValue value : values) {
                count += 1;
                totalMoney += value.getMoney();
                sid.add(value.getSid());
                ClassCSValue newValue = WritableUtils.clone(value, context.getConfiguration());
                newValues.add(newValue);
            }
            studentCount = sid.size();
            float average = totalMoney / count;
            float studentAverage = totalMoney / studentCount;
            for (ClassCSValue value : newValues) {
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
            context.write(new ClassCS(key.getClass_id(), count, totalMoney, average, studentAverage, studentCount, lowCount, highCount, studentLowCount, studentHighCount),
                    new ClassCS(key.getClass_id(), count, totalMoney, average, studentAverage, studentCount, lowCount, highCount, studentLowCount, studentHighCount));
        }
    }
}
