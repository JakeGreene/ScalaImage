package ca.jakegreene.image

import breeze.linalg.Matrix

object Image {
  case class RGBA(red: Int, green: Int, blue: Int, alpha: Int)
  
  case class ColourComponent(position: Int)
  val Red = ColourComponent(0)
  val Green = ColourComponent(1)
  val Blue = ColourComponent(2)
  val Alpha = ColourComponent(3)
  
  private val mask = 0xff
  // 0xffffffff
  private val fullMask = ((1l << (componentSize*numComponents)) - 1)
  private val componentSize = 8
  private val numComponents = 4
  /*
   * Extract the component at position from
   * the provided rgba colour
   */
  private[image] def extract(component: ColourComponent)(rgba: Int): Int = {
    val shift = (numComponents - component.position - 1) * componentSize
    return (rgba >> shift) & mask
  }
}

trait Image {
  import Image._ 
  
  def width: Int
  def height: Int
  
  def apply(row: Int, column: Int): RGBA
  
  /**
   * Access the red value for the image
   * at (row, column)
   */
  def red(row: Int, column: Int): Int
  
  /**
   * Access the green value for the image
   * at (row, column)
   */
  def green(row: Int, column: Int): Int
  
  /**
   * Access the blue value for the image
   * at (row, column)
   */
  def blue(row: Int, column: Int): Int
  
  /**
   * Access the alpha value for the image
   * at (row, column)
   */
  def alpha(row: Int, column: Int): Int
 
}

class DenseImage(imageData: ColourMatrix) extends Image {
  import Image._
  
  def height = imageData.rows
  def width = imageData.cols
  def apply(row: Int, column: Int): RGBA = {
    val r = red(row, column)
    val g = green(row, column)
    val b = blue(row, column)
    val a = alpha(row, column)
    return RGBA(r, g, b, a)
  }
  def red(row: Int, column: Int) = extract(Red)(imageData(row, column))
  def green(row: Int, column: Int) = extract(Green)(imageData(row, column))
  def blue(row: Int, column: Int) = extract(Blue)(imageData(row, column))
  def alpha(row: Int, column: Int) = extract(Alpha)(imageData(row, column))
}