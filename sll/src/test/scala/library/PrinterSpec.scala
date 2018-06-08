package library

import org.scalatest.FeatureSpec

class PrinterSpec extends FeatureSpec {
  feature("トークンを取得する") {
    scenario("シンプルな文字列") {
      assert(Reader.tokenizer("123") === Seq("123"))
    }
  }
}
