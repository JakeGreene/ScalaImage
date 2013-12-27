package ca.jakegreene.algebra.util

import breeze.linalg.Matrix

/**
 * An Immutable view of a Matrix
 */
trait MatrixView[T <: AnyVal] {
  val delegate: Matrix[T]
  def apply(row: Int, column: Int): T
}

/**
 * A view of a Matrix where the values are first 
 * transformed using the provided lens
 */
class TransformedMatrixView[T <: AnyVal](val delegate: Matrix[T], lens: T => T) extends MatrixView[T] {
  def apply(row: Int, column: Int) = lens(delegate(row, column))
}

