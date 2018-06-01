object step0_repl extends App {
  Iterator.continually(scala.io.StdIn.readLine()).takeWhile(_ != null).foreach(str => println(rep(str)))

  def rep(str: String): String = PRINT(EVAL(READ(str), env = ""))

  def READ(str: String): String = str

  def EVAL(ast: String, env: String): String = ast

  def PRINT(exp: String): String = exp
}
