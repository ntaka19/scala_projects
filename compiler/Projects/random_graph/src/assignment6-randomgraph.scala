package cs1.assignment6

import java.io._
import scala.collection.mutable.Queue
import scala.util.Random

class Graph(n: Int, e: List[(Int, Int)]) {
  val size: Int = n
  val edges: List[(Int, Int)] = e

  override def toString = {
    n.toString + ", " + edges.toString
  }



  def neighbors(x: Int) : List[Int] = {

  	/*補助関数*/
	def neigh(x:Int, rest:List[(Int,Int)]) : List[Int] = {
  		rest match {
  			case (c,d)::lrest =>
  					if (x==c) d::neigh(x,lrest)
  					else if (x==d) c::neigh(x,lrest)
  					else neigh(x,lrest)
  			case Nil => Nil
  		}
  	}


  	edges match {
  		case (a,b)::rest =>
  			if (x == a) b::neigh(x,rest)
 			else if (x == b) a::neigh(x,rest)
 			else neigh(x,rest)
 		case Nil => Nil

  	}
  }

def depth(x:Int):Int = {
  var count: Int =1
  val mark: Array[Boolean] = Array.fill(size)(false)
  val stack = new scala.collection.mutable.Stack[Int]
	mark(x) = true
	stack.push(x)
      while(!stack.isEmpty){
        var node = stack.pop
        for (i<-neighbors(node)){
          if(!mark(i)){
            mark(i) = true
            count += 1
            stack.push(i)
          }
        }
      }
      count
    }


  def maximumComponent : Int = {
  	var memo: Array[Int] = Array.fill(size)(0)
  	for (i<-0 until size){
  		memo(i)=depth(i)
  	}
  	memo.max
 } 		/* リストの生成からすべての長さから最長のものを選ぶ*/



  def connectivity : Boolean = {
    maximumComponent == n
  }
}



object RandomGraph {
  def genRandomGraph(n: Int, p: Double, rng: Random): Graph = {
    var e: List[(Int, Int)] = Nil
    for(i<-0 until n;j<-i+1 until n)if(rng.nextDouble <= p) e = (i,j)::e
/*
  Generate a random graph
*/
    new Graph(n, e)
  }

  def main(arg: Array[String]) = {
    var n = 500
    val num = 1000000/n
    var file = new PrintWriter(new File(n + "_" + num + ".txt"))
    val rng = new Random
    for(i <- 1 to 200){
      var c = 0
      for(_ <- 0 until num){
        var rg = genRandomGraph(n, i.toDouble/100/n, rng)
        c = c + rg.maximumComponent
      }
      file.println(i/100.0 + " " + c.toDouble/num/n)
    }
    file.close()
  }

}
