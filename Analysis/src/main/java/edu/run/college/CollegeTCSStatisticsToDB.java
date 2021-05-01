package edu.run.college;

import edu.Dao.Class.ClassTCSInputValue;
import edu.Dao.Class.ClassTCSKey;
import edu.Dao.Class.ClassTCSValue;
import edu.Dao.College.CollegeTCSInputValue;
import edu.Dao.College.CollegeTCSKey;
import edu.Dao.College.CollegeTCSValue;
import edu.Infomation.Class.ClassTCS;
import edu.Infomation.College.CollegeTCS;
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

public class CollegeTCSStatisticsToDB {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration configuration = new Configuration();
        configuration.set("mapreduce.framework.name", "yarn");
        DBConfiguration.configureDB(configuration, StaticConstant.jdbcDriver, StaticConstant.jdbcUrl, StaticConstant.jdbcUser, StaticConstant.jdbcPassword);
        Job job = Job.getInstance(configuration, "college three meals consume");
        job.setJarByClass(CollegeTCSStatisticsToDB.class);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);

        job.setMapOutputKeyClass(CollegeTCSKey.class);
        job.setMapOutputValueClass(CollegeTCSValue.class);
        job.setOutputKeyClass(CollegeTCS.class);
        job.setOutputValueClass(CollegeTCS.class);

        job.setInputFormatClass(DBInputFormat.class);
        job.setOutputKeyClass(DBOutputFormat.class);

        DBOutputFormat.setOutput(job, "college_three_meals_statistics",
                "college_id", "consumption_category", "consumption_count", "consumption_total_money",
                "consumption_average_money", "consumption_student_average_money", "student_count",
                "consumption_low_count", "consumption_high_count", "student_low_count", "student_high_count"
        );
        DBInputFormat.setInput(job, CollegeTCSInputValue.class,
                "select student.id,college_id,execution_time,consumption_category,money,consumption_total_money " +
                        "from student,consume,student_three_meals_statistics where student.id=consume.sid " +
                        "and student.id=student_three_meals_statistics.sid",
                "select count(1) from consume");
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }
    static class Map extends Mapper<Object, CollegeTCSInputValue, CollegeTCSKey, CollegeTCSValue>{
        @Override
        protected void map(Object key, CollegeTCSInputValue value, Context context) throws IOException, InterruptedException {
            context.write(new CollegeTCSKey(value.getCollege_id(), value.getMeal()),new CollegeTCSValue(value.getSid(), value.getMoney(), value.getStudentTotalMoney()));
        }
    }
    static class Reduce extends Reducer<CollegeTCSKey,CollegeTCSValue, CollegeTCS,CollegeTCS>{
        @Override
        protected void reduce(CollegeTCSKey key, Iterable<CollegeTCSValue> values, Context context) throws IOException, InterruptedException {
            ArrayList<CollegeTCSValue> newValues = new ArrayList<>();
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
            for (CollegeTCSValue value : values) {
                count += 1;
                totalMoney += value.getMoney();
                sid.add(value.getSid());
                CollegeTCSValue newValue = WritableUtils.clone(value, context.getConfiguration());
                newValues.add(newValue);
            }
            studentCount = sid.size();
            float average = totalMoney / count;
            float studentAverage = totalMoney / studentCount;
            for (CollegeTCSValue value : newValues) {
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
            context.write(new CollegeTCS(key.getCollege_id(), key.getMeal(), count, totalMoney, average, studentAverage, studentCount, lowCount, highCount, studentLowCount, studentHighCount),
                    new CollegeTCS(key.getCollege_id(), key.getMeal(), count, totalMoney, average, studentAverage, studentCount, lowCount, highCount, studentLowCount, studentHighCount));
        }
    }
}
