package library

import org.scalatest.FeatureSpec

class PrinterSpec extends FeatureSpec {
  feature("トークンを取得する") {
    scenario("シンプルな文字列") {
      assert(Reader.tokenizer("123") === Seq("123"))
    }

    scenario("特殊な 2 文字") {
      assert(Reader.tokenizer("~@") === Seq("~@"))
    }

    scenario("特殊な 1 文字") {
      assert(Reader.tokenizer("[]{}()'`~^@") === Seq("[", "]", "{", "}", "(", ")", "'", "`", "~", "^", "@"))
    }

    scenario("ダブルクォーテーションで括られた文字") {
      assert(Reader.tokenizer( """"123"""") === Seq( """"123""""))
    }

    scenario("ダブルクォーテーションの途中でダブルクォーテーションがある場合はそこまで") {
      assert(Reader.tokenizer( """""123"""") === Seq( """""""", "123"))
    }

    scenario("ダブルクォーテーションの途中のダブルクォーテーションはバックスラッシュでエスケープできる") {
      assert(Reader.tokenizer( """"\"123"""") === Seq( """"\"123""""))
    }
  }
}
