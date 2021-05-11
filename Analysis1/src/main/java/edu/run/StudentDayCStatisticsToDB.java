package edu.run;

import edu.bean.StudentDayCSBean;
import edu.bean.StudentDayTCSBean;
import edu.util.StaticConstant;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFlatMapFunction;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;
import scala.Tuple3;

import java.util.Date;
import java.util.Iterator;

public class StudentDayCStatisticsToDB {
    public static void main(String[] args) {
        SparkSession sparkSession = SparkSession.builder().appName("StudentDayCSAnalysis").master("local[2]").getOrCreate();
        Dataset<StudentDayTCSBean> studentDayTCSBeanDataset = sparkSession.read().format("jdbc")
                .option("url", StaticConstant.jdbcUrl)
                .option("user", StaticConstant.jdbcUser)
                .option("password", StaticConstant.jdbcPassword)
                .option("driver", StaticConstant.jdbcDriver)
                .option("query", "select sid,day,consumption_category,consumption_total_money from student_day_three_meals_statistics")
                .load().as(Encoders.bean(StudentDayTCSBean.class));
        JavaPairRDD<Tuple2<String, Date>,Double> studentDayCSJavaPairRDD = studentDayTCSBeanDataset
                .toJavaRDD().mapToPair(new PairFunction<StudentDayTCSBean, Tuple2<String, Date>, Double>() {
                    @Override
                    public Tuple2<Tuple2<String, Date>, Double> call(StudentDayTCSBean studentDayTCSBean) throws Exception {
                        return new Tuple2<>(
                                new Tuple2<>(
                                        studentDayTCSBean.getSid(),
                                        studentDayTCSBean.getDay()
                                ),
                                studentDayTCSBean.getConsumption_total_money()
                        );
                    }
                });
        JavaPairRDD<Tuple2<String,Date>,Double> studentDayCSSumJavaPairRDD = studentDayCSJavaPairRDD.reduceByKey(new Function2<Double, Double, Double>() {
            @Override
            public Double call(Double aDouble, Double aDouble2) throws Exception {
                return aDouble+aDouble2;
            }
        });
        JavaRDD<StudentDayCSBean> studentDayCSBeanJavaRDD = studentDayCSSumJavaPairRDD.map(new Function<Tuple2<Tuple2<String, Date>, Double>, StudentDayCSBean>() {
            @Override
            public StudentDayCSBean call(Tuple2<Tuple2<String, Date>, Double> tuple2DoubleTuple2) throws Exception {
                return new StudentDayCSBean(
                        tuple2DoubleTuple2._1()._1(),
                        (java.sql.Date) tuple2DoubleTuple2._1()._2(),
                        tuple2DoubleTuple2._2()
                );
            }
        });
        Dataset<StudentDayCSBean> studentDayCSBeanDataset = sparkSession.createDataFrame(studentDayCSBeanJavaRDD,StudentDayCSBean.class).as(Encoders.bean(StudentDayCSBean.class));
        studentDayCSBeanDataset.write().format("jdbc")
                .option("url", StaticConstant.jdbcUrl)
                .option("user", StaticConstant.jdbcUser)
                .option("password", StaticConstant.jdbcPassword)
                .option("driver", StaticConstant.jdbcDriver)
                .option("dbtable", "graduate.student_day_consumption_statistics")
                .save();
        studentDayCSBeanDataset.show();
        sparkSession.stop();
    }
}
