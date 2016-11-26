package com.komanov.stringformat

import com.komanov.stringformat.macros.MacroConcat._

object ScalaFormats {

  def concat(value1: Int, value2: String, nullObject: Object): String = {
    value1 + "a" + value2 + "b" + value2 + nullObject
  }

  def optimizedConcat(value1: Int, value2: String, nullObject: Object): String = {
    OptimizedConcatenation.concat(Int.box(value1), "a", value2, "b", value2, nullObject)
  }

  def sInterpolator(value1: Int, value2: String, nullObject: Object): String = {
    s"${value1}a${value2}b$value2$nullObject"
  }

  def fInterpolator(value1: Int, value2: String, nullObject: Object): String = {
    f"${value1}a${value2}b$value2$nullObject"
  }

  def rawInterpolator(value1: Int, value2: String, nullObject: Object): String = {
    raw"${value1}a${value2}b$value2$nullObject"
  }

  def sfiInterpolator(value1: Int, value2: String, nullObject: Object): String = {
    sfi"${value1}a${value2}b$value2$nullObject"
  }

}
