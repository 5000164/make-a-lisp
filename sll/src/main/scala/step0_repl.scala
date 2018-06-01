object step0_repl extends App {
  Iterator.continually(scala.io.StdIn.readLine("user> ")).takeWhile(_ != null).foreach(str => println(rep(str)))

  def rep(str: String): String = str.read.eval(env = "").print

  implicit class ReadWrapper(val str: String) {
    def read: String = str
  }

  implicit class EvalWrapper(val ast: String) {
    def eval(env: String): String = ast
  }

  implicit class PrintWrapper(val exp: String) {
    def print: String = exp
  }

}
