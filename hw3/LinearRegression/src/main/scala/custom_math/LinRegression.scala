package custom_math

import breeze.linalg._

class LinRegression {
  var weights = Vector[Double](1, 1)

  def fit(xMatrix: DenseMatrix[Double], yVector: Vector[Double]) = {
    val dotXx = xMatrix.t * xMatrix
    val dotXy = xMatrix.t * yVector
    val part1 = pinv(dotXx)
    this.weights = part1 * dotXy
  }

  def predict(xMatrix: DenseMatrix[Double]) = {
    xMatrix * this.weights
  }
}
