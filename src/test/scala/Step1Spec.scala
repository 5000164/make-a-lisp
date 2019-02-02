import org.scalatest.FeatureSpec

class Step1Spec extends FeatureSpec {
  feature("read and print") {
    scenario("simple inputs") {
      import step1_read_print.rep
      assert(rep("123") === "123")
      assert(rep("123 ") === "123")
      assert(rep("abc") === "abc")
      assert(rep("abc ") === "abc")
      assert(rep("(123 456)") === "(123 456)")
      assert(rep("( 123 456 789 ) ") === "(123 456 789)")
      assert(rep("( + 2 (* 3 4) ) ") === "(+ 2 (* 3 4))")
    }
  }
}
