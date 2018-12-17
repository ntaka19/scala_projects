package lx11

import scala.math.{abs,min}
import scala.collection.mutable.{Buffer, Map}

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.event.ActionEvent
import scalafx.geometry.Point2D
import scalafx.scene.Scene
import scalafx.scene.control.{Button, ButtonBase, ColorPicker, ToggleButton, ToolBar, ToggleGroup}
import scalafx.scene.effect.DropShadow
import scalafx.scene.input.{MouseEvent,KeyEvent,KeyCode}


import scalafx.scene.layout.{Pane, BorderPane}
import scalafx.scene.paint.Color
import scalafx.scene.shape.{Ellipse, Line, Polygon, Polyline, Rectangle, Shape}

abstract class TDShape
case class TDNoShape()                  extends TDShape
case class TDLine     (shape:Line)      extends TDShape
case class TDRectangle(shape:Rectangle) extends TDShape
case class TDEllipse  (shape:Ellipse)   extends TDShape
case class TDPolyline (shape:Polyline)  extends TDShape
case class TDPolygon  (shape:Polygon)   extends TDShape
case class TDStar     (shape:Polygon)   extends TDShape
case class TDHinomaru (flag:Rectangle, disc:Ellipse)    extends TDShape

object TechDraw extends JFXApp {

  var strokeColor: Color = Color.Black
  var fillColor: Color   = Color.White
  var shapes: Buffer[TDShape] = Buffer()
  var width: Double = 2

  type MouseHandler = MouseEvent => Unit

  object mouseAction {
    val _noAction:   MouseHandler = { _ => () }

    var pressHandlers   = Map[String, MouseHandler]()
    var dragHandlers    = Map[String, MouseHandler]()
    var releaseHandlers = Map[String, MouseHandler]()

    def set(id: String) {
      def lookup(handlers: Map[String, MouseHandler]) = {
        try { handlers(id) } catch { case _: NoSuchElementException => _noAction }
      }

      drawingPane.onMousePressed  = lookup(pressHandlers)
      drawingPane.onMouseDragged  = lookup(dragHandlers)
      drawingPane.onMouseReleased = lookup(releaseHandlers)
    }
  }

  type KeyHandler = KeyEvent => Unit

  object keyAction {
  	val _noAction: KeyHandler ={ _ => ()}
  	var pressHandlers = Map[String, KeyHandler]()

  	def set(id: String) {
  		def lookup(handlers: Map[String, KeyHandler]) = {
  			try {handlers(id)} catch { case _: NoSuchElementException => _noAction}
  		}
  		stage.scene.root().onKeyPressed = lookup(pressHandlers)
  	}
  }


  object RectangleControl { // 矩形の描画に関わるデータ構造と処理（若干未完成の箇所があります）

    var r  = new Rectangle {}
    var p0 = new Point2D(0, 0)

    def onPress(ev: MouseEvent) {
      p0 = new Point2D(ev.x, ev.y)
      r = new Rectangle {
        x = p0.x; y = p0.y
        stroke = strokeColor; fill = Color.Transparent
      }
      drawingPane.children += r
      shapes += TDRectangle(r)
    }

    def onDrag(ev: MouseEvent) {
      r.x     = min(p0.x, ev.x);  r.y      = min(p0.y, ev.y)
      r.width = abs(p0.x - ev.x); r.height = abs(p0.y - ev.y)
    }

    def onRelease(ev: MouseEvent) {
      r.fill = fillColor
    }
  }

  mouseAction.pressHandlers   += (("Rectangle", RectangleControl.onPress))
  mouseAction.dragHandlers    += (("Rectangle", RectangleControl.onDrag))
  mouseAction.releaseHandlers += (("Rectangle", RectangleControl.onRelease))

  object EllipseControl { // 楕円の描画に関わるデータ構造と処理（未完成）

    var e  = new Ellipse {}
    var p0 = new Point2D(0,0)

    def onPress(ev: MouseEvent) {
     p0 = new Point2D(ev.x, ev.y)
     e = new Ellipse{
     	centerX = p0.x ; centerY = p0.y
     	stroke = strokeColor; fill = Color.Transparent
     }
     drawingPane.children += e
     shapes += TDEllipse(e)
      }

