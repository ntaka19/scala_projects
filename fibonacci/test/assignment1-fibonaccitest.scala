package cs1.assignment1

import org.scalatest._
import FIB._

class FIBTest extends FunSuite with Matchers {
   test("FIB tests") {
   	 fib_rec(2) should equal (1)
     fib_rec(10) should equal (55)
     fib_itr(2) should equal (fib_rec(2))
     fib_itr(8) should equal (21)
     fib_matrix(3) should equal (fib_rec(3))
     fib_matrix(10) should equal (fib_rec(10))


   }

}
