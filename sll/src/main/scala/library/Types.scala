package library

import scala.collection.{Map, mutable}

class MalType

class MalList(seq: Any*) extends MalType {
  var value: List[Any] = seq.toList
  var meta: Any = null

  override def clone(): MalList = {
    val new_ml = new MalList()
    new_ml.value = value
    new_ml.meta = meta
    new_ml
  }

  def apply(idx: Int): Any = value(idx)

  def map(f: Any => Any) = new MalList(value.map(f): _*)

  def drop(cnt: Int) = new MalList(value.drop(cnt): _*)

  def :+(that: Any) = new MalList((value :+ that): _*)

  def +:(that: Any) = new MalList((that +: value): _*)

  override def toString() = {
    "(" + value.map(Printer._pr_str(_, true)).mkString(" ") + ")"
  }

  def toString(print_readably: Boolean) = {
    "(" + value.map(Printer._pr_str(_, print_readably)).mkString(" ") + ")"
  }
}

class MalVector(seq: Any*) extends MalList(seq: _*) {
  override def clone() = {
    val new_mv = new MalVector()
    new_mv.value = value
    new_mv.meta = meta
    new_mv
  }

  override def map(f: Any => Any) = new MalVector(value.map(f): _*)

  override def drop(cnt: Int) = new MalVector(value.drop(cnt): _*)

  override def toString() = {
    "[" + value.map(Printer._pr_str(_, true)).mkString(" ") + "]"
  }

  override def toString(print_readably: Boolean) = {
    "[" + value.map(Printer._pr_str(_, print_readably)).mkString(" ") + "]"
  }
}

class MalHashMap(seq: Any*) extends MalType {
  var value: Map[String, Any] = seq.toList.grouped(2).map(
    (kv: List[Any]) => (kv(0).asInstanceOf[String], kv(1))).toMap
  var meta: Any = null

  override def clone(): MalHashMap = {
    val new_hm = new MalHashMap()
    new_hm.value = value
    new_hm.meta = meta
    new_hm
  }

  def keys(): MalList = new MalList(value.keys.toSeq: _*)

  def vals(): MalList = new MalList(value.values.toSeq: _*)

  def apply(key: String): Any = value(key)

  def map(f: ((String, Any)) => (String, Any)) = {
    val res = value.map(f).map { case (k, v) => List(k, v) }
    new MalHashMap(res.flatten.toSeq: _*)
  }

  def filterKeys(f: String => Boolean) = {
    val res = value.filterKeys(f).map { case (k, v) => List(k, v) }
    new MalHashMap(res.flatten.toSeq: _*)
  }

  def ++(that: MalHashMap) = {
    val new_hm = clone()
    new_hm.value ++= that.value
    new_hm
  }

  override def toString() = {
    var res = mutable.MutableList[Any]()
    for ((k, v) <- value) {
      res += Printer._pr_str(k, true)
      res += Printer._pr_str(v, true)
    }
    "{" + res.mkString(" ") + "}"
  }

  def toString(print_readably: Boolean) = {
    var res = mutable.MutableList[Any]()
    for ((k, v) <- value) {
      res += Printer._pr_str(k, print_readably)
      res += Printer._pr_str(v, print_readably)
    }
    "{" + res.mkString(" ") + "}"
  }
}

class MalFunction(_ast: Any, _env: Env, _params: MalList, fn: ((List[Any]) => Any)) extends MalType {
  val ast = _ast
  val env = _env
  val params = _params
  var ismacro = false
  var meta: Any = null

  override def clone(): MalFunction = {
    val new_fn = new MalFunction(ast, env, params, fn)
    new_fn.ismacro = ismacro
    new_fn.meta = meta
    new_fn
  }

  def apply(args: List[Any]): Any = fn(args)

  def gen_env(args: List[Any]): Env = {
    return new Env(env, params.value.iterator, args.iterator)
  }
}

class Atom(_value: Any) extends MalType {
  var value = _value
}

object Types {
  def _list(seq: Any*) = {
    new MalList(seq: _*)
  }

  def _vector(seq: Any*) = {
    new MalVector(seq: _*)
  }

  def _hash_map(seq: Any*) = {
    new MalHashMap(seq: _*)
  }
}
