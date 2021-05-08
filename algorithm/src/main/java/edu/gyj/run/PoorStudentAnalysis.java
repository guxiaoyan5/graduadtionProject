package edu.gyj.run;

import edu.gyj.bean.student.Student;
import edu.gyj.util.StaticConstant;
import org.apache.spark.mllib.clustering.KMeans;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.Properties;

public class PoorStudentAnalysis {
    public static void main(String[] args) {
        SparkSession sparkSession = SparkSession.builder().appName("StudentAnalysis").master("local[2]").getOrCreate();
        Properties properties = new Properties();
        properties.put("user", StaticConstant.jdbcUser);
        properties.put("password", StaticConstant.jdbcPassword);
        properties.put("driver", StaticConstant.jdbcDriver);

        Dataset<Row> studentRow = sparkSession.read().jdbc(StaticConstant.jdbcUrl, StaticConstant.studentTableName, properties).select("*");
        Dataset<Student> studentDataset = studentRow.as(Encoders.bean(Student.class));
        
        Dataset<Row> studentMonthRow = sparkSession.read()
                .jdbc(StaticConstant.jdbcUrl,StaticConstant.studentMonthCSTableName,properties)
                .select("sid","month","year","consumption_count","consumption_total_money","consumption_average_money");
        KMeans kMeans = new KMeans().setK(3).setSeed(1L);

    }
}
