package cs1.assignment0

import org.scalatest._
import GCD._

class GCDTest extends FunSuite with Matchers {
   test("GCD tests1") {
     gcd(2,8) should equal (2)
     gcd(2*3*5*7,2*3*5*17) should equal (2*3*5)
   }

    test("GCD tests2") {
     gcd(3,8) should equal (1)
     
   }

    test("GCD tests3") {
     gcd(-4,8) should equal (4)
     
   }

}



