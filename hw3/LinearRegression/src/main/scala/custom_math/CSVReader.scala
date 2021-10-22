package custom_math

class CSVReader {
  def readCSV(path: String) : Array[Array[Double]] = {
    io.Source.fromFile(path)
      .getLines().drop(1)
      .map(_.split(",").map(_.trim.toDouble))
      .toArray
  }
}
