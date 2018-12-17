package cs1.assignment6

import org.scalatest._

class RandomGraphTest extends FunSuite with Matchers {


  test("neighbor tests"){
  	val a = new Graph(5,List((1,2),(2,3),(3,4),(4,1)))
  	a.neighbors(2) should equal (List(1,3))

	}

	test ("maximumComponent tests"){
    val b = new Graph(7,List((0,1),(1,2),(3,1),(4,5),(6,4))) /*頂点は0から始まることに注意*/
	  val a = new Graph(5,List((1,2),(2,3),(3,4),(4,1)))
    b.maximumComponent should equal (4)
		a.maximumComponent should equal (4)

	}
}
