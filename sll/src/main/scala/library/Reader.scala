package library

case class Reader(tokens: Seq[String]) {
  var data: Seq[String] = tokens
  var position = 0

  def peek: String = {
    if (position >= data.length) return null
    data(position)
  }

  def next: String = {
    if (position >= data.length) return null
    position = position + 1
    data(position - 1)
  }
}

object Reader {
  def readStr(str: String): Any = {
    val tokens = tokenizer(str)
    if (tokens.isEmpty) {
      null
    } else {
      readForm(new Reader(tokens))
    }
  }

  def tokenizer(str: String): Seq[String] = {
    val r = """[\s,]*(~@|[\[\]{}()'`~^@]|"(?:\\.|[^\\"])*"|;.*|[^\s\[\]{}('"`,;)]*)""".r
    r.findAllMatchIn(str).map {_.group(1)}
      .filter { s => s != "" && s(0) != ';' }
      .toSeq
  }

  def readForm(reader: Reader): Any = {
    reader.peek match {
      case "'" =>
        reader.next
        Types._list(Symbol("quote"), readForm(reader))
      case "`" =>
        reader.next
        Types._list(Symbol("quasiquote"), readForm(reader))
      case "~" =>
        reader.next
        Types._list(Symbol("unquote"), readForm(reader))
      case "~@" =>
        reader.next
        Types._list(Symbol("splice-unquote"), readForm(reader))
      case "^" =>
        reader.next
        val meta = readForm(reader)
        Types._list(Symbol("with-meta"), readForm(reader), meta)
      case "@" =>
        reader.next
        Types._list(Symbol("deref"), readForm(reader))
      case "(" =>
        readList(reader)
      case ")" =>
        throw new Exception("unexpected ')')")
      case "[" =>
        Types._vector(readList(reader, "[", "]").value: _*)
      case "]" =>
        throw new Exception("unexpected ']')")
      case "{" =>
        Types._hash_map(readList(reader, "{", "}").value: _*)
      case "}" =>
        throw new Exception("unexpected '}')")
      case _ =>
        readAtom(reader)
    }
  }

  def readList(rdr: Reader, start: String = "(", end: String = ")"): MalList = {
    var ast: MalList = Types._list()
    var token = rdr.next
    if (token != start) throw new Exception("expected '" + start + "', got EOF")
    while ( {token = rdr.peek; token != end}) {
      if (token == null) throw new Exception("expected '" + end + "', got EOF")
      ast = ast :+ readForm(rdr)
    }
    rdr.next
    ast
  }

  def readAtom(rdr: Reader): Any = {
    val token: String = rdr.next
    val re_int = """^(-?[0-9]+)$""".r
    val re_flt = """^(-?[0-9][0-9.]*)$""".r
    val re_str = """^"(.*)"$""".r
    val re_key = """^:(.*)$""".r
    token match {
      case re_int(i) => i.toLong      // integer
      case re_flt(f) => f.toDouble    // float
      case re_str(s) => parseStr(s)  // string
      case re_key(k) => "\u029e" + k  // keyword
      case "nil" => null
      case "true" => true
      case "false" => false
      case _ => Symbol(token) // symbol
    }
  }

  def parseStr(s: String): String = {
    // TODO: use re.replaceAllIn instead for single pass
    s.replace("\\\\", "\u029e")
      .replace("\\\"", "\"")
      .replace("\\n", "\n")
      .replace("\u029e", "\\")
  }
}
