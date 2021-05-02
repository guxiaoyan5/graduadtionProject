package edu.gyj.run;

import edu.gyj.util.StaticConstant;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;

import java.util.Properties;

public class SparkMysql {
    public static void main(String[] args) {
        JavaSparkContext sparkContext = new JavaSparkContext(new SparkConf().setMaster("local[2]").setAppName("sparkMysql"));
        SQLContext sqlContext = new SQLContext(sparkContext);
        String table = "test";
        Properties properties = new Properties();
        properties.put("user", StaticConstant.jdbcUser);
        properties.put("password",StaticConstant.jdbcPassword);
        properties.put("driver",StaticConstant.jdbcDriver);
        Dataset<Row> jdbc = sqlContext.read().jdbc(StaticConstant.jdbcUrl,table,properties).select("*");
        jdbc.show();
    }
}
