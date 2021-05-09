package edu.gyj.run;

import edu.gyj.bean.student.StudentMonthCS;
import edu.gyj.bean.student.StudentToTalCS;
import edu.gyj.bean.student.StudentTotalMoneyValue;
import edu.gyj.util.StaticConstant;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.ml.clustering.KMeans;
import org.apache.spark.ml.clustering.KMeansModel;
import org.apache.spark.ml.evaluation.ClusteringEvaluator;
import org.apache.spark.ml.feature.Normalizer;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.ml.linalg.Vector;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

import java.io.IOException;

public class PoorStudentAnalysis {
    public static void main(String[] args) throws IOException {
        SparkSession sparkSession = SparkSession.builder().appName("StudentAnalysis").master("local[2]").getOrCreate();
        Dataset<StudentMonthCS> studentMonthCSDataset = sparkSession.read().format("jdbc")
                .option("url", StaticConstant.jdbcUrl)
                .option("user", StaticConstant.jdbcUser)
                .option("password", StaticConstant.jdbcPassword)
                .option("driver", StaticConstant.jdbcDriver)
                .option("query", "select sid,month,year,consumption_count,consumption_total_money,consumption_average_money from student_month_data")
                .load().as(Encoders.bean(StudentMonthCS.class));
        JavaRDD<StudentMonthCS> studentMonthCSJavaRDD = studentMonthCSDataset.toJavaRDD();
        JavaPairRDD<String, StudentTotalMoneyValue> studentTotalMoneyValueJavaPairRDD = studentMonthCSJavaRDD.mapToPair(new PairFunction<StudentMonthCS, String, StudentTotalMoneyValue>() {
            @Override
            public Tuple2<String, StudentTotalMoneyValue> call(StudentMonthCS studentMonthCS) throws Exception {
                return new Tuple2<>(studentMonthCS.getSid(),
                        new StudentTotalMoneyValue(
                                studentMonthCS.getConsumption_count(),
                                studentMonthCS.getConsumption_total_money(),
                                studentMonthCS.getConsumption_average_money()
                        )
                );
            }
        });
        JavaPairRDD<String, StudentTotalMoneyValue> studentTotalMoney = studentTotalMoneyValueJavaPairRDD.reduceByKey(new Function2<StudentTotalMoneyValue, StudentTotalMoneyValue, StudentTotalMoneyValue>() {
            @Override
            public StudentTotalMoneyValue call(StudentTotalMoneyValue studentTotalMoneyValue, StudentTotalMoneyValue studentTotalMoneyValue2) throws Exception {
                int count = studentTotalMoneyValue.getConsumption_count() + studentTotalMoneyValue2.getConsumption_count();
                double totalMoney = studentTotalMoneyValue.getConsumption_total_money() + studentTotalMoneyValue2.getConsumption_total_money();
                double average = totalMoney / count;
                return new StudentTotalMoneyValue(count, totalMoney, average);
            }
        });
        JavaRDD<StudentToTalCS> studentMoneyInfo = studentTotalMoney.map(new Function<Tuple2<String, StudentTotalMoneyValue>, StudentToTalCS>() {
            @Override
            public StudentToTalCS call(Tuple2<String, StudentTotalMoneyValue> stringStudentTotalMoneyValueTuple2) throws Exception {
                return new StudentToTalCS(stringStudentTotalMoneyValueTuple2._1(),
                        stringStudentTotalMoneyValueTuple2._2().getConsumption_count(),
                        stringStudentTotalMoneyValueTuple2._2().getConsumption_total_money(),
                        stringStudentTotalMoneyValueTuple2._2().getConsumption_average_money());
            }
        });
        Dataset<StudentToTalCS> studentMoneyInfoRow = sparkSession.createDataFrame(studentMoneyInfo, StudentToTalCS.class).as(Encoders.bean(StudentToTalCS.class));
        Normalizer normalizer = new Normalizer()
                .setInputCol("features")
                .setOutputCol("features_norm")
                .setP(1.0);
        Dataset<Row> vectorAssembler = new VectorAssembler()
                .setInputCols(new String[]{"consumption_total_money", "consumption_count"}).setOutputCol("features")
                .transform(studentMoneyInfoRow);
        Dataset<Row> l1NormData = normalizer.transform(vectorAssembler);
        KMeans kMeans = new KMeans().setK(5).setSeed(1L)
                .setFeaturesCol("features_norm");
        KMeansModel kMeansModel = kMeans.fit(l1NormData);
        Dataset<Row> predictions = kMeansModel.transform(l1NormData);
        ClusteringEvaluator clusteringEvaluator = new ClusteringEvaluator();
        double silhouette = clusteringEvaluator.evaluate(predictions);
        System.out.println("Silhouette with squared euclidean distance = " + silhouette);
        Vector[] centers = kMeansModel.clusterCenters();
        System.out.println("Cluster Centers: ");
        for (Vector center : centers) {
            System.out.println(center);
        }
        predictions.show();
        predictions.select("sid","consumption_average_money","consumption_count","consumption_total_money","prediction").write().format("jdbc")
                .option("url", StaticConstant.jdbcUrl)
                .option("user", StaticConstant.jdbcUser)
                .option("password", StaticConstant.jdbcPassword)
                .option("driver", StaticConstant.jdbcDriver)
                .option("dbtable", "graduate.analysisnorm")
                .save();
        sparkSession.stop();
    }
}
