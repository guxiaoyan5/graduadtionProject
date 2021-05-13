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
import scala.Tuple7;

import java.sql.Date;

public class LevelDayCStatisticsToDB {
    private final static String[] names = new String[]{"class", "major", "college"};

    public static void main(String[] args) {
        for (String name : names) {
            run(name);
        }
    }

    public static void run(String name) {
        SparkSession sparkSession = SparkSession.builder().appName(name + "DayCSAnalysis").master("local[2]").getOrCreate();
        Dataset<StudentLevelDayCSBean> studentDayCSBeanDataset = sparkSession.read().format("jdbc")
                .option("url", StaticConstant.jdbcUrl)
                .option("user", StaticConstant.jdbcUser)
                .option("password", StaticConstant.jdbcPassword)
                .option("driver", StaticConstant.jdbcDriver)
                .option("query", "select sid," + name + "_id id,day,consumption_total_money from student_day_three_meals_statistics,student where student.id=sid")
                .load().as(Encoders.bean(StudentLevelDayCSBean.class));
        JavaPairRDD<Tuple2<Integer, Date>, Tuple2<Double, Integer>> levelDayCSJavaPairRDD = studentDayCSBeanDataset.toJavaRDD().mapToPair(new PairFunction<StudentLevelDayCSBean, Tuple2<Integer, Date>, Tuple2<Double, Integer>>() {
            @Override
            public Tuple2<Tuple2<Integer, Date>, Tuple2<Double, Integer>> call(StudentLevelDayCSBean studentLevelDayCSBean) throws Exception {
                return new Tuple2<>(new Tuple2<>(
                        studentLevelDayCSBean.getId(),
                        studentLevelDayCSBean.getDay()
                ), new Tuple2<>(
                        studentLevelDayCSBean.getConsumption_total_money(),
                        1
                ));
            }
        });
        JavaPairRDD<Tuple2<Integer, Date>, Tuple2<Double, Integer>> levelDayCSSumJavaPairRDD = levelDayCSJavaPairRDD.reduceByKey(new Function2<Tuple2<Double, Integer>, Tuple2<Double, Integer>, Tuple2<Double, Integer>>() {
            @Override
            public Tuple2<Double, Integer> call(Tuple2<Double, Integer> doubleIntegerTuple2, Tuple2<Double, Integer> doubleIntegerTuple22) throws Exception {
                return new Tuple2<>(
                        doubleIntegerTuple2._1() + doubleIntegerTuple22._1(),
                        doubleIntegerTuple2._2() + doubleIntegerTuple22._2()
                );
            }
        });
        JavaPairRDD<Tuple2<Integer, Date>, Tuple3<Double, Integer, Double>> levelDayCSAverageJavaPairRDD = levelDayCSSumJavaPairRDD.mapToPair(new PairFunction<Tuple2<Tuple2<Integer, Date>, Tuple2<Double, Integer>>, Tuple2<Integer, Date>, Tuple3<Double, Integer, Double>>() {
            @Override
            public Tuple2<Tuple2<Integer, Date>, Tuple3<Double, Integer, Double>> call(Tuple2<Tuple2<Integer, Date>, Tuple2<Double, Integer>> tuple2Tuple2Tuple2) throws Exception {
                return new Tuple2<>(
                        tuple2Tuple2Tuple2._1(),
                        new Tuple3<>(
                                tuple2Tuple2Tuple2._2()._1(),
                                tuple2Tuple2Tuple2._2()._2(),
                                tuple2Tuple2Tuple2._2()._1() / tuple2Tuple2Tuple2._2()._2()
                        )
                );
            }
        });
        JavaPairRDD<Tuple2<Integer, Date>, Tuple2<Tuple3<Double, Integer, Double>, Tuple2<Double, Integer>>> levelDayJoinJavaPairRDD = levelDayCSAverageJavaPairRDD.join(levelDayCSJavaPairRDD);
        JavaPairRDD<Tuple2<Integer, Date>, DataBean> levelDayCSDataJavaPairRDD = levelDayJoinJavaPairRDD.mapToPair(new PairFunction<Tuple2<Tuple2<Integer, Date>, Tuple2<Tuple3<Double, Integer, Double>, Tuple2<Double, Integer>>>, Tuple2<Integer, Date>, DataBean>() {
            @Override
            public Tuple2<Tuple2<Integer, Date>, DataBean> call(Tuple2<Tuple2<Integer, Date>, Tuple2<Tuple3<Double, Integer, Double>, Tuple2<Double, Integer>>> tuple2Tuple2Tuple2) throws Exception {
                double max = tuple2Tuple2Tuple2._2()._2()._1();
                double min = tuple2Tuple2Tuple2._2()._2()._1();
                int low = 0;
                int high = 0;
                if (tuple2Tuple2Tuple2._2()._2()._1() < tuple2Tuple2Tuple2._2()._1()._3()) {
                    low = 1;
                } else {
                    high = 1;
                }
                return new Tuple2<>(tuple2Tuple2Tuple2._1(),
                        new DataBean(
                                tuple2Tuple2Tuple2._2()._1()._1(),
                                tuple2Tuple2Tuple2._2()._1()._3(),
                                max,
                                min,
                                tuple2Tuple2Tuple2._2()._1()._2(),
                                high,
                                low
                        ));
            }
        });
        JavaPairRDD<Tuple2<Integer, Date>, DataBean> levelDayCSDataBeanJavaPairRDD = levelDayCSDataJavaPairRDD.reduceByKey(new Function2<DataBean, DataBean, DataBean>() {
            @Override
            public DataBean call(DataBean dataBean, DataBean dataBean2) throws Exception {
                return new DataBean(
                        dataBean.getConsumption_total_money() + dataBean2.getConsumption_total_money(),
                        (dataBean.getConsumption_total_money() + dataBean2.getConsumption_total_money()) / (dataBean.getCount() + dataBean.getCount()),
                        Math.max(dataBean.getMax(), dataBean2.getMax()),
                        Math.min(dataBean.getMin(), dataBean2.getMin()),
                        dataBean.getCount()+dataBean2.getCount(),
                        dataBean.getHigh() + dataBean2.getHigh(),
                        dataBean.getLow() + dataBean2.getLow()
                );
            }
        });
        JavaRDD<LevelDayCSBean> levelDayCSBeanJavaRDD = levelDayCSDataBeanJavaPairRDD.map(new Function<Tuple2<Tuple2<Integer, Date>, DataBean>, LevelDayCSBean>() {
            @Override
            public LevelDayCSBean call(Tuple2<Tuple2<Integer, Date>, DataBean> tuple2DataBeanTuple2) throws Exception {
                return new LevelDayCSBean(
                        tuple2DataBeanTuple2._1()._1(),
                        tuple2DataBeanTuple2._1()._2(),
                        tuple2DataBeanTuple2._2().getConsumption_total_money(),
                        tuple2DataBeanTuple2._2().getConsumption_average_money(),
                        tuple2DataBeanTuple2._2().getMax(),
                        tuple2DataBeanTuple2._2().getMin(),
                        tuple2DataBeanTuple2._2().getCount(),
                        tuple2DataBeanTuple2._2().getHigh(),
                        tuple2DataBeanTuple2._2().getLow()
                );
            }
        });
        Dataset<LevelDayCSBean> levelDayCSBeanDataset = sparkSession.createDataFrame(levelDayCSBeanJavaRDD,LevelDayCSBean.class).as(Encoders.bean(LevelDayCSBean.class));
        levelDayCSBeanDataset.write().format("jdbc")
                .option("url", StaticConstant.jdbcUrl)
                .option("user", StaticConstant.jdbcUser)
                .option("password", StaticConstant.jdbcPassword)
                .option("driver", StaticConstant.jdbcDriver)
                .option("dbtable", name+"_day_consumption_statistics1")
                .save();
        levelDayCSBeanDataset.show();
        sparkSession.stop();
    }
}
