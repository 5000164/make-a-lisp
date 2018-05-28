import org.scalatest.FeatureSpec

class Step0ReplSpec extends FeatureSpec {
  feature("入力した内容を返す") {
    scenario("Testing basic string") {
      val input = "abcABC123"
      assert(Step0Repl.REP(input) === "abcABC123")
    }

    scenario("Testing string containing spaces") {
      val input = "hello mal world"
      assert(Step0Repl.REP(input) === "hello mal world")
    }

    scenario("Testing string containing symbols") {
      val input = "[]{}\"'* ;:()"
      assert(Step0Repl.REP(input) === "[]{}\"'* ;:()")
    }

    scenario("Test long string") {
      val input = "hello world abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ 0123456789 (;:() []{}\"'* ;:() []{}\"'* ;:() []{}\"'*)"
      assert(Step0Repl.REP(input) === "hello world abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ 0123456789 (;:() []{}\"'* ;:() []{}\"'* ;:() []{}\"'*)")
    }
  }
}
