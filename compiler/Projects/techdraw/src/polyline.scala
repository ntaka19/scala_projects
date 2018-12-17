package lx11

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.shape.Polyline
import scalafx.scene.layout.Pane

object PolylineExample extends JFXApp {
  val canvas = new Pane {}
  val pline = new Polyline()
  pline.points.addAll(100, 100, 200, 100, 200, 200, 100, 200, 100, 120)

  stage = new PrimaryStage {
    title = "Image"
    scene = new Scene(600, 400) {
      root = canvas
    }
  }

  canvas.children += pline
}
