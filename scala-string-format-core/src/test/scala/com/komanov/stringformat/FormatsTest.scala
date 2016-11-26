package com.komanov.stringformat

import org.specs2.mutable.SpecificationWithJUnit
import org.specs2.specification.core.Fragment

class FormatsTest extends SpecificationWithJUnit {

  val formats: List[(String, (Int, String, Object) => String)] = List(
    "javaConcat" -> JavaFormats.concat,
    "messageFormat" -> JavaFormats.messageFormat,
    "messageFormatCached" -> JavaFormats.messageFormatCached,
    "slf4j" -> JavaFormats.slf4j,
    "scalaConcat" -> ScalaFormats.concat,
    "optimizedConcat" -> ScalaFormats.optimizedConcat,
    "sInterpolator" -> ScalaFormats.sInterpolator,
    "fInterpolator" -> ScalaFormats.fInterpolator,
    "rawInterpolator" -> ScalaFormats.rawInterpolator,
    "sfiInterpolator" -> ScalaFormats.sfiInterpolator
  )

  Fragment.foreach(formats) { case (name, f) =>
    s"$name" should {
      "product the same result as JavaConcat" >> {
        f(1, "str", null) must beEqualTo(JavaFormats.concat(1, "str", null))
        f(1, null, "str") must beEqualTo(JavaFormats.concat(1, null, "str"))
      }
    }
  }

}
