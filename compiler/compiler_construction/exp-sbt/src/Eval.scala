import Abssyn._
import Base._

object Eval {
  def eval (env: Map[Var, Int], e: Exp) : Int =
    e match {
      //env.get(x) match {
      //  case Some(i) => i
      //  case None => 0
      //if(env.contains(x)) 
      //getOrElse(x,0)
      //}
      case VarExp(x) => env.get(x) match {
                          case Some(v) => env(x)
                          case None => 0
      }
        //if(env.get(x)==None) 0 else env(x)
      case IntExp(i) => i 
      case PlusExp(e1, e2) => eval (env, e1) + eval (env, e2)
      case MinusExp(e1, e2) => eval (env, e1) - eval (env, e2)
      case TimesExp(e1, e2) => eval (env, e1) * eval (env, e2)
      case DevideExp(e1, e2) => eval (env, e1) / eval (env, e2)
    }
}



