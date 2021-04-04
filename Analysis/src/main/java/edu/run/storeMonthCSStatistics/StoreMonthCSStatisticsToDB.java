package edu.run.storeMonthCSStatistics;

import edu.Dao.StoreMonthCS.StoreMonthCSKey;
import edu.Dao.StoreMonthCS.StoreMonthCSValue;
import edu.Dao.StudentStoreMonthCS.StudentStoreMonthCSKey;
import edu.Infomation.Consume;
import edu.Infomation.StoreMonthCS;
import edu.Infomation.StudentStoreMonthCS;
import edu.run.studentStoreMonthCSStatistics.StudentStoreMonthCSStatisticsToDB;
import edu.util.StaticConstant;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBInputFormat;
import org.apache.hadoop.mapreduce.lib.db.DBOutputFormat;

import java.io.IOException;

/**
 * 未测试
 */
public class StoreMonthCSStatisticsToDB {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration configuration = new Configuration();
        configuration.set("mapreduce.framework.name", "yarn");
        DBConfiguration.configureDB(configuration, StaticConstant.jdbcDriver, StaticConstant.jdbcUrl, StaticConstant.jdbcUser, StaticConstant.jdbcPassword);
        Job job = Job.getInstance(configuration, "store month consume");
        //job.addArchiveToClassPath(new Path("hdfs://master:9000/user/util/mysql-connector-java-5.1.49.jar"));
        job.setJarByClass(StoreMonthCSStatisticsToDB.class);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);

        job.setMapOutputKeyClass(StoreMonthCSKey.class);
        job.setMapOutputValueClass(StoreMonthCSValue.class);

        job.setOutputKeyClass(StoreMonthCS.class);
        job.setOutputValueClass(StoreMonthCS.class);

        job.setInputFormatClass(DBInputFormat.class);
        job.setOutputFormatClass(DBOutputFormat.class);

        DBOutputFormat.setOutput(job, "store_month_consumption_statistics",
                new String[]{"store_id", "month","year", "consumption_count", "consumption_total_money", "consumption_average_money","consumption_student_average_money","student_count"});
        DBInputFormat.setInput(job, StudentStoreMonthCS.class, "student_store_month_statistics", null, null,
                new String[]{"sid", "store_id", "month","year", "consumption_count", "consumption_total_money", "consumption_average_money"});
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }
}

class Map extends Mapper<Object, StudentStoreMonthCS, StoreMonthCSKey, StoreMonthCSValue> {
    @Override
    protected void map(Object key, StudentStoreMonthCS value, Context context) throws IOException, InterruptedException {
        context.write(new StoreMonthCSKey(value.getStore_id(), value.getMonth(), value.getYear()), new StoreMonthCSValue(value.getSid(), value.getConsumption_count(), value.getConsumption_total_money()));
    }
}

class Reduce extends Reducer<StoreMonthCSKey, StoreMonthCSValue, StoreMonthCS, StoreMonthCS> {
    @Override
    protected void reduce(StoreMonthCSKey key, Iterable<StoreMonthCSValue> values, Context context) throws IOException, InterruptedException {
        int student_count = 0;
        float sum = 0;
        int count = 0;
        for (StoreMonthCSValue value : values) {
            student_count += 1;
            sum += value.getConsumption_total_money();
            count += value.getConsumption_count();
        }
        float student_average = sum / student_count;
        float average = sum / count;
        context.write(new StoreMonthCS(key.getStore_id(), key.getMonth(), key.getYear(), count, sum, average, student_average, student_count), new StoreMonthCS(key.getStore_id(), key.getMonth(), key.getYear(), count, sum, average, student_average, student_count));
    }
}