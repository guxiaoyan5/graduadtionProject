package edu.run;

import edu.bean.StudentMonthCSBean;
import edu.bean.StudentMonthTCSBean;
import edu.util.StaticConstant;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;
import scala.Tuple3;

public class StudentMonthCStatisticsToDB {
    public static void main(String[] args) {
        SparkSession sparkSession = SparkSession.builder().appName("StudentMonthCSAnalysis").master("local[2]").getOrCreate();
        Dataset<StudentMonthTCSBean> studentMonthTCSBeanDataset = sparkSession.read().format("jdbc")
                .option("url", StaticConstant.jdbcUrl)
                .option("user", StaticConstant.jdbcUser)
                .option("password", StaticConstant.jdbcPassword)
                .option("driver", StaticConstant.jdbcDriver)
                .option("query", "select sid,year,month,consumption_category,consumption_total_money from student_month_three_meals_statistics")
                .load().as(Encoders.bean(StudentMonthTCSBean.class));
        JavaPairRDD<Tuple3<String,Integer,Integer>,Double> studentMonthTCSJavaPairRDD = studentMonthTCSBeanDataset.toJavaRDD()
                .mapToPair(new PairFunction<StudentMonthTCSBean, Tuple3<String, Integer, Integer>, Double>() {
                    @Override
                    public Tuple2<Tuple3<String, Integer, Integer>, Double> call(StudentMonthTCSBean studentMonthTCSBean) throws Exception {
                        return new Tuple2<>(
                                new Tuple3<>(
                                        studentMonthTCSBean.getSid(),
                                        studentMonthTCSBean.getYear(),
                                        studentMonthTCSBean.getMonth()
                                ),
                                studentMonthTCSBean.getConsumption_total_money()
                        );
                    }
                });
        JavaPairRDD<Tuple3<String,Integer,Integer>,Double> studentMonthCSJavaPairRDD =studentMonthTCSJavaPairRDD.reduceByKey(new Function2<Double, Double, Double>() {
                    @Override
                    public Double call(Double aDouble, Double aDouble2) throws Exception {
                        return aDouble+aDouble2;
                    }
                });
        JavaRDD<StudentMonthCSBean> studentMonthCSBeanJavaRDD = studentMonthCSJavaPairRDD.map(new Function<Tuple2<Tuple3<String, Integer, Integer>, Double>, StudentMonthCSBean>() {
            @Override
            public StudentMonthCSBean call(Tuple2<Tuple3<String, Integer, Integer>, Double> tuple3DoubleTuple2) throws Exception {
                return new StudentMonthCSBean(
                        tuple3DoubleTuple2._1()._1(),
                        tuple3DoubleTuple2._1()._2(),
                        tuple3DoubleTuple2._1()._3(),
                        tuple3DoubleTuple2._2()
                );
            }
        });
        Dataset<StudentMonthCSBean> studentMonthCSBeanDataset = sparkSession.createDataFrame(studentMonthCSBeanJavaRDD,StudentMonthCSBean.class).as(Encoders.bean(StudentMonthCSBean.class));
        studentMonthCSBeanDataset.write().format("jdbc")
                .option("url", StaticConstant.jdbcUrl)
                .option("user", StaticConstant.jdbcUser)
                .option("password", StaticConstant.jdbcPassword)
                .option("driver", StaticConstant.jdbcDriver)
                .option("dbtable", "graduate.student_month_consumption_statistics")
                .save();
        studentMonthCSBeanDataset.show();
        sparkSession.stop();
    }
}
