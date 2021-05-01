package edu.run.classCS;

import edu.Dao.Class.*;
import edu.Infomation.Class.ClassCS;
import edu.Infomation.Class.ClassTCS;
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

public class ClassTCSStatisticsToDB {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration configuration = new Configuration();
        configuration.set("mapreduce.framework.name", "yarn");
        DBConfiguration.configureDB(configuration, StaticConstant.jdbcDriver, StaticConstant.jdbcUrl, StaticConstant.jdbcUser, StaticConstant.jdbcPassword);
        Job job = Job.getInstance(configuration, "class three meals consume");
        job.setJarByClass(ClassTCSStatisticsToDB.class);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);

        job.setMapOutputKeyClass(ClassTCSKey.class);
        job.setMapOutputValueClass(ClassTCSValue.class);
        job.setOutputKeyClass(ClassTCS.class);
        job.setOutputValueClass(ClassTCS.class);

        job.setInputFormatClass(DBInputFormat.class);
        job.setOutputKeyClass(DBOutputFormat.class);

        DBOutputFormat.setOutput(job, "class_three_meals_statistics",
                "class_id", "consumption_category", "consumption_count", "consumption_total_money",
                "consumption_average_money", "consumption_student_average_money", "student_count",
                "consumption_low_count", "consumption_high_count", "student_low_count", "student_high_count"
        );
        DBInputFormat.setInput(job, ClassTCSInputValue.class,
                "select student.id,class_id,execution_time,consumption_category,money,consumption_total_money " +
                        "from student,consume,student_three_meals_statistics where student.id=consume.sid " +
                        "and student.id=student_three_meals_statistics.sid and (((hour(execution_time) between 0 and 9) and consumption_category = '早') or ((hour(execution_time) between 10 and 15) and consumption_category = '午') or ((hour(execution_time) between 16 and 24) and consumption_category = '晚'))",
                "select count(1) from consume");
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }

    static class Map extends Mapper<Object, ClassTCSInputValue, ClassTCSKey, ClassTCSValue> {
        @Override
        protected void map(Object key, ClassTCSInputValue value, Context context) throws IOException, InterruptedException {
            context.write(new ClassTCSKey(value.getClass_id(), value.getMeal()), new ClassTCSValue(value.getSid(), value.getMoney(), value.getStudentTotalMoney()));
        }
    }

    static class Reduce extends Reducer<ClassTCSKey, ClassTCSValue, ClassTCS, ClassTCS> {
        @Override
        protected void reduce(ClassTCSKey key, Iterable<ClassTCSValue> values, Context context) throws IOException, InterruptedException {
            ArrayList<ClassTCSValue> newValues = new ArrayList<>();
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
            for (ClassTCSValue value : values) {
                count += 1;
                totalMoney += value.getMoney();
                sid.add(value.getSid());
                ClassTCSValue newValue = WritableUtils.clone(value, context.getConfiguration());
                newValues.add(newValue);
            }
            studentCount = sid.size();
            float average = totalMoney / count;
            float studentAverage = totalMoney / studentCount;
            for (ClassTCSValue value : newValues) {
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
            context.write(new ClassTCS(key.getClass_id(), key.getMeal(), count, totalMoney, average, studentAverage, studentCount, lowCount, highCount, studentLowCount, studentHighCount),
                    new ClassTCS(key.getClass_id(), key.getMeal(), count, totalMoney, average, studentAverage, studentCount, lowCount, highCount, studentLowCount, studentHighCount));
        }
    }
}
