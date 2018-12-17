object minfree{
  def main(args: Array[String]) = {
    println
  }

  def minfree(xs:List[Int]): Int = {
    Stream.from(0).filter(x=> !xs.contains(x)).head
  }

}
