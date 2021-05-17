package edu.gyj.example;

import org.apache.spark.ml.clustering.KMeans;
import org.apache.spark.ml.clustering.KMeansModel;
import org.apache.spark.ml.evaluation.ClusteringEvaluator;
import org.apache.spark.ml.linalg.Vector;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;


public class JavaKMeansExample {
    public static void main(String[] args) {
        SparkSession sparkSession = SparkSession.builder().master("local[2]")
                .appName("KMeansExample").getOrCreate();
        Dataset<Row> dataset = sparkSession.read().format("libsvm")
                .load("src/main/resources/kmeans_data.txt");
        KMeans kMeans = new KMeans().setSeed(1L).setK(2);
        KMeansModel kMeansModel = kMeans.fit(dataset);
        Dataset<Row> predictions = kMeansModel.transform(dataset);
        ClusteringEvaluator clusteringEvaluator = new ClusteringEvaluator();
        double silhouette = clusteringEvaluator.evaluate(predictions);
        System.out.println("Silhouette with squared euclidean distance = " + silhouette);
        Vector[] centers = kMeansModel.clusterCenters();
        System.out.println("Cluster Centers: ");
        for (Vector center : centers) {
            System.out.println(center);
        }
        // $example off$
        sparkSession.stop();
    }
}
