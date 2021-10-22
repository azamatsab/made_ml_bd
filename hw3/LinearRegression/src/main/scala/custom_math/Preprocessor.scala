package custom_math

import breeze.linalg.DenseMatrix

class Preprocessor {
  def create_matrix(data: Array[Array[Double]])= {
    val dataMatrix = DenseMatrix(data:_*)
    val onesVector = DenseMatrix.ones[Double](dataMatrix.rows, 1)
    DenseMatrix.horzcat(onesVector, dataMatrix)
  }
}
