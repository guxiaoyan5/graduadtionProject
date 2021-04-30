package edu.run.student;

import edu.Dao.Student.StudentCSKey;
import edu.Dao.Student.StudentCSValue;
import edu.Dao.Student.StudentDayCSKey;
import edu.Dao.Student.StudentDayCSValue;
import edu.Infomation.Consume;
import edu.Infomation.Student.StudentCS;
import edu.Infomation.Student.StudentDayCS;
import edu.Infomation.Student.StudentMonthCS;
import edu.util.StaticConstant;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBInputFormat;
import org.apache.hadoop.mapreduce.lib.db.DBOutputFormat;

import java.io.IOException;
import java.sql.Date;

public class StudentCSStatisticsToDB {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration configuration = new Configuration();
        configuration.set("mapreduce.framework.name", "yarn");
        DBConfiguration.configureDB(configuration, StaticConstant.jdbcDriver, StaticConstant.jdbcUrl, StaticConstant.jdbcUser, StaticConstant.jdbcPassword);
        Job job = Job.getInstance(configuration, "student consume");
        //job.addArchiveToClassPath(new Path("hdfs://master:9000/user/util/mysql-connector-java-5.1.49.jar"));
        job.setJarByClass(StudentCSStatisticsToDB.class);
        job.setMapperClass(StudentMap.class);
        job.setReducerClass(StudentReduce.class);

        job.setMapOutputKeyClass(StudentCSKey.class);
        job.setMapOutputValueClass(StudentCSValue.class);

        job.setOutputKeyClass(StudentCS.class);
        job.setOutputValueClass(StudentCS.class);

        job.setInputFormatClass(DBInputFormat.class);
        job.setOutputFormatClass(DBOutputFormat.class);

        DBOutputFormat.setOutput(job, "student_consumption_statistics",
                "sid", "consumption_count", "consumption_total_money",
                "consumption_average_money");
        DBInputFormat.setInput(job, Consume.class, "consume", null, null,
                "sid", "execution_time", "money", "store_id ", "mode");
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);

    }

    static class StudentMap extends Mapper<Object, Consume, StudentCSKey, StudentCSValue> {
        @Override
        protected void map(Object key, Consume value, Context context) throws IOException, InterruptedException {
            context.write(new StudentCSKey(value.getSid()), new StudentCSValue(value.getMoney()));
        }
    }

    static class StudentReduce extends Reducer<StudentCSKey, StudentCSValue, StudentCS, StudentCS> {
        @Override
        protected void reduce(StudentCSKey key, Iterable<StudentCSValue> values, Context context) throws IOException, InterruptedException {
            int count = 0;
            float totalMoney = 0;
            for (StudentCSValue value : values) {
                count += 1;
                totalMoney += value.getMoney();
            }
            float average = totalMoney / count;
            context.write(new StudentCS(key.getSid(), count, totalMoney, average), new StudentCS(key.getSid(), count, totalMoney, average));
        }
    }
}

