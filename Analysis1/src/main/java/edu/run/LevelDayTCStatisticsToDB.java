package edu.run;

import edu.bean.DataBean;
import edu.bean.LevelDayCSBean;
import edu.bean.LevelDayTCSBean;
import edu.bean.StudentLevelDayTCSBean;
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

public class LevelDayTCStatisticsToDB {
    private final static String[] names = new String[]{"class", "major", "college"};

    public static void main(String[] args) {
        for (String name : names) {
            run(name);
        }
    }

    public static void run(String name) {
        SparkSession sparkSession = SparkSession.builder().appName(name + "DayTCSAnalysis").master("local[2]").getOrCreate();
        Dataset<StudentLevelDayTCSBean> studentDayTCSBeanDataset = sparkSession.read().format("jdbc")
                .option("url", StaticConstant.jdbcUrl)
                .option("user", StaticConstant.jdbcUser)
                .option("password", StaticConstant.jdbcPassword)
                .option("driver", StaticConstant.jdbcDriver)
                .option("query", "select sid," + name + "_id id,day,consumption_category,consumption_total_money from student_day_three_meals_statistics,student where student.id=sid")
                .load().as(Encoders.bean(StudentLevelDayTCSBean.class));
        JavaPairRDD<Tuple3<Integer, String, Date>, Tuple2<Double, Integer>> levelDayTCSJavaPairRDD = studentDayTCSBeanDataset.toJavaRDD().mapToPair(new PairFunction<StudentLevelDayTCSBean, Tuple3<Integer, String, Date>, Tuple2<Double, Integer>>() {
            @Override
            public Tuple2<Tuple3<Integer, String, Date>, Tuple2<Double, Integer>> call(StudentLevelDayTCSBean studentLevelDayCSBean) throws Exception {
                return new Tuple2<>(new Tuple3<>(
                        studentLevelDayCSBean.getId(),
                        studentLevelDayCSBean.getConsumption_category(),
                        studentLevelDayCSBean.getDay()
                ), new Tuple2<>(
                        studentLevelDayCSBean.getConsumption_total_money(),
                        1
                ));
            }
        });
        JavaPairRDD<Tuple3<Integer, String, Date>, Tuple2<Double, Integer>> levelDayTCSSumJavaPairRDD = levelDayTCSJavaPairRDD.reduceByKey(new Function2<Tuple2<Double, Integer>, Tuple2<Double, Integer>, Tuple2<Double, Integer>>() {
            @Override
            public Tuple2<Double, Integer> call(Tuple2<Double, Integer> doubleIntegerTuple2, Tuple2<Double, Integer> doubleIntegerTuple22) throws Exception {
                return new Tuple2<>(
                        doubleIntegerTuple2._1() + doubleIntegerTuple22._1(),
                        doubleIntegerTuple2._2() + doubleIntegerTuple22._2()
                );
            }
        });
        JavaPairRDD<Tuple3<Integer, String, Date>, Tuple3<Double, Integer, Double>> levelDayTCSAverageJavaPairRDD = levelDayTCSSumJavaPairRDD.mapToPair(new PairFunction<Tuple2<Tuple3<Integer, String, Date>, Tuple2<Double, Integer>>, Tuple3<Integer, String, Date>, Tuple3<Double, Integer, Double>>() {
            @Override
            public Tuple2<Tuple3<Integer, String, Date>, Tuple3<Double, Integer, Double>> call(Tuple2<Tuple3<Integer, String, Date>, Tuple2<Double, Integer>> tuple3Tuple2Tuple2) throws Exception {
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
        JavaPairRDD<Tuple3<Integer, String, Date>, Tuple2<Tuple3<Double, Integer, Double>, Tuple2<Double, Integer>>> levelDayJoinJavaPairRDD = levelDayTCSAverageJavaPairRDD.join(levelDayTCSJavaPairRDD);
        JavaPairRDD<Tuple3<Integer, String, Date>, DataBean> levelDayTCSDataJavaPairRDD = levelDayJoinJavaPairRDD.mapToPair(new PairFunction<Tuple2<Tuple3<Integer, String, Date>, Tuple2<Tuple3<Double, Integer, Double>, Tuple2<Double, Integer>>>, Tuple3<Integer, String, Date>, DataBean>() {
            @Override
            public Tuple2<Tuple3<Integer, String, Date>, DataBean> call(Tuple2<Tuple3<Integer, String, Date>, Tuple2<Tuple3<Double, Integer, Double>, Tuple2<Double, Integer>>> tuple3Tuple2Tuple2) throws Exception {
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
        JavaPairRDD<Tuple3<Integer, String, Date>, DataBean> levelDayTCSDataBeanJavaPairRDD = levelDayTCSDataJavaPairRDD.reduceByKey(new Function2<DataBean, DataBean, DataBean>() {
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
        JavaRDD<LevelDayTCSBean> levelDayTCSBeanJavaRDD = levelDayTCSDataBeanJavaPairRDD.map(new Function<Tuple2<Tuple3<Integer, String, Date>, DataBean>, LevelDayTCSBean>() {
            @Override
            public LevelDayTCSBean call(Tuple2<Tuple3<Integer, String, Date>, DataBean> tuple3DataBeanTuple2) throws Exception {
                return new LevelDayTCSBean(
                        tuple3DataBeanTuple2._1()._1(),
                        tuple3DataBeanTuple2._1()._3(),
                        tuple3DataBeanTuple2._1()._2(),
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
        Dataset<LevelDayTCSBean> levelDayTCSBeanDataset = sparkSession.createDataFrame(levelDayTCSBeanJavaRDD, LevelDayTCSBean.class).as(Encoders.bean(LevelDayTCSBean.class));
        levelDayTCSBeanDataset.write().format("jdbc")
                .option("url", StaticConstant.jdbcUrl)
                .option("user", StaticConstant.jdbcUser)
                .option("password", StaticConstant.jdbcPassword)
                .option("driver", StaticConstant.jdbcDriver)
                .option("dbtable", name + "_day_three_meals_statistics1")
                .save();
        levelDayTCSBeanDataset.show();
        sparkSession.stop();
    }
}

