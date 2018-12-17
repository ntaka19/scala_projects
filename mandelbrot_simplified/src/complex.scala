package ex08

import scala.math._

class Complex(_re: Double, _im: Double) {
  var re = _re
  var im = _im

  // クラスの引数(_re, _im)を利用して定義した例
  def plus(c: Complex) = new Complex(_re + c.re, _im + c.im)

  // re, im関数を利用して定義した例
  def minus(c: Complex) = new Complex(re - c.re, im - c.im)

  def neg = new Complex(-re, -im)

  def abs = sqrt(re*re + im*im)
  
  override def toString(): String = if (im >= 0) f"($re%f+$im%fi)" else f"($re%f${im}%fi)"

  def +(c: Complex) = plus(c)
  def +(x: Double)  = new Complex(re + x, im)

  def -(c: Complex) = minus(c)
  def -(x: Double)  = new Complex(re - x, im)

  def ==(c: Complex) = re == c.re && im == c.im
  def ==(x: Double): Boolean = this==(new Complex(x, 0))

  def unary_- = neg

  def *(c: Complex) = new Complex(re * (c.re) - im * (c.im), re * (c.im) + im * (c.re))
  def *(x: Double)  = new Complex(re * x , im * x)

  def set(_re:Double, _im:Double){
    re = _re
    im = _im
  }
}

object Complex {
  def fromPolar(r: Double, theta: Double) = new Complex(r * cos(theta), r * sin(theta))

  def plus(c1:Complex,c2:Complex,c3:Complex){
    c3.re = c1.re + c2.re
    c3.im = c1.im + c2.im
  }
  

  def minus(c1:Complex,c2:Complex,c3:Complex){
    c3.re = c1.re + c2.re
    c3.im = c1.im + c2.im
  }

  def times(c1:Complex,c2:Complex,c3:Complex){
    c3.re = c1.re * c2.re - c1.im * c2.im
    c3.im = c1.im * c2.re + c1.re * c2.im
  }

  def neg(c1:Complex,c2:Complex){
    c2.re = -1 * c1.re
    c2.im = -1 * c2.re
  }
}


