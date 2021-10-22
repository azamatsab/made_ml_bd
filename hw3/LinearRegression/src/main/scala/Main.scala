import custom_math.LinRegression
import custom_math.CSVReader
import custom_math.DataSplitter
import custom_math.Metrics
import custom_math.Preprocessor

object Main {
  val DataPath = "Real estate.csv"
  val reader = new CSVReader
  val preprocessor = new Preprocessor
  val splitter = new DataSplitter

  val data = reader.readCSV(DataPath)
  val dataMatrix = preprocessor.create_matrix(data)
  val (xTrain, yTrain, xVal, yVal) = splitter.split(dataMatrix, 100)

  val model = new LinRegression
  val metrics = new Metrics

  def main(args: Array[String]): Unit = {
    model.fit(xTrain, yTrain)
    val predictions = model.predict(xVal)
    println("MAE score on validation: " + metrics.mae(predictions, yVal))
    println("RMSE score on validation: " + metrics.rMse(predictions, yVal))
  }
}
