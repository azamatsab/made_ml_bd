package custom_math

import breeze.linalg._

class LinRegression {
  var weights = Vector[Double](0)

  def fit(xMatrix: DenseMatrix[Double], yVector: Vector[Double]) = {
    val dotXx = xMatrix.t * xMatrix
    val dotXy = xMatrix.t * yVector
    val invDotXx = pinv(dotXx)
    this.weights = invDotXx * dotXy
  }

  def predict(xMatrix: DenseMatrix[Double]) = {
    xMatrix * this.weights
  }
}
