//reference:http://www.geocities.jp/m_hiroi/java/scala13.html

class Cell[A](var prev:Cell[A] = null,var next:Cell[A] = null)
class ItemCell[A](var item: A) extends Cell[A]
//cell のアイテムのみを出力

class DList[A]{
  private val head: Cell[A] = new Cell()
  head.prev = head //これで双方向リストは空リストになる。
  head.next = head
  
  //n番目のセルを求める
  private def _nth(n:Int):Cell[A] = {
    val m = if(n>=0) n else n.abs-1 
    //nが0以上であればlistのnext. 負の場合はprev方向をたどる。
    
    var cp = if(n>=0) head.next else head.prev
    var i = 0
    while(cp!=head){
      if(i==m) return cp 
      cp = if(n>=0) cp.next else cp.prev
      i+=1
    }
    cp
  }
  //参照 データの中身を取り出す感じ。
  def apply(n:Int):A = 
    _nth(n) match{
      case that:ItemCell[A] => that.item
      case _ => throw new Exception("DList.apply: out of range")
    }
  
  //データの更新
  def update(n:Int,x:A):Unit = 
    _nth(n) match {
      case that: ItemCell[A] => that.item = x
      case _ => throw new Exception("DList.update: out of range")
    
    }
  
  //データの挿入
  
}
