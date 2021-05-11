package edu.run;

import edu.bean.ConsumeBean;
import edu.bean.StudentDayTCSBean;
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
import scala.Tuple4;

import java.util.Calendar;
import java.util.Date;

public class StudentMonthTCStatisticsToDB {
    public static void main(String[] args) {
        SparkSession sparkSession = SparkSession.builder().appName("StudentMonthTCSAnalysis").master("local[2]").getOrCreate();
        Dataset<StudentDayTCSBean> studentDayTCSBeanDataset = sparkSession.read().format("jdbc")
                .option("url", StaticConstant.jdbcUrl)
                .option("user", StaticConstant.jdbcUser)
                .option("password", StaticConstant.jdbcPassword)
                .option("driver", StaticConstant.jdbcDriver)
                .option("query", "select sid,day,consumption_category,consumption_total_money from student_day_three_meals_statistics")
                .load().as(Encoders.bean(StudentDayTCSBean.class));
        JavaPairRDD<Tuple3<String, String, Date>, Double> studentDayTCSBeanJavaRDD = studentDayTCSBeanDataset.toJavaRDD().mapToPair(new PairFunction<StudentDayTCSBean, Tuple3<String, String, Date>, Double>() {
            @Override
            public Tuple2<Tuple3<String, String, Date>, Double> call(StudentDayTCSBean studentDayTCSBean) throws Exception {
                return new Tuple2<>(new Tuple3<>(studentDayTCSBean.getSid(), studentDayTCSBean.getConsumption_category(), studentDayTCSBean.getDay()), studentDayTCSBean.getConsumption_total_money());
            }
        });
        JavaPairRDD<Tuple4<String, String, Integer, Integer>, Double> studentMonthTCSJavaRDD = studentDayTCSBeanJavaRDD.mapToPair(new PairFunction<Tuple2<Tuple3<String, String, Date>, Double>, Tuple4<String, String, Integer, Integer>, Double>() {
            @Override
            public Tuple2<Tuple4<String, String, Integer, Integer>, Double> call(Tuple2<Tuple3<String, String, Date>, Double> tuple3DoubleTuple2) throws Exception {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(tuple3DoubleTuple2._1()._3());
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                return new Tuple2<>(
                        new Tuple4<>(
                                tuple3DoubleTuple2._1()._1(),
                                tuple3DoubleTuple2._1()._2(),
                                year,
                                month
                        ),
                        tuple3DoubleTuple2._2());
            }
        });
        JavaPairRDD<Tuple4<String, String, Integer, Integer>, Double> studentMonthTCSSumJavaRDD = studentMonthTCSJavaRDD.reduceByKey(new Function2<Double, Double, Double>() {
            @Override
            public Double call(Double aDouble, Double aDouble2) throws Exception {
                return aDouble+aDouble2;
            }
        });
        JavaRDD<StudentMonthTCSBean> studentMonthTCSBeanJavaRDD = studentMonthTCSSumJavaRDD.map(new Function<Tuple2<Tuple4<String, String, Integer, Integer>, Double>, StudentMonthTCSBean>() {
            @Override
            public StudentMonthTCSBean call(Tuple2<Tuple4<String, String, Integer, Integer>, Double> tuple4DoubleTuple2) throws Exception {
                return new StudentMonthTCSBean(
                        tuple4DoubleTuple2._1()._1(),
                        tuple4DoubleTuple2._1()._3(),
                        tuple4DoubleTuple2._1()._4(),
                        tuple4DoubleTuple2._1()._2(),
                        tuple4DoubleTuple2._2()
                );
            }
        });
        Dataset<StudentMonthTCSBean> studentMonthTCSBeanDataset = sparkSession.createDataFrame(studentMonthTCSBeanJavaRDD,StudentMonthTCSBean.class).as(Encoders.bean(StudentMonthTCSBean.class));
        studentMonthTCSBeanDataset.write().format("jdbc")
                .option("url", StaticConstant.jdbcUrl)
                .option("user", StaticConstant.jdbcUser)
                .option("password", StaticConstant.jdbcPassword)
                .option("driver", StaticConstant.jdbcDriver)
                .option("dbtable", "student_month_three_meals_statistics")
                .save();
        studentMonthTCSBeanDataset.show();
    }
}
