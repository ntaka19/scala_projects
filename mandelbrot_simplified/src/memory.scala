package ex08

object Memory {
  val runtime = scala.sys.runtime
  def consumedMemory = runtime.totalMemory() - runtime.freeMemory()

  def log(N: Int, usage: Long, heading: String) {
    println(f"$heading> $usage bytes used.  ${usage / N} bytes, or ${usage / N / 8} words, per a Complex object")
  }

  def memoryTest1(N: Int) = {
    runtime.gc()
    val memUsageBefore = consumedMemory
    val v: Array[Complex] = Array.tabulate(N){ i => new Complex(i, i) }
    runtime.gc()
    val memUsageAfter  = consumedMemory
    memUsageAfter - memUsageBefore
  }

  def memoryTest2(N: Int) = {
    runtime.gc()
    val memUsageBefore = consumedMemory
    val v: Array[Complex] = Array.tabulate(N){ i => new Complex(i, i) }
    runtime.gc()
    val memUsageAfter  = consumedMemory
    (memUsageAfter - memUsageBefore, scala.util.Random.nextInt(v.length))
  }

  def main(arguments: Array[String]) {
    val N = 1 << 20
    for (i <- 1 to 5) {
      val usage1 = memoryTest1(N)
      val usage2 = memoryTest2(N) match { case (usage, _) => usage }
      println()
      log(N, usage1, "Test 1")
      log(N, usage2, "Test 2")
      println()
    }
  }
}
