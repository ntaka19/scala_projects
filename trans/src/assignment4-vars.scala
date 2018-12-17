package cs1.assignment4

import scala.collection.mutable._


object Trans {
  def trans(x: ArraySeq[Int], n: Int): ArraySeq[Int] = {
    def trans_sub(x: ArraySeq[Int], rs: Int, re: Int): ArraySeq[Int] = {

    	if (rs==re){
     	x
     	}

     	else{
     		trans_sub(x,rs,(rs+re)/2) 
 			trans_sub(x,(rs+re)/2+1,re) /* この時点で書き換えができている。破壊的代入だから*/

     		for (i <-rs until (rs+re)/2+1){
 			val y= x(i)
 			x(i) = x(i)+x(i+(re-rs+1)/2)
 			x(i+(re-rs+1)/2) = y-x(i+(re-rs+1)/2)			
 			}
 			x
		} 		
 		}  	
    

    trans_sub(x, 0, n-1)
  }

  def main(arg: Array[String]) = {
    val x = ArraySeq(0,0,0,0,0,0,0,1)
    trans(x,8)
    println(x) //ArraySeq(1, -1, -1, 1, -1, 1, 1, -1)
    println(trans(ArraySeq(1,2,3,4,5,6,7,8), 8)) //ArraySeq(36, -4, -8, 0, -16, 0, 0, 0)
    println(trans(ArraySeq(1,1,1,1,1,1,1,1), 8)) //ArraySeq(8, 0, 0, 0, 0, 0, 0, 0)
  }

}
