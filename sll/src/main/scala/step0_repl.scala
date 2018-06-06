object step0_repl extends App {
  Iterator.continually(scala.io.StdIn.readLine("user> ")).takeWhile(_ != null).foreach(str => println(rep(str)))

  def rep(str: String): String = read(str).eval(env = "").print

  def read(str: String): String = str

  implicit class EvalWrapper(val ast: String) {
    def eval(env: String): String = ast
  }

  implicit class PrintWrapper(val exp: String) {
    def print: String = exp
  }

}