    def onDrag(ev: MouseEvent) {
    	e.centerX = min(p0.x, ev.x);  e.centerY = min(p0.y, ev.y)
      e.radiusX = abs(p0.x-ev.x);   e.radiusY = abs(p0.y-ev.y)
    }

    def onRelease(ev: MouseEvent) {
      e.fill = fillColor
    }
  }

  mouseAction.pressHandlers   += (("Ellipse", EllipseControl.onPress))
  mouseAction.dragHandlers    += (("Ellipse", EllipseControl.onDrag))
  mouseAction.releaseHandlers += (("Ellipse", EllipseControl.onRelease))

  object LineControl {

	 var l = new Line {}
	 var p0 = new Point2D(0,0)

	 def onPress(ev:MouseEvent){
		p0 = new Point2D(ev.x, ev.y)

		l = new Line {
			startX = p0.x ; startY = p0.y
			endX = p0.x ; endY = p0.y
			stroke = strokeColor; fill = Color.Transparent
		}
		drawingPane.children += l
		shapes += TDLine(l)
		}

	def onDrag(ev: MouseEvent){
		l.endX = ev.x ; l.endY = ev.y
	}

	 def onRelease(ev: MouseEvent) {
      l.fill = fillColor
    }
}
  mouseAction.pressHandlers   += (("Line", LineControl.onPress))
  mouseAction.dragHandlers    += (("Line", LineControl.onDrag))
  mouseAction.releaseHandlers += (("Line", LineControl.onRelease))

  def subline(s:Line, l:Line){
    s.startX = l.startX() /*sをlのコピー 色書かないと透明となる*/
    s.startX = l.startY()
    s.endX = l.endX()
    s.endY = l.endY()
    s.strokeWidth = 20
  }


  object PolylineControl {
    var p = new Polyline{strokeWidth = 2}
    var p0 = new Point2D(0,0)
    var touch = 0

    def onPress(ev: MouseEvent){
      if (touch == 0){
        drawingPane.children += p
        shapes += TDPolyline(p)
        touch = 1
      }
      if (ev.clickCount != 1) {
        p = new Polyline{strokeWidth = 2}
        touch = 0
      }
      else {
        p0 = new Point2D(ev.x,ev.y)
        p.points ++= List(p0.x,p0.y)
        p.stroke = strokeColor
      }
    }
    def onRelease(ev: MouseEvent) {
      p.stroke = strokeColor
    }
}
  mouseAction.pressHandlers   += (("Polyline", PolylineControl.onPress))
  mouseAction.releaseHandlers += (("Polyline", PolylineControl.onRelease))

  object DotsControl {
		var e = new Ellipse{}

		def onPress(ev:MouseEvent){
			e = new Ellipse{
			fill=strokeColor;stroke=strokeColor;e.radiusX=width;e.radiusY=width;e.centerX=ev.x;e.centerY=ev.y
			}
			drawingPane.children += e
		}
		def onDrag(ev:MouseEvent){
			e = new Ellipse{
				fill=strokeColor;stroke=strokeColor;e.radiusX=width;e.radiusY=width;e.centerX=ev.x;e.centerY=ev.y
			}
			drawingPane.children += e
		}
	}
    mouseAction.pressHandlers   += (("Dots", DotsControl.onPress))
 	  mouseAction.dragHandlers    += (("Dots", DotsControl.onDrag))


  def remove(s:TDShape):Unit = {
  	s match {
  		case TDRectangle(r) => drawingPane.children -= r
  		case TDLine(l) => drawingPane.children -= l
  		case TDEllipse(e) => drawingPane.children -=e
      case TDPolyline(p) => drawingPane.children -=p
  	}
  	shapes -= s
  }

  def reset = {
    drawingPane.children = Nil
    shapes = Buffer()
  }
/*
  object CuiControl{
    def onKeyPress(ev: KeyEvent){
      if (ev.isControlDown){
        ev.code match{
          case KeyCode.R => reset
          case KeyCode.ESCAPE => System.exit(0)
          case _ =>
          }
        }
      }
    }
      keyAction.pressHandlers += (("Key", CuiControl.onKeyPress))
*/
  object SelectControl { // 選択ツールに関わるデータ構造と処理

