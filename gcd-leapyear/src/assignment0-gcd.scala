package cs1.assignment0

object GCD {
  //The greatest common divisor of the absolute value of x and the abosolute value of y
  def gcd(x: Int, y: Int) : Int = {
    if (x.abs>y.abs){ 
    	if (x.abs % y.abs == 0){y.abs}
   		else{gcd(y.abs,x.abs % y.abs)}
    	}
    else {
    	if (y.abs % x.abs == 0){x.abs}
    	else{gcd(x.abs,y.abs % x.abs)}
    }
   }
  }

