object fizzbuzz{
  def main(args:Array[String]){
    for (n<-1 to 100){
      if(n%3==0|| n%5==0) 
        print("fizzbuzz\n")
      else if(n%3 == 0)
        print("fizz\n")
      else if (n%5==0)
        print("buzz\n")
      else 
        print(n+"\n")
      print("")
    }
  
  }
}
