package library

import org.scalatest.FeatureSpec

class ReaderSpec extends FeatureSpec {
  feature("トークンの情報を扱う") {
    scenario("peek でトークンの現在位置の情報を返す") {
      val reader = Reader(Seq("a", "b", "c"))
      assert(reader.peek === "a")
    }

    scenario("トークンを過ぎた後に peek で情報を取得する") {
      val reader = Reader(Seq("a", "b", "c"))
      assert(reader.next === "a")
      assert(reader.next === "b")
      assert(reader.next === "c")
      assert(reader.peek === null)
    }

    scenario("next でトークンの現在位置の情報を返した後に現在位置を進める") {
      val reader = Reader(Seq("a", "b", "c"))
      assert(reader.peek === "a")
      assert(reader.next === "a")
      assert(reader.peek === "b")
    }

    scenario("next でトークンを過ぎる") {
      val reader = Reader(Seq("a", "b", "c"))
      assert(reader.next === "a")
      assert(reader.next === "b")
      assert(reader.next === "c")
      assert(reader.next === null)
      assert(reader.next === null)
    }
  }

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
      assert(Reader.tokenizer(""""123"""") === Seq(""""123""""))
    }

    scenario("ダブルクォーテーションの途中でダブルクォーテーションがある場合はそこまで") {
      assert(Reader.tokenizer("""""123"""") === Seq("""""""", "123"))
    }

    scenario("ダブルクォーテーションの途中のダブルクォーテーションはバックスラッシュでエスケープできる") {
      assert(Reader.tokenizer(""""\"123"""") === Seq(""""\"123""""))
    }

    scenario("セミコロンを含んだ文字列") {
      assert(Reader.tokenizer("1;23") === Seq("1"))
    }

    scenario("特殊じゃない文字") {
      assert(Reader.tokenizer("a b c 123 true false nil") === Seq("a", "b", "c", "123", "true", "false", "nil"))
    }
  }

  feature("readForm") {
    scenario("Integer のトークンは Integer で解釈される") {
      assert(Reader.readForm(new Reader(Seq("123"))) === 123)
    }

    scenario("Double のトークンは Double で解釈される") {
      assert(Reader.readForm(new Reader(Seq("123.4"))) === 123.4)
    }

    scenario("ダブルクォーテーションで括られた文字列のトークンは文字列で解釈される") {
      assert(Reader.readForm(new Reader(Seq(""""123""""))) === "123")
    }

    scenario("It is judged keywords that the first letter is :") {
      assert(Reader.readForm(new Reader(Seq(":keyword"))) === "\u029ekeyword")
    }

    scenario("It is judged null that a word is nil") {
      assert(Reader.readForm(new Reader(Seq("nil"))) === null)
    }

    scenario("It is judged true that a word is true") {
      assert(Reader.readForm(new Reader(Seq("true"))) === true)
    }

    scenario("It is judged false that a word is false") {
      assert(Reader.readForm(new Reader(Seq("false"))) === false)
    }
  }
}
