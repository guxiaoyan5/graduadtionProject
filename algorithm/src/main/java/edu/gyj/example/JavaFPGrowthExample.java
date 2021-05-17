package edu.gyj.example;


import org.apache.spark.ml.fpm.FPGrowth;
import org.apache.spark.ml.fpm.FPGrowthModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.*;

import java.util.Arrays;
import java.util.List;

public class JavaFPGrowthExample {
    public static void main(String[] args) {
        SparkSession sparkSession = SparkSession
                .builder()
                .appName("javaFPGrowthExample")
                .master("local[2]")
                .getOrCreate();
        List<Row> data = Arrays.asList(
                RowFactory.create(Arrays.asList("1 2 5".split(" "))),
                RowFactory.create(Arrays.asList("1 2 3 5".split(" "))),
                RowFactory.create(Arrays.asList("1 2".split(" ")))
        );
        StructType structType = new StructType(new StructField[]{new StructField(
                "items", new ArrayType(DataTypes.StringType, true), false, Metadata.empty()
        )});
        Dataset<Row> itemsDF = sparkSession.createDataFrame(data, structType);
        FPGrowthModel model = new FPGrowth()
                .setItemsCol("items")
                .setMinSupport(0.5)
                .setMinConfidence(0.6)
                .fit(itemsDF);
        model.freqItemsets().show();
        model.associationRules().show();
        model.transform(itemsDF).show();
        sparkSession.stop();
    }
}
