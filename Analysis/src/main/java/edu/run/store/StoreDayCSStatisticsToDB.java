package edu.run.store;

import edu.Dao.Store.StoreDayCSKey;
import edu.Dao.Store.StoreDayCSValue;
import edu.Dao.Store.StoreMonthCSKey;
import edu.Dao.Store.StoreMonthCSValue;
import edu.Infomation.StudentStore.StudentStoreDayCS;
import edu.Infomation.StudentStore.StudentStoreMonthCS;
import edu.Infomation.store.StoreDayCS;
import edu.Infomation.store.StoreMonthCS;
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

public class StoreDayCSStatisticsToDB {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration configuration = new Configuration();
        configuration.set("mapreduce.framework.name", "yarn");
        DBConfiguration.configureDB(configuration, StaticConstant.jdbcDriver, StaticConstant.jdbcUrl, StaticConstant.jdbcUser, StaticConstant.jdbcPassword);
        Job job = Job.getInstance(configuration, "store day consume");
        //job.addArchiveToClassPath(new Path("hdfs://master:9000/user/util/mysql-connector-java-5.1.49.jar"));
        job.setJarByClass(StoreDayCSStatisticsToDB.class);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);

        job.setMapOutputKeyClass(StoreDayCSKey.class);
        job.setMapOutputValueClass(StoreDayCSValue.class);

        job.setOutputKeyClass(StoreDayCS.class);
        job.setOutputValueClass(StoreDayCS.class);

        job.setInputFormatClass(DBInputFormat.class);
        job.setOutputFormatClass(DBOutputFormat.class);

        DBOutputFormat.setOutput(job, "store_day_consumption_statistics",
                "store_id", "day", "consumption_count", "consumption_total_money", "consumption_average_money", "consumption_student_average_money", "student_count");
        DBInputFormat.setInput(job, StudentStoreDayCS.class, "student_store_day_statistics", null, null,
                "sid", "store_id", "day", "consumption_count", "consumption_total_money", "consumption_average_money");
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }

    private static class Map extends Mapper<Object, StudentStoreDayCS, StoreDayCSKey, StoreDayCSValue> {
        @Override
        protected void map(Object key, StudentStoreDayCS value, Context context) throws IOException, InterruptedException {
            String new_value = value.getDay().toString().split(" ")[0];
            int year = Integer.parseInt(new_value.split("-")[0]);
            int month = Integer.parseInt(new_value.split("-")[1]);
            int day = Integer.parseInt(new_value.split("-")[2]);
            context.write(new StoreDayCSKey(value.getStore_id(), month, year, day), new StoreDayCSValue(value.getSid(), value.getConsumption_count(), value.getConsumption_total_money()));
        }
    }

    private static class Reduce extends Reducer<StoreDayCSKey, StoreDayCSValue, StoreDayCS, StoreDayCS> {
        @Override
        protected void reduce(StoreDayCSKey key, Iterable<StoreDayCSValue> values, Context context) throws IOException, InterruptedException {
            int student_count = 0;
            float sum = 0;
            int count = 0;
            for (StoreDayCSValue value : values) {
                student_count += 1;
                sum += value.getConsumption_total_money();
                count += value.getConsumption_count();
            }
            float student_average = sum / student_count;
            float average = sum / count;
            String date = key.getYear() + "-" + key.getMonth() + "-" + key.getDay();
            context.write(new StoreDayCS(key.getStore_id(), Date.valueOf(date), count, sum, average, student_average, student_count),
                    new StoreDayCS(key.getStore_id(), Date.valueOf(date), count, sum, average, student_average, student_count));
        }
    }
}
