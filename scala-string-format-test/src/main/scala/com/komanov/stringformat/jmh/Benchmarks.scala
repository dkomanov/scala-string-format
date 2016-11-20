package com.komanov.stringformat.jmh

import java.text.MessageFormat
import java.util.concurrent.TimeUnit

import com.komanov.stringformat.OptimizedConcatenation
import org.openjdk.jmh.annotations._
import org.slf4j.helpers.MessageFormatter

@State(Scope.Benchmark)
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(value = 1, jvmArgs = Array("-Xmx2G"))
@Measurement(iterations = 2, time = 3, timeUnit = TimeUnit.SECONDS)
@Warmup(iterations = 2, time = 2, timeUnit = TimeUnit.SECONDS)
abstract class BenchmarkBase

class ManyParamsBenchmark extends BenchmarkBase {

  @Param(Array("1", "10000"))
  var value1: java.lang.Integer = 1
  @Param(Array("", "string__10", "string___________________________________________________________________________________________100"))
  var value2: String = ""

  var nullObject: Object = null

  private val messageFormatInstance = new MessageFormat("{0}a{1}b{2}{3}")

  @Benchmark
  def scalaConcat(): String = {
    value1 + "a" + value2 + "b" + value2 + nullObject
  }

  @Benchmark
  def messageFormat(): String = {
    MessageFormat.format("{0}a{1}b{2}{3}", Array[Object](value1, value2, value2, nullObject): _*)
  }

  @Benchmark
  def messageFormatCached(): String = {
    messageFormatInstance.format(Array(value1, value2, value2, nullObject))
  }

  @Benchmark
  def slf4j(): String = {
    MessageFormatter.arrayFormat("{}a{}b{}{}", Array(value1, value2, value2, nullObject)).getMessage
  }

  @Benchmark
  def concatOptimized(): String = {
    OptimizedConcatenation.concat(value1, "a", value2, "b", value2, nullObject)
  }

  @Benchmark
  def sInterpolator(): String = {
    s"${value1}a${value2}b${value2}${nullObject}"
  }

  @Benchmark
  def fInterpolator(): String = {
    f"${value1}a${value2}b${value2}${nullObject}"
  }

}
