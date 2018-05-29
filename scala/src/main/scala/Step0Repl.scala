object Step0Repl {
  def READ(str: String): String = {
    str
  }

  def EVAL(str: String, env: String): String = {
    str
  }

  def PRINT(str: String): String = {
    str
  }

  def REP(str: String): String = {
    PRINT(EVAL(READ(str), ""))
  }

  def main(args: Array[String]) {
    var line: String = null
    while ( {line = scala.io.StdIn.readLine("user> "); line != null}) {
      try {
        println(REP(line))
      } catch {
        case e: Exception => {
          println("Error: " + e.getMessage)
          println("    " + e.getStackTrace.mkString("\n    "))
        }
      }
    }
  }
}