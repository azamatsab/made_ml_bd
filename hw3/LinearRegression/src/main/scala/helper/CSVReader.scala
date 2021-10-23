package helper

class CSVReader {
  def readCSV(path: String, drop: Int): Array[Array[Double]] = {
    io.Source.fromFile(path)
      .getLines().drop(drop)
      .map(_.split(",").map(_.trim.toDouble))
      .toArray
  }
}
