package helper

import breeze.linalg.DenseMatrix

class Preprocessor {
  def create_matrix(data: Array[Array[Double]]): DenseMatrix[Double] = {
    val dataMatrix = DenseMatrix(data: _*)
    val onesVector = DenseMatrix.ones[Double](dataMatrix.rows, 1)
    DenseMatrix.horzcat(onesVector, dataMatrix)
  }
}
