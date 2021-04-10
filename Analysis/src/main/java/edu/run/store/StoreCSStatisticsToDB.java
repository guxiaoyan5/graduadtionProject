package edu.run.store;

import edu.Dao.Store.StoreCSKey;
import edu.Dao.Store.StoreCSValue;
import edu.Dao.Store.StoreDayCSKey;
import edu.Dao.Store.StoreDayCSValue;
import edu.Infomation.StudentStore.StudentStoreCS;
import edu.Infomation.StudentStore.StudentStoreDayCS;
import edu.Infomation.store.StoreCS;
import edu.Infomation.store.StoreDayCS;
import edu.util.StaticConstant;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBInputFormat;
import org.apache.hadoop.mapreduce.lib.db.DBOutputFormat;

import java.io.IOException;

public class StoreCSStatisticsToDB {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration configuration = new Configuration();
        configuration.set("mapreduce.framework.name", "yarn");
        DBConfiguration.configureDB(configuration, StaticConstant.jdbcDriver, StaticConstant.jdbcUrl, StaticConstant.jdbcUser, StaticConstant.jdbcPassword);
        Job job = Job.getInstance(configuration, "store consume");
        //job.addArchiveToClassPath(new Path("hdfs://master:9000/user/util/mysql-connector-java-5.1.49.jar"));
        job.setJarByClass(StoreCSStatisticsToDB.class);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);

        job.setMapOutputKeyClass(StoreCSKey.class);
        job.setMapOutputValueClass(StoreCSValue.class);

        job.setOutputKeyClass(StoreCS.class);
        job.setOutputValueClass(StoreCS.class);

        job.setInputFormatClass(DBInputFormat.class);
        job.setOutputFormatClass(DBOutputFormat.class);

        DBOutputFormat.setOutput(job, "store_consumption_statistics",
                "store_id",  "consumption_count", "consumption_total_money", "consumption_average_money", "consumption_student_average_money", "student_count");
        DBInputFormat.setInput(job, StudentStoreCS.class, "student_store_statistics", null, null,
                "sid", "store_id", "consumption_count", "consumption_total_money", "consumption_average_money");
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }

    private static class Map extends Mapper<Object, StudentStoreCS, StoreCSKey, StoreCSValue> {
        @Override
        protected void map(Object key, StudentStoreCS value, Context context) throws IOException, InterruptedException {
            context.write(new StoreCSKey(value.getStore_id()), new StoreCSValue(value.getSid(), value.getConsumption_count(), value.getConsumption_total_money()));
        }
    }

    private static class Reduce extends Reducer<StoreCSKey, StoreCSValue, StoreCS, StoreCS> {
        @Override
        protected void reduce(StoreCSKey key, Iterable<StoreCSValue> values, Context context) throws IOException, InterruptedException {
            int student_count = 0;
            float sum = 0;
            int count = 0;
            for (StoreCSValue value : values) {
                student_count += 1;
                sum += value.getConsumption_total_money();
                count += value.getConsumption_count();
            }
            float student_average = sum / student_count;
            float average = sum / count;
            context.write(new StoreCS(key.getStore_id(), count, sum, average, student_average, student_count),
                    new StoreCS(key.getStore_id(), count, sum, average, student_average, student_count));
        }
    }
}
