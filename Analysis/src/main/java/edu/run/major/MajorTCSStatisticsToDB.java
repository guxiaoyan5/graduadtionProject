package edu.run.major;

import edu.Dao.Class.ClassTCSInputValue;
import edu.Dao.Class.ClassTCSKey;
import edu.Dao.Class.ClassTCSValue;
import edu.Dao.Major.MajorTCSInputValue;
import edu.Dao.Major.MajorTCSKey;
import edu.Dao.Major.MajorTCSValue;
import edu.Infomation.Class.ClassTCS;
import edu.Infomation.Major.MajorTCS;
import edu.run.classCS.ClassTCSStatisticsToDB;
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

public class MajorTCSStatisticsToDB {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration configuration = new Configuration();
        configuration.set("mapreduce.framework.name", "yarn");
        DBConfiguration.configureDB(configuration, StaticConstant.jdbcDriver, StaticConstant.jdbcUrl, StaticConstant.jdbcUser, StaticConstant.jdbcPassword);
        Job job = Job.getInstance(configuration, "major three meals consume");
        job.setJarByClass(MajorTCSStatisticsToDB.class);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);

        job.setMapOutputKeyClass(MajorTCSKey.class);
        job.setMapOutputValueClass(MajorTCSValue.class);
        job.setOutputKeyClass(MajorTCS.class);
        job.setOutputValueClass(MajorTCS.class);

        job.setInputFormatClass(DBInputFormat.class);
        job.setOutputKeyClass(DBOutputFormat.class);

        DBOutputFormat.setOutput(job, "major_three_meals_statistics",
                "major_id", "consumption_category", "consumption_count", "consumption_total_money",
                "consumption_average_money", "consumption_student_average_money", "student_count",
                "consumption_low_count", "consumption_high_count", "student_low_count", "student_high_count"
        );
        DBInputFormat.setInput(job, MajorTCSInputValue.class,
                "select student.id,major_id,execution_time,consumption_category,money,consumption_total_money " +
                        "from student,consume,student_three_meals_statistics where student.id=consume.sid " +
                        "and student.id=student_three_meals_statistics.sid and (((hour(execution_time) between 0 and 9) and consumption_category = '早') or ((hour(execution_time) between 10 and 15) and consumption_category = '午') or ((hour(execution_time) between 16 and 24) and consumption_category = '晚'))",
                "select count(1) from consume");
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }

    static class Map extends Mapper<Object, MajorTCSInputValue, MajorTCSKey, MajorTCSValue> {
        @Override
        protected void map(Object key, MajorTCSInputValue value, Context context) throws IOException, InterruptedException {
            context.write(new MajorTCSKey(value.getMajor_id(), value.getMeal()), new MajorTCSValue(value.getSid(), value.getMoney(), value.getStudentTotalMoney()));
        }
    }

    static class Reduce extends Reducer<MajorTCSKey, MajorTCSValue, MajorTCS, MajorTCS> {
        @Override
        protected void reduce(MajorTCSKey key, Iterable<MajorTCSValue> values, Context context) throws IOException, InterruptedException {
            ArrayList<MajorTCSValue> newValues = new ArrayList<>();
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
            for (MajorTCSValue value : values) {
                count += 1;
                totalMoney += value.getMoney();
                sid.add(value.getSid());
                MajorTCSValue newValue = WritableUtils.clone(value, context.getConfiguration());
                newValues.add(newValue);
            }
            studentCount = sid.size();
            float average = totalMoney / count;
            float studentAverage = totalMoney / studentCount;
            for (MajorTCSValue value : newValues) {
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
            context.write(new MajorTCS(key.getMajor_id(), key.getMeal(), count, totalMoney, average, studentAverage, studentCount, lowCount, highCount, studentLowCount, studentHighCount),
                    new MajorTCS(key.getMajor_id(), key.getMeal(), count, totalMoney, average, studentAverage, studentCount, lowCount, highCount, studentLowCount, studentHighCount));
        }
    }
}
