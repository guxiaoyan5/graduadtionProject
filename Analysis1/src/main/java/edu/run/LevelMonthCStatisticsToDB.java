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

import java.sql.Date;
import java.util.Calendar;

public class LevelMonthCStatisticsToDB {
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
//        Dataset<StudentLevelMonthCSBean> studentMonthCSBeanDataset = sparkSession.read().format("jdbc")
//                .option("url", StaticConstant.jdbcUrl)
//                .option("user", StaticConstant.jdbcUser)
//                .option("password", StaticConstant.jdbcPassword)
//                .option("driver", StaticConstant.jdbcDriver)
//                .option("query", "select sid," + name + "_id id,year,month,consumption_total_money from student_month_consumption_statistics,student where student.id=sid")
//                .load().as(Encoders.bean(StudentLevelMonthCSBean.class));
//        JavaPairRDD<Tuple3<Integer, Integer, Integer>, Tuple2<Double, Integer>> levelMonthCSJavaPairRDD = studentMonthCSBeanDataset.toJavaRDD().mapToPair(new PairFunction<StudentLevelMonthCSBean, Tuple3<Integer, Integer, Integer>, Tuple2<Double, Integer>>() {
//            @Override
//            public Tuple2<Tuple3<Integer, Integer, Integer>, Tuple2<Double, Integer>> call(StudentLevelMonthCSBean studentLevelMonthCSBean) throws Exception {
//                return new Tuple2<>(new Tuple3<>(
//                        studentLevelMonthCSBean.getId(),
//                        studentLevelMonthCSBean.getYear(),
//                        studentLevelMonthCSBean.getMonth()
//                ), new Tuple2<>(
//                        studentLevelMonthCSBean.getConsumption_total_money(),
//                        1
//                ));
//            }
//        });
//        JavaPairRDD<Tuple3<Integer, Integer, Integer>, Tuple2<Double, Integer>> levelMonthCSSumJavaPairRDD = levelMonthCSJavaPairRDD.reduceByKey(new Function2<Tuple2<Double, Integer>, Tuple2<Double, Integer>, Tuple2<Double, Integer>>() {
//            @Override
//            public Tuple2<Double, Integer> call(Tuple2<Double, Integer> doubleIntegerTuple2, Tuple2<Double, Integer> doubleIntegerTuple22) throws Exception {
//                return new Tuple2<>(
//                        doubleIntegerTuple2._1() + doubleIntegerTuple22._1(),
//                        doubleIntegerTuple2._2() + doubleIntegerTuple22._2()
//                );
//            }
//        });
//        JavaPairRDD<Tuple3<Integer, Integer, Integer>, Tuple3<Double, Integer, Double>> levelMonthCSAverageJavaPairRDD = levelMonthCSSumJavaPairRDD.mapToPair(new PairFunction<Tuple2<Tuple3<Integer, Integer, Integer>, Tuple2<Double, Integer>>, Tuple3<Integer, Integer, Integer>, Tuple3<Double, Integer, Double>>() {
//            @Override
//            public Tuple2<Tuple3<Integer, Integer, Integer>, Tuple3<Double, Integer, Double>> call(Tuple2<Tuple3<Integer, Integer, Integer>, Tuple2<Double, Integer>> tuple3Tuple2Tuple2) throws Exception {
//                return new Tuple2<>(
//                        tuple3Tuple2Tuple2._1(),
//                        new Tuple3<>(
//                                tuple3Tuple2Tuple2._2()._1(),
//                                tuple3Tuple2Tuple2._2()._2(),
//                                tuple3Tuple2Tuple2._2()._1() / tuple3Tuple2Tuple2._2()._2()
//                        )
//                );
//            }
//        });
//        JavaPairRDD<Tuple3<Integer, Integer, Integer>, Tuple2<Tuple3<Double, Integer, Double>, Tuple2<Double, Integer>>> levelMonthJoinJavaPairRDD = levelMonthCSAverageJavaPairRDD.join(levelMonthCSJavaPairRDD);
//        JavaPairRDD<Tuple3<Integer, Integer, Integer>, DataBean> levelMonthCSDataJavaPairRDD = levelMonthJoinJavaPairRDD.mapToPair(new PairFunction<Tuple2<Tuple3<Integer, Integer, Integer>, Tuple2<Tuple3<Double, Integer, Double>, Tuple2<Double, Integer>>>, Tuple3<Integer, Integer, Integer>, DataBean>() {
//            @Override
//            public Tuple2<Tuple3<Integer, Integer, Integer>, DataBean> call(Tuple2<Tuple3<Integer, Integer, Integer>, Tuple2<Tuple3<Double, Integer, Double>, Tuple2<Double, Integer>>> tuple3Tuple2Tuple2) throws Exception {
//                double max = tuple3Tuple2Tuple2._2()._2()._1();
//                double min = tuple3Tuple2Tuple2._2()._2()._1();
//                int low = 0;
//                int high = 0;
//                if (tuple3Tuple2Tuple2._2()._2()._1() < tuple3Tuple2Tuple2._2()._1()._3()) {
//                    low = 1;
//                } else {
//                    high = 1;
//                }
//                return new Tuple2<>(tuple3Tuple2Tuple2._1(),
//                        new DataBean(
//                                tuple3Tuple2Tuple2._2()._1()._1(),
//                                tuple3Tuple2Tuple2._2()._1()._3(),
//                                max,
//                                min,
//                                tuple3Tuple2Tuple2._2()._1()._2(),
//                                high,
//                                low
//                        ));
//            }
//        });
//        JavaPairRDD<Tuple3<Integer, Integer, Integer>, DataBean> levelMonthCSDataBeanJavaPairRDD = levelMonthCSDataJavaPairRDD.reduceByKey(new Function2<DataBean, DataBean, DataBean>() {
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
//        JavaRDD<LevelMonthCSBean> levelMonthCSBeanJavaRDD = levelMonthCSDataBeanJavaPairRDD.map(new Function<Tuple2<Tuple3<Integer, Integer, Integer>, DataBean>, LevelMonthCSBean>() {
//            @Override
//            public LevelMonthCSBean call(Tuple2<Tuple3<Integer, Integer, Integer>, DataBean> tuple3DataBeanTuple2) throws Exception {
//                return new LevelMonthCSBean(
//                        tuple3DataBeanTuple2._1()._1(),
//                        tuple3DataBeanTuple2._1()._2(),
//                        tuple3DataBeanTuple2._1()._3(),
//                        tuple3DataBeanTuple2._2().getConsumption_total_money(),
//                        tuple3DataBeanTuple2._2().getConsumption_average_money(),
//                        tuple3DataBeanTuple2._2().getMax(),
//                        tuple3DataBeanTuple2._2().getMin(),
//                        tuple3DataBeanTuple2._2().getCount(),
//                        tuple3DataBeanTuple2._2().getHigh(),
//                        tuple3DataBeanTuple2._2().getLow()
//                );
//            }
//        });
//        Dataset<LevelMonthCSBean> levelMonthCSBeanDataset = sparkSession.createDataFrame(levelMonthCSBeanJavaRDD, LevelMonthCSBean.class).as(Encoders.bean(LevelMonthCSBean.class));
//        levelMonthCSBeanDataset.write().format("jdbc")
//                .option("url", StaticConstant.jdbcUrl)
//                .option("user", StaticConstant.jdbcUser)
//                .option("password", StaticConstant.jdbcPassword)
//                .option("driver", StaticConstant.jdbcDriver)
//                .option("dbtable", name + "_month_consumption_statistics1")
//                .save();
//        levelMonthCSBeanDataset.show();
//        sparkSession.stop();
//    }

