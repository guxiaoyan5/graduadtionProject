package edu.run.majorMonthCSStatistics;

import edu.Dao.ClassMonthCS.ClassMonthCSKey;
import edu.Dao.ClassMonthCS.ClassMonthCSMapInputValue;
import edu.Dao.ClassMonthCS.ClassMonthCSValue;
import edu.Dao.MajorMonthCS.MajorMonthCSKey;
import edu.Dao.MajorMonthCS.MajorMonthCSMapInputValue;
import edu.Dao.MajorMonthCS.MajorMonthCSValue;
import edu.Infomation.ClassMonthCS;
import edu.Infomation.MajorMonthCS;
import edu.run.classMonthCSStatistics.ClassMonthCSStatisticsToDB;
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
public class MajorMonthCSStatisticsToDB {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration configuration = new Configuration();
        configuration.set("mapreduce.framework.name", "yarn");
        DBConfiguration.configureDB(configuration, StaticConstant.jdbcDriver, StaticConstant.jdbcUrl, StaticConstant.jdbcUser, StaticConstant.jdbcPassword);
        Job job = Job.getInstance(configuration, "major month consume");
        //job.addArchiveToClassPath(new Path("hdfs://master:9000/user/util/mysql-connector-java-5.1.49.jar"));
        job.setJarByClass(MajorMonthCSStatisticsToDB.class);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);

        job.setMapOutputKeyClass(MajorMonthCSKey.class);
        job.setMapOutputValueClass(MajorMonthCSValue.class);

        job.setOutputKeyClass(MajorMonthCS.class);
        job.setOutputValueClass(MajorMonthCS.class);

        job.setInputFormatClass(DBInputFormat.class);
        job.setOutputFormatClass(DBOutputFormat.class);

        DBOutputFormat.setOutput(job, "major_month_consumption_statistics",
                new String[]{"major_id", "month", "year", "consumption_count", "consumption_total_money", "consumption_average_money","consumption_student_average_money","student_count"});
        DBInputFormat.setInput(job, ClassMonthCSMapInputValue.class,
                "select class.class_id,major_id,month,year,consumption_count,consumption_total_money form class,class_month_consumption_statistics where class.class_id = class_month_consumption_statistics.class_id",
                "select count(*) from class_month_consumption_statistics");

        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }
}

class Map extends Mapper<Object, MajorMonthCSMapInputValue, MajorMonthCSKey, MajorMonthCSValue> {
    @Override
    protected void map(Object key, MajorMonthCSMapInputValue value, Context context) throws IOException, InterruptedException {
        context.write(new MajorMonthCSKey(value.getMajor_id(), value.getMonth(), value.getYear()), new MajorMonthCSValue(value.getClass_id(), value.getConsumption_count(), value.getConsumption_total_money(), value.getStudent_count()));
    }
}

class Reduce extends Reducer<MajorMonthCSKey, MajorMonthCSValue, MajorMonthCS, MajorMonthCS> {
    @Override
    protected void reduce(MajorMonthCSKey key, Iterable<MajorMonthCSValue> values, Context context) throws IOException, InterruptedException {
        int student_count = 0;
        int consumption_count = 0;
        float consumption_total_money = 0;
        for (MajorMonthCSValue value : values) {
            student_count += value.getStudent_count();
            consumption_count += value.getConsumption_count();
            consumption_total_money += value.getConsumption_total_money();
        }
        float student_average = consumption_total_money / student_count;
        float consumption_average_money = consumption_total_money / consumption_count;
        context.write(new MajorMonthCS(key.getMajor_id(), key.getMonth(), key.getYear(), consumption_count, consumption_total_money, consumption_average_money, student_average, student_count), new MajorMonthCS(key.getMajor_id(), key.getMonth(), key.getYear(), consumption_count, consumption_total_money, consumption_average_money, student_average, student_count));
    }
}