package edu.run.student;

import edu.Dao.Student.StudentMonthCSKey;
import edu.Dao.Student.StudentMonthCSValue;
import edu.Infomation.Consume;
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

/**
 * 已测试
 */
public class StudentMonthCSStatisticsToDB {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration configuration = new Configuration();
        configuration.set("mapreduce.framework.name", "yarn");
        DBConfiguration.configureDB(configuration, StaticConstant.jdbcDriver, StaticConstant.jdbcUrl, StaticConstant.jdbcUser, StaticConstant.jdbcPassword);
        Job job = Job.getInstance(configuration, "student month consume");

        job.setJarByClass(StudentMonthCSStatisticsToDB.class);
        job.setMapperClass(StudentMonthMap.class);
        job.setReducerClass(StudentMonthReduce.class);

        job.setMapOutputKeyClass(StudentMonthCSKey.class);
        job.setMapOutputValueClass(StudentMonthCSValue.class);

        job.setOutputKeyClass(StudentMonthCS.class);
        job.setOutputValueClass(StudentMonthCS.class);

        job.setInputFormatClass(DBInputFormat.class);
        job.setOutputFormatClass(DBOutputFormat.class);

        DBOutputFormat.setOutput(job, "student_month_consumption_statistics",
                "sid", "month", "year", "consumption_count", "consumption_total_money", "consumption_average_money");
        DBInputFormat.setInput(job, Consume.class, "consume", null, null,
                "sid", "execution_time", "money", "store_id ", "mode");
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }
}

class StudentMonthMap extends Mapper<Object, Consume, StudentMonthCSKey, StudentMonthCSValue> {
    @Override
    protected void map(Object key, Consume value, Context context) throws IOException, InterruptedException {
        String new_value = value.getExecution_time().toString().split(" ")[0];
        int year = Integer.parseInt(new_value.split("-")[0]);
        int month = Integer.parseInt(new_value.split("-")[1]);
        context.write(new StudentMonthCSKey(value.getSid(), month, year), new StudentMonthCSValue(value.getMoney()));
    }
}

class StudentMonthReduce extends Reducer<StudentMonthCSKey, StudentMonthCSValue, StudentMonthCS, StudentMonthCS> {
    @Override
    protected void reduce(StudentMonthCSKey key, Iterable<StudentMonthCSValue> values, Context context) throws IOException, InterruptedException {
        int count = 0;
        float totalMoney = 0;
        for (StudentMonthCSValue value : values) {
            count += 1;
            totalMoney += value.getMoney();
        }
        float average = totalMoney / count;
        context.write(new StudentMonthCS(key.getSid(), key.getMonth(), key.getYear(), count, totalMoney, average), new StudentMonthCS(key.getSid(), key.getMonth(), key.getYear(), count, totalMoney, average));
    }
}
