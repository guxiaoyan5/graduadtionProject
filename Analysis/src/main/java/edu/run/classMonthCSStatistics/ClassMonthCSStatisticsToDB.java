package edu.run.classMonthCSStatistics;

import edu.Dao.ClassMonthCS.ClassMonthCSKey;
import edu.Dao.ClassMonthCS.ClassMonthCSMapInputValue;
import edu.Dao.ClassMonthCS.ClassMonthCSValue;
import edu.Infomation.ClassMonthCS;
import edu.util.StaticConstant;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBInputFormat;
import org.apache.hadoop.mapreduce.lib.db.DBOutputFormat;

import java.io.IOException;
/*
未检验
 */
public class ClassMonthCSStatisticsToDB {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration configuration = new Configuration();
        configuration.set("mapreduce.framework.name", "yarn");
        DBConfiguration.configureDB(configuration, StaticConstant.jdbcDriver, StaticConstant.jdbcUrl, StaticConstant.jdbcUser, StaticConstant.jdbcPassword);
        Job job = Job.getInstance(configuration, "class month consume");
        //job.addArchiveToClassPath(new Path("hdfs://master:9000/user/util/mysql-connector-java-5.1.49.jar"));
        job.setJarByClass(ClassMonthCSStatisticsToDB.class);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);

        job.setMapOutputKeyClass(ClassMonthCSKey.class);
        job.setMapOutputValueClass(ClassMonthCSValue.class);

        job.setOutputKeyClass(ClassMonthCS.class);
        job.setOutputValueClass(ClassMonthCS.class);

        job.setInputFormatClass(DBInputFormat.class);
        job.setOutputFormatClass(DBOutputFormat.class);

        DBOutputFormat.setOutput(job, "class_month_consumption_statistics",
                new String[]{"class_id", "month", "year", "consumption_count", "consumption_total_money", "consumption_average_money","consumption_student_average_money","student_count"});
        DBInputFormat.setInput(job, ClassMonthCSMapInputValue.class,
                "select student.sid,class_id,month,year,consumption_count,consumption_total_money form student,student_month_consumption_statistics where student.sid = student_month_consumption_statistics.id",
                "select count(*) from student_month_consumption_statistics");
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }
}

class Map extends Mapper<Object, ClassMonthCSMapInputValue, ClassMonthCSKey, ClassMonthCSValue> {
    @Override
    protected void map(Object key, ClassMonthCSMapInputValue value, Context context) throws IOException, InterruptedException {
        context.write(new ClassMonthCSKey(value.getClass_id(), value.getMonth(), value.getYear()), new ClassMonthCSValue(value.getSid(), value.getConsumption_count(), value.getConsumption_total_money()));
    }
}

class Reduce extends Reducer<ClassMonthCSKey, ClassMonthCSValue, ClassMonthCS, ClassMonthCS> {
    @Override
    protected void reduce(ClassMonthCSKey key, Iterable<ClassMonthCSValue> values, Context context) throws IOException, InterruptedException {
        int studentCount = 0;
        int count = 0;
        float sum = 0;
        float studentAverage = 0;
        float average = 0;
        for (ClassMonthCSValue value : values) {
            studentCount += 1;
            count += value.getConsumption_count();
            sum += value.getConsumption_total_money();
        }
        studentAverage = sum / studentCount;
        average = sum / count;
        context.write(new ClassMonthCS(key.getClass_id(),key.getMonth(),key.getYear(),count,sum,average,studentAverage,studentCount),new ClassMonthCS(key.getClass_id(),key.getMonth(),key.getYear(),count,sum,average,studentAverage,studentCount));
    }
}