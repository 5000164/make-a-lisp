package library

import scala.collection.mutable

case class Env(outer: Env = null, binds: Iterator[Any] = null, exprs: Iterator[Any] = null) {
  val data: mutable.Map[Symbol, Any] = mutable.Map()
  if (binds != null && exprs != null) {
    binds.foreach(b => {
      val k = b.asInstanceOf[Symbol]
      if (k == '&) {
        data(binds.next().asInstanceOf[Symbol]) = Types._list(exprs.toSeq: _*)
      } else {
        data(k) = exprs.next()
      }
    })
  }

  def find(key: Symbol): Env = {
    if (data.contains(key)) {
      this
    } else if (outer != null) {
      outer.find(key)
    } else {
      null
    }
  }

  def set(key: Symbol, value: Any): Any = {
    data(key) = value
    value
  }

  def get(key: Symbol): Any = {
    val env = find(key)
    if (env == null) throw new Exception("'" + key.name + "' not found")
    env.data(key)
  }
}
