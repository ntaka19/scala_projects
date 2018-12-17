package cs1.assignment3

import org.scalatest._
import Monoids._

class MonoidsTest extends FunSuite with Matchers {

  test("Monoid tests"){
    intAddMonoid.op(2,3) should equal (5)
    intMulMonoid.op(3,3) should equal (9)
    doubleAddMonoid.op(2,3) should equal (5)
    doubleMulMonoid.op(2,3) should equal (6)
    booleanAndMonoid.op(true,false) should equal (false)
    booleanOrMonoid.op(true,false) should equal (true)
    stringMonoid.op("x","xx") should equal ("xxx")
    listMonoid.op(List(2,3),List(3)) should equal (List(2,3,3))
    
    endMonoid.op((x:Int)=>x+1,(x:Int)=>2*x)(2) should equal (5)
	productMonoid[Int,Int](intMulMonoid,intAddMonoid).op((2,4),(2,4)) should equal ((4,8))
  }

  test("Polymorphism tests"){
  	concatenate[Int](List(1,2),intAddMonoid) should equal (3)
  	foldMap[Int,Int](List(2,4),intAddMonoid)(x=>x+1) should equal (8) 
  	pow("ab",5,stringMonoid) should equal ("ababababab")
  	pow[Int](3, 3, intMulMonoid) should equal (27)

  }


}



