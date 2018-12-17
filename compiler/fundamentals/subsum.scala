object subsum{
  
  def main(args:Array[String]){
    var S = List(1,3,4,5) 
    var T = List(0)
    var sum = 4;
    print(subsum(S,T,sum))
    print("\n")
  }

/*
  def subsum(S:List[Int],T:List[Int],sum:Int):List[Int] = {
    //while(S.length !=0){
    
    if (sum == 0) return List(9)  
    else if(S.length == 0) return List(-2) 
    else 
      subsum(S.tail, T:+S(0),sum-S(0))
      subsum(S.tail, T,sum)
    
    }*/

   

  
  def subsum(S:List[Int],T:List[Int],sum:Int):List[Int]= {
      if (sum != 0){ 
        S match {
        case fst::rest => 
          subsum(rest, T:+fst,sum-fst) //; subsum(rest,T,sum)
           //subsum(rest, T,sum)
        case Nil => return Nil
        }
      }
      else return T 
    }
  
}
