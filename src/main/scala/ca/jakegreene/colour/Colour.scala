package ca.jakegreene.colour

object Colour {
  case class RGBA(red: Int, green: Int, blue: Int, alpha: Int)
  
  sealed trait Component { def position: Int }
  case object Red extends Component { def position = 0 }
  case object Green extends Component { def position = 1 }
  case object Blue extends Component { def position = 2 }
  case object Alpha extends Component { def position = 3 }
  
  private val componentMask = 0xff
  // 0xffffffff
  private val fullMask = ((1l << (componentSize*numComponents)) - 1)
  private val componentSize = 8
  private val numComponents = 4
  /*
   * Extract the component at position from
   * the provided rgba colour
   */
  def extract(component: Component)(rgba: Int): Int = {
    val shift = (numComponents - component.position - 1) * componentSize
    return (rgba >> shift) & componentMask
  }
  
  private val notRedMask = (componentMask << 16) | (componentMask << 8) | componentMask
  private val notGreenMask  = (componentMask << 24) | (componentMask << 8) | componentMask
  private val notBlueMask = (componentMask << 24) | (componentMask << 16) | componentMask
  private val notAlphaMask = (componentMask << 24) | (componentMask << 16) | (componentMask << 8)
  def change(component: Component)(rgba:Int, value: Int): Int = {
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
  
  def toRGBA(rgba: Int): RGBA = {
    val red = extract(Red)(rgba)
    val green = extract(Green)(rgba)
    val blue = extract(Blue)(rgba)
    val alpha = extract(Alpha)(rgba)
    return RGBA(red, green, blue, alpha)
  }
  
  def toInt(rgba: RGBA): Int = {
    return (rgba.red << 24) | (rgba.green << 16) | (rgba.blue << 8) | rgba.alpha
  }
}