package edu.run.collegeMonthCSStatistics;

import edu.Dao.College.CollegeMonthCSKey;
import edu.Dao.College.CollegeMonthCSMapInputValue;
import edu.Dao.College.CollegeMonthCSValue;
import edu.Infomation.College.CollegeMonthCS;
import edu.util.StaticConstant;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBInputFormat;
import org.apache.hadoop.mapreduce.lib.db.DBOutputFormat;

import java.io.IOException;

/**
 * 已检测
 */
public class CollegeMonthCSStatisticsToDB {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration configuration = new Configuration();
        configuration.set("mapreduce.framework.name", "yarn");
        DBConfiguration.configureDB(configuration, StaticConstant.jdbcDriver, StaticConstant.jdbcUrl, StaticConstant.jdbcUser, StaticConstant.jdbcPassword);
        Job job = Job.getInstance(configuration, "college month consume");
        //job.addArchiveToClassPath(new Path("hdfs://master:9000/user/util/mysql-connector-java-5.1.49.jar"));
        job.setJarByClass(CollegeMonthCSStatisticsToDB.class);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);

        job.setMapOutputKeyClass(CollegeMonthCSKey.class);
        job.setMapOutputValueClass(CollegeMonthCSValue.class);

        job.setOutputKeyClass(CollegeMonthCS.class);
        job.setOutputValueClass(CollegeMonthCS.class);

        job.setInputFormatClass(DBInputFormat.class);
        job.setOutputFormatClass(DBOutputFormat.class);

        DBOutputFormat.setOutput(job, "college_month_consumption_statistics",
                new String[]{"college_id", "month", "year", "consumption_count", "consumption_total_money", "consumption_average_money","consumption_student_average_money","student_count"});
        DBInputFormat.setInput(job, CollegeMonthCSMapInputValue.class,
                "select major.id,college_id,month,year,consumption_count,consumption_total_money,student_count from major,major_month_consumption_statistics where major.id = major_month_consumption_statistics.major_id",
                "select count(1) from major_month_consumption_statistics");

        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }
}

class Map extends Mapper<Object, CollegeMonthCSMapInputValue, CollegeMonthCSKey, CollegeMonthCSValue>{
    @Override
    protected void map(Object key, CollegeMonthCSMapInputValue value, Context context) throws IOException, InterruptedException {
        context.write(new CollegeMonthCSKey(value.getCollege_id(), value.getMonth() ,value.getYear()),new CollegeMonthCSValue(value.getMajor_id(), value.getConsumption_count(), value.getConsumption_total_money(), value.getStudent_count()));
    }
}

class Reduce extends Reducer<CollegeMonthCSKey,CollegeMonthCSValue, CollegeMonthCS,CollegeMonthCS>{
    @Override
    protected void reduce(CollegeMonthCSKey key, Iterable<CollegeMonthCSValue> values, Context context) throws IOException, InterruptedException {
        int student_count = 0;
        int consumption_count = 0;
        float consumption_total_money = 0;
        for (CollegeMonthCSValue value : values) {
            student_count += value.getStudent_count();
            consumption_count += value.getConsumption_count();
            consumption_total_money += value.getConsumption_total_money();
        }
        float student_average = consumption_total_money / student_count;
        float consumption_average_money = consumption_total_money / consumption_count;
        context.write(new CollegeMonthCS(key.getCollege_id(), key.getMonth(), key.getYear(), consumption_count, consumption_total_money, consumption_average_money, student_average, student_count), new CollegeMonthCS(key.getCollege_id(), key.getMonth(), key.getYear(), consumption_count, consumption_total_money, consumption_average_money, student_average, student_count));
    }
}