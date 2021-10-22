package custom_math

import breeze.linalg.Vector
import breeze.numerics.{abs, sqrt}
import breeze.stats.meanAndVariance

class Metrics {
  def mae(predictions: Vector[Double], targets: Vector[Double]) = {
    meanAndVariance(abs(predictions - targets)).mean
  }

  def mse(predictions: Vector[Double], targets: Vector[Double]) = {
    val diff = predictions - targets
    meanAndVariance(diff * diff).mean
  }

  def rMse(predictions: Vector[Double], targets: Vector[Double]) = {
    sqrt(this.mse(predictions, targets))
  }
}
