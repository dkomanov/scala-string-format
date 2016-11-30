package com.komanov.stringformat

object InputArgApp extends App {

  for (a <- InputArg.values()) {
    println(s"$a: ${JavaFormats.concat(a.value1, a.value2, null).length}")
  }

}