    public static void run(String name,SparkSession sparkSession) {
        Dataset<StudentLevelDayCSBean> studentDayCSBeanDataset = sparkSession.read().format("jdbc")
                .option("url", StaticConstant.jdbcUrl)
                .option("user", StaticConstant.jdbcUser)
                .option("password", StaticConstant.jdbcPassword)
                .option("driver", StaticConstant.jdbcDriver)
                .option("query", "select sid," + name + "_id id,day,consumption_total_money from student_day_three_meals_statistics,student where student.id=sid")
                .load().as(Encoders.bean(StudentLevelDayCSBean.class));
        JavaRDD<StudentLevelMonthCSBean> studentMonthCSBeanJavaRDD = studentDayCSBeanDataset.toJavaRDD().map(new Function<StudentLevelDayCSBean, StudentLevelMonthCSBean>() {
            @Override
            public StudentLevelMonthCSBean call(StudentLevelDayCSBean studentLevelDayCSBean) throws Exception {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(studentLevelDayCSBean.getDay());
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH)+1;
                return new StudentLevelMonthCSBean(
                        studentLevelDayCSBean.getSid(),
                        studentLevelDayCSBean.getId(),
                        year,
                        month,
                        studentLevelDayCSBean.getConsumption_total_money()
                );
            }
        });

