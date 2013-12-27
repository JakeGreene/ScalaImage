package ca.jakegreene.image

import breeze.linalg.Matrix
import ca.jakegreene.algebra.util.MatrixView
import ca.jakegreene.algebra.util.TransformedMatrixView
import ca.jakegreene.colour.Colour
import ca.jakegreene.colour.Colour.RGBA
import ca.jakegreene.colour.Colour.Red
import ca.jakegreene.colour.Colour.Green
import ca.jakegreene.colour.Colour.Blue
import ca.jakegreene.colour.Colour.Alpha

trait Image {
  
  def width: Int
  def height: Int
  
  def apply(row: Int, column: Int): RGBA
  
  /**
   * Access the red values of the image
   */
  def red: MatrixView[Int]
  
  /**
   * Access the green values of the image
   */
  def green: MatrixView[Int]
  
  /**
   * Access the blue values of the image
   */
  def blue: MatrixView[Int]
  
  /**
   * Access the alpha values of the image
   */
  def alpha: MatrixView[Int]
 
}

class DenseImage(imageData: ColourMatrix) extends Image {
  
  def height = imageData.rows
  def width = imageData.cols
  def apply(row: Int, column: Int): RGBA = Colour.toRGBA(imageData(row, column))
  
  private val redView = new TransformedMatrixView(imageData, Colour.extract(Red))
  def red = redView
  
  private val greenView = new TransformedMatrixView(imageData, Colour.extract(Green))
  def green = greenView
  
  private val blueView = new TransformedMatrixView(imageData, Colour.extract(Blue))
  def blue = blueView
  
  private val alphaView = new TransformedMatrixView(imageData, Colour.extract(Alpha))
  def alpha = alphaView
}