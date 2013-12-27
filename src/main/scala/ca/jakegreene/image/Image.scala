package ca.jakegreene.image

import breeze.linalg.Matrix

object Image {
  case class RGBA(red: Int, green: Int, blue: Int, alpha: Int)
  
  sealed trait ColourComponent { def position: Int }
  case object Red extends ColourComponent { def position = 0 }
  case object Green extends ColourComponent { def position = 1 }
  case object Blue extends ColourComponent { def position = 2 }
  case object Alpha extends ColourComponent { def position = 3 }
  
  private val componentMask = 0xff
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
    return (rgba >> shift) & componentMask
  }
  
  private val notRedMask = (componentMask << 16) | (componentMask << 8) | componentMask
  private val notGreenMask  = (componentMask << 24) | (componentMask << 8) | componentMask
  private val notBlueMask = (componentMask << 24) | (componentMask << 16) | componentMask
  private val notAlphaMask = (componentMask << 24) | (componentMask << 16) | (componentMask << 8)
  private[image] def change(component: ColourComponent)(rgba:Int, value: Int): Int = {
    val mask = component match {
      case Red => notRedMask
      case Green => notGreenMask
      case Blue => notBlueMask
      case Alpha => notAlphaMask
    }
    val previousComponents = rgba & mask
    val shift = (numComponents - component.position - 1) * componentSize
    return (value << shift) + previousComponents 
  }
  
  private[image] def colour(rgba: Int): RGBA = {
    val red = extract(Red)(rgba)
    val green = extract(Green)(rgba)
    val blue = extract(Blue)(rgba)
    val alpha = extract(Alpha)(rgba)
    return RGBA(red, green, blue, alpha)
  }
  
  private[image] def colour(rgba: RGBA): Int = {
    return (rgba.red << 24) | (rgba.green << 16) | (rgba.blue << 8) | rgba.alpha
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