package cs1.assignment4

import scala.collection.mutable._
import org.scalatest._
import Trans._

class TransTest extends FunSuite with Matchers {

  test("trans tests"){
  	val x = ArraySeq(1,1,1,1)
  	trans(trans(x,4),4)(2) should equal (4)
  }


}



