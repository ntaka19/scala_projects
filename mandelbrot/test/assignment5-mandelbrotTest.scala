package cs1.assignment5

import org.scalatest._
import Mandelbrot._

class ButtonTest extends FunSuite with Matchers {
      /*
      Drawing (-0.768000-0.248000i)-(-0.752000-0.232000i)
      Drawing (-0.752416-0.236416i)-(-0.752416-0.236416i)
      Drawing (-0.752416-0.236416i)-(-0.752416-0.236416i)
      */






      test("backtest"){
      	backupdate(List(new Complex(-0.768000,-0.248000),new Complex(-0.752000, -0.232000))) should equal (Complex(-0.768000, -0.248000))
      }
  }