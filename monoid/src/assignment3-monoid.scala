package cs1.assignment3

trait Monoid[T] {
  def op(x: T, y: T): T
  def id :T
}


object Monoids {
  val intAddMonoid = new Monoid[Int] {
    def op(x: Int, y: Int): Int = x + y
    def id = 0
  }

  //Monoid on Int with the integer multiplication as the operator
  val intMulMonoid = new Monoid[Int] { 
  	def op(x:Int, y:Int) : Int = x * y
  	def id = 1 
  }

// Monoid on Double with the floating point number addition as the operator
  val doubleAddMonoid = new Monoid[Double] {
  	def op(x:Double , y:Double): Double = x + y
  	def id = 0 
  }

// Monoid on Double with the floating point number multiplication as the operator
  val doubleMulMonoid = new Monoid[Double] {
  	def op(x:Double, y:Double): Double = x * y
  	def id = 1
  }

// Monoid on Boolean with the conjunction as the operator
  val booleanAndMonoid = new Monoid[Boolean] {
  	def op(x:Boolean, y:Boolean) = x && y 
  	def id = true
  }

// Monoid on Boolean with the disjunction as the operator
  val booleanOrMonoid = new Monoid[Boolean] {
  	def op(x:Boolean, y:Boolean) = x || y
  	def id = false 
  }

// Monoid on String with the concatenation + as the operator (e.g., stringMonoid.op("a","b") return "ab")
  val stringMonoid = new Monoid[String] {
  	def op(x:String , y:String): String = x + y
  	def id = ""
  }

// Monoid on List with the concatenation ++ as the operator (e.g., listMonoid[Int].op(List(1),List(2)) return List(1,2))
  def listMonoid[A] = new Monoid[List[A]] {
  	def op(x:List[A], y:List[A]): List[A] = x ++ y
  	def id = Nil
  }

// Monoid on (A=>A) with the composition as the operator (e.g., endMonoid[Int].op((x=>x+1), (x=>2*x)) return (x=>2*x+1))
  def endMonoid[A] = new Monoid[A=>A] {
  	def op(x:A=>A , y:A=>A): A=>A = a=>x(y(a))
  	def id = x=>x
  }


// The function returning the product monoid
  def productMonoid[A, B](a: Monoid[A], b: Monoid[B]) = new Monoid[(A,B)] {
  	def op(c:(A,B),d:(A,B)): (A,B) =
  		(c,d) match {
  			case ((c1,c2), (d1,d2)) =>(a.op(c1,d1), b.op(c2,d2))
  		}
  	/*op(x:Monoid[A].op, y:Monoid[B].op) = (x,y)*/
  	def id = (a.id, b.id) 
  }

  def concatenate[T](as: List[T], m: Monoid[T]): T = {
  	as match {
  		case a::lst => m.op(a,concatenate(lst,m))
  		case Nil => m.id
  	}
  }

  def foldMap[S,T](as: List[S], m: Monoid[T])(f: S => T): T = {
  	as match {
  		case a::lst => m.op(f(a),foldMap(lst, m)(f))
  		case Nil => m.id
  	}
  }


  def pow[T](a: T, n: Int, m: Monoid[T]): T = {
  	n match {
  		case 0 => m.id 
  		case _ =>  if (n%2 == 0) pow(m.op(a,a), n/2, m)
                   else m.op(a,pow(m.op(a,a),(n-1)/2,m)) 		
 	}
  }


  def main(arg: Array[String]) = {
    println(intAddMonoid.op(2, 3))

    println(stringMonoid.op("AAA", "BBB"))
    println(concatenate(List("AAA", "BBB", "CCC"), stringMonoid))
    println(concatenate(List(1, 2, 3, 4), intAddMonoid))
    println(concatenate(List(1, 2, 3, 4), intMulMonoid))
    println(foldMap(List(1, 2, 3, 4), intMulMonoid)(i=>i+1))
    println(pow(2, 11, intMulMonoid))
    println(pow("a", 11, stringMonoid))
    println(pow(1.3, 8, doubleMulMonoid))

  }
}