    var selection: TDShape = TDNoShape()
    var p0 = new Point2D(0,0)
    var s = new Line {}
    var x1:Double = 0.0 ; var y1:Double = 0.0
    var x2:Double = 0; var y2:Double = 0

    def onPress(ev: MouseEvent){
      val x = ev.x ; val y= ev.y
      p0 = new Point2D(ev.x, ev.y)

      selection match {
          case TDLine(l)=> p0 = new Point2D((l.endX()-l.startX())/2, (l.endY() - l.startY())/2)
          case _ =>
      }
      selection match {
        case TDRectangle(r) => r.effect = null
        case TDEllipse(e) => e.effect = null
        case TDLine(l) => l.effect = null
        case  _ =>
        }

        val oShape = shapes.reverse.find((shape: TDShape) =>
            shape match {
              case TDRectangle(r) => r.contains(x, y)
              case TDEllipse(e) => e.contains(x,y)
              case TDLine(l) => subline(s,l)
              s.contains(x,y)
            })

      selection = oShape match {
        case Some(shape) => shape
              case _ => TDNoShape()
            }
            selection match {
              case TDRectangle(r) =>r.effect = {x1= r.x(); y1 = r.y() ;new DropShadow(10, Color.Blue)}
              case TDEllipse(e) => e.effect = {x1 = e.centerX(); y1 = e.centerY(); new DropShadow(10, Color.Blue)}
              case TDLine(l) => l.effect = new DropShadow(10, Color.Blue)
            }
          }

      def onDrag(ev: MouseEvent){
      val x0 = p0.x ; val y0 = p0.y

      selection match {
        case TDRectangle(r) => r.x =x1 + ev.x - x0 ; r.y = y1 + ev.y - y0
        case TDLine(l) => l.startX() = ev.x-p0.x ; l.startY() = ev.y -p0.y ; l.endX = ev.x + p0.x; l.endY = ev.y + p0.y
        case TDEllipse(e) => e.centerX = ev.x; e.centerY = ev.y
        case TDNoShape() =>
      }
    }

      def onKeyPress(ev: KeyEvent){
      	/*キーイベント、println(ev)で入力されているか確認が*/
        if (ev.isControlDown) {
          ev.code match {
          case KeyCode.R => reset
          case _ =>
          }
        }
        else ev.code match {
      		case KeyCode.DELETE => remove(selection)
          case _ =>
        }
      }
  }

  mouseAction.pressHandlers += (("Select", SelectControl.onPress))
  mouseAction.dragHandlers += (("Select", SelectControl.onDrag))
  keyAction.pressHandlers += (("Select", SelectControl.onKeyPress))


object SizeControl{
  var selection: TDShape = TDNoShape()
  var p0 = new Point2D(0,0)

  var x1:Double = 0 ; var y1:Double = 0
  var x2:Double = 0 ; var y2:Double = 0

  def onPress(ev:MouseEvent){
    val x = ev.x; val y = ev.y
    val oShape = shapes.reverse.find((shape: TDShape)=>
        shape match {
          case TDRectangle(r) => r.contains(x,y)
          case TDEllipse(e) => e.contains(x,y)
          case TDLine(l) => l.contains(x,y)
          case _ => false
    })

    selection = oShape match{
      case Some(shape) => shape
      case _ => TDNoShape()
    }

    selection match {
 		 case TDRectangle(r) => x1 = r.width() ; y1 = r.height()
 		 case TDEllipse(e) => x1 = e.radiusX(); x2 = e.radiusY()
 		 case TDLine(l) => x1 = l.startX(); y1 = l.startY()
                      x2 = l.endX(); y2 = l.endY()
     case _ =>
    }
    p0 = new Point2D(ev.x, ev.y)
  }

