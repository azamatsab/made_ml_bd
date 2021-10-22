package custom_math

import breeze.linalg._


class DataSplitter{
  def split(dataMatrix: DenseMatrix[Double], valSize: Int) = {
      val target = dataMatrix(::, -1)
      val xData = dataMatrix(::, Range(0, dataMatrix.cols - 1))

      val xTrain = xData(0 to xData.rows - valSize, ::)
      val xVal = xData(xData.rows - valSize to xData.rows - 1, ::)
      val yTrain = target(0 to xData.rows - valSize)
      val yVal = target(xData.rows - valSize to xData.rows - 1)

      (xTrain, yTrain, xVal, yVal)
    }
}
