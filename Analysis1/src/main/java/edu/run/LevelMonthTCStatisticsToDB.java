package edu.run;

import edu.bean.*;
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

public class LevelMonthTCStatisticsToDB {
    private final static String[] names = new String[]{"class", "major", "college"};

    public static void main(String[] args) {
        SparkSession sparkSession = SparkSession.builder().appName("MonthCSAnalysis").master("local[2]").getOrCreate();
        for (String name : names) {
            run(name,sparkSession);
        }
        sparkSession.stop();
    }

//    public static void run(String name) {
//        SparkSession sparkSession = SparkSession.builder().appName(name + "MonthCSAnalysis").master("local[2]").getOrCreate();
//        Dataset<StudentLevelMonthTCSBean> studentMonthTCSBeanDataset = sparkSession.read().format("jdbc")
//                .option("url", StaticConstant.jdbcUrl)
//                .option("user", StaticConstant.jdbcUser)
//                .option("password", StaticConstant.jdbcPassword)
//                .option("driver", StaticConstant.jdbcDriver)
//                .option("query", "select sid," + name + "_id id,year,month,consumption_category,consumption_total_money from student_month_three_meals_statistics,student where student.id=sid")
//                .load().as(Encoders.bean(StudentLevelMonthTCSBean.class));
//        JavaPairRDD<Tuple4<Integer, Integer, Integer,String>, Tuple2<Double, Integer>> levelMonthTCSJavaPairRDD = studentMonthTCSBeanDataset.toJavaRDD().mapToPair(new PairFunction<StudentLevelMonthTCSBean, Tuple4<Integer, Integer, Integer, String>, Tuple2<Double, Integer>>() {
//            @Override
//            public Tuple2<Tuple4<Integer, Integer, Integer, String>, Tuple2<Double, Integer>> call(StudentLevelMonthTCSBean studentLevelMonthTCSBean) throws Exception {
//                return new Tuple2<>(new Tuple4<>(
//                        studentLevelMonthTCSBean.getId(),
//                        studentLevelMonthTCSBean.getYear(),
//                        studentLevelMonthTCSBean.getMonth(),
//                        studentLevelMonthTCSBean.getConsumption_category()
//                ), new Tuple2<>(
//                        studentLevelMonthTCSBean.getConsumption_total_money(),
//                        1
//                ));
//            }
//        });
//        JavaPairRDD<Tuple4<Integer, Integer, Integer,String>, Tuple2<Double, Integer>> levelMonthTCSSumJavaPairRDD = levelMonthTCSJavaPairRDD.reduceByKey(new Function2<Tuple2<Double, Integer>, Tuple2<Double, Integer>, Tuple2<Double, Integer>>() {
//            @Override
//            public Tuple2<Double, Integer> call(Tuple2<Double, Integer> doubleIntegerTuple2, Tuple2<Double, Integer> doubleIntegerTuple22) throws Exception {
//                return new Tuple2<>(
//                        doubleIntegerTuple2._1() + doubleIntegerTuple22._1(),
//                        doubleIntegerTuple2._2() + doubleIntegerTuple22._2()
//                );
//            }
//        });
//        JavaPairRDD<Tuple4<Integer, Integer, Integer,String>, Tuple3<Double, Integer, Double>> levelMonthTCSAverageJavaPairRDD = levelMonthTCSSumJavaPairRDD.mapToPair(new PairFunction<Tuple2<Tuple4<Integer, Integer, Integer, String>, Tuple2<Double, Integer>>, Tuple4<Integer, Integer, Integer, String>, Tuple3<Double, Integer, Double>>() {
//            @Override
//            public Tuple2<Tuple4<Integer, Integer, Integer, String>, Tuple3<Double, Integer, Double>> call(Tuple2<Tuple4<Integer, Integer, Integer, String>, Tuple2<Double, Integer>> tuple4Tuple2Tuple2) throws Exception {
//                return new Tuple2<>(
//                        tuple4Tuple2Tuple2._1(),
//                        new Tuple3<>(
//                                tuple4Tuple2Tuple2._2()._1(),
//                                tuple4Tuple2Tuple2._2()._2(),
//                                tuple4Tuple2Tuple2._2()._1() / tuple4Tuple2Tuple2._2()._2()
//                        )
//                );
//            }
//        });
//        JavaPairRDD<Tuple4<Integer, Integer, Integer,String>,  Tuple2<Tuple3<Double, Integer, Double>, Tuple2<Double, Integer>>> levelMonthJoinJavaPairRDD = levelMonthTCSAverageJavaPairRDD.join(levelMonthTCSJavaPairRDD);
//        JavaPairRDD<Tuple4<Integer, Integer, Integer,String>, DataBean> levelMonthTCSDataJavaPairRDD = levelMonthJoinJavaPairRDD.mapToPair(new PairFunction<Tuple2<Tuple4<Integer, Integer, Integer, String>, Tuple2<Tuple3<Double, Integer, Double>, Tuple2<Double, Integer>>>, Tuple4<Integer, Integer, Integer, String>, DataBean>() {
//            @Override
//            public Tuple2<Tuple4<Integer, Integer, Integer, String>, DataBean> call(Tuple2<Tuple4<Integer, Integer, Integer, String>, Tuple2<Tuple3<Double, Integer, Double>, Tuple2<Double, Integer>>> tuple4Tuple2Tuple2) throws Exception {
//                double max = tuple4Tuple2Tuple2._2()._2()._1();
//                double min = tuple4Tuple2Tuple2._2()._2()._1();
//                int low = 0;
//                int high = 0;
//                if (tuple4Tuple2Tuple2._2()._2()._1() < tuple4Tuple2Tuple2._2()._1()._3()) {
//                    low = 1;
//                } else {
//                    high = 1;
//                }
//                return new Tuple2<>(tuple4Tuple2Tuple2._1(),
//                        new DataBean(
//                                tuple4Tuple2Tuple2._2()._1()._1(),
//                                tuple4Tuple2Tuple2._2()._1()._3(),
//                                max,
//                                min,
//                                tuple4Tuple2Tuple2._2()._1()._2(),
//                                high,
//                                low
//                        ));
//            }
//        });
//        JavaPairRDD<Tuple4<Integer, Integer, Integer,String>, DataBean> levelMonthTCSDataBeanJavaPairRDD = levelMonthTCSDataJavaPairRDD.reduceByKey(new Function2<DataBean, DataBean, DataBean>() {
//            @Override
//            public DataBean call(DataBean dataBean, DataBean dataBean2) throws Exception {
//                return new DataBean(
//                        dataBean.getConsumption_total_money() + dataBean2.getConsumption_total_money(),
//                        (dataBean.getConsumption_total_money() + dataBean2.getConsumption_total_money()) / (dataBean.getCount() + dataBean.getCount()),
//                        Math.max(dataBean.getMax(), dataBean2.getMax()),
//                        Math.min(dataBean.getMin(), dataBean2.getMin()),
//                        dataBean.getCount() + dataBean2.getCount(),
//                        dataBean.getHigh() + dataBean2.getHigh(),
//                        dataBean.getLow() + dataBean2.getLow()
//                );
//            }
//        });
//        JavaRDD<LevelMonthTCSBean> levelMonthTCSBeanJavaRDD = levelMonthTCSDataBeanJavaPairRDD.map(new Function<Tuple2<Tuple4<Integer, Integer, Integer, String>, DataBean>, LevelMonthTCSBean>() {
//            @Override
//            public LevelMonthTCSBean call(Tuple2<Tuple4<Integer, Integer, Integer, String>, DataBean> tuple4DataBeanTuple2) throws Exception {
//                return new LevelMonthTCSBean(
//                        tuple4DataBeanTuple2._1()._1(),
//                        tuple4DataBeanTuple2._1()._2(),
//                        tuple4DataBeanTuple2._1()._3(),
//                        tuple4DataBeanTuple2._1()._4(),
//                        tuple4DataBeanTuple2._2().getConsumption_total_money(),
//                        tuple4DataBeanTuple2._2().getConsumption_average_money(),
//                        tuple4DataBeanTuple2._2().getMax(),
//                        tuple4DataBeanTuple2._2().getMin(),
//                        tuple4DataBeanTuple2._2().getCount(),
//                        tuple4DataBeanTuple2._2().getHigh(),
//                        tuple4DataBeanTuple2._2().getLow()
//                );
//            }
//        });
//
//        Dataset<LevelMonthTCSBean> levelMonthTCSBeanDataset = sparkSession.createDataFrame(levelMonthTCSBeanJavaRDD, LevelMonthTCSBean.class).as(Encoders.bean(LevelMonthTCSBean.class));
//        levelMonthTCSBeanDataset.write().format("jdbc")
//                .option("url", StaticConstant.jdbcUrl)
//                .option("user", StaticConstant.jdbcUser)
//                .option("password", StaticConstant.jdbcPassword)
//                .option("driver", StaticConstant.jdbcDriver)
//                .option("dbtable", name + "_month_three_meals_statistics1")
//                .save();
//        levelMonthTCSBeanDataset.show();
//        sparkSession.stop();
//    }

