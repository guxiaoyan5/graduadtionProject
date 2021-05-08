package edu.run.mealsTime;

import edu.Dao.mealsTime.MealsTimeKey;
import edu.Dao.mealsTime.MealsTimeValue;
import edu.Infomation.Consume;
import edu.Infomation.MealsTime.MealsTime;
import edu.util.StaticConstant;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBInputFormat;
import org.apache.hadoop.mapreduce.lib.db.DBOutputFormat;

import java.io.IOException;
import java.util.Calendar;

public class MealsTimeCountStatisticsToDB {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration configuration = new Configuration();
        configuration.set("mapreduce.framework.name", "yarn");
        DBConfiguration.configureDB(configuration, StaticConstant.jdbcDriver, StaticConstant.jdbcUrl, StaticConstant.jdbcUser, StaticConstant.jdbcPassword);
        Job job = Job.getInstance(configuration, "meals  count");
        job.setJarByClass(MealsTimeCountStatisticsToDB.class);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);

        job.setMapOutputKeyClass(MealsTimeKey.class);
        job.setMapOutputValueClass(MealsTimeValue.class);
        job.setOutputKeyClass(MealsTime.class);
        job.setOutputValueClass(MealsTime.class);

        job.setInputFormatClass(DBInputFormat.class);
        job.setOutputKeyClass(DBOutputFormat.class);

        DBOutputFormat.setOutput(job, "meals_time", "hour", "minute", "count");
        DBInputFormat.setInput(job, Consume.class, "consume", null, null, "sid", "execution_time", "money", "store_id ", "mode");
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }

    static class Map extends Mapper<Object, Consume, MealsTimeKey, MealsTimeValue> {
        @Override
        protected void map(Object key, Consume value, Context context) throws IOException, InterruptedException {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(value.getExecution_time());
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            context.write(new MealsTimeKey(minute, hour), new MealsTimeValue(1));
        }
    }

    static class Reduce extends Reducer<MealsTimeKey, MealsTimeValue, MealsTime, MealsTime> {
        @Override
        protected void reduce(MealsTimeKey key, Iterable<MealsTimeValue> values, Context context) throws IOException, InterruptedException {
            int sum = 0;
            for (MealsTimeValue value : values) {
                sum += value.getCount();
            }
            context.write(new MealsTime(key.getHour(), key.getMinutes(), sum), new MealsTime(key.getHour(), key.getMinutes(), sum));
        }
    }
}
