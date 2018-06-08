package library

import org.scalatest.FeatureSpec

class PrinterSpec extends FeatureSpec {
  feature("トークンを取得する") {
    scenario("シンプルな文字列") {
      assert(Reader.tokenizer("123") === Seq("123"))
    }

    scenario("フォーマットに使用する特殊な 2 文字") {
      assert(Reader.tokenizer( """(format t "~@d" 10000)""") === Seq("(", "format", "t", """"~@d"""", "10000", ")"))
    }
  }
}
