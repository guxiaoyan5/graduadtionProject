package edu.studentCount.run;

import edu.Infomation.Student;
import edu.studentCount.Dao.CollegeStudentCount;
import edu.util.StaticConstant;
import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBInputFormat;
import org.apache.hadoop.mapreduce.lib.db.DBOutputFormat;

import java.io.IOException;

public class CollegeStudentCountToDB {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration configuration = new Configuration();
        configuration.set("mapreduce.framework.name", "yarn");
        DBConfiguration.configureDB(configuration, StaticConstant.jdbcDriver,StaticConstant.jdbcUrl,StaticConstant.jdbcUser,StaticConstant.jdbcPassword);
        Job job = Job.getInstance(configuration,"College student number");
        //job.addArchiveToClassPath(new Path("hdfs://master:9000/user/util/mysql-connector-java-5.1.49.jar"));
        job.setJarByClass(CollegeStudentCountToDB.class);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setOutputKeyClass(CollegeStudentCount.class);
        job.setOutputValueClass(CollegeStudentCount.class);

        job.setInputFormatClass(DBInputFormat.class);
        job.setOutputFormatClass(DBOutputFormat.class);

        DBOutputFormat.setOutput(job,"college_student_count",new String[]{"college","student_count"});
        DBInputFormat.setInput(job,Student.class,"student",null,null, new String[]{"id", "name","class","major","college","sex"});
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }
}

class Map extends Mapper<Object, Student, Text, IntWritable>{
    @Override
    protected void map(Object key, Student value, Context context) throws IOException, InterruptedException {
        context.write(new Text(value.getCollege()),new IntWritable(1));
    }
}
class Reduce extends Reducer<Text,IntWritable, CollegeStudentCount,CollegeStudentCount>{
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum = 0;
        for (IntWritable value:values){
            sum+=value.get();
        }
        context.write(new CollegeStudentCount(key.toString(),sum),new CollegeStudentCount(key.toString(),sum));
    }
}