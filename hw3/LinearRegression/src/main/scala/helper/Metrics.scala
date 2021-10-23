package helper

import breeze.linalg.Vector
import breeze.numerics.{abs, sqrt}
import breeze.stats.meanAndVariance

class Metrics {
  def mae(predictions: Vector[Double], targets: Vector[Double]): Double = {
    meanAndVariance(abs(predictions - targets)).mean
  }

  def rMse(predictions: Vector[Double], targets: Vector[Double]): Double = {
    sqrt(this.mse(predictions, targets))
  }

  def mse(predictions: Vector[Double], targets: Vector[Double]) : Double= {
    val diff = predictions - targets
    meanAndVariance(diff * diff).mean
  }
}
