object step1_read_print extends App {
  Iterator.continually(scala.io.StdIn.readLine("user> ")).takeWhile(_ != "").foreach(str => println(rep(str)))

  def rep(str: String): String = read(str).eval(env = "").print

  def read(str: String): Any = library.Reader.readStr(str)

  implicit class EvalWrapper(val ast: Any) {
    def eval(env: String): Any = ast
  }

  implicit class PrintWrapper(val exp: Any) {
    def print: String = library.Printer._pr_str(exp, true)
  }

}
