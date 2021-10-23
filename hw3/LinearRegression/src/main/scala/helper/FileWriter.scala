package helper

import scala.util.Using, java.io.{PrintWriter, File}

class FileWriter {
  def write(path: String, data: Iterable[String]): Unit = {
    Using(new PrintWriter(new File(path))) {
      writer => data.foreach(writer.println)
    }
  }
}
