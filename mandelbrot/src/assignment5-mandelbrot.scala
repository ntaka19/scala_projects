package cs1.assignment5

import scala.math.{abs,min,max}

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry._
import scalafx.scene.{Group,Node,Scene}
import scalafx.scene.canvas.{Canvas,GraphicsContext}
import scalafx.scene.control.Button
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.{BorderPane,HBox,Priority,VBox}
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color._

// import Complex

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
  var backlist: List[(Complex, Complex)] = List()
  var forlist: List[(Complex, Complex)] = List()
  /**
   * complex 関数
   *
   * (x, y): (Double, Double) - 描画領域上の位置
   *
   * マウスで指定されたキャンバス上の座標(x, y)に相当する複素数を与える．
   **/
  def complex(x: Double, y: Double) = {
    val c1 = region(0); val c2 = region(1)
    new Complex((c1.re * (w - x) + c2.re * x) / w,
                (c1.im * (h - y) + c2.im * y) / h)
  }
  /**
   * ドラッグを開始した点が表す複素数の値
   **/
  var p1: Complex = new Complex(0, 0)
  /**
   * canvas.onMousePressed メソッド: ドラッグを開始したときに自動的に呼び出される
   *
   * ドラッグを開始した座標(e.x, e.y)に該当する複素数を求め，p1 に保存する．
   **/
  canvas.onMousePressed  = { (e: MouseEvent) =>
    p1 = complex(e.x, e.y)
  }

  /**
   * canvas.onMouseReleased メソッド: ドラッグを終えたときに自動的に呼び出される
   *
   * ドラッグを終えた座標(e.x, e.y)に該当する複素数(p2)を得，p1 とあわせて次の描画領域(region)を設定し，再描画する．描画領域の設定にあたっては，領域の上下左右が反転しないこと，縦横比が揃うことに注意を払っている．
   **/
   /* */
  canvas.onMouseReleased = { (e: MouseEvent) =>
    val p2 = complex(e.x, e.y)
    val size = max(abs(p2.re - p1.re), abs(p2.im - p1.im))
    backlist = (region(0),region(1))::backlist
    forlist = List()

    region(0) = new Complex(min(p1.re, p2.re), min(p1.im, p2.im))
    region(1) = new Complex(region(0).re + size, region(0).im + size)

    
    draw(canvas.graphicsContext2D)
  }

type Pair = (Complex,Complex)

 def pairtoarray(p: Pair): Array[Complex] = {
  	p match{ case (p1,p2) => Array(p1, p2)}
  }

def arraytopair(a: Array[Complex]): Pair = {
  	a match{ case Array(p1,p2) => (p1, p2)}
  }

def backupdate(lst:List[Pair]):Pair ={
	lst match {
		case b0::rest => b0
	}
}

def forupdate(lst:List[Pair]):Pair ={
	lst match {
		case f0::rest => f0
	}
}


/*改良中のものに対応するupdate
  def backupdate(backlist:List[(Complex,Complex)],current:Array[Complex], forlist:List[(Complex,Complex)]): (Complex,Complex) = {
    backlist match {
      case Nil =>  (backlist, current, forlist)     
      case b0::rest => (rest,b0,current::forlist)          
   	 }   
	}

  def forupdate(forlist:List[(Complex,Complex)],current:Array[Complex], forlist:List[(Complex,Complex)]): (Complex,Complex) = {
    forlist match {
      case Nil => (backlist, current. forlist)
      case f0::rest => (current::forlist, f0, rest)             
  	  }   
	}
*/

  val quitB = new Button("終了") { onAction =
      () => { println("終了します．"); System.exit(0) }
  }


  val backwardB = new Button("戻る") { onAction =
        () => {
             println("戻る")
             backlist match {
             	case Nil => println("戻れない")
             	case b0::blist =>
             		var b = backlist
             	 	backlist = blist
             	 	forlist = arraytopair(region)::forlist
             	 	region  = pairtoarray(backupdate(b))
             	 }
             
                draw(canvas.graphicsContext2D)
         	}
		}

  val forwardB = new Button("前へ") { onAction =
      () => {
      		println("前へ")
      		forlist match{
      			case Nil => println("進めない")
      			case f0::flist =>
      				var f = forlist
      				forlist = flist
      				backlist = arraytopair(region)::backlist
      				region = pairtoarray(forupdate(f)) 
  				}
  				draw(canvas.graphicsContext2D)
  			}
  		}
  

/*改良中よりよいテストのために
val backwardB = new Button("戻る") { onAction =
        () => {
             backlist match {
             	case Nil => println("戻れない")
             	case b0::blist =>
             		println("戻る")
             	 	backupdate(backlist,region,forlist) match{
             	 		case (a,b,c) => backlist = a
             	 						region = b
             	 						forlist = c /*単なる代入なので適当にa,b,c*/ 
             }
                draw(canvas.graphicsContext2D)
         	}
		}
	}


  val forwardB = new Button("前へ") { onAction =
      () => {
      		forlist match{
      			case Nil => println("進めない")
      			case f0::flist =>
  					println("前へ")
      				forlist = flist
      				forupdate(backlist, region, forlist) match {
      					case (a,b,c) => backlist = a
      									 region = b 
      									 forlist =c 
      				}
  				}
  				draw(canvas.graphicsContext2D)
  			}
  		}
  		*/

/*初期*
val backwardB = new Button("戻る") { onAction =
        () => {
        	println("戻る")
          	backlist match {
              case Nil =>  println("戻れない")
              case (c0,c1)::blist => { 
              	forlist = (region(0),region(1))::forlist
              	region(0) = c0
              	region(1) = c1
              	backlist = blist
              } 
             }
             draw(canvas.graphicsContext2D)
         }
     }

val forwardB = new Button("前へ") { onAction =
      () => {
      	forlist match{
      		case (c1,c2)::rst =>
      				println("前へ");
      				backlist = (region(0),region(1))::backlist
      				region(0) = c1
      				region(1) = c2 
					forlist =  rst
					
         case Nil => println("進めない")
  			}
  			draw(canvas.graphicsContext2D)
  		}
  	}
*/



  def theTitle() = f"Mandelbrot's microscope (${region(0).re}%f,${region(1).im}%f)-(${region(0).re}%f,${region(1).im}%f)"

  stage = new PrimaryStage {
    title = theTitle()
    scene = new Scene {
      root = new BorderPane {
        hgrow = Priority.Always
        vgrow = Priority.Always
        center = canvas
        top = new HBox {
          children = List(quitB, backwardB, forwardB)
        }
      }
    }
  }

  draw(canvas.graphicsContext2D)

  def draw(gc: GraphicsContext) {
    println(f"Drawing ${region(0)}-${region(1)}")
    gc.clearRect(0, 0, w-1, h-1)
    for (x <- Range(0, w-1)) {
      for (y <- Range(0, h-1)) {
        val c = complex(x, y)
        var z = new Complex(0, 0)
        var n = 0
        while (n < 255 && z.abs <= 2) {
          z = z*z + c
          n = n + 1
        }
        gc.stroke = Color.hsb(30, 1, n / 256.0)
        gc.strokeLine(x, y, x, y)
      }
    }
    stage.title = theTitle()
  }
}
