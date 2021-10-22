package custom_math

import breeze.linalg._


class DataSplitter{
  def split(dataMatrix: DenseMatrix[Double], valSize: Int) = {
      val target = dataMatrix(::, -1)
      val xData = dataMatrix(::, Range(0, dataMatrix.cols - 1))
      val rows = xData.rows
      val trainSize = rows - valSize
      val xTrain = xData(0 to trainSize, ::)
      val xVal = xData(trainSize to rows - 1, ::)
      val yTrain = target(0 to trainSize)
      val yVal = target(trainSize to rows - 1)

      (xTrain, yTrain, xVal, yVal)
    }
}
