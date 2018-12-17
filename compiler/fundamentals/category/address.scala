//ref http://rirakkumya.hatenablog.com/entry/2013/03/31/191056　
// より

//住所圏
case class Address(zip:String)

object Addr {
  type 住所 = Address
  type 郵便番号 = String
  def zip:住所 => 郵便番号 = _.zip
}

//住所と郵便番号を対象として定義し、case class とString を割あて、射(def)でつなげた。


Addr.zip(Address("100-0000"))// Addr.郵便番号 = 100-0000
//住所にzip射を適用すると郵便番号に変換される。


//住所があるかわからない場合
//scala ではOption型を使うが、Option型を適用するには型があわない。
//したがって、map を使う。

Some(Address("100-0000")) map Addr.zip
//->Option[Addr.郵便番号] = Some(100-0000) 
//Option[住所] => Option[郵便番号
//
//map に当たるものが関手である。
//_.zipの下りがよくわからん。。]
