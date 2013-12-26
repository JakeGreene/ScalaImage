package ca.jakegreene.image

import breeze.linalg.Matrix

trait Image {
  
  case class RGBA(red: Int, green: Int, blue: Int, alpha: Int)
  
  def width: Int
  def height: Int
  
  def apply(x: Int, y: Int): RGBA
  
  /**
   * Access all of the red values for the image
   */
  def red: Matrix[Int]
  
  /**
   * Access the red value of the pixel at (x, y)
   */
  def red(x: Int, y: Int): Int
  
  /**
   * Access all of the green values for the image
   */
  def green: Matrix[Int]
  
  /**
   * Access the green value of the pixel (x, y)
   */
  def green(x: Int, y: Int): Int
  
  /**
   * Access all of the blue values for the image
   */
  def blue: Matrix[Int]
  
  /**
   * Access the blue value of the pixel (x, y)
   */
  def blue(x: Int, y: Int): Int
  
  /**
   * Access all of the alpha values for the image
   */
  def alpha: Matrix[Int]
  
  /**
   * Access the alpha value for the pixel at (x, y)
   */
  def alpha(x: Int, y: Int): Int
 
}