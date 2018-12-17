package cs1.assignment1

object FIB {
  //An implementation of the Fibonacci function using the definition of the Fibonacci number
  def fib_rec(n: Int) : BigInt = n match {
    case 0 | 1 => n
    case _ => fib_rec(n-1) + fib_rec(n-2)
  }

  //An implementation of the Fibonacci function using iteration (tail recursion)

  def fib_itr(n: Int) : BigInt = {

  		 def cord(n:Int,a:BigInt,b:BigInt) : (Int,BigInt,BigInt) =
  	  		n match {
  	  			case 0 => (0,a,b)
  	  			case _ => cord(n-1,a+b,a)
  	  		}
  	   def filter0(vec:(Int,BigInt,BigInt)) : BigInt =
         vec match {
           case (_,a,_) => a
         }

  	filter0(cord(n,0,1))
  }



 def fib_matrix(n: Int) : BigInt = {
   val I = List[BigInt](1,0,0,1)
   val A = List[BigInt](1,1,1,0)

    def multiply(a: List[BigInt], b: List[BigInt]) : List[BigInt] =
      (a,b) match {
      case (List(a0,a1,a2,a3),List(b0,b1,b2,b3)) =>
        List(a0 * b0 + a1 * b2,
             a0 * b1 + a1 * b3,
             a2 * b0 + a3 * b2,
             a2 * b1 + a3 * b3)
    }

    def power(n:Int,a:List[BigInt],lst:List[BigInt]) : List[BigInt] = n match {
      case 0 => lst   // lst
      case _ => if (n%2 == 0) power(n/2,multiply(a,a),lst)
                else power(n/2,multiply(a,a),multiply(lst,a))
    }

    def filter1(a:List[BigInt]) : BigInt =
      a match {
        case a0::a1::a2::a3::Nil => a2
      }
    filter1(power(n,A,I))
  }
}
