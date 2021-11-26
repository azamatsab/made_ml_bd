package ru.made

import org.apache.spark.sql._
import org.apache.spark.sql.functions._


object Main {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .master("local[*]")
      .appName("hw4")
      .getOrCreate()

    import spark.implicits._

    val df = spark.read
      .option("header", "true")
      .option("inferSchema", "true")
      .csv("data/tripadvisor_hotel_reviews.csv")

    val docCount = df.count()

    // User-defined functions
    val clean: String => String = _.replaceAll("""[\p{Punct}]""", "").trim
    val cleanUDF = udf(clean)

    def calcWords(text: String): Long = {
      text.split(" ").length
    }
    val calcWordsUdf = udf {text => calcWords(text)}

    def calcIdf(docCount: Long, df: Long): Double = {
      math.log10((docCount + 1) / (df + 1))
    }
    val calcIdfUdf = udf {df: Long => calcIdf(docCount, df)}

    // Привести все к одному регистру
    val dfLower = df.select($"Review", lower($"Review"))

    // Удалить все спецсимволы
    val dfClean = dfLower.withColumn("CleanText", cleanUDF('Review)).select("CleanText")

    // Посчитать частоту слова в предложении
    val textLenCount = dfClean.withColumn("doc_id", monotonically_increasing_id())
      .withColumn("doc_length", calcWordsUdf(col("CleanText")))
    val columns = textLenCount.columns.map(col) :+
      (explode(split(col("CleanText"), " ")) as "token")
    val flattened = textLenCount.select(columns: _*)

    val tfCount = flattened.groupBy("doc_id", "token", "doc_length")
      .agg(count("CleanText") / col("doc_length") as "tf")

    // Посчитать количество документов со словом
    val dfCount = flattened.groupBy("token")
      .agg(countDistinct("doc_id") as "df")

    // Посчитать idf
    val idfCount = dfCount.withColumn("idf", calcIdfUdf(col("df")))

    // Взять только 100 самых встречаемых.
    val tokenCount = flattened.groupBy("token")
      .agg(count("CleanText") as "count").orderBy(desc("count")).limit(100)
    val idfCountLim = tokenCount.join(idfCount, Seq("token"), "left")

    // Сджойнить две полученные таблички и посчитать Tf-Idf (только для слов из предыдущего пункта)
    val tfIdf = idfCountLim
      .join(tfCount, Seq("token"), "left")
      .withColumn("tf_idf", col("tf") * col("idf")).select("token", "tf_idf", "doc_id")
      .groupBy("doc_id").pivot("token").sum("tf_idf").orderBy("doc_id").na.fill(0)
    tfIdf.show
  }
}