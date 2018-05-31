object step0_repl extends App {
  Iterator.continually(scala.io.StdIn.readLine()).takeWhile(_ != null).foreach(println)
}
