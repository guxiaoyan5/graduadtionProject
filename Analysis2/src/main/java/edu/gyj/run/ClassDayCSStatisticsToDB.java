package edu.gyj.run;

import edu.gyj.bean.Consume;
import edu.gyj.bean.classCS.ClassDayCSClassKey;
import edu.gyj.bean.classCS.ClassDayCSInputData;
import edu.gyj.bean.classCS.ClassDayCSKey;
import edu.gyj.bean.classCS.ClassDayCSValue;
import edu.gyj.bean.student.Student;
import edu.gyj.result.ClassDayCSResult;
import edu.gyj.util.StaticConstant;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.*;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

import java.sql.Date;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class ClassDayCSStatisticsToDB {
    public static void main(String[] args) {
        SparkSession sparkSession = SparkSession.builder().appName("ClassDayCSAnalysis").master("local[*]").getOrCreate();
        Dataset<ClassDayCSInputData> dataset = sparkSession.read().format("jdbc")
                .option("url", StaticConstant.jdbcUrl)
                .option("user", StaticConstant.jdbcUser)
                .option("password", StaticConstant.jdbcPassword)
                .option("driver", StaticConstant.jdbcDriver)
                .option("query", "select student.id,class_id,execution_time,money from student,consume where student.id = sid")
                .load().as(Encoders.bean(ClassDayCSInputData.class));
        //求出每人消费
        JavaPairRDD<ClassDayCSKey, ClassDayCSValue> classDayCS = dataset.toJavaRDD().mapToPair(new PairFunction<ClassDayCSInputData, ClassDayCSKey, ClassDayCSValue>() {
            @Override
            public Tuple2<ClassDayCSKey, ClassDayCSValue> call(ClassDayCSInputData classDayCSInputData) throws Exception {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(classDayCSInputData.getExecution_time());
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                return new Tuple2<>(new ClassDayCSKey(classDayCSInputData.getId(), classDayCSInputData.getClass_id(), year, month, day), new ClassDayCSValue(classDayCSInputData.getMoney()));
            }
        });
        JavaPairRDD<ClassDayCSKey, ClassDayCSValue> classDayCSStudentCS = classDayCS.reduceByKey(new Function2<ClassDayCSValue, ClassDayCSValue, ClassDayCSValue>() {
            @Override
            public ClassDayCSValue call(ClassDayCSValue classDayCSValue, ClassDayCSValue classDayCSValue2) throws Exception {
                return new ClassDayCSValue(classDayCSValue.getMoney() + classDayCSValue2.getMoney());
            }
        });
        //求出平均人次消费
        JavaPairRDD<ClassDayCSClassKey, ClassDayCSValue> classDayCSMap = dataset.toJavaRDD().mapToPair(new PairFunction<ClassDayCSInputData, ClassDayCSClassKey, ClassDayCSValue>() {
            @Override
            public Tuple2<ClassDayCSClassKey, ClassDayCSValue> call(ClassDayCSInputData classDayCSInputData) throws Exception {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(classDayCSInputData.getExecution_time());
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                return new Tuple2<>(new ClassDayCSClassKey(classDayCSInputData.getClass_id(), year, month, day), new ClassDayCSValue(classDayCSInputData.getMoney(), 1));
            }
        });
        JavaPairRDD<ClassDayCSClassKey, ClassDayCSValue> classDayReduce = classDayCSMap.reduceByKey(new Function2<ClassDayCSValue, ClassDayCSValue, ClassDayCSValue>() {
            @Override
            public ClassDayCSValue call(ClassDayCSValue classDayCSValue, ClassDayCSValue classDayCSValue2) throws Exception {
                int count = classDayCSValue.getCount() + classDayCSValue2.getCount();
                double money = classDayCSValue.getMoney() + classDayCSValue2.getMoney();
                double average = money / count;
                return new ClassDayCSValue(money, count, average);
            }
        });
        //求出人均消费
        JavaPairRDD<ClassDayCSClassKey, ClassDayCSValue> classDayCSStudentMap = classDayCS.mapToPair(new PairFunction<Tuple2<ClassDayCSKey, ClassDayCSValue>, ClassDayCSClassKey, ClassDayCSValue>() {
            @Override
            public Tuple2<ClassDayCSClassKey, ClassDayCSValue> call(Tuple2<ClassDayCSKey, ClassDayCSValue> classDayCSKeyClassDayCSValueTuple2) throws Exception {
                return new Tuple2<>(new ClassDayCSClassKey(
                        classDayCSKeyClassDayCSValueTuple2._1().getClass_id(),
                        classDayCSKeyClassDayCSValueTuple2._1().getYear(),
                        classDayCSKeyClassDayCSValueTuple2._1().getMonth(),
                        classDayCSKeyClassDayCSValueTuple2._1().getDay()
                ), new ClassDayCSValue(
                        classDayCSKeyClassDayCSValueTuple2._1().getSid(),
                        classDayCSKeyClassDayCSValueTuple2._2().getMoney(),
                        1
                ));
            }
        });
        JavaPairRDD<ClassDayCSClassKey, ClassDayCSValue> classDayCSStudentReduce = classDayCSStudentMap.reduceByKey(new Function2<ClassDayCSValue, ClassDayCSValue, ClassDayCSValue>() {
            @Override
            public ClassDayCSValue call(ClassDayCSValue classDayCSValue, ClassDayCSValue classDayCSValue2) throws Exception {
                int count = classDayCSValue.getCount() + classDayCSValue2.getCount();
                double money = classDayCSValue.getMoney() + classDayCSValue2.getMoney();
                double average = money / count;
                return new ClassDayCSValue(money, count, average);
            }
        });
        //求出高于低于每人次消费
        JavaPairRDD<ClassDayCSClassKey, Tuple2<ClassDayCSValue, ClassDayCSValue>> classDayCSLowAndHigh = classDayCSMap.join(classDayReduce);
        JavaPairRDD<ClassDayCSClassKey, ClassDayCSValue> classDayCSLowAndHighMap = classDayCSLowAndHigh.mapToPair(new PairFunction<Tuple2<ClassDayCSClassKey, Tuple2<ClassDayCSValue, ClassDayCSValue>>, ClassDayCSClassKey, ClassDayCSValue>() {

            @Override
            public Tuple2<ClassDayCSClassKey, ClassDayCSValue> call(Tuple2<ClassDayCSClassKey, Tuple2<ClassDayCSValue, ClassDayCSValue>> classDayCSClassKeyTuple2Tuple2) throws Exception {
                int low = 0, high = 0;
                if (classDayCSClassKeyTuple2Tuple2._2()._1().getMoney() < classDayCSClassKeyTuple2Tuple2._2()._2().getAverage()) {
                    low = 1;
                } else {
                    high = 1;
                }
                return new Tuple2<>(classDayCSClassKeyTuple2Tuple2._1(),
                        new ClassDayCSValue(classDayCSClassKeyTuple2Tuple2._2()._2().getMoney(),
                                classDayCSClassKeyTuple2Tuple2._2()._2().getCount(),
                                classDayCSClassKeyTuple2Tuple2._2()._2().getAverage(),
                                low, high));
            }
        });
        JavaPairRDD<ClassDayCSClassKey, ClassDayCSValue> classDayCSLowAndHighReduce = classDayCSLowAndHighMap.reduceByKey(new Function2<ClassDayCSValue, ClassDayCSValue, ClassDayCSValue>() {
            @Override
            public ClassDayCSValue call(ClassDayCSValue classDayCSValue, ClassDayCSValue classDayCSValue2) throws Exception {
                return new ClassDayCSValue(classDayCSValue.getMoney(),
                        classDayCSValue.getCount(),
                        classDayCSValue.getAverage(),
                        classDayCSValue.getLow() + classDayCSValue2.getLow(),
                        classDayCSValue.getHigh() + classDayCSValue2.getHigh());
            }
        });
        //求出高于低于人均消费次数
        JavaPairRDD<ClassDayCSClassKey, Tuple2<ClassDayCSValue, ClassDayCSValue>> studentDayCSLowAndHigh = classDayCSStudentMap.join(classDayCSStudentReduce);
        JavaPairRDD<ClassDayCSClassKey,ClassDayCSValue> studentDayCSLowAndHighMap = studentDayCSLowAndHigh.mapToPair(new PairFunction<Tuple2<ClassDayCSClassKey, Tuple2<ClassDayCSValue, ClassDayCSValue>>, ClassDayCSClassKey, ClassDayCSValue>() {
            @Override
            public Tuple2<ClassDayCSClassKey, ClassDayCSValue> call(Tuple2<ClassDayCSClassKey, Tuple2<ClassDayCSValue, ClassDayCSValue>> classDayCSClassKeyTuple2Tuple2) throws Exception {
                int low = 0, high = 0;
                if (classDayCSClassKeyTuple2Tuple2._2()._1().getMoney() < classDayCSClassKeyTuple2Tuple2._2()._2().getAverage()) {
                    low = 1;
                } else {
                    high = 1;
                }
                return new Tuple2<>(classDayCSClassKeyTuple2Tuple2._1(),
                        new ClassDayCSValue(classDayCSClassKeyTuple2Tuple2._2()._2().getMoney(),
                                classDayCSClassKeyTuple2Tuple2._2()._2().getCount(),
                                classDayCSClassKeyTuple2Tuple2._2()._2().getAverage(),
                                low, high));

            }
        });
        JavaPairRDD<ClassDayCSClassKey, ClassDayCSValue> studentDayCSLowAndHighReduce = studentDayCSLowAndHighMap.reduceByKey(new Function2<ClassDayCSValue, ClassDayCSValue, ClassDayCSValue>() {
            @Override
            public ClassDayCSValue call(ClassDayCSValue classDayCSValue, ClassDayCSValue classDayCSValue2) throws Exception {
                return new ClassDayCSValue(classDayCSValue.getMoney(),
                        classDayCSValue.getCount(),
                        classDayCSValue.getAverage(),
                        classDayCSValue.getLow() + classDayCSValue2.getLow(),
                        classDayCSValue.getHigh() + classDayCSValue2.getHigh());
            }
        });
        //合并结果
        JavaPairRDD<ClassDayCSClassKey, Tuple2<ClassDayCSValue, ClassDayCSValue>> result = classDayCSLowAndHighReduce.join(studentDayCSLowAndHighReduce);
        JavaRDD<ClassDayCSResult> resultData = result.map(new Function<Tuple2<ClassDayCSClassKey, Tuple2<ClassDayCSValue, ClassDayCSValue>>, ClassDayCSResult>() {
            @Override
            public ClassDayCSResult call(Tuple2<ClassDayCSClassKey, Tuple2<ClassDayCSValue, ClassDayCSValue>> classDayCSClassKeyTuple2Tuple2) throws Exception {
                String date = classDayCSClassKeyTuple2Tuple2._1().getYear()+"-"+classDayCSClassKeyTuple2Tuple2._1().getMonth()+"-"+classDayCSClassKeyTuple2Tuple2._1().getDay();
                return new ClassDayCSResult(
                        classDayCSClassKeyTuple2Tuple2._1().getClass_id(),
                        Date.valueOf(date),
                        classDayCSClassKeyTuple2Tuple2._2()._1().getCount(),
                        classDayCSClassKeyTuple2Tuple2._2()._1().getMoney(),
                        classDayCSClassKeyTuple2Tuple2._2()._1().getAverage(),
                        classDayCSClassKeyTuple2Tuple2._2()._2().getAverage(),
                        classDayCSClassKeyTuple2Tuple2._2()._2().getCount(),
                        classDayCSClassKeyTuple2Tuple2._2()._1().getLow(),
                        classDayCSClassKeyTuple2Tuple2._2()._1().getHigh(),
                        classDayCSClassKeyTuple2Tuple2._2()._2().getLow(),
                        classDayCSClassKeyTuple2Tuple2._2()._2().getHigh()
                );
            }
        });
        Dataset<ClassDayCSResult> data =  sparkSession.createDataset(resultData.rdd(),Encoders.bean(ClassDayCSResult.class));
        data.write().format("jdbc")
                .option("url", StaticConstant.jdbcUrl)
                .option("user", StaticConstant.jdbcUser)
                .option("password", StaticConstant.jdbcPassword)
                .option("driver", StaticConstant.jdbcDriver)
                .option("dbtable", "graduate.class_day_consumption_statistics")
                .save();
        sparkSession.stop();
    }
}