        JavaPairRDD<Tuple3<Integer, Integer, Integer>, Tuple2<Double, Integer>> levelMonthCSJavaPairRDD = studentMonthCSBeanJavaRDD.mapToPair(new PairFunction<StudentLevelMonthCSBean, Tuple3<Integer, Integer, Integer>, Tuple2<Double, Integer>>() {
            @Override
            public Tuple2<Tuple3<Integer, Integer, Integer>, Tuple2<Double, Integer>> call(StudentLevelMonthCSBean studentLevelMonthCSBean) throws Exception {
                return new Tuple2<>(new Tuple3<>(
                        studentLevelMonthCSBean.getId(),
                        studentLevelMonthCSBean.getYear(),
                        studentLevelMonthCSBean.getMonth()
                ), new Tuple2<>(
                        studentLevelMonthCSBean.getConsumption_total_money(),
                        1
                ));
            }
        });
        JavaPairRDD<Tuple3<Integer, Integer, Integer>, Tuple2<Double, Integer>> levelMonthCSSumJavaPairRDD = levelMonthCSJavaPairRDD.reduceByKey(new Function2<Tuple2<Double, Integer>, Tuple2<Double, Integer>, Tuple2<Double, Integer>>() {
            @Override
            public Tuple2<Double, Integer> call(Tuple2<Double, Integer> doubleIntegerTuple2, Tuple2<Double, Integer> doubleIntegerTuple22) throws Exception {
                return new Tuple2<>(
                        doubleIntegerTuple2._1() + doubleIntegerTuple22._1(),
                        doubleIntegerTuple2._2() + doubleIntegerTuple22._2()
                );
            }
        });
        JavaPairRDD<Tuple3<Integer, Integer, Integer>, Tuple3<Double, Integer, Double>> levelMonthCSAverageJavaPairRDD = levelMonthCSSumJavaPairRDD.mapToPair(new PairFunction<Tuple2<Tuple3<Integer, Integer, Integer>, Tuple2<Double, Integer>>, Tuple3<Integer, Integer, Integer>, Tuple3<Double, Integer, Double>>() {
            @Override
            public Tuple2<Tuple3<Integer, Integer, Integer>, Tuple3<Double, Integer, Double>> call(Tuple2<Tuple3<Integer, Integer, Integer>, Tuple2<Double, Integer>> tuple3Tuple2Tuple2) throws Exception {
                return new Tuple2<>(
                        tuple3Tuple2Tuple2._1(),
                        new Tuple3<>(
                                tuple3Tuple2Tuple2._2()._1(),
                                tuple3Tuple2Tuple2._2()._2(),
                                tuple3Tuple2Tuple2._2()._1() / tuple3Tuple2Tuple2._2()._2()
                        )
                );
            }
        });
        JavaPairRDD<Tuple3<Integer, Integer, Integer>, Tuple2<Tuple3<Double, Integer, Double>, Tuple2<Double, Integer>>> levelMonthJoinJavaPairRDD = levelMonthCSAverageJavaPairRDD.join(levelMonthCSJavaPairRDD);
        JavaPairRDD<Tuple3<Integer, Integer, Integer>, DataBean> levelMonthCSDataJavaPairRDD = levelMonthJoinJavaPairRDD.mapToPair(new PairFunction<Tuple2<Tuple3<Integer, Integer, Integer>, Tuple2<Tuple3<Double, Integer, Double>, Tuple2<Double, Integer>>>, Tuple3<Integer, Integer, Integer>, DataBean>() {
            @Override
            public Tuple2<Tuple3<Integer, Integer, Integer>, DataBean> call(Tuple2<Tuple3<Integer, Integer, Integer>, Tuple2<Tuple3<Double, Integer, Double>, Tuple2<Double, Integer>>> tuple3Tuple2Tuple2) throws Exception {
                double max = tuple3Tuple2Tuple2._2()._2()._1();
                double min = tuple3Tuple2Tuple2._2()._2()._1();
                int low = 0;
                int high = 0;
                if (tuple3Tuple2Tuple2._2()._2()._1() < tuple3Tuple2Tuple2._2()._1()._3()) {
                    low = 1;
                } else {
                    high = 1;
                }
                return new Tuple2<>(tuple3Tuple2Tuple2._1(),
                        new DataBean(
                                tuple3Tuple2Tuple2._2()._1()._1(),
                                tuple3Tuple2Tuple2._2()._1()._3(),
                                max,
                                min,
                                tuple3Tuple2Tuple2._2()._1()._2(),
                                high,
                                low
                        ));
            }
        });
        JavaPairRDD<Tuple3<Integer, Integer, Integer>, DataBean> levelMonthCSDataBeanJavaPairRDD = levelMonthCSDataJavaPairRDD.reduceByKey(new Function2<DataBean, DataBean, DataBean>() {
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
        JavaRDD<LevelMonthCSBean> levelMonthCSBeanJavaRDD = levelMonthCSDataBeanJavaPairRDD.map(new Function<Tuple2<Tuple3<Integer, Integer, Integer>, DataBean>, LevelMonthCSBean>() {
            @Override
            public LevelMonthCSBean call(Tuple2<Tuple3<Integer, Integer, Integer>, DataBean> tuple3DataBeanTuple2) throws Exception {
                return new LevelMonthCSBean(
                        tuple3DataBeanTuple2._1()._1(),
                        tuple3DataBeanTuple2._1()._2(),
                        tuple3DataBeanTuple2._1()._3(),
                        tuple3DataBeanTuple2._2().getConsumption_total_money(),
                        tuple3DataBeanTuple2._2().getConsumption_average_money(),
                        tuple3DataBeanTuple2._2().getMax(),
                        tuple3DataBeanTuple2._2().getMin(),
                        tuple3DataBeanTuple2._2().getCount(),
                        tuple3DataBeanTuple2._2().getHigh(),
                        tuple3DataBeanTuple2._2().getLow()
                );
            }
        });
        Dataset<LevelMonthCSBean> levelMonthCSBeanDataset = sparkSession.createDataFrame(levelMonthCSBeanJavaRDD, LevelMonthCSBean.class).as(Encoders.bean(LevelMonthCSBean.class));
        levelMonthCSBeanDataset.write().format("jdbc")
                .option("url", StaticConstant.jdbcUrl)
                .option("user", StaticConstant.jdbcUser)
                .option("password", StaticConstant.jdbcPassword)
                .option("driver", StaticConstant.jdbcDriver)
                .option("dbtable", name + "_month_consumption_statistics1")
                .save();
        levelMonthCSBeanDataset.show();
    }
}