    public static void run(String name,SparkSession sparkSession) {
        Dataset<StudentLevelDayTCSBean> studentDayTCSBeanDataset = sparkSession.read().format("jdbc")
                .option("url", StaticConstant.jdbcUrl)
                .option("user", StaticConstant.jdbcUser)
                .option("password", StaticConstant.jdbcPassword)
                .option("driver", StaticConstant.jdbcDriver)
                .option("query", "select sid," + name + "_id id,day,consumption_category,consumption_total_money from student_day_three_meals_statistics,student where student.id=sid")
                .load().as(Encoders.bean(StudentLevelDayTCSBean.class));
        JavaRDD<StudentLevelMonthTCSBean> studentLevelMonthTCSBeanJavaRDD = studentDayTCSBeanDataset.toJavaRDD().map(new Function<StudentLevelDayTCSBean, StudentLevelMonthTCSBean>() {
            @Override
            public StudentLevelMonthTCSBean call(StudentLevelDayTCSBean studentLevelDayTCSBean) throws Exception {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(studentLevelDayTCSBean.getDay());
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH)+1;
                return new StudentLevelMonthTCSBean(
                        studentLevelDayTCSBean.getSid(),
                        studentLevelDayTCSBean.getId(),
                        year,
                        month,
                        studentLevelDayTCSBean.getConsumption_category(),
                        studentLevelDayTCSBean.getConsumption_total_money()
                );
            }
        });
        JavaPairRDD<Tuple4<Integer, Integer, Integer,String>, Tuple2<Double, Integer>> levelMonthTCSJavaPairRDD = studentLevelMonthTCSBeanJavaRDD.mapToPair(new PairFunction<StudentLevelMonthTCSBean, Tuple4<Integer, Integer, Integer, String>, Tuple2<Double, Integer>>() {
            @Override
            public Tuple2<Tuple4<Integer, Integer, Integer, String>, Tuple2<Double, Integer>> call(StudentLevelMonthTCSBean studentLevelMonthTCSBean) throws Exception {
                return new Tuple2<>(new Tuple4<>(
                        studentLevelMonthTCSBean.getId(),
                        studentLevelMonthTCSBean.getYear(),
                        studentLevelMonthTCSBean.getMonth(),
                        studentLevelMonthTCSBean.getConsumption_category()
                ), new Tuple2<>(
                        studentLevelMonthTCSBean.getConsumption_total_money(),
                        1
                ));
            }
        });
        JavaPairRDD<Tuple4<Integer, Integer, Integer,String>, Tuple2<Double, Integer>> levelMonthTCSSumJavaPairRDD = levelMonthTCSJavaPairRDD.reduceByKey(new Function2<Tuple2<Double, Integer>, Tuple2<Double, Integer>, Tuple2<Double, Integer>>() {
            @Override
            public Tuple2<Double, Integer> call(Tuple2<Double, Integer> doubleIntegerTuple2, Tuple2<Double, Integer> doubleIntegerTuple22) throws Exception {
                return new Tuple2<>(
                        doubleIntegerTuple2._1() + doubleIntegerTuple22._1(),
                        doubleIntegerTuple2._2() + doubleIntegerTuple22._2()
                );
            }
        });
        JavaPairRDD<Tuple4<Integer, Integer, Integer,String>, Tuple3<Double, Integer, Double>> levelMonthTCSAverageJavaPairRDD = levelMonthTCSSumJavaPairRDD.mapToPair(new PairFunction<Tuple2<Tuple4<Integer, Integer, Integer, String>, Tuple2<Double, Integer>>, Tuple4<Integer, Integer, Integer, String>, Tuple3<Double, Integer, Double>>() {
            @Override
            public Tuple2<Tuple4<Integer, Integer, Integer, String>, Tuple3<Double, Integer, Double>> call(Tuple2<Tuple4<Integer, Integer, Integer, String>, Tuple2<Double, Integer>> tuple4Tuple2Tuple2) throws Exception {
                return new Tuple2<>(
                        tuple4Tuple2Tuple2._1(),
                        new Tuple3<>(
                                tuple4Tuple2Tuple2._2()._1(),
                                tuple4Tuple2Tuple2._2()._2(),
                                tuple4Tuple2Tuple2._2()._1() / tuple4Tuple2Tuple2._2()._2()
                        )
                );
            }
        });
        JavaPairRDD<Tuple4<Integer, Integer, Integer,String>,  Tuple2<Tuple3<Double, Integer, Double>, Tuple2<Double, Integer>>> levelMonthJoinJavaPairRDD = levelMonthTCSAverageJavaPairRDD.join(levelMonthTCSJavaPairRDD);
        JavaPairRDD<Tuple4<Integer, Integer, Integer,String>, DataBean> levelMonthTCSDataJavaPairRDD = levelMonthJoinJavaPairRDD.mapToPair(new PairFunction<Tuple2<Tuple4<Integer, Integer, Integer, String>, Tuple2<Tuple3<Double, Integer, Double>, Tuple2<Double, Integer>>>, Tuple4<Integer, Integer, Integer, String>, DataBean>() {
            @Override
            public Tuple2<Tuple4<Integer, Integer, Integer, String>, DataBean> call(Tuple2<Tuple4<Integer, Integer, Integer, String>, Tuple2<Tuple3<Double, Integer, Double>, Tuple2<Double, Integer>>> tuple4Tuple2Tuple2) throws Exception {
                double max = tuple4Tuple2Tuple2._2()._2()._1();
                double min = tuple4Tuple2Tuple2._2()._2()._1();
                int low = 0;
                int high = 0;
                if (tuple4Tuple2Tuple2._2()._2()._1() < tuple4Tuple2Tuple2._2()._1()._3()) {
                    low = 1;
                } else {
                    high = 1;
                }
                return new Tuple2<>(tuple4Tuple2Tuple2._1(),
                        new DataBean(
                                tuple4Tuple2Tuple2._2()._1()._1(),
                                tuple4Tuple2Tuple2._2()._1()._3(),
                                max,
                                min,
                                tuple4Tuple2Tuple2._2()._1()._2(),
                                high,
                                low
                        ));
            }
        });
        JavaPairRDD<Tuple4<Integer, Integer, Integer,String>, DataBean> levelMonthTCSDataBeanJavaPairRDD = levelMonthTCSDataJavaPairRDD.reduceByKey(new Function2<DataBean, DataBean, DataBean>() {
            @Override
            public DataBean call(DataBean dataBean, DataBean dataBean2) throws Exception {
                return new DataBean(
                        dataBean.getConsumption_total_money() + dataBean2.getConsumption_total_money(),
                        (dataBean.getConsumption_total_money() + dataBean2.getConsumption_total_money()) / (dataBean.getCount() + dataBean.getCount()),
                        Math.max(dataBean.getMax(), dataBean2.getMax()),
                        Math.min(dataBean.getMin(), dataBean2.getMin()),
                        dataBean.getCount() + dataBean2.getCount(),
                        dataBean.getHigh() + dataBean2.getHigh(),
                        dataBean.getLow() + dataBean2.getLow()
                );
            }
        });
        JavaRDD<LevelMonthTCSBean> levelMonthTCSBeanJavaRDD = levelMonthTCSDataBeanJavaPairRDD.map(new Function<Tuple2<Tuple4<Integer, Integer, Integer, String>, DataBean>, LevelMonthTCSBean>() {
            @Override
            public LevelMonthTCSBean call(Tuple2<Tuple4<Integer, Integer, Integer, String>, DataBean> tuple4DataBeanTuple2) throws Exception {
                return new LevelMonthTCSBean(
                        tuple4DataBeanTuple2._1()._1(),
                        tuple4DataBeanTuple2._1()._2(),
                        tuple4DataBeanTuple2._1()._3(),
                        tuple4DataBeanTuple2._1()._4(),
                        tuple4DataBeanTuple2._2().getConsumption_total_money(),
                        tuple4DataBeanTuple2._2().getConsumption_average_money(),
                        tuple4DataBeanTuple2._2().getMax(),
                        tuple4DataBeanTuple2._2().getMin(),
                        tuple4DataBeanTuple2._2().getCount(),
                        tuple4DataBeanTuple2._2().getHigh(),
                        tuple4DataBeanTuple2._2().getLow()
                );
            }
        });

        Dataset<LevelMonthTCSBean> levelMonthTCSBeanDataset = sparkSession.createDataFrame(levelMonthTCSBeanJavaRDD, LevelMonthTCSBean.class).as(Encoders.bean(LevelMonthTCSBean.class));
        levelMonthTCSBeanDataset.write().format("jdbc")
                .option("url", StaticConstant.jdbcUrl)
                .option("user", StaticConstant.jdbcUser)
                .option("password", StaticConstant.jdbcPassword)
                .option("driver", StaticConstant.jdbcDriver)
                .option("dbtable", name + "_month_three_meals_statistics1")
                .save();
        levelMonthTCSBeanDataset.show();
    }
}
