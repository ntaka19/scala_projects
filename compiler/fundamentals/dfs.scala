object graph{
  
  def main(args:Array[String]){}
  
  val adjacent: Array[List[Int]] = Array(
    List(1, 2),
    List(0, 2, 3),
    List(0, 1, 4),
    List(1, 4, 5),
    List(2, 3, 6),
    List(3),
    List(4))
  
  def depthFirstSearch(start:Int, goal:Int){
    def dfs(path:List[Int]){
      val p = path.head
      if (p==goal) println(path.reverse)
      else for (x<-adjacent(p) if(!path.contains(x))) dfs(x::path)
    }
    dfs(List(start))
  }
  depthFirstSearch(0,6)
}
