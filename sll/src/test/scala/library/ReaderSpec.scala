package library

import org.scalatest.FeatureSpec

class ReaderSpec extends FeatureSpec {
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

    scenario("セミコロンを含んだ文字列") {
      assert(Reader.tokenizer("1;23") === Seq("1"))
    }

    scenario("特殊じゃない文字") {
      assert(Reader.tokenizer("a b c 123 true false nil") === Seq("a", "b", "c", "123", "true", "false", "nil"))
    }
  }
}
