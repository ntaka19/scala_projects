package cs1.lx02a

import org.scalatest._
import LX02A._

class LX02ATest extends FlatSpec with Matchers {

  "leapyear" should "be true for 4で割り切れる年" in {
    leapyear(2004) should be (true)
    leapyear(2008) should be (true)
  }

  "leapyear" should "be false for 4で割り切れない年" in {
    leapyear(2001) should be (false)
    leapyear(2002) should be (false)
    leapyear(2003) should be (false)
  }

  "leapyear" should "be false to 100で割り切れる年" in {
    leapyear(1800) should be (false)
    leapyear(1900) should be (false)
    leapyear(2100) should be (false)
  }

  "leapyear" should "be true to 400で割り切れる年" in {
    leapyear(1200) should be (true)
    leapyear(1600) should be (true)
    leapyear(2000) should be (true)
  }

  "leapyear" should "be false to 400で割り切れる年" in {
    leapyear(2100) should be (false)
    leapyear(2500) should be (false)
    leapyear(2900) should be (false)
  }
}

