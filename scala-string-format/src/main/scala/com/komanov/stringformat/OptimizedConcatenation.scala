package com.komanov.stringformat

import scala.annotation.varargs

object OptimizedConcatenation {

  def concat(s1: Object, s2: Object): String = {
    concatNonNull(orNull(s1), orNull(s2))
  }

  def concat(s1: Object, s2: Object, s3: Object): String = {
    concatNonNull(orNull(s1), orNull(s2), orNull(s3))
  }

  def concat(s1: Object, s2: Object, s3: Object, s4: Object): String = {
    concatNonNull(orNull(s1), orNull(s2), orNull(s3), orNull(s4))
  }

  def concat(s1: Object, s2: Object, s3: Object, s4: Object, s5: Object): String = {
    concatNonNull(orNull(s1), orNull(s2), orNull(s3), orNull(s4), orNull(s5))
  }

  def concat(s1: Object, s2: Object, s3: Object, s4: Object, s5: Object, o6: Object): String = {
    concatNonNull(orNull(s1), orNull(s2), orNull(s3), orNull(s4), orNull(s5), orNull(o6))
  }

  @varargs
  def concatObjects(objects: Object*): String = {
    if (objects.isEmpty) {
      ""
    } else {
      val sb = new StringBuilder(objects.size * 8)
      objects.foldLeft(sb)((sb, s) => if (s != null) sb.append(s) else sb).toString
    }
  }

  def concat(s1: String, s2: String): String = {
    concatNonNull(orNull(s1), orNull(s2))
  }

  def concat(s1: String, s2: String, s3: String): String = {
    concatNonNull(orNull(s1), orNull(s2), orNull(s3))
  }

  def concat(s1: String, s2: String, s3: String, s4: String): String = {
    concatNonNull(orNull(s1), orNull(s2), orNull(s3), orNull(s4))
  }

  def concat(s1: String, s2: String, s3: String, s4: String, s5: String): String = {
    concatNonNull(orNull(s1), orNull(s2), orNull(s3), orNull(s4), orNull(s5))
  }

  def concat(s1: String, s2: String, s3: String, s4: String, s5: String, s6: String): String = {
    concatNonNull(orNull(s1), orNull(s2), orNull(s3), orNull(s4), orNull(s5), orNull(s6))
  }

  @varargs
  def concatStrings(strings: String*): String = {
    if (strings.isEmpty) {
      ""
    } else {
      val len = strings.map(s => if (s == null) 0 else s.length).sum
      val sb = new StringBuilder(len)
      strings.foldLeft(sb)((sb, s) => if (s != null) sb.append(s) else sb).toString
    }
  }

  private def concatNonNull(s1: String, s2: String): String = {
    new StringBuilder(s1.length + s2.length)
      .append(s1)
      .append(s2)
      .toString
  }

  private def concatNonNull(s1: String, s2: String, s3: String): String = {
    new StringBuilder(s1.length + s2.length + s3.length)
      .append(s1)
      .append(s2)
      .append(s3)
      .toString
  }

  private def concatNonNull(s1: String, s2: String, s3: String, s4: String): String = {
    new StringBuilder(s1.length + s2.length + s3.length + s4.length)
      .append(s1)
      .append(s2)
      .append(s3)
      .append(s4)
      .toString
  }

  private def concatNonNull(s1: String, s2: String, s3: String, s4: String, s5: String): String = {
    new StringBuilder(s1.length + s2.length + s3.length + s4.length + s5.length)
      .append(s1)
      .append(s2)
      .append(s3)
      .append(s4)
      .append(s5)
      .toString
  }

  private def concatNonNull(s1: String, s2: String, s3: String, s4: String, s5: String, s6: String): String = {
    new StringBuilder(s1.length + s2.length + s3.length + s4.length + s5.length + s6.length)
      .append(s1)
      .append(s2)
      .append(s3)
      .append(s4)
      .append(s5)
      .append(s6)
      .toString
  }

  @inline
  private def orNull(o: Object): String = if (o == null) "null" else o.toString

  @inline
  private def orNull(s: String): String = if (s == null) "null" else s

}
