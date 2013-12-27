package ca.jakegreene

import breeze.linalg.Matrix
package object image {
  /**
   * Can use an Int to compactly store the 
   * RGBA data of a colour. 
   */
  type ColourMatrix = Matrix[Int]
}