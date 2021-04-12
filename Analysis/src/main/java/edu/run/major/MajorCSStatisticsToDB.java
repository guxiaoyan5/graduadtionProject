package edu.run.major;

import edu.Dao.Class.ClassCSInputValue;
import edu.Dao.Class.ClassCSKey;
import edu.Dao.Class.ClassCSValue;
import edu.Dao.Class.ClassDayCSInputValue;
import edu.Dao.Major.MajorCSInputValue;
import edu.Dao.Major.MajorCSKey;
import edu.Dao.Major.MajorCSValue;
import edu.Dao.Major.MajorDayCSInputValue;
import edu.Infomation.Class.ClassCS;
import edu.Infomation.Major.MajorCS;
import edu.run.classCS.ClassCSStatisticsToDB;
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

public class MajorCSStatisticsToDB {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration configuration = new Configuration();
        configuration.set("mapreduce.framework.name", "yarn");
        DBConfiguration.configureDB(configuration, StaticConstant.jdbcDriver, StaticConstant.jdbcUrl, StaticConstant.jdbcUser, StaticConstant.jdbcPassword);
        Job job = Job.getInstance(configuration, "Major  consume");
        job.setJarByClass(MajorCSStatisticsToDB.class);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);

        job.setMapOutputKeyClass(MajorCSKey.class);
        job.setMapOutputValueClass(MajorCSValue.class);
        job.setOutputKeyClass(MajorCS.class);
        job.setOutputValueClass(MajorCS.class);

        job.setInputFormatClass(DBInputFormat.class);
        job.setOutputKeyClass(DBOutputFormat.class);

        DBOutputFormat.setOutput(job, "major_consumption_statistics",
                "major_id", "consumption_count", "consumption_total_money",
                "consumption_average_money", "consumption_student_average_money", "student_count",
                "consumption_low_count", "consumption_high_count", "student_low_count", "student_high_count"
        );
        DBInputFormat.setInput(job, MajorCSInputValue.class,
                "select student.id,Major_id,execution_time,money,consumption_total_money from student,consume,student_consumption_statistics where student.id=consume.sid and student.id=student_consumption_statistics.sid",
                "select count(1) from consume");
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }

    private static class Map extends Mapper<Object, MajorCSInputValue, MajorCSKey, MajorCSValue> {
        @Override
        protected void map(Object key, MajorCSInputValue value, Context context) throws IOException, InterruptedException {
            context.write(new MajorCSKey(value.getMajor_id()), new MajorCSValue(value.getSid(), value.getMoney(), value.getStudentTotalMoney()));
        }
    }

    private static class Reduce extends Reducer<MajorCSKey, MajorCSValue, MajorCS, MajorCS> {
        @Override
        protected void reduce(MajorCSKey key, Iterable<MajorCSValue> values, Context context) throws IOException, InterruptedException {
            ArrayList<MajorCSValue> newValues = new ArrayList<>();
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
            for (MajorCSValue value : values) {
                count += 1;
                totalMoney += value.getMoney();
                sid.add(value.getSid());
                MajorCSValue newValue = WritableUtils.clone(value,context.getConfiguration());
                newValues.add(newValue);
            }
            studentCount = sid.size();
            float average = totalMoney / count;
            float studentAverage = totalMoney / studentCount;
            for (MajorCSValue value : newValues) {
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
            context.write(new MajorCS(key.getMajor_id(), count, totalMoney, average, studentAverage, studentCount, lowCount, highCount, studentLowCount, studentHighCount),
                    new MajorCS(key.getMajor_id(), count, totalMoney, average, studentAverage, studentCount, lowCount, highCount, studentLowCount, studentHighCount));
        }
    }
}
