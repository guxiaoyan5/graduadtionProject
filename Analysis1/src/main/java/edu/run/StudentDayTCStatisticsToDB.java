package edu.run;

import edu.bean.ConsumeBean;
import edu.bean.StudentDayTCSBean;
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

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

public class StudentDayTCStatisticsToDB {
    public static void main(String[] args) {
        SparkSession sparkSession = SparkSession.builder().appName("StudentDayTCSAnalysis").master("local[2]").getOrCreate();
        Dataset<ConsumeBean> consumeBeanDataset = sparkSession.read().format("jdbc")
                .option("url", StaticConstant.jdbcUrl)
                .option("user", StaticConstant.jdbcUser)
                .option("password", StaticConstant.jdbcPassword)
                .option("driver", StaticConstant.jdbcDriver)
                .option("query", "select sid,execution_time,money,store_id,mode from consume")
                .load().as(Encoders.bean(ConsumeBean.class));
        JavaPairRDD<String, Tuple2<Timestamp, Double>> studentConsumeDataset = consumeBeanDataset.toJavaRDD().mapToPair(new PairFunction<ConsumeBean, String, Tuple2<Timestamp, Double>>() {
            @Override
            public Tuple2<String, Tuple2<Timestamp, Double>> call(ConsumeBean consumeBean) throws Exception {
                return new Tuple2<>(consumeBean.getSid(), new Tuple2<>(consumeBean.getExecution_time(), consumeBean.getMoney()));
            }
        });
        JavaPairRDD<Tuple3<String, String, Date>, Double> studentTCSDataset = studentConsumeDataset.mapToPair(new PairFunction<Tuple2<String, Tuple2<Timestamp, Double>>, Tuple3<String, String, Date>, Double>() {
            @Override
            public Tuple2<Tuple3<String, String, Date>, Double> call(Tuple2<String, Tuple2<Timestamp, Double>> stringTuple2Tuple2) throws Exception {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(stringTuple2Tuple2._2()._1());
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                String consumption_category;
                if (hour < 10) {
                    consumption_category = "早";
                } else if (hour < 16) {
                    consumption_category = "午";
                } else {
                    consumption_category = "晚";
                }
                Date date = Date.valueOf(year+"-"+month+"-"+day);
                return new Tuple2<>(new Tuple3<>(stringTuple2Tuple2._1(),consumption_category,date),stringTuple2Tuple2._2()._2());
            }
        });
        JavaPairRDD<Tuple3<String, String, Date>, Double> studentTCS = studentTCSDataset.reduceByKey(new Function2<Double, Double, Double>() {
            @Override
            public Double call(Double aDouble, Double aDouble2) throws Exception {
                return aDouble+aDouble2;
            }
        });
        JavaRDD<StudentDayTCSBean> studentDayTCSBeanJavaRDD = studentTCS.map(new Function<Tuple2<Tuple3<String, String, Date>, Double>, StudentDayTCSBean>() {
            @Override
            public StudentDayTCSBean call(Tuple2<Tuple3<String, String, Date>, Double> tuple3DoubleTuple2) throws Exception {
                return new StudentDayTCSBean(
                        tuple3DoubleTuple2._1()._1(),
                        tuple3DoubleTuple2._1()._3(),
                        tuple3DoubleTuple2._1()._2(),
                        tuple3DoubleTuple2._2()
                );
            }
        });
        Dataset<StudentDayTCSBean> studentDayTCSBeanDataset = sparkSession.createDataFrame(studentDayTCSBeanJavaRDD,StudentDayTCSBean.class).as(Encoders.bean(StudentDayTCSBean.class));
        studentDayTCSBeanDataset.write().format("jdbc")
                .option("url", StaticConstant.jdbcUrl)
                .option("user", StaticConstant.jdbcUser)
                .option("password", StaticConstant.jdbcPassword)
                .option("driver", StaticConstant.jdbcDriver)
                .option("dbtable", "graduate.student_day_three_meals_statistics")
                .save();
        studentDayTCSBeanDataset.show();
        sparkSession.stop();
    }
}
