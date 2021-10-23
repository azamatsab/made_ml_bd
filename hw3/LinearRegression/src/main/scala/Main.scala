import helper.{CSVReader, DataSplitter, FileWriter, LinRegression, Metrics, Preprocessor}

object Main {
  val reader = new CSVReader
  val preprocessor = new Preprocessor
  val splitter = new DataSplitter
  val writer = new FileWriter
  val model = new LinRegression
  val metrics = new Metrics

  val ValPath = "validation.txt"
  val TestPath = "testPreds.txt"

  def main(args: Array[String]): Unit = {
    val data = reader.readCSV(args(0), drop=1)
    val testData = reader.readCSV(args(1), drop=0)
    val dataMatrix = preprocessor.create_matrix(data)
    val testMatrix = preprocessor.create_matrix(testData)
    val (xTrain, yTrain, xVal, yVal) = splitter.split(dataMatrix, 0.1)

    model.fit(xTrain, yTrain)
    val predictions = model.predict(xVal)
    val val_info = Array[String]("MAE score on validation: " + metrics.mae(predictions, yVal),
                                "RMSE score on validation: " + metrics.rMse(predictions, yVal))
    writer.write(ValPath, val_info)

    val testPreds = model.predict(testMatrix)
    val testStrPreds = testPreds.map(x => x.toString)
    writer.write(TestPath, testStrPreds.toArray)
  }
}
