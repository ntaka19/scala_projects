package ex08

import scala.math.{abs,min,max}

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry._
import scalafx.scene.{Group,Node,Scene}
import scalafx.scene.canvas.{Canvas,GraphicsContext}
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.{BorderPane}
import scalafx.scene.paint.Color

/**
 * マンデルブロ顕微鏡の実装例
 *
 * 二つの複素数 -2+2i と 2+2i を対角頂点とする矩形複素領域について描画したのちに，ユーザがマウスドラッグによって指定する領域を拡大表示する．
 **/
object Mandelbrot extends JFXApp {
  /**
   * (w, h): 描画領域の幅と高さ
   **/
  val w = 500; val h = 500
  /**
   * 描画領域
   **/
  val canvas = new Canvas(w, h)

  /**
   * region: 描画中の複素平面上の矩形領域を表す対角頂点対
   **/
  var region: Array[Complex] = Array(new Complex(-2, -2), new Complex(2, 2))

  /**
   * complex 関数
   *
   * (x, y): (Double, Double) - 描画領域上の位置
   *
   * マウスで指定されたキャンバス上の座標(x, y)に相当する複素数を与える．
   **/
   /*
  def complex(x: Double, y: Double) = {
    val c1 = region(0); val c2 = region(1)
    new Complex((c1.re * (w - x) + c2.re * x) / w,
                (c1.im * (h - y) + c2.im * y) / h)
  }
  */


  def complex(x: Double, y:Double, c:Complex)=
  {
  	val c1 = region(0); val c2 = region(1)
  	c.re = (c1.re * (w - x) + c2.re * x) / w
	c.im = (c1.im * (h - y) + c2.im * y) / h
  }

  def theTitle() = f"Mandelbrot's microscope (${region(0).re}%f,${region(0).im}%f)-(${region(1).re}%f,${region(1).im}%f)"

  stage = new PrimaryStage {
    title = theTitle()
    scene = new Scene {
      root = new BorderPane { center = canvas }
    }
  }
 
var m = new Complex(0,0)
var z = new Complex(0,0)
var c = new Complex(0,0)
val colors: Array[Color] = Array.tabulate(256){i=> Color.hsb(30,1,i/256.0)}

  def color(x: Int, y: Int): Color = {
    complex(x, y,c)
    var n = 0
  	z.set(0,0)
    while (n < 255 && z.abs <= 2) {

    Complex.times(z,z,m) 
    Complex.plus(m,c,z)
      /*z = z*z + c*/ 
      n = n + 1
      
    }
    /*Color.hsb(30, 1, n / 256.0)*/
    colors(n)
  }


  draw(canvas.graphicsContext2D)

  def draw(gc: GraphicsContext) {
    println(f"Drawing ${region(0)}-${region(1)}")
    gc.clearRect(0, 0, w-1, h-1)
    for (x <- Range(0, w-1)) {
      for (y <- Range(0, h-1)) {
        gc.stroke = color(x, y)
        gc.strokeLine(x, y, x, y)
      }
    }
    stage.title = theTitle()
  }
}