  def onDrag(ev:MouseEvent){
    val x = ev.x ; val y = ev.y
    selection match {
      case TDRectangle(r) => r.width = abs(x1 + ev.x-p0.x);r.height = abs(y1+ev.y-p0.y)
      case TDEllipse(e) => e.radiusX = abs(x1+ev.x-p0.x);e.radiusY = abs(y1+ev.y-p0.y)
      case TDLine(l) => l.startX = x1+ev.x-p0.x; l.startY= y1+ev.y-p0.y;l.endX=x2+ev.x-p0.x;l.endY=y2+ev.y-p0.y
    }
  }
}
mouseAction.pressHandlers += (("Size", SizeControl.onPress))
mouseAction.dragHandlers += (("Size", SizeControl.onDrag))

  val drawingPane = new Pane { }
  val shapeGroup = new ToggleGroup()

  shapeGroup.selectedToggle.onChange {/*try catch構文を用いることで選択ボタンを二回押すことで生じるNullPointerExceptionを対処*/
    try{
    val id = shapeGroup.selectedToggle().asInstanceOf[javafx.scene.control.ToggleButton].id()
    mouseAction.set(id)
    keyAction.set(id)
  }
      catch{
        case _: NullPointerException => ()
      }
    drawingPane.requestFocus()
  }

  val shapeTools: List[ToggleButton] = List(
    new ToggleButton {
      id = "Select"; text = "選択"
      graphic = new Rectangle { width = 0; height = 32; fill = Color.Transparent }
      toggleGroup = shapeGroup
      minWidth = 40; minHeight = 40
    },

    new ToggleButton {
      id = "Size"; text = "サイズ変更"
      graphic = new Rectangle { width = 0; height = 32; fill = Color.Transparent }
      toggleGroup = shapeGroup
      minWidth = 40; minHeight = 40
    },

    new ToggleButton {
      id = "Line"
      graphic = new Line {
        stroke = Color.Black; strokeWidth = 3
        startX = 0; startY = 0; endX = 28;  endY = 28
      }
      toggleGroup = shapeGroup
      minWidth = 40; minHeight = 40
    },

    new ToggleButton {
      id = "Rectangle"
      graphic = new Rectangle {
        stroke = Color.Black; fill = Color.White
        width = 32; height = 32
      }
      toggleGroup = shapeGroup
    },

    new ToggleButton {
      id = "Ellipse"
      graphic = new Ellipse {
        stroke = Color.Black; fill = Color.White
        radiusX = 16; radiusY = 16
      }
      toggleGroup = shapeGroup
    },

    new ToggleButton {
      id = "Polyline"
      graphic = new Polyline{
        stroke = Color.Black
         points ++= List(0,0,11,29,22,0,33,29)
      }
      toggleGroup = shapeGroup
    },
    new ToggleButton{
			id = "Dots";text="Dots"
			graphic = new Rectangle { width = 0; height = 32; fill = Color.Transparent }
			toggleGroup = shapeGroup
			minWidth = 40; minHeight = 40
		}
  )


  val colorTools = Seq(
    new ColorPicker(strokeColor) {
      onAction = { e: ActionEvent => strokeColor = value()
      val c = value()
      strokeColor = Color.hsb(c.hue, c.saturation, c.brightness, 0.5)
      SelectControl.selection match {
          case TDRectangle(r) => r.stroke = strokeColor
       	  case TDEllipse(e) => e.stroke = strokeColor
          case TDLine(l) => l.stroke = strokeColor
          case TDPolyline(p) => p.stroke = strokeColor
          case _ => ()
      		}
  		}
    },
    new ColorPicker(fillColor) {
      onAction = { e: ActionEvent =>
        val c = value()
        fillColor = Color.hsb(c.hue, c.saturation, c.brightness, 0.5)
        SelectControl.selection match {
          case TDRectangle(r) => r.fill = fillColor
          case TDEllipse(e) => e.fill = fillColor
          case TDLine(l) => l.fill = fillColor
          case _ => ()
        }
      }
    })

  stage = new PrimaryStage {
    title = "TechDraw"
    scene = new Scene(600, 400) {
      root = new BorderPane {
        top = new ToolBar { content = shapeTools ++ colorTools }
        center = drawingPane
      }
    }
  }
}
